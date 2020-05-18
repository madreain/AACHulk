package com.madreain.libhulk.base

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.androidadvance.topsnackbar.TSnackbar
import com.chad.library.adapter.base.BaseQuickAdapter
import com.madreain.hulk.R
import com.madreain.libhulk.config.HulkConfig
import com.madreain.libhulk.mvvm.BaseListViewModel
import com.madreain.libhulk.mvvm.IListView
import com.madreain.libhulk.utils.ActivityUtils
import com.madreain.libhulk.utils.ListUtils
import com.madreain.libhulk.utils.ToastUtils
import com.madreain.libhulk.view.IVaryViewHelperController
import com.madreain.libhulk.view.VaryViewHelperController
import com.madreain.libhulk.view.baseviewholder.HulkViewHolder
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import java.lang.reflect.ParameterizedType

/**
 * @author madreain
 * @date 2020/3/16.
 * module：
 * description：
 */
abstract class BaseListActivity<VM : BaseListViewModel<*>, DB : ViewDataBinding, A : BaseQuickAdapter<T, HulkViewHolder>, T> :
    AppCompatActivity(),
    IListView<T> {

    protected lateinit var mViewModel: VM
    protected var mBinding: DB? = null
    protected var adapter: A? = null

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
     * list展示相关
     */
    protected var pageNum: Int = 1
    private var mLoadMoreEnable = true
    protected var loadPageNum = 1 //当前正在加载的page，但是当前page接口还未做出响应
    private var mRefreshEnable = true //是否能进行下拉刷新


    abstract fun loadPageListData(pageNo: Int)

    abstract fun getSmartRefreshLayout(): SmartRefreshLayout?

    abstract fun getRecyclerView(): RecyclerView?

    abstract fun getLayoutManager(): RecyclerView.LayoutManager?

    abstract fun getAdapter(): Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        ActivityUtils.get()?.addActivity(this)
        if (HulkConfig.isArouterOpen()) {
            ARouter.getInstance().inject(this)
        }
        super.onCreate(savedInstanceState)
        initViewDataBinding()
        createViewModel()
        viewController = initVaryViewHelperController()
        lifecycle.addObserver(mViewModel)
        registerViewChange()
        initRefreshLoadMore()
        init(savedInstanceState)
        registerDataChange()
    }

    /***
     * view
     */
    protected open fun initVaryViewHelperController(): IVaryViewHelperController? {
        return VaryViewHelperController(getReplaceView())
    }


    /**
     * DataBinding
     * actualTypeArguments[0]  BaseListViewModel
     * actualTypeArguments[1]  ViewDataBinding
     * actualTypeArguments[2]  T
     *
     */
    private fun initViewDataBinding() {
        val cls: Class<*> =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<*>
        if (ViewDataBinding::class.java != cls && ViewDataBinding::class.java.isAssignableFrom(cls)) {
            mBinding = DataBindingUtil.setContentView(this, getLayoutId())
            mBinding?.lifecycleOwner = this
        } else {
            setContentView(getLayoutId())
        }

    }

    /**
     *     创建viewmodel
     *     actualTypeArguments[0]  BaseViewModel
     *     actualTypeArguments[1]  ViewDataBinding
     *     actualTypeArguments[2]  T
     *
     */
    private fun createViewModel() {
        //创建viewmodel
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[0]
            val tClass = tp as? Class<VM> ?: BaseListViewModel::class.java
            mViewModel = ViewModelProviders.of(this)[tClass] as VM
        }
    }

    /**
     * 注册视图变化事件
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
        })
        mViewModel.viewChange.refreshComplete.observe(this, Observer {
            refreshComplete()
        })
    }

    /**
     * 接口请求的数据变化
     */
    private fun registerDataChange() {
        mViewModel.mResult = MutableLiveData<T>()
        //数据变化的监听
        mViewModel.mResult?.observe(this, Observer {
            showListData(it as MutableList<T>, pageNum)
        })
    }


    /**
     * 相关view替换
     */

    override fun showTips(msg: String) {
        val snackBar = TSnackbar.make(
            findViewById(android.R.id.content),
            msg,
            TSnackbar.LENGTH_SHORT
        )
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(resources.getColor(R.color.mCCE4FF))
        val textView =
            snackBarView.findViewById<TextView>(com.androidadvance.topsnackbar.R.id.snackbar_text)
        textView.setTextColor(resources.getColor(R.color.m177AE6))
        snackBar.show()
    }

    /**
     * 展示弹窗
     */
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
                dialog = ProgressDialog(this)
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

    /**
     * 消失
     */
    override fun dismissDialog() {
        dialog?.let {
            if (it.isShowing) it?.dismiss()
        }
    }

    /**
     * loading
     */
    override fun showLoading() {
        viewController?.showLoading()
    }

    /***
     * loading 带文字
     */
    override fun showLoading(msg: String?) {
        viewController?.showLoading(msg)
    }

    /**
     * 无数据，空白页
     */
    override fun showEmpty(content: String?) {
        //加载当前page 出现List 为empty的情况--需要判断是否是第一页或者不是第一页
        viewController?.showEmpty(content)
    }

    override fun showEmpty(
        content: String?,
        clickListener: View.OnClickListener?
    ) {
        //加载当前page 出现List 为empty的情况--需要判断是否是第一页或者不是第一页
        if (loadPageNum > 1) {
            showNoMore()
        } else {
            pageNum = 1
            if (mRefreshEnable) {
                getSmartRefreshLayout()?.isEnabled = true
            }
            getSmartRefreshLayout()?.finishRefresh()
            adapter?.data?.clear()
            viewController?.showEmpty(content, clickListener)
        }
    }

    /**
     * 网络错误
     */
    override fun showNetworkError(listener: View.OnClickListener?) {
        viewController?.showNetworkError(listener)
    }

    /**
     * 网络错误
     */
    override fun showNetworkError(
        msg: String?,
        listener: View.OnClickListener?
    ) {
        if (loadPageNum > 1) {
            showLoadMoreError()
        } else {
            pageNum = 1
            if (mRefreshEnable) {
                getSmartRefreshLayout()?.isEnabled = true
            }
            getSmartRefreshLayout()?.finishRefresh()
            adapter?.data?.clear()
            viewController?.showNetworkError(msg, listener)
        }
    }

    /**
     * 自定义view展示
     */
    override fun showCustomView(
        drawableInt: Int,
        title: String?,
        msg: String?,
        btnText: String?,
        listener: View.OnClickListener?
    ) {
        viewController?.showCustomView(drawableInt, title, msg, btnText, listener)
    }

    /**
     * 恢复
     */
    override fun restore() {
        viewController?.restore()
    }

    override val isHasRestore: Boolean
        get() = viewController?.isHasRestore ?: false

    override fun showToast(msg: String) {
        ToastUtils.showShortToastSafe(hulkActivity, msg)
    }

    override fun showToast(msg: Int) {
        ToastUtils.show(hulkActivity, msg)
    }

    override val hulkActivity: Activity
        get() = this

    override val hulkContext: Context
        get() = this

    override val hulkAppContext: Context
        get() = applicationContext

    /**
     * list展示相关
     */


    /**
     *
     * @param moreEnable 设置是否加载更多操作
     */
    open fun setLoadMoreEnable(moreEnable: Boolean) {
        mLoadMoreEnable = moreEnable
        getSmartRefreshLayout()?.setEnableLoadMore(mLoadMoreEnable)
    }

    /**
     *  @param refreshEnable 设置是否刷新操作
     */
    open fun setRefreshEnable(refreshEnable: Boolean) {
        mRefreshEnable = refreshEnable
        getSmartRefreshLayout()?.isEnabled = mRefreshEnable
    }


    /**
     * 设置刷新加载相关
     */
    private fun initRefreshLoadMore() {
        getAdapter()
        //设置相关设置
        getRecyclerView()?.layoutManager = getLayoutManager()
        getRecyclerView()?.adapter = adapter
        getSmartRefreshLayout()?.isEnabled = mRefreshEnable
        if (mRefreshEnable) {
            getSmartRefreshLayout()?.setOnRefreshListener {
                loadPageNum = 1
                pageNum = 1
                loadPageListData(pageNum)
            }
        }
        if (mLoadMoreEnable) {
            getSmartRefreshLayout()?.setOnLoadMoreListener {
                loadPageNum = pageNum + 1
                pageNum = pageNum + 1
                loadPageListData(pageNum)
            }
        }
    }

    /**
     * 自动刷新
     */
    open fun autoRefresh() {
        if (ListUtils.getCount(adapter?.data) > 0) {
            getSmartRefreshLayout()?.autoRefresh()
        } else {
            showLoading()
            loadPageListData(1)
        }
    }

    /**
     * 数据展示
     */
    override fun showListData(datas: List<T>?, pageNum: Int) {
        this.pageNum = pageNum
        if (mRefreshEnable) {
            getSmartRefreshLayout()?.isEnabled = true
        }
        if (pageNum == 1) {
            getSmartRefreshLayout()?.finishRefresh()
            adapter?.setNewData(datas as MutableList<T>)
        } else {
            getSmartRefreshLayout()?.finishLoadMore()
            datas?.let {
                adapter?.addData(it)
            }
        }
    }

    /**
     * 加载更多---没有更多了
     */
    open fun showNoMore() {
        if (mRefreshEnable) {
            getSmartRefreshLayout()?.isEnabled = true
        }
        getSmartRefreshLayout()?.finishLoadMoreWithNoMoreData()
    }

    /**
     * 加载更多--当前page发生网络错误
     */
    open fun showLoadMoreError() {
        if (mRefreshEnable) {
            getSmartRefreshLayout()?.isEnabled = true
        }
        getSmartRefreshLayout()?.finishLoadMore(false)
    }

    /**
     * 刷新完成
     */
    override fun refreshComplete() {
        getSmartRefreshLayout()?.finishLoadMore()
        getSmartRefreshLayout()?.finishRefresh()
    }


    /**
     * 销毁
     */
    override fun onDestroy() {
        super.onDestroy()
        ActivityUtils.get()?.remove(this)
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
        if (adapter != null) {
            adapter == null
        }
        if (getSmartRefreshLayout() != null) {
            getSmartRefreshLayout()?.setOnRefreshListener(null)
            getSmartRefreshLayout()?.setOnLoadMoreListener(null)
            getSmartRefreshLayout() == null
        }
    }


}