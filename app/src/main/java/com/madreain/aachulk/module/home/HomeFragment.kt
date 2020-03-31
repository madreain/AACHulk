package com.madreain.aachulk.module.home

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.madreain.aachulk.R
import com.madreain.libhulk.base.BaseFragment
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<HomeViewModel, ViewDataBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getReplaceView(): View {
        return layout_home
    }

    override fun init(savedInstanceState: Bundle?) {
        mViewModel.onStart()
        mViewModel.result.observe(viewLifecycleOwner, Observer<String?> { s -> text_home.text = s })
    }

    override fun getSmartRefreshLayout(): SmartRefreshLayout? {
        return smartRefreshLayout
    }

    override fun refreshData() {
        mViewModel.refreshData()
    }

}