package com.madreain.libhulk.network

import com.madreain.libhulk.components.base.IPage
import com.madreain.libhulk.config.LibConfig
import com.madreain.libhulk.network.callbacks.*
import com.madreain.libhulk.network.exception.NetworkException
import com.madreain.libhulk.network.exception.ReturnCodeException
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object NetHelper {

    private lateinit var retrofit: Retrofit

    /**
     * 初始化，在application中初始化
     */
    fun init() {
        val builder = OkHttpClient.Builder()
        LibConfig.getOkHttpInterceptors()?.let {
            for (interceptor in it) {
                builder.addInterceptor(interceptor)
            }
        }
        val okHttpClient =
            builder.addNetworkInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
                .addNetworkInterceptor(StethoInterceptor())
                .build()
        retrofit = Retrofit.Builder()
            .baseUrl(LibConfig.getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    /**
     * retrofit通过ApiServer获取接口
     */
    fun <T> getService(service: Class<T>): T {
        return retrofit.create(service)
    }

    /**
     * 异常统一处理
     */
    @Suppress("EXPERIMENTAL_API_USAGE")
    fun <T> request(
        block: suspend CoroutineScope.() -> T?,
        onStart: () -> Unit = {},
        onSuccess: (T?) -> Unit = {},
        onError: (Throwable) -> Unit = {},
        onComplete: () -> Unit = {}
    ): Job {
        //flow会导致findViewById为空
        return GlobalScope.launch(Dispatchers.Main) {
            flow {
                emit(block())
            }.flowOn(Dispatchers.IO)
                .onStart {
                    onStart()
                }.onCompletion {
                    onComplete()
                }.catch { throwable ->
                    if (throwable is ReturnCodeException) {
                        LibConfig.getRetCodeInterceptors()?.forEach {
                            if (it.code == throwable.code) {
                                val interrupt = it.interceptor(throwable)
                                if (interrupt) {//如果拦截器返回为true，则不继续之后的错误处理
                                    return@catch
                                }
                            }
                        }
                    }
                    if (throwable is UnknownHostException || throwable is ConnectException
                        || throwable is SocketTimeoutException
                    ) {
                        onError(NetworkException())
                    } else {
                        onError(throwable)
                    }
                }
                .collect { res ->
                    onSuccess(res)
                }
        }
    }


    /**
     * 异常统一处理
     */
    @Suppress("EXPERIMENTAL_API_USAGE")
    fun <T> request(
        block: suspend CoroutineScope.() -> T?,
        onSuccess: (T?) -> Unit = {},
        onError: (Throwable) -> Unit = {},
        vararg callbacks: Callback<T>
    ): Job {
        return request(block, onStart = {
            callbacks.forEach {
                it.onStart()
            }
        }, onSuccess = { res ->
            onSuccess(res)
            callbacks.forEach {
                it.onSuccess(res)
            }
        }, onError = { throwable ->
            onError(throwable)
            val error: Throwable =
                if (throwable is UnknownHostException || throwable is ConnectException
                    || throwable is SocketTimeoutException
                ) NetworkException() else throwable
            callbacks.forEach {
                it.onError(error)
            }
        }, onComplete = {
            callbacks.forEach {
                it.onComplete()
            }
        })
    }


    /**
     * 异常统一处理
     */
    @Suppress("EXPERIMENTAL_API_USAGE")
    fun <T> request(
        page: IPage,
        block: suspend CoroutineScope.() -> T?,
        onSuccess: (T?) -> Unit = {},
        onError: (Throwable) -> Unit = {},
        toast: Boolean = true,
        load: Boolean = false,
        wait: Boolean = false
    ): Job {
        val callbacks = ArrayList<Callback<T>>()
        if (toast) {
            callbacks.add(ToastCallback())
        }
        if (load) {
            callbacks.add(LoadCallback(page) {
                request(page, block, onSuccess, onError, toast, load, wait)
            })
        }
        if (wait) {
            callbacks.add(WaitCallback(page))
        }

        val callbacksArray = callbacks.toTypedArray()
        return request(block, onSuccess, onError, *callbacksArray)
    }
}
