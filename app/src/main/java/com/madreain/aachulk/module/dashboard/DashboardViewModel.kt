package com.madreain.aachulk.module.dashboard

import com.madreain.aachulk.module.api.ApiService
import com.madreain.libhulk.em.RequestDisplay
import com.madreain.libhulk.mvvm.BaseListViewModel

class DashboardViewModel : BaseListViewModel<ApiService>() {

    public override fun onStart() {

    }

    fun searDashboardchCity(city: String, pageNo: Int) {
        launchOnlyresult(
            //调用接口方法
            block = {
                getApiService().searDashboardchCity(city)
            },
            //重试
            reTry = {
                //调用重试的方法
                searDashboardchCity(city, pageNo)
            },
            //成功
            success = {
                //成功回调
            }, type = RequestDisplay.REPLACE, pageNo = pageNo
        )
    }
}