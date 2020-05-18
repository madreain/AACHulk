package com.madreain.aachulk.module.EventBus;

import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.jeremyliao.liveeventbus.LiveEventBus
import com.madreain.aachulk.R
import com.madreain.aachulk.consts.ARouterUri
import com.madreain.aachulk.consts.LiveEventBusKey
import com.madreain.aachulk.databinding.ActivityEventBusBinding
import com.madreain.aachulk.utils.ActionBarUtils
import com.madreain.libhulk.base.BaseActivity
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.activity_event_bus.*
import kotlinx.android.synthetic.main.toolbar.*


/**
 * @author madreain
 * @date
 * module：
 * description：
 */

@Route(path = ARouterUri.EventBusActivity)
public class EventBusActivity : BaseActivity<EventBusViewModel, ActivityEventBusBinding>() {


    override fun getLayoutId(): Int {
        return R.layout.activity_event_bus;
    }

    override fun getReplaceView(): View {
        return activity_event_bus
    }

    override fun init(savedInstanceState: Bundle?) {
        //ActionBar相关设置
        ActionBarUtils.setSupportActionBarWithBack(toolbar, null, View.OnClickListener {
            onBackPressed()
        })
        ActionBarUtils.setToolBarTitleText(toolbar, "标题")

        //LiveEventBus接受数据
        LiveEventBus
            .get(LiveEventBusKey.AACKey, String::class.java)
            .observe(this, object : Observer<String?> {
                override fun onChanged(@Nullable s: String?) {
                    tv_event.setText(s)
                }
            })
    }

    override fun getSmartRefreshLayout(): SmartRefreshLayout? {
        return null
    }

    override fun refreshData() {

    }

}
