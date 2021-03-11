package com.madreain.aachulk.interceptor

import com.madreain.libhulk.network.interceptors.result.ErrorInterceptor

/**
 * @author madreain
 * @date 2019/3/4.
 * module：
 * description：returnCode返回session_100 拦截处理
 */
class SessionInterceptor : ErrorInterceptor(-100) {
    override fun interceptor(throwable: Throwable): Boolean {
        // TODO: 2021/3/9 互踢的操作
        return true
    }


}