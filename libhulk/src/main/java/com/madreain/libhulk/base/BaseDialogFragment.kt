package com.madreain.libhulk.base

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.launcher.ARouter
import com.androidadvance.topsnackbar.TSnackbar
import com.madreain.hulk.R
import com.madreain.libhulk.config.HulkConfig
import com.madreain.libhulk.mvvm.BaseViewModel
import com.madreain.libhulk.mvvm.IView
import com.madreain.libhulk.utils.ToastUtils
import com.madreain.libhulk.view.IVaryViewHelperController
import com.madreain.libhulk.view.VaryViewHelperController
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import java.lang.reflect.ParameterizedType

/**
 * @author madreain
 * @date 2020/3/16.
 * module：
 * description：
 */
abstract class BaseDialogFragment<VM : BaseViewModel<*>, DB : ViewDataBinding> :
    DialogFragment(),
    IView {

    //viewmodel
    protected lateinit var mViewModel: VM
    //databing
    protected var mBinding: DB? = null

    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * @return 该View 替换为显示loadingView 或者 emptyView 或者 netWorkErrorView
     */
    abstract fun getReplaceView(): View

    /**
     * 初始化
     */
    abstract fun init(savedInstanceState: Bundle?)

    /**
     * 替换view
     */
    private var viewController: IVaryViewHelperController? = null

    /**
     * 弹窗
     */
    private var dialog: ProgressDialog? = null

    /**
     * 刷新相关 因为单界面不存在加载，这样只针对是否开启刷新功能做处理，可设置为null，为null则不具备刷新相关能力
     */
    abstract fun getSmartRefreshLayout(): SmartRefreshLayout?

    private var mRefreshEnable = true //是否能进行下拉刷新

    abstract fun refreshData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val cls =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<*>
        if (ViewDataBinding::class.java != cls && ViewDataBinding::class.java.isAssignableFrom(cls)) {
            mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
            return mBinding?.root
        }
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (HulkConfig.isArouterOpen()) {
            ARouter.getInstance().inject(this)
        }
        createViewModel()
        viewController = initVaryViewHelperController()
        lifecycle.addObserver(mViewModel)
        //注册 UI事件
        registerViewChange()
        initRefresh()
        init(savedInstanceState)
    }


    /***
     * view
     */
    protected open fun initVaryViewHelperController(): IVaryViewHelperController? {
        return VaryViewHelperController(getReplaceView())
    }

    private fun initRefresh() {
        if (getSmartRefreshLayout() != null) {
            getSmartRefreshLayout()?.isEnabled = mRefreshEnable
            //不具备加载功能
            getSmartRefreshLayout()?.setEnableLoadMore(false)
            if (mRefreshEnable) {
                getSmartRefreshLayout()?.setOnRefreshListener {
                    refreshData()
                }
            }
        }
    }

    /**
     *
     *     actualTypeArguments[0]  BaseViewModel
     *     actualTypeArguments[1]  ViewDataBinding
     *
     */
    private fun createViewModel() {
        //创建viewmodel
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[0]
            val tClass = tp as? Class<VM> ?: BaseViewModel::class.java
            mViewModel = ViewModelProviders.of(this)[tClass] as VM
        }
    }

    /**
     * 注册 UI 事件
     */
    private fun registerViewChange() {
        mViewModel.viewChange.showLoading.observe(this, Observer {
            viewController?.let {
                if (!it.isHasRestore) {
                    showLoading()
                }
            }
        })
        mViewModel.viewChange.showDialogProgress.observe(this, Observer {
            showDialogProgress(it)
        })
        mViewModel.viewChange.dismissDialog.observe(this, Observer {
            dismissDialog()
        })
        mViewModel.viewChange.showToast.observe(this, Observer {
            showToast(it)
        })
        mViewModel.viewChange.showTips.observe(this, Observer {
            showTips(it)
        })
        mViewModel.viewChange.showEmpty.observe(this, Observer {
            showEmpty(it)
        })
        mViewModel.viewChange.showNetworkError.observe(this, Observer {
            showNetworkError(it, mViewModel.listener)
        })
        mViewModel.viewChange.restore.observe(this, Observer {
            viewController?.restore()
            //代表有设置刷新
            if (getSmartRefreshLayout() != null) {
                getSmartRefreshLayout()?.finishRefresh()
            }
        })
    }


    /**
     * 相关view替换
     */

    override fun showTips(msg: String) {
        activity?.let {
            val snackBar = TSnackbar.make(
                it.findViewById(android.R.id.content),
                msg,
                TSnackbar.LENGTH_SHORT
            )
            val snackBarView = snackBar.view
            snackBarView.setBackgroundColor(resources.getColor(R.color.colorAccent))
            val textView =
                snackBarView.findViewById<TextView>(com.androidadvance.topsnackbar.R.id.snackbar_text)
            textView.setTextColor(resources.getColor(R.color.m90EE90))
            snackBar.show()
        }
    }

    override fun showDialogProgress(msg: String) {
        showDialogProgress(msg, true, null)
    }

    override fun showDialogProgress(
        msg: String,
        cancelable: Boolean,
        onCancelListener: DialogInterface.OnCancelListener?
    ) {
        try {
            if (dialog == null) {
                dialog = ProgressDialog(context)
                dialog?.setCancelable(cancelable)
                dialog?.setCanceledOnTouchOutside(cancelable)
                dialog?.setOnCancelListener(onCancelListener)
            }
            if (!TextUtils.isEmpty(msg)) dialog?.setMessage(msg)
            dialog?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun dismissDialog() {
        dialog?.let {
            if (it.isShowing) it.dismiss()
        }
    }

    override fun showLoading() {
        viewController?.showLoading()
    }

    override fun showLoading(msg: String?) {
        viewController?.showLoading(msg)
    }

    override fun showEmpty(content: String?) {
        viewController?.showEmpty(content)
    }

    override fun showEmpty(
        content: String?,
        clickListener: View.OnClickListener?
    ) {
        viewController?.showEmpty(content, clickListener)
    }

    override fun showNetworkError(listener: View.OnClickListener?) {
        viewController?.showNetworkError(listener)
    }

    override fun showNetworkError(
        msg: String?,
        listener: View.OnClickListener?
    ) {
        viewController?.showNetworkError(msg, listener)
    }

    override fun showCustomView(
        drawableInt: Int,
        title: String?,
        msg: String?,
        btnText: String?,
        listener: View.OnClickListener?
    ) {
        viewController?.showCustomView(drawableInt, title, msg, btnText, listener)
    }

    override fun restore() {
        viewController?.restore()
    }

    override val isHasRestore: Boolean
        get() = viewController?.isHasRestore ?: false

    override fun showToast(msg: String) {
        ToastUtils.showShortToastSafe(hulkActivity, msg)
    }

    override fun showToast(msg: Int) {
        hulkActivity?.let {
            ToastUtils.show(it, msg)
        }
    }

    override val hulkActivity: Activity?
        get() = activity

    override val hulkContext: Context?
        get() = context

    override val hulkAppContext: Context?
        get() = activity?.applicationContext


    /**
     *  @param refreshEnable 设置是否刷新操作
     */
    open fun setRefreshEnable(refreshEnable: Boolean) {
        //不为空才可以刷新
        if (getSmartRefreshLayout() != null) {
            mRefreshEnable = refreshEnable
            getSmartRefreshLayout()?.isEnabled = mRefreshEnable
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        //相关销毁，相关事件置空
        if (mBinding != null) {
            mBinding == null
        }
        if (viewController != null) {
            viewController == null
        }
        if (dialog != null) {
            dialog == null
        }
        if (getSmartRefreshLayout() != null) {
            getSmartRefreshLayout()?.setOnRefreshListener(null)
            getSmartRefreshLayout()?.setOnLoadMoreListener(null)
            getSmartRefreshLayout() == null
        }
    }

}