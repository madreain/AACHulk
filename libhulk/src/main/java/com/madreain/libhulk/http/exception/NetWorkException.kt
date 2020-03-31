package com.madreain.libhulk.http.exception

/**
 * @author madreain
 * @date 2020/3/13.
 * module： 自定义网络错误异常
 * description：
 */
class NetWorkException : Exception {
    /**
     * 网络错误
     */
    constructor() : super() {}

    /**
     * 网络错误传入错误内容
     * @param msg
     */
    constructor(msg: String?) : super(msg) {}
}