package com.madreain.aachulk.module.custom

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.madreain.aachulk.R
import com.madreain.aachulk.consts.ARouterUri
import com.madreain.aachulk.databinding.ActivityListBinding
import com.madreain.aachulk.module.list.ListAdapter
import com.madreain.aachulk.module.list.ListData
import com.madreain.aachulk.utils.ActionBarUtils
import com.madreain.aachulk.view.MyVaryViewHelperController
import com.madreain.libhulk.base.BaseListActivity
import com.madreain.libhulk.view.IVaryViewHelperController
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @author madreain
 * @date 2020/3/31.
 * module：
 * description：
 */
@Route(path = ARouterUri.CustomActivity)
class CustomActivity :
    BaseListActivity<CustomViewModel, ActivityListBinding, ListAdapter, ListData>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_list
    }

    override fun getReplaceView(): View {
        return smartRefreshLayout
    }

    override fun init(savedInstanceState: Bundle?) {
        //ActionBar相关设置
        ActionBarUtils.setSupportActionBarWithBack(toolbar, null, View.OnClickListener {
            onBackPressed()
        })
        ActionBarUtils.setToolBarTitleText(toolbar, "自定义view展示,这里只修改了自定义的loading")
        //请求接口
        mViewModel.searchCity("中国", 1)
    }

    /**
     * list相关方法
     */
    override fun loadPageListData(pageNo: Int) {
        mViewModel.searchCity("中国", pageNo)
    }

    override fun getSmartRefreshLayout(): SmartRefreshLayout {
        return smartRefreshLayout
    }

    override fun getRecyclerView(): RecyclerView {
        return recyclerView
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(this)
    }

    override fun getAdapter() {
        adapter = ListAdapter()
    }

    /**
     * 自定义相关不同状态展示的view,这里只是修改了loading的效果不同，可根据每个项目的实际情况
     * @return
     */
    override fun initVaryViewHelperController(): IVaryViewHelperController? {
        return MyVaryViewHelperController(getReplaceView())
    }


}