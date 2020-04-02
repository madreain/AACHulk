package com.madreain.aachulk.module.noDataList

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.madreain.aachulk.R
import com.madreain.aachulk.consts.ARouterUri
import com.madreain.aachulk.databinding.ActivityNodataBinding
import com.madreain.aachulk.module.list.ListData
import com.madreain.aachulk.utils.ActionBarUtils
import com.madreain.libhulk.base.BaseListActivity
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.activity_nodatalist.*
import kotlinx.android.synthetic.main.activity_single.layout
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @author madreain
 * @date 2020/3/24.
 * module：
 * description：
 */
@Route(path = ARouterUri.NoDataListActivity)
class NoDataListActivity :
    BaseListActivity<NoDataListViewModel, ActivityNodataBinding, NoDataListAdapter, ListData>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_nodatalist
    }

    override fun getReplaceView(): View {
        return layout
    }

    override fun init(savedInstanceState: Bundle?) {
        //ActionBar相关设置
        ActionBarUtils.setSupportActionBarWithBack(toolbar, null, View.OnClickListener {
            onBackPressed()
        })
        ActionBarUtils.setToolBarTitleText(toolbar, "刷新有数据，加载更多无数据展示界面")
        //请求接口
        mViewModel.searchCity("浙江", 1)
    }

    /**
     * 设置SmartRefreshLayout
     */
    override fun getSmartRefreshLayout(): SmartRefreshLayout? {
        return smartRefreshLayout
    }


    override fun loadPageListData(pageNo: Int) {
        if (pageNo == 1) {
            mViewModel.searchCity("浙江", 1)
        } else
            mViewModel.searchCity("广德", pageNo)
    }

    override fun getRecyclerView(): RecyclerView? {
        return recyclerView
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager? {
        return LinearLayoutManager(this)
    }

    override fun getAdapter() {
        adapter = NoDataListAdapter()
    }

}