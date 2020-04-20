package com.madreain.aachulk.module.main

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.madreain.aachulk.R
import com.madreain.aachulk.consts.ARouterUri
import com.madreain.aachulk.consts.HulkKey
import com.madreain.aachulk.module.EventBus.EventBusData
import com.madreain.aachulk.module.dialog.DialogFragment
import com.madreain.aachulk.utils.ActionBarUtils
import com.madreain.libhulk.base.BaseActivity
import com.madreain.libhulk.utils.EventBusUtils
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

@Route(path = ARouterUri.MainActivity)
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
            //            startActivity(Intent(hulkActivity, SingleActivity::class.java))
            ARouter.getInstance().build(ARouterUri.SingleActivity).navigation()
        }
        //single刷新功能展示
        tv_refresh_single.setOnClickListener {
            //            startActivity(Intent(hulkActivity, RefreshSingleActivity::class.java))
            ARouter.getInstance().build(ARouterUri.RefreshSingleActivity).navigation()
        }
        //list展示
        tv_list.setOnClickListener {
            //            startActivity(Intent(hulkActivity, ListActivity::class.java))
            ARouter.getInstance().build(ARouterUri.ListActivity).navigation()
        }
        //Detaillist展示
        tv_detail_list.setOnClickListener {
            //            startActivity(Intent(hulkActivity, DetailListActivity::class.java))
            ARouter.getInstance().build(ARouterUri.DetailListActivity).navigation()
        }
        //Detaillist展示
        tv_main2.setOnClickListener {
            //            startActivity(Intent(hulkActivity, Main2Activity::class.java))
            ARouter.getInstance().build(ARouterUri.Main2Activity).navigation()
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
            //            startActivity(Intent(hulkActivity, NoDataActivity::class.java))
            ARouter.getInstance().build(ARouterUri.NoDataActivity).navigation()
        }
        //无数据list
        tv_nodatalist.setOnClickListener {
            //            startActivity(Intent(hulkActivity, NoDataListActivity::class.java))
            ARouter.getInstance().build(ARouterUri.NoDataListActivity).navigation()
        }
        //自定义view替换
        tv_custom.setOnClickListener {
            //            startActivity(Intent(hulkActivity, CustomActivity::class.java))
            ARouter.getInstance().build(ARouterUri.CustomActivity)
                .withString(HulkKey.CustomKey, "测试Arouter")
                .navigation()
        }
        //多布局展示
        tv_multi.setOnClickListener {
            //            startActivity(Intent(hulkActivity, MultiActivity::class.java))
            ARouter.getInstance().build(ARouterUri.MultiActivity).navigation()
        }
        //EventBus
        tv_event.setOnClickListener {
            //            startActivity(Intent(hulkActivity, EventBusActivity::class.java))
            ARouter.getInstance().build(ARouterUri.EventBusActivity).navigation()
            //传递参数
            tv_event.postDelayed({
                EventBusUtils.post(EventBusData("我是一个EventBus测试"))
            }, 1000)
        }
        //多个baseurl
        tv_more.setOnClickListener {
            ARouter.getInstance().build(ARouterUri.ChangeBaseUrlActivity).navigation()
        }
        //多个baseurl
        tv_more2.setOnClickListener {
            ARouter.getInstance().build(ARouterUri.ChangeBaseUrlTwoActivity).navigation()
        }
    }

    override fun getSmartRefreshLayout(): SmartRefreshLayout? {
        return null
    }

    override fun refreshData() {

    }

}
