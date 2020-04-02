package com.madreain.aachulk.module.multi;

import com.madreain.aachulk.module.api.ApiService
import com.madreain.libhulk.em.RequestDisplay
import com.madreain.libhulk.mvvm.BaseListViewModel


/**
 * @author madreain
 * @date
 * module：
 * description：
 */
public class MultiViewModel : BaseListViewModel<ApiService>() {


    public override fun onStart() {

    }

    fun getMultiList(pageNo: Int) {
        launchOnlyresult(
            //调用接口方法
            block = {
                getApiService().getMultiList()
            },
            //重试
            reTry = {
                //调用重试的方法
                getMultiList(pageNo)
            },
            //成功
            success = {
                //成功回调
            }, type = RequestDisplay.REPLACE, pageNo = pageNo
        )
    }

}
