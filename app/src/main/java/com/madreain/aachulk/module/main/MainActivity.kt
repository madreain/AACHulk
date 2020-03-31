package com.madreain.aachulk.module.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.madreain.aachulk.R
import com.madreain.aachulk.consts.HulkKey
import com.madreain.aachulk.module.custom.CustomActivity
import com.madreain.aachulk.module.detailList.DetailListActivity
import com.madreain.aachulk.module.dialog.DialogFragment
import com.madreain.aachulk.module.list.ListActivity
import com.madreain.aachulk.module.main2.Main2Activity
import com.madreain.aachulk.module.noData.NoDataActivity
import com.madreain.aachulk.module.noDataList.NoDataListActivity
import com.madreain.aachulk.module.refreshSingle.RefreshSingleActivity
import com.madreain.aachulk.utils.ActionBarUtils
import com.madreain.libhulk.base.BaseActivity
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : BaseActivity<MainViewModel, ViewDataBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getReplaceView(): View {
        return main_layout
    }

    override fun init(savedInstanceState: Bundle?) {
        ActionBarUtils.setToolBarTitleText(toolbar, "AACHulk项目展示")
        //single展示
        tv_single.setOnClickListener {
            startActivity(Intent(hulkActivity, NoDataActivity::class.java))
        }
        //single刷新功能展示
        tv_refresh_single.setOnClickListener {
            startActivity(Intent(hulkActivity, RefreshSingleActivity::class.java))
        }
        //list展示
        tv_list.setOnClickListener {
            startActivity(Intent(hulkActivity, ListActivity::class.java))
        }
        //Detaillist展示
        tv_detail_list.setOnClickListener {
            startActivity(Intent(hulkActivity, DetailListActivity::class.java))
        }
        //Detaillist展示
        tv_main2.setOnClickListener {
            startActivity(Intent(hulkActivity, Main2Activity::class.java))
        }
        //dialog
        tv_dialog.setOnClickListener {
            val dialogFragment = DialogFragment()
            val bundle = Bundle()
            bundle.putString(HulkKey.CommonTitle, "提示标题")
            bundle.putString(HulkKey.CommonDesc, "提示内容")
            bundle.putString(HulkKey.CommonLeft, "取消")
            bundle.putString(HulkKey.CommonRight, "确定")
            bundle.putString(HulkKey.CommonRemind, "不再提醒")
            bundle.putBoolean(HulkKey.CommonExternalArea, true)
            dialogFragment.setArguments(bundle)
            dialogFragment.setOnLeftRightClickListener(object :
                DialogFragment.onLeftRightClickListener {
                override fun onLeftClick(isRemind: Boolean) {
                    showToast("点击了左边按钮，是否不再提醒" + isRemind)
                }

                override fun onRightClick(isRemind: Boolean) {
                    showToast("点击了右边按钮，是否不再提醒" + isRemind)
                }
            })
            dialogFragment.show(supportFragmentManager, DialogFragment::class.java.getName())
        }
        //无数据
        tv_nodata.setOnClickListener {
            startActivity(Intent(hulkActivity, NoDataActivity::class.java))
        }
        //无数据list
        tv_nodatalist.setOnClickListener {
            startActivity(Intent(hulkActivity, NoDataListActivity::class.java))
        }
        //自定义view替换
        tv_custom.setOnClickListener {
            startActivity(Intent(hulkActivity, CustomActivity::class.java))
        }
    }

    override fun getSmartRefreshLayout(): SmartRefreshLayout? {
        return null
    }

    override fun refreshData() {

    }

}
