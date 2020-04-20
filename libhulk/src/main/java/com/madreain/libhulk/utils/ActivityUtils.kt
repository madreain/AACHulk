package com.madreain.libhulk.utils

import android.app.Activity
import java.util.*

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：activity 管理工具类
 */
class ActivityUtils {
    /**
     * 获取activityStack
     *
     * @return
     */
    var activityStack: Stack<Activity>? = null
        get() {
            if (field == null) {
                field = Stack()
            }
            return field
        }
        private set

    /**
     * 添加
     *
     * @param activity
     */
    fun addActivity(activity: Activity) {
        activityStack?.add(activity)
    }

    /**
     * 移除
     *
     * @param activity
     */
    fun remove(activity: Activity?) {
        activityStack?.remove(activity)
    }

    /**
     * 关闭指定界面
     *
     * @param activity 要关闭的Activity
     */
    fun finish(activity: Activity?) {
        if (activity != null) {
            activity.finish()
            activityStack?.remove(activity)
        }
    }

    fun isActivityExist(clz: Class<out Activity?>): Boolean {
        return activityStack!!.contains(getActivity(clz))
    }

    val isEmpty: Boolean
        get() = activityStack!!.isEmpty()

    /**
     * 关闭指定界面
     *
     * @param clz 要关闭的Activity.class
     */
    fun finish(clz: Class<out Activity?>) {
        finish(getActivity(clz))
    }

    fun finishAll() {
        while (activityStack?.size != 0) {
            finish(activityStack?.peek())
        }
        //        MobclickAgent.onKillProcess(Utils.getContext());
    }

    /**
     * 结束其他Activity
     *
     * @param clz 当前Activity.class 不需要finish
     */
    fun finishOthers(clz: Class<out Activity?>) {
        val activity = getActivity(clz)
        finishOthers(activity)
    }

    /**
     * 结束其他Activity
     *
     * @param activity 当前Activity 不需要finish
     */
    fun finishOthers(activity: Activity?) {
        for (a in activityStack!!) {
            if (activity !== a && a != null) {
                a.finish()
            }
        }
        activityStack?.clear()
        activityStack?.add(activity)
    }

    /**
     * 根据className 获取Activity对象
     *
     * @param clz Class
     */
    fun getActivity(clz: Class<out Activity?>): Activity? {
        for (activity in activityStack!!) {
            if (activity != null && activity.javaClass == clz) {
                return activity
            }
        }
        return null
    }

    /**
     * 回退到指定的Activity.class
     *
     * @param clz Activity.class
     */
    fun backTo(clz: Class<out Activity?>) {
        if (activityStack!!.size > 0) {
            var activity = activityStack?.peek()
            while (activity != null && clz != activity.javaClass) {
                activity.finish()
                activityStack?.remove(activity)
                if (activityStack!!.size > 0) {
                    activity = activityStack?.peek()
                }
            }
        }
    }

    /**
     * 判断activity是否是最顶部Activity
     *
     * @param activity 需要判断的activity
     * @return
     */
    fun isTopActivity(activity: Activity): Boolean {
        return activityStack!!.size != 0 && activityStack?.peek() === activity
    }

    companion object {
        private var activityUtils: ActivityUtils? = null
        fun get(): ActivityUtils? {
            if (activityUtils == null) {
                synchronized(ActivityUtils::class.java) {
                    if (activityUtils == null) {
                        activityUtils = ActivityUtils()
                    }
                }
            }
            return activityUtils
        }
    }
}