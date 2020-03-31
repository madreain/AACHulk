package com.madreain.aachulk.view

import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.madreain.aachulk.R
import com.madreain.libhulk.view.IVaryViewHelperController
import com.madreain.libhulk.view.VaryViewHelper

/**
 * @author madreain
 * @date 2020/3/31.
 * module：
 * description：View替换：loading,Empty,NetError......,这些都可以自定义去写，
 */
class MyVaryViewHelperController private constructor(private val helper: VaryViewHelper) :
    IVaryViewHelperController {

    //是否已经调用过restore方法
    private var hasRestore: Boolean = false

    constructor(replaceView: View) : this(VaryViewHelper(replaceView)) {}

    override fun showNetworkError(onClickListener: View.OnClickListener?) {
        showNetworkError("网络状态异常，请刷新重试", onClickListener)
    }

    override fun showNetworkError(
        msg: String?,
        onClickListener: View.OnClickListener?
    ) {
        hasRestore = false
        val layout = helper.inflate(R.layout.hulk_page_error)
        val againBtn =
            layout.findViewById<Button>(R.id.pager_error_loadingAgain)
        val tv_title = layout.findViewById<TextView>(R.id.tv_title)
        tv_title.visibility = View.GONE
        val tv_msg = layout.findViewById<TextView>(R.id.tv_msg)
        tv_msg.text = msg
        if (null != onClickListener) {
            againBtn.setOnClickListener(onClickListener)
        }
        helper.showView(layout)
    }

    override fun showCustomView(
        drawableInt: Int,
        title: String?,
        msg: String?,
        btnText: String?,
        listener: View.OnClickListener?
    ) {
        hasRestore = false
        val layout = helper.inflate(R.layout.hulk_page_error)
        val iv_flag =
            layout.findViewById<ImageView>(R.id.iv_flag)
        val tv_title = layout.findViewById<TextView>(R.id.tv_title)
        val tv_msg = layout.findViewById<TextView>(R.id.tv_msg)
        val againBtn =
            layout.findViewById<Button>(R.id.pager_error_loadingAgain)
        iv_flag.setImageResource(drawableInt)
        if (TextUtils.isEmpty(title)) {
            tv_title.visibility = View.GONE
        } else {
            tv_title.visibility = View.VISIBLE
            tv_title.text = title
        }
        if (TextUtils.isEmpty(msg)) {
            tv_msg.visibility = View.GONE
        } else {
            tv_msg.visibility = View.VISIBLE
            tv_msg.text = msg
        }
        if (TextUtils.isEmpty(btnText)) {
            againBtn.visibility = View.GONE
        } else {
            againBtn.text = btnText
            if (null != listener) {
                againBtn.setOnClickListener(listener)
            }
        }
        helper.showView(layout)
    }

    override fun showEmpty(emptyMsg: String?) {
        hasRestore = false
        val layout = helper.inflate(R.layout.hulk_page_no_data)
        val textView = layout.findViewById<TextView>(R.id.tv_no_data)
        if (!TextUtils.isEmpty(emptyMsg)) {
            textView.text = emptyMsg
        }
        helper.showView(layout)
    }

    override fun showEmpty(
        emptyMsg: String?,
        onClickListener: View.OnClickListener?
    ) {
        hasRestore = false
        val layout = helper.inflate(R.layout.hulk_page_no_data_click)
        val againBtn =
            layout.findViewById<Button>(R.id.pager_error_loadingAgain)
        val textView = layout.findViewById<TextView>(R.id.tv_no_data)
        if (!TextUtils.isEmpty(emptyMsg)) {
            textView.text = emptyMsg
        }
        if (null != onClickListener) {
            againBtn.setOnClickListener(onClickListener)
            //            againBtn.setVisibility(View.VISIBLE);
            againBtn.visibility = View.GONE //按钮都隐藏，空页面没有刷新 2018.9.5
        } else {
            againBtn.visibility = View.GONE
        }
        helper.showView(layout)
    }

    override fun showLoading() {
        hasRestore = false
        val layout = helper.inflate(R.layout.view_page_loading)
        helper.showView(layout)
    }

    override fun showLoading(msg: String?) {
        hasRestore = false
        val layout = helper.inflate(R.layout.view_page_loading)
        val tv_msg = layout.findViewById<TextView>(R.id.tv_msg)
        tv_msg.text = msg
        helper.showView(layout)
    }

    override fun restore() {
        hasRestore = true
        helper.restoreView()
    }

    override val isHasRestore: Boolean
        get() = hasRestore

}