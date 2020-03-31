package com.madreain.aachulk.module.detailList


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.madreain.aachulk.R
import com.madreain.aachulk.databinding.ActivityDetailListBinding
import com.madreain.aachulk.utils.ActionBarUtils
import com.madreain.libhulk.base.BaseListActivity
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @author madreain
 * @date 2020/3/19.
 * module：
 * description：
 */
class DetailListActivity :
    BaseListActivity<DetailListViewModel, ActivityDetailListBinding, DetailListAdapter, DetailListData>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_detail_list
    }

    override fun getReplaceView(): View {
        return smartRefreshLayout
    }

    override fun init(savedInstanceState: Bundle?) {
        //ActionBar相关设置
        ActionBarUtils.setSupportActionBarWithBack(toolbar, null, View.OnClickListener {
            onBackPressed()
        })
        ActionBarUtils.setToolBarTitleText(toolbar, "DetailList带头部底部的数据展示界面")
        //请求接口
        mViewModel.searchCity("中国", 1)
        //增加头部
        adapter!!.addHeaderView(LayoutInflater.from(this).inflate(R.layout.head_detail, null))
        //增加底部
        adapter!!.addFooterView(LayoutInflater.from(this).inflate(R.layout.footer_detail, null))
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
        adapter = DetailListAdapter()
    }

}