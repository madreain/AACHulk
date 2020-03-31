package com.madreain.aachulk.module.home

import androidx.lifecycle.MutableLiveData
import com.madreain.aachulk.module.api.ApiService
import com.madreain.libhulk.mvvm.BaseViewModel

class HomeViewModel : BaseViewModel<ApiService>() {

    var result = MutableLiveData<String>()

    public override fun onStart() {
        result.value = "This is home fragment"
    }

    public fun refreshData(){
        result.value = "刷新得到新数据"
        //恢复原先
        viewChange.restore.call()
    }

}