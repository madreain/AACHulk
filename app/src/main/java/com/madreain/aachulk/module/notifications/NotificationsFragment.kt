package com.madreain.aachulk.module.notifications

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.madreain.aachulk.R
import com.madreain.aachulk.databinding.FragmentNotificationsBinding
import com.madreain.libhulk.base.BaseFragment
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : BaseFragment<NotificationsViewModel, FragmentNotificationsBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_notifications
    }

    override fun getReplaceView(): View {
        return layout_notifications
    }

    override fun init(savedInstanceState: Bundle?) {
        mViewModel.onStart()
        mViewModel.result.observe(
            this, Observer {
                mBinding!!.notificationsData = it[0]
            })
    }

    override fun getSmartRefreshLayout(): SmartRefreshLayout? {
        return null
    }

    override fun refreshData() {

    }

}