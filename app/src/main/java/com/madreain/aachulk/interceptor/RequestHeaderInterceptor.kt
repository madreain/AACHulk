package com.madreain.aachulk.interceptor

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * @author madreain
 * @date 2019-07-06.
 * module：
 * description：请求头拦截器
 */
class RequestHeaderInterceptor : Interceptor {
    //统一请求头的封装根据自身项目添加
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authorised: Request
        val headers = Headers.Builder()
            .add("app_id", "wpkxpsejggapivjf")
            .add("app_secret", "R3FzaHhSSXh4L2tqRzcxWFBmKzBvZz09")
            .build()
        authorised = request.newBuilder().headers(headers).build()
        return chain.proceed(authorised)
    }
}