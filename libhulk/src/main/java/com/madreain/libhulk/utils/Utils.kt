package com.madreain.libhulk.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

object Utils {

    lateinit var context: Context
    var activity: Activity? = null

    /**
     * 在Application中初始化
     */
    fun init(app: Application) {
        context = app
    }

    fun setCurrentActivity(activity: Activity) {
        this.activity = activity
    }

    /**
     * 吐司
     */
    fun toast(text: String?) {
        if (!TextUtils.isEmpty(text)) {
            val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

    /**
     * dp转px
     */
    fun dp2px(dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 获取Activity屏幕宽度
     */
    fun getActivityWidth(activity: Activity): Int {
        val metric = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metric)
        return metric.widthPixels // 屏幕宽度（像素）
    }

    /**
     * 获取Activity屏幕高度
     */
    fun getActivityHeight(activity: Activity): Int {
        val metric = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metric)
        return metric.heightPixels // 屏幕高度（像素）
    }

    /**
     * 获取statusBar高度
     */
    fun getStatusBarHeight(): Int {
        val result: Int
        val resourceId =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        } else {
            result = dp2px(25f)
        }
        return result
    }

    /**
     * 获取颜色值
     */
    fun getColor(@ColorRes id: Int): Int {

        return ContextCompat.getColor(context, id)
    }

    /**
     * 获取图片
     */
    fun getDrawable(@ColorRes id: Int): Drawable? {
        return ContextCompat.getDrawable(context, id)
    }
}