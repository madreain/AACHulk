package com.madreain.libhulk.network.callbacks

import com.madreain.libhulk.components.base.IPageInit

/**
 * 加载动作
 * 请求开始时，现实加载页面，请求结束后消失
 */
class LoadCallback<T>(private val pageInit: IPageInit, private val onRetry: () -> Unit) :
    Callback<T> {
    override fun onStart() {
        pageInit.showLoading()
    }

    override fun onSuccess(data: T?) {
        pageInit.dismissLoadOrError()
    }

    override fun onError(error: Throwable) {
        pageInit.showError(error.message, onRetry)
    }

    override fun onComplete() {
    }
}