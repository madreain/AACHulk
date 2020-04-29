package com.madreain.libhulk.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * @author madreain
 * @date 2019-07-04.
 * module： 吐司相关工具类
 * description：
 */
class ToastUtils private constructor() {
    companion object {
        private var sToast: Toast? = null
        private val sHandler = Handler(Looper.getMainLooper())
        private var isJumpWhenMore = true
        /**
         * 吐司初始化
         *
         * @param isJumpWhenMore 当连续弹出吐司时，是要弹出新吐司还是只修改文本内容
         *
         * `true`: 弹出新吐司<br></br>`false`: 只修改文本内容
         *
         * 如果为`false`的话可用来做显示任意时长的吐司
         */
        fun init(isJumpWhenMore: Boolean) {
            Companion.isJumpWhenMore = isJumpWhenMore
        }

        /**
         * 安全地显示短时吐司
         *
         * @param text 文本
         */
        fun showShortToastSafe(
            context: Context?,
            text: CharSequence?
        ) {
            sHandler.post {
                show(
                    context,
                    text,
                    Toast.LENGTH_SHORT
                )
            }
        }

        /**
         * @param text 文本
         */
        fun show(
            context: Context?,
            text: CharSequence?
        ) { //        show(text, Toast.LENGTH_LONG);
            showShortToastSafe(context, text)
        }

        /**
         * @param resId 资源Id
         */
        fun show(context: Context, @StringRes resId: Int) { //        show(resId, Toast.LENGTH_SHORT);
            showShortToastSafe(
                context,
                context.resources.getText(resId).toString()
            )
        }

        /**
         * @param resId 资源Id
         * @param args  参数
         */
        fun show(
            context: Context, @StringRes resId: Int,
            vararg args: Any?
        ) {
            show(context, resId, Toast.LENGTH_SHORT, *args)
        }

        /**
         * @param format 格式
         * @param args   参数
         */
        fun show(
            context: Context,
            format: String,
            vararg args: Any?
        ) {
            show(context, format, Toast.LENGTH_SHORT, *args)
        }

        /**
         * @param resId    资源Id
         * @param duration 显示时长
         */
        private fun show(
            context: Context, @StringRes resId: Int,
            duration: Int
        ) {
            show(
                context,
                context.resources.getText(resId).toString(),
                duration
            )
        }

        /**
         * @param resId    资源Id
         * @param duration 显示时长
         * @param args     参数
         */
        private fun show(
            context: Context, @StringRes resId: Int,
            duration: Int,
            vararg args: Any
        ) {
            show(
                context,
                String.format(context.resources.getString(resId), *args),
                duration
            )
        }

        /**
         * @param format   格式
         * @param duration 显示时长
         * @param args     参数
         */
        private fun show(
            context: Context,
            format: String,
            duration: Int,
            vararg args: Any
        ) {
            show(context, String.format(format, *args), duration)
        }

        /**
         * @param text     文本
         * @param duration 显示时长
         */
        @SuppressLint("ShowToast")
        private fun show(
            context: Context?,
            text: CharSequence?,
            duration: Int
        ) {
            if (context == null) return
            if (isJumpWhenMore) cancelToast()
            if (sToast == null) {
                sToast = Toast.makeText(context, text, duration)
                sToast?.setGravity(Gravity.CENTER, 0, 0)
            } else {
                sToast?.setText(text)
                sToast?.duration = duration
            }
            sToast?.show()
        }

        /**
         * 取消吐司显示
         */
        fun cancelToast() {
            if (sToast != null) {
                sToast?.cancel()
                sToast = null
            }
        }
    }

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }
}