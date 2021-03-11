package com.madreain.libhulk.network.interceptors.request

import com.madreain.libhulk.config.LibConfig
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.internal.EverythingIsNonNull
import java.io.IOException

/**
 * 模拟数据
 */
@EverythingIsNonNull
class MockInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val originalUrl = request.url.toString()
        val path =
            originalUrl.substring(LibConfig.getBaseUrl().length)
        return if (sMockUrls.contains(path)) {
            request =
                request.newBuilder().url("${LibConfig.getMockUrl()}$path")
                    .build()
            chain.proceed(request)
        } else {
            chain.proceed(request)
        }
    }

    companion object {
        private val sMockUrls =
            listOf(
                ""
            )
    }
}