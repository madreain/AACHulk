package com.madreain.aachulk.module.noDataList

import com.madreain.aachulk.module.api.ApiService
import com.madreain.libhulk.em.RequestDisplay
import com.madreain.libhulk.mvvm.BaseListViewModel

/**
 * @author madreain
 * @date 2020/3/16.
 * module：
 * description：
 */
class NoDataListViewModel : BaseListViewModel<ApiService>() {

    public override fun onStart() {
    }

    public fun searchCity(value: String, pageNo: Int) {
        launchOnlyresult(
            //调用接口方法
            block = {
                getApiService().searchCity(value)
            },
            //重试
            reTry = {
                //调用重试的方法
                searchCity(value, pageNo)
            },
            //成功
            success = {
                //成功回调
                //通知ui刷新
            }, type = RequestDisplay.REPLACE, pageNo = pageNo
        )
    }


}