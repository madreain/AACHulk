package com.madreain.libhulk.components.base

/**
 * 用来处理页面初始化加载、加载失败等功能
 */
interface IPageInit {

    /**
     * 显示初始化加载视图
     */
    fun showLoading()

    /**
     * 显示初始化加载失败视图
     */
    fun showError(msg: String?, retry: (() -> Unit)? = null)

    /**
     * 隐藏初始化加载视图
     */
    fun dismissLoadOrError()
}