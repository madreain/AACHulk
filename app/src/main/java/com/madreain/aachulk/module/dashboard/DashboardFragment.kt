package com.madreain.aachulk.module.dashboard

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.madreain.aachulk.R
import com.madreain.libhulk.base.BaseListFragment
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment :
    BaseListFragment<DashboardViewModel, ViewDataBinding, DashboardAdapter, DashboardData>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_dashboard
    }

    override fun getReplaceView(): View {
        return layout_dashboard
    }

    override fun init(savedInstanceState: Bundle?) {
        //请求接口
        mViewModel.searDashboardchCity("中国", 1)
    }

    override fun getSmartRefreshLayout(): SmartRefreshLayout? {
        return smartRefreshLayout
    }

    override fun loadPageListData(pageNo: Int) {
        mViewModel.searDashboardchCity("中国", pageNo)
    }

    override fun getRecyclerView(): RecyclerView? {
        return recyclerView
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager? {
        return LinearLayoutManager(context)
    }

    override fun getAdapter() {
        adapter = DashboardAdapter()
    }

}