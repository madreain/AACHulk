package com.madreain.aachulk.interceptor

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * @author madreain
 * @date 2019-07-06.
 * module：
 * description：请求头拦截器,如果需要设置多baseurl得继承BaseUrlInterceptor去写
 *                                   单baseurl直接继承Interceptor去写
 */
class RequestHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(processRequest(chain.request()))
    }

    private fun processRequest(request: Request): Request {
        if (request == null) return request
        val newBuilder = request.newBuilder()
        //设置传递过来的相关的headers
        return newBuilder.headers(addHeaders()).build()
    }

    fun addHeaders(): Headers {
        return Headers.Builder()
            .add("app_id", "wpkxpsejggapivjf")
            .add("app_secret", "R3FzaHhSSXh4L2tqRzcxWFBmKzBvZz09")
            .build()
    }


}