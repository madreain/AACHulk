package com.madreain.libhulk.network.callbacks

/**
 * 网络请求过程中的四种回调
 */
interface Callback<T> {

    /**
     * 请求开始
     */
    fun onStart()

    /**
     * 请求成功
     */
    fun onSuccess(data: T?)

    /**
     * 请求失败
     */
    fun onError(error: Throwable)

    /**
     * 请求结束
     */
    fun onComplete()
}