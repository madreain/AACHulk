package com.madreain.aachulk.module.base

import androidx.annotation.Keep
import com.madreain.libhulk.mvvm.IRes

/**
 * @author madreain
 * @date 2020-03-16.
 * module：接口返回封装类
 * description：
 */
@Keep
data class BaseRes<T>(
    val msg: String,
    val code: String,
    val `data`: T,
    val version: String
) : IRes<T> {
    override fun getHulkMsg() = msg
    override fun getHulkCode() = code
    override fun getHulkResult() = data
    override fun getHulkVersion() = version
}