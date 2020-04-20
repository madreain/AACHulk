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
class WanAndroidBaseRes<T> : IRes<T> {
    private var `data`: T? = null
    private var errorCode: String? = null
    private var errorMsg: String? = null
    private var version: String? = null

    override fun getResult(): T {
        return data!!
    }

    override fun getVersion(): String {
        return version!!
    }

    override fun getMsg(): String {
        return errorMsg!!
    }

    override fun getCode(): String {
        return errorCode!!
    }

}