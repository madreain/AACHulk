package com.madreain.aachulk.module.single

import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.ToastUtils
import com.madreain.aachulk.module.api.ApiService
import com.madreain.libhulk.components.base.IPage
import com.madreain.libhulk.network.NetHelper

/**
 * @author madreain
 * @date 2020/3/16.
 * module：
 * description：
 */
class SingleViewModel : ViewModel() {


    fun cityList(page: IPage, onSuccess: (data: List<SingleData>?) -> Unit) {
        NetHelper.request(page, block = {
            NetHelper.getService(ApiService::class.java).getCityList().asResult()
        }, onSuccess = {
            onSuccess(it)
        }, onError = {

        })
    }

}