package com.madreain.libhulk.mvvm

import android.view.View
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madreain.libhulk.config.HulkConfig
import com.madreain.libhulk.em.RequestDisplay
import com.madreain.libhulk.http.exception.NetWorkException
import com.madreain.libhulk.http.exception.ResultException
import com.madreain.libhulk.http.exception.ReturnCodeException
import com.madreain.libhulk.http.exception.ReturnCodeNullException
import com.madreain.libhulk.http.interceptor.IReturnCodeErrorInterceptor
import com.madreain.libhulk.utils.NetworkUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException
import java.lang.reflect.ParameterizedType

/**
 * @author madreain
 * @date 2020/3/13.
 * module：
 * description：
 */
abstract class BaseViewModel<API> : ViewModel(), LifecycleObserver {

    //接口类
    private var apiService: API? = null
    //网络请求展示类型
    private var type: RequestDisplay? = null
    //默认相关错误提示
    private val emptyMsg: String = "暂无数据"
    private val errorMsg: String = "网络错误"
    private val codeNullMsg: String = "未设置成功状态码"
    //重试的监听
    var listener: View.OnClickListener? = null

    /**
     * 获取接口操作类
     */
    fun getApiService(): API {
        if (apiService == null) {
            apiService = HulkConfig.getRetrofit()?.create(
                (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<API>
            )
        }
        return apiService ?: throw RuntimeException("Api service is null")
    }

    /**
     * 开始执行方法
     */
    protected abstract fun onStart()


    /**
     * 网络相关工具
     */
    val networkUtils: NetworkUtils by lazy { NetworkUtils() }

    /**
     * 视图变化
     */
    val viewChange: ViewChange by lazy { ViewChange() }

    /**
     * 所有网络请求都在 viewModelScope 域中启动，当页面销毁时会自动
     * 调用ViewModel的  #onCleared 方法取消所有协程
     */
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch { block() }

    /**
     * 用流的方式进行网络请求
     */
    fun <T> launchFlow(block: suspend () -> T): Flow<T> {
        return flow {
            emit(block())
        }
    }

    /**
     * 过滤请求结果，其他全抛异常
     * @param block 请求体
     * @param success 成功回调
     * @param error 失败回调
     * @param complete  完成回调（无论成功失败都会调用）
     * @param type RequestDisplay类型 NULL无交互  TOAST  REPLACE 替换
     * @param view
     *
     **/
    fun <T> launchOnlyresult(
        block: suspend CoroutineScope.() -> IRes<T>,
        //成功
        success: (T) -> Unit = {},
        //错误 根据错误进行不同分类
        error: (Throwable) -> Unit = {
            if (!networkUtils.isConnected()) {
                onNetWorkError({ reTry() })//没网
            } else {
                if (it is NetWorkException) {
                    onNetWorkError({ reTry() })
                } else if (it is ReturnCodeException) {
                    isIntercepted(it)
                    onReturnCodeError(
                        (it as ReturnCodeException).returnCode,
                        it.message,
                        { reTry() })
                } else if (it is ReturnCodeNullException) {
                    onNetWorkError({ reTry() }, codeNullMsg)
                } else if (it is ResultException) {
                    onTEmpty({ reTry() })
                } else {
                    onNetWorkError({ reTry() }) //UnknownHostException 1：服务器地址错误；2：网络未连接
                }
            }
        },
        //完成
        complete: () -> Unit = {},
        //重试
        reTry: () -> Unit = {},
        //当前请求的CurrentDomainName,默认的DOMAIN_NAME，也可自行设置
        currentDomainName: String = HulkConfig.DOMAIN_NAME,
        //接口操作交互类型
        type: RequestDisplay = RequestDisplay.NULL
    ) {
        //接口操作交互类型赋值
        this.type = type
        //开始请求接口前
        when (type) {
            RequestDisplay.NULL -> {
            }
            RequestDisplay.TOAST -> {
                viewChange.showDialogProgress.value = ""
            }
            RequestDisplay.REPLACE -> {
                viewChange.showLoading.call()
            }
        }
        //正式请求接口
        launchUI {
            //异常处理
            handleException(
                //调用接口
                { withContext(Dispatchers.IO) { block() } },
                { res ->
                    //接口成功返回
                    executeResponse(res, currentDomainName) {
                        success(it)
                    }
                },
                {
                    //接口失败返回
                    error(it)
                },
                {
                    //接口完成
                    complete()
                }
            )
        }
    }


    /**
     * 请求结果过滤
     */
    private suspend fun <T> executeResponse(
        response: IRes<T>,
        currentDomainName: String,
        success: suspend CoroutineScope.(T) -> Unit
    ) {
        coroutineScope {
            //多baseurl
            if (HulkConfig.getMoreBaseUrl() && currentDomainName != HulkConfig.DOMAIN_NAME) {
                //获取当前baseurl对应的成功码
                val retSuccessList =
                    HulkConfig.getRetSuccessMap()?.get(currentDomainName)
                //当前对应的baseurl对应的code
                if (retSuccessList != null) {
                    //状态码正确
                    if (retSuccessList.contains(response.getHulkCode())) {
                        //数据为空，或者list.size=0
                        if (response.getHulkResult() == null || response.getHulkResult().toString().equals(
                                "[]"
                            )
                        ) {
                            //返回结果null
                            throw ResultException(
                                response.getHulkMsg()
                            )
                        } else {
                            success(response.getHulkResult())
                            //完成的回调所有弹窗消失
                            viewChange.dismissDialog.call()
                            viewChange.restore.call()
                        }
                    } else {
                        //状态码错误
                        throw ReturnCodeException(
                            response.getHulkCode(),
                            response.getHulkMsg()
                        )
                    }
                } else {
                    //未设置状态码
                    throw ReturnCodeNullException(
                        response.getHulkCode(),
                        response.getHulkMsg()
                    )
                }
                //接口多状态码的返回 接口成功返回后判断是否是增删改查成功，不满足的话，返回异常
            } else if (HulkConfig.getRetSuccessList() != null) {
                //成功
                if (HulkConfig.getRetSuccessList().contains(response.getHulkCode())) {
                    //数据为空，或者list.size=0
                    if (response.getHulkResult() == null || response.getHulkResult().toString().equals(
                            "[]"
                        )
                    ) {
                        //返回结果null
                        throw ResultException(
                            response.getHulkMsg()
                        )
                    } else {
                        success(response.getHulkResult())
                        //完成的回调所有弹窗消失
                        viewChange.dismissDialog.call()
                        viewChange.restore.call()
                    }
                } else {
                    //状态码错误
                    throw ReturnCodeException(
                        response.getHulkCode(),
                        response.getHulkMsg()
                    )
                }
                //接口单状态码
            } else if (HulkConfig.getRetSuccess() != null) {
                //成功
                if (response.getHulkCode().equals(HulkConfig.getRetSuccess())) {
                    //数据为空，或者list.size=0
                    if (response.getHulkResult() == null || response.getHulkResult().toString().equals(
                            "[]"
                        )
                    ) {
                        //返回结果null
                        throw ResultException(
                            response.getHulkMsg()
                        )
                    } else {
                        success(response.getHulkResult())
                        //完成的回调所有弹窗消失
                        viewChange.dismissDialog.call()
                        viewChange.restore.call()
                    }
                } else {
                    //状态码错误
                    throw ReturnCodeException(
                        response.getHulkCode(),
                        response.getHulkMsg()
                    )
                }
                //未设置状态码
            } else {
                throw ReturnCodeNullException(
                    response.getHulkCode(),
                    response.getHulkMsg()
                )
            }
        }
    }

    /**
     * 异常统一处理
     */
    private suspend fun <T> handleException(
        block: suspend CoroutineScope.() -> IRes<T>,
        success: suspend CoroutineScope.(IRes<T>) -> Unit,
        error: suspend CoroutineScope.(Throwable) -> Unit,
        complete: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                success(block())
            } catch (e: Throwable) {
                error(e)
            } finally {
                complete()
            }
        }
    }


    /**
     * 数据为空
     */
    private fun onTEmpty( //重试
        reTry: () -> Unit = {
        }
    ) {
        when (type) {
            RequestDisplay.NULL -> {
            }
            RequestDisplay.TOAST -> {
                viewChange.showToast.value = emptyMsg
                viewChange.dismissDialog.call()
            }
            RequestDisplay.REPLACE -> {
                this.listener = View.OnClickListener {
                    reTry()
                }
                viewChange.showEmpty.value = emptyMsg

            }
        }
    }

    /**
     * 网络异常，状态码异常，未设置成功状态码
     */
    private fun onNetWorkError(
        reTry: () -> Unit = {
        },
        merrorMsg: String = errorMsg
    ) {
        when (type) {
            RequestDisplay.NULL -> {

            }
            RequestDisplay.TOAST -> {
                viewChange.showToast.value = merrorMsg
                viewChange.dismissDialog.call()
            }
            RequestDisplay.REPLACE -> {
                this.listener = View.OnClickListener {
                    reTry()
                }
                viewChange.showNetworkError.value = merrorMsg
            }
        }
    }

    /**
     * 返回code错误
     */
    private fun onReturnCodeError(
        returnCode: String,
        message: String?,
        reTry: () -> Unit = {
        }
    ) {
        when (type) {
            RequestDisplay.NULL -> {
            }
            RequestDisplay.TOAST -> {
                viewChange.showToast.value = message
                viewChange.dismissDialog.call()
            }
            RequestDisplay.REPLACE -> {
                this.listener = View.OnClickListener {
                    reTry()
                }
                viewChange.showNetworkError.value = message
            }
        }
    }

    /**
     * 判断是否被拦截
     */
    private fun isIntercepted(t: Throwable): Boolean {
        var isIntercepted = false //是否被拦截了
        for (interceptor: IReturnCodeErrorInterceptor in HulkConfig.getRetCodeInterceptors()) {
            if (interceptor.intercept((t as ReturnCodeException).returnCode)) {
                isIntercepted = true
                interceptor.doWork((t as ReturnCodeException).returnCode, t.message)
                break
            }
        }
        return isIntercepted
    }

}