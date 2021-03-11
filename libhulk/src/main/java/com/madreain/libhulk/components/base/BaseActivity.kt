package com.madreain.libhulk.components.base

import android.content.Context
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter
import com.madreain.libhulk.click.ClickInject
import com.madreain.libhulk.components.view.DefaultView
import com.madreain.libhulk.utils.StatusUtils


abstract class BaseActivity : AppCompatActivity, IPage {

    /**
     * 用来处理页面初始化加载、加载失败等功能
     * 通过代理模式，实现延迟初始化
     */
    val pageInitView by lazy {
        buildPageInit()
    }

    constructor() : super()
    constructor(contentLayoutId: Int) : super(contentLayoutId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        ClickInject.injectClickAnnotation(this)
        //状态栏颜色
        StatusUtils.setLightBar(this, isLightStatusBar())
        init(savedInstanceState)
        observeLiveData()
        observeEventBus()
    }

    /**
     * 是否用浅色状态栏（黑色字体） 用深色状态栏时请重写此方法
     */
    protected open fun isLightStatusBar(): Boolean {
        return true
    }

    /**
     * 初始化
     */
    abstract fun init(savedInstanceState: Bundle?)

    /**
     * 注册liveData观察者
     */
    open fun observeLiveData() {
    }

    /**
     * 注册eventBus
     */
    open fun observeEventBus() {
    }


    /**
     * 路由跳转
     * @param path 路径
     * @param finishAfterRoute 路由后是否关闭当前页面
     * @param routeHook 参数注入
     */
    fun route(
        path: String,
        finishAfterRoute: Boolean = false,
        routeHook: ((routeBuild: Postcard) -> Unit)? = null
    ) {
        val aRouter = ARouter.getInstance().build(path)
        routeHook?.invoke(aRouter)
        aRouter.navigation()
        if (finishAfterRoute) {
            finish()
        }
    }

    /**
     * 创建IPageInit，可以通过重写实现IPageInit自定义
     */
    open fun buildPageInit(): IPageInit? {
        val initView = DefaultView(this)
        findViewById<FrameLayout>(android.R.id.content).addView(
            initView,
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        initView.dismissLoadOrError()
        return initView
    }

    /**
     * IPageContext实现，具体请看IpageContext
     */
    override fun getDialogContext(): Context? {
        return this
    }

    /**
     * IPageInit实现，具体请看IPageInit
     * 实际由pageInitView代理
     */
    override fun showLoading() {
        pageInitView?.showLoading()
    }

    /**
     * IPageInit实现，具体请看IPageInit
     * 实际由pageInitView代理
     */
    override fun showError(msg: String?, retry: (() -> Unit)?) {
        pageInitView?.showError(msg, retry)
    }

    /**
     * IPageInit实现，具体请看IPageInit
     * 实际由pageInitView代理
     */
    override fun dismissLoadOrError() {
        pageInitView?.dismissLoadOrError()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}