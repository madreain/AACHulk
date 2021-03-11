package com.madreain.aachulk.module.multi;

import android.os.Bundle;
import android.view.View;
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route

import com.madreain.aachulk.R
import com.madreain.aachulk.consts.RouteUrls
import com.madreain.aachulk.module.single.SingleData
import com.madreain.aachulk.utils.ActionBarUtils
import com.madreain.libhulk.components.base.BaseActivity
import com.madreain.libhulk.components.view.list.ListResult
import kotlinx.android.synthetic.main.activity_multi.*
import kotlinx.android.synthetic.main.toolbar.*


/**
 * @author madreain
 * @date
 * module：
 * description：
 */

@Route(path = RouteUrls.Multi)
public class MultiActivity :
    BaseActivity(R.layout.activity_multi) {

    private val multiListViewModel by viewModels<MultiListViewModel>()

    override fun init(savedInstanceState: Bundle?) {
        //ActionBar相关设置
        ActionBarUtils.setSupportActionBarWithBack(toolbar, null, View.OnClickListener {
            onBackPressed()
        })
        ActionBarUtils.setToolBarTitleText(toolbar, "多布局的展示")
        val multiAdapter = MultiAdapter()
        listView.setAdapter(multiAdapter)
        listView.setOnDataLoadListener { page, isFirst ->
            //请求接口
            multiListViewModel.searchMultiCity(this, "中国",
                onSuccess = {
                    listView.attachData(
                        ListResult(
                            true,
                            it,
                            multiListViewModel.nextPage
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
