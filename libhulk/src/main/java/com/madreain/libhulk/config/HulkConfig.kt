package com.madreain.libhulk.config

import com.madreain.libhulk.http.interceptor.IReturnCodeErrorInterceptor
import com.madreain.libhulk.http.interceptor.IVersionDifInterceptor
import com.madreain.libhulk.utils.LogUtils
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import retrofit2.Retrofit
import java.lang.RuntimeException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

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
    //是否开启arouter
    private var arouterOpen = true
    //设置多个BaseUrl，配合默认的DOMAIN_NAME
    private var mDomainNameHub: HashMap<String, HttpUrl>? = null
    private var isMoreBaseUrl = false
    //设置默认的DOMAIN_NAME
    const val DOMAIN_NAME = "HULK_DOMAIN_NAME"
    const val DOMAIN_NAME_HEADER = DOMAIN_NAME + ": "
    /**
     * 如果在 Url 地址中加入此标识符, 意味着您想对此 Url 开启超级模式, 框架会将 '=' 后面的数字作为 PathSize, 来确认最终需要被超级模式替换的 BaseUrl
     */
    const val IDENTIFICATION_PATH_SIZE = "#baseurl_path_size="
    //多个baseurl就对应的不同returnCode 正常态的值
    private var retSuccessMap: HashMap<String, List<String>>? = null

    @Synchronized
    fun builder(): ConfigBuilder {
        if (configBuilder == null) {
            configBuilder = ConfigBuilder()
        }
        return configBuilder as ConfigBuilder
    }

    /**
     * get set方法
     *
     * @return
     */
    fun getRetrofit(): Retrofit {
        return retrofit ?: throw RuntimeException("retrofit is null")
    }

    fun getBaseUrl(): String {
        return baseUrl ?: throw RuntimeException("baseUrl is null")
    }

    fun getRetSuccess(): String? {
        return retSuccess
    }

    fun getRetSuccessList(): List<String> {
        return retSuccessList ?: ArrayList<String>()
    }

    fun isArouterOpen(): Boolean {
        return arouterOpen
    }

    fun getConnectTimeout(): Long {
        return connectTimeout
    }

    fun getReadTimeout(): Long {
        return readTimeout
    }

    fun getWriteTimeout(): Long {
        return writeTimeout
    }


    fun getOkHttpInterceptors(): List<Interceptor> {
        return okHttpInterceptors ?: ArrayList()
    }

    fun getRetCodeInterceptors(): List<IReturnCodeErrorInterceptor> {
        return retCodeInterceptors as MutableList<IReturnCodeErrorInterceptor>
    }

    fun getVersionDifInterceptors(): List<IVersionDifInterceptor>? {
        return versionDifInterceptors
    }

    fun isCacheOpen(): Boolean {
        return cacheOpen
    }

    fun getConfigBuilder(): ConfigBuilder? {
        return configBuilder
    }

    fun getMDomainNameHub(): HashMap<String, HttpUrl>? {
        return mDomainNameHub
    }


    fun getMoreBaseUrl(): Boolean {
        return isMoreBaseUrl
    }

    fun getDomainName(): String {
        return DOMAIN_NAME
    }

    fun getDomainNameHeader(): String {
        return DOMAIN_NAME_HEADER
    }

    fun getRetSuccessMap(): HashMap<String, List<String>>? {
        return retSuccessMap
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

        fun setArouterOpen(arouterOpen: Boolean): ConfigBuilder {
            HulkConfig.arouterOpen = arouterOpen
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
            okHttpInterceptors?.add(okHttpInterceptor)
            return this
        }

        fun addOkHttpInterceptor(
            mSwitch: Boolean,
            interceptor: Interceptor
        ): ConfigBuilder {
            if (mSwitch) if (okHttpInterceptors == null) {
                okHttpInterceptors = ArrayList()
            }
            okHttpInterceptors?.add(interceptor)
            return this
        }

        fun addRetCodeInterceptors(retCodeInterceptor: IReturnCodeErrorInterceptor): ConfigBuilder {
            if (retCodeInterceptors == null) {
                retCodeInterceptors = ArrayList()
            }
            retCodeInterceptors?.add(retCodeInterceptor)
            return this
        }

        fun addVersionDifInterceptors(versionDifInterceptor: IVersionDifInterceptor): ConfigBuilder {
            if (versionDifInterceptors == null) {
                versionDifInterceptors = ArrayList()
            }
            versionDifInterceptors?.add(versionDifInterceptor)
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

        fun addDomain(domainName: String, domainUrl: String): ConfigBuilder {
            if (mDomainNameHub == null) {
                mDomainNameHub = HashMap()
            }
            domainUrl.toHttpUrlOrNull()?.let { mDomainNameHub?.put(domainName, it) }
            //设置为多baseurl
            isMoreBaseUrl = true
            return this
        }

        //        fun getRetSuccessMap(): HashMap<String, List<String>> {
//            return retSuccessMap
//        }
        fun addRetSuccess(domainNameKey: String, retSuccess: String): ConfigBuilder {
            if (retSuccessMap == null) {
                retSuccessMap = HashMap()
            }
            val retSuccessList =
                Arrays.asList(*retSuccess.split(",").toTypedArray())
            retSuccessMap?.put(domainNameKey, retSuccessList)
            return this
        }

        fun build() {}
    }
}