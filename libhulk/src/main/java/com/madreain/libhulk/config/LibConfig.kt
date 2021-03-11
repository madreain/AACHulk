package com.madreain.libhulk.config

import android.app.Application
import com.madreain.libhulk.network.NetHelper
import com.madreain.libhulk.network.interceptors.result.ErrorInterceptor
import com.madreain.libhulk.utils.Utils
import okhttp3.Interceptor


/**
 * 需在application中配置
 */
object LibConfig {

    //服务地址
    private var baseUrl: String? = null
    private var mockUrl: String? = null

    //returnCode 正常态的值 真对不同接口返回支持单正常态值的返回，也支持增删改查不同正常态值的返回
    private var retSuccess: Int = 0

    //oKHttp拦截器
    private var okHttpInterceptors: MutableList<Interceptor>? = null

    //接口返回ReturnCode不是正常态拦截器
    private var retCodeInterceptors: MutableList<ErrorInterceptor>? = null

    private var configBuilder: ConfigBuilder? = null

    fun getBaseUrl(): String {
        return baseUrl ?: throw RuntimeException("baseUrl is null")
    }

    fun getMockUrl(): String {
        return mockUrl ?: throw RuntimeException("mockUrl is null")
    }

    fun getRetSuccess(): Int {
        return retSuccess
    }

    fun getOkHttpInterceptors(): List<Interceptor>? {
        return okHttpInterceptors
    }

    fun getRetCodeInterceptors(): List<ErrorInterceptor>? {
        return retCodeInterceptors
    }

    @Synchronized
    fun builder(): ConfigBuilder {
        if (configBuilder == null) {
            configBuilder = ConfigBuilder()
        }
        return configBuilder as ConfigBuilder
    }

    class ConfigBuilder {

        fun setBaseUrl(baseUrl: String?): ConfigBuilder {
            LibConfig.baseUrl = baseUrl
            return this
        }

        fun setMockUrl(mockUrl: String?): ConfigBuilder {
            LibConfig.mockUrl = mockUrl
            return this
        }

        fun setRetSuccess(retSuccess: Int): ConfigBuilder {
            LibConfig.retSuccess = retSuccess
            return this
        }

        fun addOkHttpInterceptor(okHttpInterceptor: Interceptor): ConfigBuilder {
            if (okHttpInterceptors == null) {
                okHttpInterceptors = ArrayList()
            }
            okHttpInterceptors?.add(okHttpInterceptor)
            return this
        }

        fun addRetCodeInterceptors(errorInterceptor: ErrorInterceptor): ConfigBuilder {
            if (retCodeInterceptors == null) {
                retCodeInterceptors = ArrayList()
            }
            retCodeInterceptors?.add(errorInterceptor)
            return this
        }

        /**
         * 封装框架中的相关初始化
         */
        fun init(application: Application): ConfigBuilder {
            Utils.init(application)
            NetHelper.init()
            return this
        }

    }

}