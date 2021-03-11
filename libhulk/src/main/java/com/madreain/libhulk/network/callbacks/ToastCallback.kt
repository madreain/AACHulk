package com.madreain.libhulk.network.callbacks


/**
 * 吐司动作
 * 请求失败时，吐司错误原因
 */
class ToastCallback<T> : Callback<T> {
    override fun onStart() {

    }

    override fun onSuccess(data: T?) {
    }

    override fun onError(error: Throwable) {
        // TODO: 2021/2/9 出错的统一处理
//        Utils.toast(error.message)
    }

    override fun onComplete() {
    }
}