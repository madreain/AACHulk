package com.madreain.aachulk.module.multi

import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.ToastUtils
import com.madreain.aachulk.module.api.ApiService
import com.madreain.aachulk.module.single.SingleData
import com.madreain.libhulk.components.base.IPage
import com.madreain.libhulk.network.NetHelper

/**
 * @author madreain
 * @date 2020/3/16.
 * module：
 * description：
 */
class MultiListViewModel : ViewModel() {

    //接口不带分页做的假的
    var nextPage: Long = 1

    fun searchMultiCity(
        page: IPage,
        city: String,
        onSuccess: (data: List<MultiListData>?) -> Unit={},
        onError: (e: Throwable) -> Unit = {}
    ) {
        nextPage++
        NetHelper.request(page, block = {
            NetHelper.getService(ApiService::class.java).searchMultiCity(city).asResult()
        }, onSuccess = {
            onSuccess(it)
        }, onError = {
            onError(it)
            ToastUtils.showLong(it.message)
        })
    }

}