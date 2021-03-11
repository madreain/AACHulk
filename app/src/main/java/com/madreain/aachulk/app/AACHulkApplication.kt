package com.madreain.aachulk.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import com.facebook.stetho.Stetho
import com.madreain.aachulk.BuildConfig
import com.madreain.aachulk.interceptor.RequestHeaderInterceptor
import com.madreain.aachulk.interceptor.SessionInterceptor
import com.madreain.libhulk.config.LibConfig
import com.madreain.libhulk.network.interceptors.request.MockInterceptor

/**
 * @author madreain
 * @date 2020/3/16.
 * module：
 * description：
 */
@Suppress("ConstantConditionIf")
class AACHulkApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        LibConfig.builder()
            .setBaseUrl(BuildConfig.BASE_URL)
            .setMockUrl(BuildConfig.MOCK_URL)
            .setRetSuccess(1)
            .addOkHttpInterceptor(RequestHeaderInterceptor())
            .addOkHttpInterceptor(MockInterceptor())
            .addRetCodeInterceptors(SessionInterceptor())
            .init(this)
        ARouter.init(this)
        ARouter.openLog()
        ARouter.openDebug()
        Stetho.initializeWithDefaults(this)
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

}