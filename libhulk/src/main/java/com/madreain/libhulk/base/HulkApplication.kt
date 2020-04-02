package com.madreain.libhulk.base

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author madreain
 * @date 2020/3/16.
 * module：
 * description：
 */
abstract class HulkApplication : Application() {
    abstract fun initHulkConfig() //初始化配置参数
    override fun onCreate() {
        super.onCreate()
        initHulkConfig()
        //ARouter的初始化
        ARouter.init(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }

}