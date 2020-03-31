package com.madreain.aachulk.module.noData

import androidx.lifecycle.MutableLiveData
import com.madreain.aachulk.module.api.ApiService
import com.madreain.aachulk.module.list.ListData
import com.madreain.libhulk.em.RequestDisplay
import com.madreain.libhulk.mvvm.BaseViewModel

/**
 * @author madreain
 * @date 2020/3/16.
 * module：
 * description：
 */
class NoDataViewModel : BaseViewModel<ApiService>() {

    public override fun onStart() {
    }

    var result = MutableLiveData<List<ListData>>()
    public fun searchCity(value: String) {
        launchOnlyresult(
            //调用接口方法
            block = {
                getApiService().searchCity(value)
            },
            //重试
            reTry = {
                //调用重试的方法
                searchCity(value)
            },
            //成功
            success = {
                //成功回调
                result.value = it
                //通知ui刷新
            }, type = RequestDisplay.REPLACE
        )
    }


}