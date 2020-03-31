package com.madreain.aachulk.module.refreshSingle

import androidx.lifecycle.MutableLiveData
import com.madreain.aachulk.module.api.ApiService
import com.madreain.libhulk.em.RequestDisplay
import com.madreain.libhulk.mvvm.BaseViewModel

/**
 * @author madreain
 * @date 2020/3/16.
 * module：
 * description：
 */
class RefreshSingleViewModel : BaseViewModel<ApiService>() {

    public override fun onStart() {
        cityList()
    }

    var result = MutableLiveData<List<RefreshSingleData>>()
    private fun cityList() {
        launchOnlyresult(
            //调用接口方法
            block = {
                getApiService().getRefreshCityList()
            },
            //重试
            reTry = {
                //调用重试的方法
                cityList()
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