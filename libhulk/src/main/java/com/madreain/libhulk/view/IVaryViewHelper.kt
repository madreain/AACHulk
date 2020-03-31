package com.madreain.libhulk.view

import android.content.Context
import android.view.View

/**
 * @author madreain
 * @date 2020/3/13.
 * module：
 * description：
 */
interface IVaryViewHelper {
    /**
     * 当前显示的view
     * @return
     */
    val currentView: View

    /**
     * 需替换的布局view
     * @return
     */
    val replaceView: View

    /**
     * 恢复view
     */
    fun restoreView()

    /**
     * 设置要展示view
     * @param view
     */
    fun showView(view: View)

    /**
     * 设置要加载布局的layoutId
     * @param layoutId
     * @return
     */
    fun inflate(layoutId: Int): View

    /**
     * 上下文
     * @return
     */
    val context: Context
}