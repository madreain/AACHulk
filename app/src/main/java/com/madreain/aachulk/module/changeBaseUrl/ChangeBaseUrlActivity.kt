package com.madreain.aachulk.module.changeBaseUrl;

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route

import com.madreain.aachulk.R;
import com.madreain.aachulk.consts.ARouterUri
import com.madreain.libhulk.base.BaseActivity;
import com.madreain.aachulk.databinding.ActivityChangeBaseUrlBinding
import com.madreain.aachulk.utils.ActionBarUtils
import com.scwang.smartrefresh.layout.SmartRefreshLayout

import kotlinx.android.synthetic.main.activity_change_base_url.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @author madreain
 * @date
 * module：
 * description：
 */

@Route(path = ARouterUri.ChangeBaseUrlActivity)
public class ChangeBaseUrlActivity :
    BaseActivity<ChangeBaseUrlViewModel, ActivityChangeBaseUrlBinding>() {


    override fun getLayoutId(): Int {
        return R.layout.activity_change_base_url;
    }

    override fun getReplaceView(): View {
        return activity_change_base_url
    }

    override fun init(savedInstanceState: Bundle?) {
        //ActionBar相关设置
        ActionBarUtils.setSupportActionBarWithBack(toolbar, null, View.OnClickListener {
            onBackPressed()
        })
        ActionBarUtils.setToolBarTitleText(toolbar, "多个BaseUrl")

        //请求接口
        mViewModel.onStart()
        //接口请求的数据变化
        mViewModel.result.observe(this, Observer {
            mBinding?.changeBaseUrlDataS = it
            mBinding?.changeBaseUrlData = it[0]
        })
    }

    override fun getSmartRefreshLayout(): SmartRefreshLayout? {
        return null
    }

    override fun refreshData() {

    }

}
