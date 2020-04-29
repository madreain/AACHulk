package com.madreain.libhulk.mvvm

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import com.madreain.libhulk.view.IVaryViewHelperController

/**
 * @author madreain
 * @date 2020/3/13.
 * module：
 * description：
 */
interface IView : IVaryViewHelperController {
    /**
     * 显示提示文字
     * @param msg
     */
    fun showTips(msg: String)

    /**
     * 显示对话框
     * @param msg
     */
    fun showDialogProgress(msg: String)

    /**
     * 显示对话框,dismiss监听
     *
     * @param msg        消息内容
     * @param cancelable dialog能否消失
     */
    fun showDialogProgress(
        msg: String,
        cancelable: Boolean,
        onCancelListener: DialogInterface.OnCancelListener?
    )

    /**
     * 对话框消失
     */
    fun dismissDialog()

    /**
     * 吐司显示
     * @param msg
     */
    fun showToast(msg: String)

    /**
     * 吐司显示
     * @param msg
     */
    fun showToast(msg: Int)

    /**
     * 是否已经调用过restore()方法
     * @return
     */
    abstract override val isHasRestore: Boolean

    /**
     * 获取activity
     * @return
     */
    val hulkActivity: Activity?

    /**
     * 获取activity的context
     * @return
     */
    val hulkContext: Context?

    /**
     * 获取app的context
     * @return
     */
    val hulkAppContext: Context?

}