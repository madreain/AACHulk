package com.madreain.libhulk.components.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.madreain.libhulk.R
import com.madreain.libhulk.components.base.IPageInit
import com.madreain.libhulk.extensions.clickDelay
import kotlinx.android.synthetic.main.deault_view.view.*

class DefaultView : FrameLayout, IPageInit {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        val content = LayoutInflater.from(context).inflate(R.layout.deault_view, this, false)
        addView(content)
    }

    /**
     *
     * 显示加载
     */
    override fun showLoading() {
        visibility = View.VISIBLE
        lavLoading.visibility = View.VISIBLE
        layoutError.visibility = View.GONE
    }

    /**
     *
     * 显示加载失败
     */
    override fun showError(msg: String?, retry: (() -> Unit)?) {
        visibility = View.VISIBLE
        lavLoading.visibility = View.GONE
        tvError.text = msg ?: "请求错误"
        tvRefresh.clickDelay {
            retry?.invoke()
        }
        layoutError.visibility = View.VISIBLE
    }

    /**
     * 隐藏加载（加载失败）
     */
    override fun dismissLoadOrError() {
        visibility = View.GONE
    }
}