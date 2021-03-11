//package com.madreain.aachulk.module.changeBaseUrlTwo;
//
//import androidx.lifecycle.MutableLiveData
//import com.madreain.aachulk.consts.HulkKey
//import com.madreain.aachulk.module.api.ApiService
//import com.madreain.aachulk.module.changeBaseUrl.ChangeBaseUrlData
//import com.madreain.libhulk.em.RequestDisplay
//import com.madreain.libhulk.mvvm.BaseListViewModel
//
//
///**
// * @author madreain
// * @date
// * module：
// * description：
// */
//public class ChangeBaseUrlTwoViewModel : BaseListViewModel<ApiService>() {
//
//
//    public override fun onStart() {
//        getCategories(1)
//    }
//
//    fun getCategories(pageNo: Int) {
//        launchOnlyresult(
//            //调用接口方法
//            block = {
//                getApiService().getCategories()
//            },
//            //重试
//            reTry = {
//                //调用重试的方法
//                getCategories(pageNo)
//            },
//            //成功
//            success = {
//                //成功回调
//            },
//            currentDomainName = HulkKey.GANK_DOMAIN_NAME,
//            type = RequestDisplay.REPLACE,
//            pageNo = pageNo
//        )
//    }
//
//}
