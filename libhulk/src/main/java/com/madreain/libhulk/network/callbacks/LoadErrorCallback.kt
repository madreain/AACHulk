package com.madreain.libhulk.network.callbacks

import android.content.Context

/**
 * 加载失败动作
 * 错误时显示加载失败页面，点击重试
 */
class LoadErrorCallback<T>(context: Context, onRetry: () -> Unit) : Callback<T> {
    override fun onStart() {
    }

    override fun onSuccess(data: T?) {
    }

    override fun onError(error: Throwable) {
    }

    override fun onComplete() {
    }
}