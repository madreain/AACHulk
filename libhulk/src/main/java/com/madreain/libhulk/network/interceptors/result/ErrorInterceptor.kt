package com.madreain.libhulk.network.interceptors.result

/**
 * 错误码拦截器
 */
abstract class ErrorInterceptor(val code: Int) {

    /**
     * 具体的拦截实现，
     * 如果返回值为true，则中断其他的错误处理
     */
    abstract fun interceptor(throwable: Throwable): Boolean
}