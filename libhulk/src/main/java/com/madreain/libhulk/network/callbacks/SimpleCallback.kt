package com.madreain.libhulk.network.callbacks

/**
 * Callback空实现
 */
open class SimpleCallback<T> : Callback<T> {
    override fun onStart() {}
    override fun onSuccess(data: T?) {}
    override fun onError(error: Throwable) {}
    override fun onComplete() {}
}