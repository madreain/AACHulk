package com.madreain.aachulk.module.list

import com.madreain.aachulk.module.api.ApiService
import com.madreain.libhulk.em.RequestDisplay
import com.madreain.libhulk.mvvm.BaseListViewModel

/**
 * @author madreain
 * @date 2020/3/16.
 * module：
 * description：
 */
class ListViewModel : BaseListViewModel<ApiService>() {

    public override fun onStart() {

    }

    fun searchCity(city: String, pageNo: Int) {
        launchOnlyresult(
            //调用接口方法
            block = {
                getApiService().searchCity(city)
            },
            //重试
            reTry = {
                //调用重试的方法
                searchCity(city, pageNo)
            },
            //成功
            success = {
                //成功回调
            }, type = RequestDisplay.REPLACE, pageNo = pageNo
        )
    }

}