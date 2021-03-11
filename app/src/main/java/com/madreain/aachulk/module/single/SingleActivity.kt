package com.madreain.aachulk.module.single

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.madreain.aachulk.R
import com.madreain.aachulk.consts.RouteUrls
import com.madreain.aachulk.utils.ActionBarUtils
import com.madreain.libhulk.components.base.BaseActivity
import kotlinx.android.synthetic.main.activity_single.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @author madreain
 * @date 2020/3/24.
 * module：
 * description：
 */
@Route(path = RouteUrls.Single)
class SingleActivity : BaseActivity(R.layout.activity_single) {

    private val singleViewModel by viewModels<SingleViewModel>()

    override fun init(savedInstanceState: Bundle?) {
        //ActionBar相关设置
        ActionBarUtils.setSupportActionBarWithBack(toolbar, null, View.OnClickListener {
            onBackPressed()
        })
        ActionBarUtils.setToolBarTitleText(toolbar, "单数据展示界面")
        //请求接口
        singleViewModel.cityList(this, onSuccess = {
            tv.visibility = View.VISIBLE
            it?.let {
                tv.text = it[0].name
            }
        }, onError = {
            tv.visibility = View.GONE
        })
    }

}