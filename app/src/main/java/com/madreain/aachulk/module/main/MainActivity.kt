package com.madreain.aachulk.module.main

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.madreain.aachulk.R
import com.madreain.aachulk.consts.EventsKey
import com.madreain.aachulk.consts.RouteKeys
import com.madreain.aachulk.consts.RouteUrls
import com.madreain.aachulk.utils.ActionBarUtils
import com.madreain.libhulk.click.OnClick
import com.madreain.libhulk.components.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

@Route(path = RouteUrls.Main)
class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun init(savedInstanceState: Bundle?) {
        ActionBarUtils.setToolBarTitleText(toolbar, "AACHulk项目展示")
        //LiveEventBus的接受
        LiveEventBus
            .get(EventsKey.EVENT_INCOMMING_MSG, String::class.java)
            .observe(this, androidx.lifecycle.Observer {
                tvEvent.text = it
            })
    }

    @OnClick(
        [R.id.tvSingle, R.id.tvList, R.id.tvDetailList, R.id.tvMain2, R.id.tvDialog, R.id.tvNodata, R.id.tvCustom, R.id.tvMulti, R.id.tvEvent],
        true
    )
    fun onClick(view: View) {
        when (view.id) {
            //single展示
            R.id.tvSingle -> {
                route(RouteUrls.Single)
            }
            //list展示
            R.id.tvList -> {
                route(RouteUrls.List)
            }
            //Detaillist展示
            R.id.tvDetailList -> {
                route(RouteUrls.DetailList)
            }
            R.id.tvMain2 -> {
                route(RouteUrls.Main2)
            }
            //dialog
            R.id.tvDialog -> {
                ToastUtils.showLong("参考xpopup的官网使用")
                //这里可参考xpopup的官网使用
                //注意要修改maxWidth，需在init方法中的 super.init()之前设置popupInfo.maxWidth = ConvertUtils.dp2px(300f)
            }
            //无数据
            R.id.tvNodata -> {
                route(RouteUrls.NoDataList)
            }
            //自定义view替换
            R.id.tvCustom -> {
                route(RouteUrls.Custom, routeHook = {
                    it.withString(RouteKeys.CustomKey, "测试Arouter")
                })
            }
            //多布局展示
            R.id.tvMulti -> {
                route(RouteUrls.Multi)
            }
            //传递参数
            R.id.tvEvent -> {
                LiveEventBus
                    .get(EventsKey.EVENT_INCOMMING_MSG)
                    .postDelay("我是一个LiveEventBus测试" + Math.random(), 100)
            }
        }
    }

}
