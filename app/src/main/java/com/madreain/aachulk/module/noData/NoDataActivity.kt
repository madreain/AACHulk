package com.madreain.aachulk.module.noData

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.madreain.aachulk.R
import com.madreain.aachulk.consts.ARouterUri
import com.madreain.aachulk.databinding.ActivityNodataBinding
import com.madreain.aachulk.utils.ActionBarUtils
import com.madreain.libhulk.base.BaseActivity
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.activity_single.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @author madreain
 * @date 2020/3/24.
 * module：
 * description：
 */
@Route(path = ARouterUri.NoDataActivity)
class NoDataActivity : BaseActivity<NoDataViewModel, ActivityNodataBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_nodata
    }

    override fun getReplaceView(): View {
        return layout
    }

    override fun init(savedInstanceState: Bundle?) {
        //ActionBar相关设置
        ActionBarUtils.setSupportActionBarWithBack(toolbar, null, View.OnClickListener {
            onBackPressed()
        })
        ActionBarUtils.setToolBarTitleText(toolbar, "无数据展示界面")
        //请求接口
        mViewModel.searchCity("广德")
        //接口请求的数据变化
        mViewModel.result.observe(this, Observer {
            mBinding!!.listDataS = it
            mBinding!!.listData = it[0]
        })
    }

    /**
     * 设置SmartRefreshLayout
     */
    override fun getSmartRefreshLayout(): SmartRefreshLayout? {
        return null
    }

    override fun refreshData() {

    }

}