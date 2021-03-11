package com.madreain.libhulk.components.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter
import com.madreain.libhulk.click.ClickInject
import com.madreain.libhulk.components.view.DefaultView

abstract class BaseFragment : Fragment, IPage {

    /**
     * fragment根布局
     */
    private val rootFrameLayout: FrameLayout? = null

    /**
     * 用来处理页面初始化加载、加载失败等功能
     * 通过代理模式，实现延迟初始化
     */
    private val pageInitView by lazy {
        buildPageInit()
    }

    constructor() : super()
    constructor(contentLayoutId: Int) : super(contentLayoutId)

    /**
     * 创建一个rootFrameLayout的根布局，实际视图放在根布局上
     * 这样操作，可以使InitView 附着在rootFrameLayout之上
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val rootView = FrameLayout(requireContext())
        rootView.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        rootView.addView(
            view,
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ClickInject.injectClickAnnotation(this)
        init(view, savedInstanceState)
        observeEventBus()
        observeLiveData()
    }

    /**
     * 初始化
     * 初始化工作在这个方法中执行，不包括点击事件注册，observe事件注册
     */
    abstract fun init(view: View, savedInstanceState: Bundle?)

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
     * @param routeHook 参数注入
     */
    fun route(
        path: String,
        routeHook: ((routeBuild: Postcard) -> Unit)? = null
    ) {
        val aRouter = ARouter.getInstance().build(path)
        routeHook?.invoke(aRouter)
        aRouter.navigation()
    }

    /**
     * 创建IPageInit，可以通过重写实现IPageInit自定义
     */
    open fun buildPageInit(): IPageInit? {
        val initView = DefaultView(requireContext())
        rootFrameLayout?.addView(
            initView,
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        return initView
    }

    /**
     * IPageContext实现，具体请看IpageContext
     */
    override fun getDialogContext(): Context? {
        return context
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
}