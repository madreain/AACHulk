package com.madreain.aachulk.module.list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.madreain.aachulk.R
import com.madreain.aachulk.databinding.ActivityListBinding
import com.madreain.aachulk.utils.ActionBarUtils
import com.madreain.libhulk.base.BaseListActivity
import com.madreain.libhulk.utils.LogUtils
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @author madreain
 * @date 2020/3/19.
 * module：
 * description：
 */
class ListActivity :
    BaseListActivity<ListViewModel, ActivityListBinding, ListAdapter, ListData>() {

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
        ActionBarUtils.setToolBarTitleText(toolbar, "List数据展示界面")
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

}