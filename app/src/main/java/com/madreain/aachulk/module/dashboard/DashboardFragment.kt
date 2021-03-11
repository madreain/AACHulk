package com.madreain.aachulk.module.dashboard

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.madreain.aachulk.R
import com.madreain.aachulk.module.list.ListAdapter
import com.madreain.aachulk.module.list.ListViewModel
import com.madreain.aachulk.module.single.SingleData
import com.madreain.libhulk.components.base.BaseFragment
import com.madreain.libhulk.components.view.list.ListResult
import kotlinx.android.synthetic.main.activity_list.*

class DashboardFragment :
    BaseFragment(R.layout.fragment_dashboard) {

    private val listViewModel by viewModels<ListViewModel>()


    override fun init(view: View, savedInstanceState: Bundle?) {

        val listAdapter = ListAdapter()
        listView.setAdapter(listAdapter)
        listView.setOnDataLoadListener { page, isFirst ->
            //请求接口
            listViewModel.searchCity(this, "中国",
                onSuccess = {
                    listView.attachData(
                        ListResult(
                            true,
                            it,
                            listViewModel.nextPage
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