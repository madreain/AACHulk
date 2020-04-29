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
data class WanAndroidBaseRes<T>(
    val errorMsg: String,
    val errorCode: String,
    val `data`: T,
    val version: String
) : IRes<T> {
    override fun getHulkMsg() = errorMsg
    override fun getHulkCode() = errorCode
    override fun getHulkResult() = data
    override fun getHulkVersion() = version
}