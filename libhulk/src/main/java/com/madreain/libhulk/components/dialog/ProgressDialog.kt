package com.madreain.libhulk.components.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import com.madreain.libhulk.R


/**
 * 网络请求点击后的等待动画
 */
class ProgressDialog(context: Context) : Dialog(context, R.style.ProgressDialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_progress)
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        val params = window?.attributes
        params?.width = ViewGroup.LayoutParams.WRAP_CONTENT
        params?.height = ViewGroup.LayoutParams.WRAP_CONTENT

        window?.attributes = params
        window?.setGravity(Gravity.CENTER)
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        )
    }

    override fun show() {
        super.show()
        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        progressBar.indeterminateDrawable.clearColorFilter()
        //优化显示效果
        progressBar.alpha = 0f
        progressBar.clearAnimation()
        progressBar.animate().alpha(1f).setStartDelay(300).setDuration(250).start()
    }
}