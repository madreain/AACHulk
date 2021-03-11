package com.madreain.libhulk.components.base

import android.content.Context


/**
 * 由页面实现，包括activity、fragment、view
 * 这里不使用applicationContext，是由于dialog所需的context无法使用applicationContext
 */
interface IPageContext {

    /**
     * 获取Context，有别于applicationContext
     */
    fun getDialogContext(): Context?
}