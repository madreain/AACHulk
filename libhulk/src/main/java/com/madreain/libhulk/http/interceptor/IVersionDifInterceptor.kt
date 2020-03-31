package com.madreain.libhulk.http.interceptor

/**
 * @author madreain
 * @date 2020/3/13.
 * module： 请求接口返回的versino版本和本地版本不一致拦截处理
 * description：
 */
interface IVersionDifInterceptor {
    /**
     * 根据返回的version，判断是否进行拦截
     *
     * @param version
     * @return
     */
    fun intercept(version: String?): Boolean

    /**
     * intercept(String returnCode)方法为true的时候调用该方法
     *
     * @param serviceVersion
     */
    fun doWork(serviceVersion: String?)
}