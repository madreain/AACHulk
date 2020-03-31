package com.madreain.libhulk.view

import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.madreain.hulk.R

/**
 * @author madreain
 * @date 2020/3/13.
 * module：变化view的默认辅助控制类（如果不使用默认的可实现IVaryViewHelperController自定义）
 * description：View替换：loading显示隐藏、无数据的展示view、网络异常的view、简单自定义的view
 */
class VaryViewHelperController private constructor(private val helper: VaryViewHelper) :
    IVaryViewHelperController {
    //是否已经调用过restore方法
    private var hasRestore: Boolean = false

    constructor(replaceView: View) : this(VaryViewHelper(replaceView)) {}

    override fun showLoading() {
        hasRestore = false
        val layout = helper.inflate(R.layout.hulk_page_loading)
        helper.showView(layout)
    }

    override fun showLoading(msg: String?) {
        hasRestore = false
        val layout = helper.inflate(R.layout.hulk_page_loading)
        val tv_msg = layout.findViewById<TextView>(R.id.tv_msg)
        tv_msg.text = msg
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
        listener: View.OnClickListener?
    ) {
        hasRestore = false
        val layout = helper.inflate(R.layout.hulk_page_no_data_click)
        val againBtn =
            layout.findViewById<Button>(R.id.pager_error_loadingAgain)
        val textView = layout.findViewById<TextView>(R.id.tv_no_data)
        if (!TextUtils.isEmpty(emptyMsg)) {
            textView.text = emptyMsg
        }
        if (null != listener) {
            againBtn.setOnClickListener(listener)
            //            againBtn.setVisibility(View.VISIBLE);
            againBtn.visibility = View.GONE //按钮都隐藏，空页面没有刷新
        } else {
            againBtn.visibility = View.GONE
        }
        helper.showView(layout)
    }

    override fun showNetworkError(listener: View.OnClickListener?) {
        showNetworkError("网络状态异常，请刷新重试", listener)
    }

    override fun showNetworkError(
        msg: String?,
        listener: View.OnClickListener?
    ) {
        hasRestore = false
        val layout = helper.inflate(R.layout.hulk_page_error)
        val againBtn =
            layout.findViewById<Button>(R.id.pager_error_loadingAgain)
        val tv_title = layout.findViewById<TextView>(R.id.tv_title)
        tv_title.visibility = View.GONE
        val tv_msg = layout.findViewById<TextView>(R.id.tv_msg)
        tv_msg.text = msg
        if (null != listener) {
            againBtn.setOnClickListener(listener)
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
    }

    override fun restore() {
        hasRestore = true
        helper.restoreView()
    }

    override val isHasRestore: Boolean
        get() = hasRestore

}