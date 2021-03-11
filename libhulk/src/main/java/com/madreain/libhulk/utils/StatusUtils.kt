package com.madreain.libhulk.utils


import android.app.Activity
import android.view.View
import android.view.Window
import android.view.WindowManager
import java.lang.reflect.Field
import java.lang.reflect.Method

object StatusUtils {


    fun setLightBar(activity: Activity, isLightBar: Boolean) {
        lollipopStatusBarColorSet(
            activity,
            0xffffffff.toInt()
        )
//        if (isLightBar) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//            } else {
//                lollipopStatusBarColorSet(
//                    activity,
//                    0xffdddddd.toInt()
//                )
//            }
//            setMIUI6StatusBarDarkMode(
//                activity,
//                true
//            )
//        } else {
//            setMIUI6StatusBarDarkMode(
//                activity,
//                false
//            )
//        }
    }


    //小米设置黑色字符
    private fun setMIUI6StatusBarDarkMode(
        activity: Activity,
        darkmode: Boolean
    ) {
        val clazz: Class<out Window?> = activity.window.javaClass
        try {
            var darkModeFlag = 0
            val layoutParams =
                Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field: Field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            darkModeFlag = field.getInt(layoutParams)
            val extraFlagField: Method = clazz.getMethod(
                "setExtraFlags",
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType
            )
            extraFlagField.invoke(
                activity.window,
                if (darkmode) darkModeFlag else 0,
                darkModeFlag
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    public fun lollipopStatusBarColorSet(activity: Activity, statusColor: Int) {
        val window = activity.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = statusColor
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }
}