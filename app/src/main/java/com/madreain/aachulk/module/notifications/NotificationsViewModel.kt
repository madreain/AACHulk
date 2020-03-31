package com.madreain.aachulk.module.notifications

import androidx.lifecycle.MutableLiveData
import com.madreain.aachulk.module.api.ApiService
import com.madreain.libhulk.em.RequestDisplay
import com.madreain.libhulk.mvvm.BaseViewModel

class NotificationsViewModel : BaseViewModel<ApiService>() {

    var result = MutableLiveData<List<NotificationsData>>()

    public override fun onStart() {
        launchOnlyresult(
            //调用接口方法
            block = {
                getApiService().getNotificationsCityList()
            },
            //重试
            reTry = {
                //调用重试的方法
                onStart()
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