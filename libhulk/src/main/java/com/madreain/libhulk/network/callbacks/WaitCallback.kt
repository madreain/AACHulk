package com.madreain.libhulk.network.callbacks

import com.madreain.libhulk.components.base.IPageContext
import com.madreain.libhulk.components.dialog.ProgressDialog

/**
 * 等待过程中的转圈圈动作
 * 在请求开始时显示，请求结束后消失
 */
class WaitCallback<T>(private val pageContext: IPageContext) : Callback<T> {
    private var dialog: ProgressDialog? = null

    override fun onStart() {
        try {
            pageContext.getDialogContext()?.let {
                if (dialog == null) {
                    dialog = ProgressDialog(it)
                }
                if (dialog?.isShowing != true)
                    dialog?.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onSuccess(data: T?) {
    }

    override fun onError(error: Throwable) {
    }

    override fun onComplete() {
        dialog?.dismiss()
        dialog = null
    }
}