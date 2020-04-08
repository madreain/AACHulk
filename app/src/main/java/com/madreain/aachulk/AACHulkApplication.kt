package com.madreain.aachulk

import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.madreain.aachulk.interceptor.RequestHeaderInterceptor
import com.madreain.aachulk.interceptor.SessionInterceptor
import com.madreain.libhulk.base.HulkApplication
import com.madreain.libhulk.config.HulkConfig
import com.madreain.libhulk.http.ApiClient
import com.madreain.libhulk.utils.Utils
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import okhttp3.logging.HttpLoggingInterceptor

/**
 * @author madreain
 * @date 2020/3/16.
 * module：
 * description：
 */
class AACHulkApplication : HulkApplication() {


    override fun initHulkConfig() { //配置项
        HulkConfig.builder() //这里只需要选择设置一个
            .setRetSuccess(BuildConfig.CODE_SUCCESS)
            .setRetSuccessList(BuildConfig.CODELIST_SUCCESS)
            .setBaseUrl(BuildConfig.BASE_URL)
            .setLogOpen(BuildConfig.OPEN_LOG)
            .setArouterOpen(BuildConfig.OPEN_AROUTER)
            .setEventBusOpen(BuildConfig.OPEN_EVENTBUS)
            .addOkHttpInterceptor(RequestHeaderInterceptor()) //请求头拦截器
            .addOkHttpInterceptor(
                BuildConfig.OPEN_LOG,
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            ) //okhttp请求日志开关+消息拦截器.md
            .addRetCodeInterceptors(SessionInterceptor()) // returnCode非正常态拦截器
            .setRetrofit(
                ApiClient.instance!!.getRetrofit(
                    ApiClient.instance!!.getOkHttpClient(
                        HulkConfig.getOkHttpInterceptors()
                    )
                )
            )
            .build()
        //application 上下文
        Utils.init(this)
        //ARouter的初始化
        initArouter()
        //SmartRefreshLayout的统一设置
        initSmartRefreshLayout()
    }

    /**
     * ARouter的初始化
     */
    private fun initArouter() {
        //测试环境
        if (BuildConfig.OPEN_LOG) {
            ARouter.openLog() // 打印日志
            ARouter.openDebug() // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this)
    }

    /**
     * SmartRefreshLayout的统一设置
     */
    private fun initSmartRefreshLayout() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator({ context: Context, refreshLayout: RefreshLayout ->
            //            refreshLayout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)
            return@setDefaultRefreshHeaderCreator MaterialHeader(context)
        })

        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator({ context: Context, refreshLayout: RefreshLayout ->
            return@setDefaultRefreshFooterCreator ClassicsFooter(context).setDrawableSize(20f)
        })
    }

}