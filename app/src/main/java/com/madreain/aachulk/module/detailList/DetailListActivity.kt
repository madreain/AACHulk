package com.madreain.aachulk.module.detailList


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.madreain.aachulk.R
import com.madreain.aachulk.consts.RouteUrls
import com.madreain.aachulk.module.list.ListViewModel
import com.madreain.aachulk.module.single.SingleData
import com.madreain.aachulk.utils.ActionBarUtils
import com.madreain.libhulk.components.base.BaseActivity
import com.madreain.libhulk.components.view.list.ListResult
import kotlinx.android.synthetic.main.activity_detail_list.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @author madreain
 * @date 2020/3/19.
 * module：
 * description：
 */
@Route(path = RouteUrls.DetailList)
class DetailListActivity :
    BaseActivity(R.layout.activity_detail_list) {

    private val listViewModel by viewModels<ListViewModel>()

    override fun init(savedInstanceState: Bundle?) {
        //ActionBar相关设置
        ActionBarUtils.setSupportActionBarWithBack(toolbar, null, View.OnClickListener {
            onBackPressed()
        })
        ActionBarUtils.setToolBarTitleText(toolbar, "DetailList带头部底部的数据展示界面")
        //请求接口
        val detailListAdapter = DetailListAdapter()
        listView.setAdapter(detailListAdapter)
        listView.setOnDataLoadListener { page, isFirst ->
            //请求接口
            listViewModel.searchCity(this, "中国",
                onSuccess = {
                    if (isFirst) {
                        //增加头部
                        detailListAdapter.addHeaderView(
                            LayoutInflater.from(this).inflate(R.layout.head_detail, null)
                        )
                        //增加底部
                        detailListAdapter.addFooterView(
                            LayoutInflater.from(this).inflate(R.layout.footer_detail, null)
                        )
                    }
                    listView.attachData(
                        ListResult(
                            true,
                            it,
                            listViewModel.nextPage
                        )
                    )
                }, onError = {
                    listView.attachData(
                        ListResult<SingleData>(false, null, null)
                    )
                })
        }
        listView.autoRefreshNoAnimation()

    }


}