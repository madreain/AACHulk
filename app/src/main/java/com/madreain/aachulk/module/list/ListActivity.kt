package com.madreain.aachulk.module.list

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.madreain.aachulk.R
import com.madreain.aachulk.consts.RouteUrls
import com.madreain.aachulk.module.single.SingleData
import com.madreain.aachulk.utils.ActionBarUtils
import com.madreain.libhulk.components.base.BaseActivity
import com.madreain.libhulk.components.view.list.ListResult
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @author madreain
 * @date 2020/3/19.
 * module：
 * description：
 */
@Route(path = RouteUrls.List)
class ListActivity :
    BaseActivity(R.layout.activity_list) {

    private val listViewModel by viewModels<ListViewModel>()

    override fun init(savedInstanceState: Bundle?) {
        //ActionBar相关设置
        ActionBarUtils.setSupportActionBarWithBack(toolbar, null, View.OnClickListener {
            onBackPressed()
        })
        ActionBarUtils.setToolBarTitleText(toolbar, "List数据展示界面")

        val listAdapter = ListAdapter()
        listView.setAdapter(listAdapter)
//        listView.setRefreshEnable(false)
//        listView.setEnableLoadMore(false)
        listView.setOnDataLoadListener { page, isFirst ->
            //请求接口
            listViewModel.searchCity(this, "中国",
                onSuccess = {
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