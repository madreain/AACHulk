package com.madreain.libhulk.config

import com.madreain.libhulk.http.interceptor.IReturnCodeErrorInterceptor
import com.madreain.libhulk.http.interceptor.IVersionDifInterceptor
import okhttp3.Interceptor
import retrofit2.Retrofit
import java.util.*

/**
 * @author madreain
 * @date 2020/3/13.
 * module：
 * description：
 */
object HulkConfig {
    private var retrofit: Retrofit? = null
    //服务地址
    private var baseUrl: String? = null
    //returnCode 正常态的值 真对不同接口返回支持单正常态值的返回，也支持增删改查不同正常态值的返回
    private var retSuccess: String? = null
    private var retSuccessList: List<String>? = null
    //网络相关
    //日志开关
    private var logOpen = false
    //连接超时时间 单位秒
    private var connectTimeout: Long = 10
    //读超时时间
    private var readTimeout: Long = 10
    //写超时时间
    private var writeTimeout: Long = 10
    //异常处理的 相关拦截器
    //oKHttp拦截器
    private var okHttpInterceptors: MutableList<Interceptor>? = null
    //接口返回ReturnCode不是正常态拦截器
    private var retCodeInterceptors: MutableList<IReturnCodeErrorInterceptor>? = null
    //服务端版本和本地版本不一致拦截器
    private var versionDifInterceptors: MutableList<IVersionDifInterceptor>? = null
    //是否开启缓存
    private var cacheOpen = false
    private var configBuilder: ConfigBuilder? = null
    @Synchronized
    fun builder(): ConfigBuilder? {
        if (configBuilder == null) {
            configBuilder = ConfigBuilder()
        }
        return configBuilder
    }

    /**
     * get set方法
     *
     * @return
     */
    fun getRetrofit(): Retrofit? {
        return retrofit
    }

    fun setRetrofit(retrofit: Retrofit?) {
        HulkConfig.retrofit = retrofit
    }

    fun getBaseUrl(): String? {
        return baseUrl
    }

    fun setBaseUrl(baseUrl: String?) {
        HulkConfig.baseUrl = baseUrl
    }

    fun getRetSuccess(): String? {
        return retSuccess
    }

    fun setRetSuccess(retSuccess: String?) {
        HulkConfig.retSuccess = retSuccess
    }

    fun getRetSuccessList(): List<String>? {
        return retSuccessList
    }

    fun setRetSuccessList(retSuccessList: List<String>?) {
        HulkConfig.retSuccessList = retSuccessList
    }

    fun isLogOpen(): Boolean {
        return logOpen
    }

    fun setLogOpen(logOpen: Boolean) {
        HulkConfig.logOpen = logOpen
    }

    fun getConnectTimeout(): Long {
        return connectTimeout
    }

    fun setConnectTimeout(connectTimeout: Long) {
        HulkConfig.connectTimeout = connectTimeout
    }

    fun getReadTimeout(): Long {
        return readTimeout
    }

    fun setReadTimeout(readTimeout: Long) {
        HulkConfig.readTimeout = readTimeout
    }

    fun getWriteTimeout(): Long {
        return writeTimeout
    }

    fun setWriteTimeout(writeTimeout: Long) {
        HulkConfig.writeTimeout = writeTimeout
    }

    fun getOkHttpInterceptors(): List<Interceptor>? {
        return okHttpInterceptors
    }

    fun setOkHttpInterceptors(okHttpInterceptors: MutableList<Interceptor>?) {
        HulkConfig.okHttpInterceptors = okHttpInterceptors
    }

    fun getRetCodeInterceptors(): List<IReturnCodeErrorInterceptor> {
        return retCodeInterceptors!!
    }

    fun setRetCodeInterceptors(retCodeInterceptors: MutableList<IReturnCodeErrorInterceptor>?) {
        HulkConfig.retCodeInterceptors = retCodeInterceptors
    }

    fun getVersionDifInterceptors(): List<IVersionDifInterceptor>? {
        return versionDifInterceptors
    }

    fun setVersionDifInterceptors(versionDifInterceptors: MutableList<IVersionDifInterceptor>?) {
        HulkConfig.versionDifInterceptors = versionDifInterceptors
    }

    fun isCacheOpen(): Boolean {
        return cacheOpen
    }

    fun setCacheOpen(cacheOpen: Boolean) {
        HulkConfig.cacheOpen = cacheOpen
    }

    fun getConfigBuilder(): ConfigBuilder? {
        return configBuilder
    }

    fun setConfigBuilder(configBuilder: ConfigBuilder?) {
        HulkConfig.configBuilder = configBuilder
    }

    class ConfigBuilder {
        fun setRetrofit(retrofit: Retrofit?): ConfigBuilder {
            HulkConfig.retrofit = retrofit
            return this
        }

        fun setBaseUrl(baseUrl: String?): ConfigBuilder {
            HulkConfig.baseUrl = baseUrl
            return this
        }

        fun setRetSuccess(retSuccess: String?): ConfigBuilder {
            HulkConfig.retSuccess = retSuccess
            return this
        }

        fun setRetSuccessList(retSuccessList: List<String>?): ConfigBuilder {
            HulkConfig.retSuccessList = retSuccessList
            return this
        }

        fun setRetSuccessList(retSuccessList: String): ConfigBuilder {
            HulkConfig.retSuccessList =
                Arrays.asList(*retSuccessList.split(",").toTypedArray())
            return this
        }

        fun setLogOpen(logOpen: Boolean): ConfigBuilder {
            HulkConfig.logOpen = logOpen
            return this
        }

        fun setConnectTimeout(connectTimeout: Long): ConfigBuilder {
            HulkConfig.connectTimeout = connectTimeout
            return this
        }

        fun setReadTimeout(readTimeout: Long): ConfigBuilder {
            HulkConfig.readTimeout = readTimeout
            return this
        }

        fun setWriteTimeout(writeTimeout: Long): ConfigBuilder {
            HulkConfig.writeTimeout = writeTimeout
            return this
        }

        fun addOkHttpInterceptor(okHttpInterceptor: Interceptor): ConfigBuilder {
            if (okHttpInterceptors == null) {
                okHttpInterceptors = ArrayList()
            }
            okHttpInterceptors!!.add(okHttpInterceptor)
            return this
        }

        fun addOkHttpInterceptor(
            mSwitch: Boolean,
            interceptor: Interceptor
        ): ConfigBuilder {
            if (mSwitch) if (okHttpInterceptors == null) {
                okHttpInterceptors = ArrayList()
            }
            okHttpInterceptors!!.add(interceptor)
            return this
        }

        fun addRetCodeInterceptors(retCodeInterceptor: IReturnCodeErrorInterceptor): ConfigBuilder {
            if (retCodeInterceptors == null) {
                retCodeInterceptors = ArrayList()
            }
            retCodeInterceptors!!.add(retCodeInterceptor)
            return this
        }

        fun addVersionDifInterceptors(versionDifInterceptor: IVersionDifInterceptor): ConfigBuilder {
            if (versionDifInterceptors == null) {
                versionDifInterceptors = ArrayList()
            }
            versionDifInterceptors!!.add(versionDifInterceptor)
            return this
        }

        fun setCacheOpen(cacheOpen: Boolean): ConfigBuilder {
            HulkConfig.cacheOpen = cacheOpen
            return this
        }

        fun setConfigBuilder(configBuilder: ConfigBuilder?): ConfigBuilder {
            HulkConfig.configBuilder = configBuilder
            return this
        }

        fun build() {}
    }
}