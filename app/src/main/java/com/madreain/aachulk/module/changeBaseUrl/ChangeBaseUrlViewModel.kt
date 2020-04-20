package com.madreain.aachulk.module.changeBaseUrl;

import androidx.lifecycle.MutableLiveData
import com.madreain.aachulk.consts.HulkKey
import com.madreain.aachulk.module.api.ApiService
import com.madreain.libhulk.em.RequestDisplay
import com.madreain.libhulk.mvvm.BaseViewModel


/**
 * @author madreain
 * @date
 * module：
 * description：
 */
public class ChangeBaseUrlViewModel : BaseViewModel<ApiService>() {


    public override fun onStart() {
        getWxArticle()
    }

    var result = MutableLiveData<List<ChangeBaseUrlData>>()
    fun getWxArticle() {
        launchOnlyresult(
            //调用接口方法
            block = {
                getApiService().getWxArticle()
            },
            //重试
            reTry = {
                //调用重试的方法
                getWxArticle()
            },
            //成功
            success = {
                //成功回调
                result.value = it
                //成功回调
            },
            currentDomainName = HulkKey.WANANDROID_DOMAIN_NAME,
            type = RequestDisplay.REPLACE
        )
    }

}
