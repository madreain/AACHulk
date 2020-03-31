package com.madreain.libhulk.http.exception

/**
 * @author madreain
 * @date 2020/3/13.
 * module：
 * description：自定义响应码异常类：api接口调用时服务器操作异常，返回异常响应码
 */
class ReturnCodeException
/**
 * 响应码异常，返回异常的响应码及其显示错误的提示
 *
 * @param returnCode
 * @param msg
 */(val returnCode: String, msg: String?) :
    Exception(msg)