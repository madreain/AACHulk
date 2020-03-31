package com.madreain.libhulk.mvvm

import com.madreain.libhulk.http.SingleLiveEvent

/**
 * @author madreain
 * @date 2020/3/24.
 * module：
 * description：view的改变
 */
class ViewChange {
    //加载的loading
    val showLoading by lazy { SingleLiveEvent<String>() }
    //对话框显示
    val showDialogProgress by lazy { SingleLiveEvent<String>() }
    //对话框消失
    val dismissDialog by lazy { SingleLiveEvent<String>() }
    //toast
    val showToast by lazy { SingleLiveEvent<String>() }
    //tip提示
    val showTips by lazy { SingleLiveEvent<String>() }
    //无数据
    val showEmpty by lazy { SingleLiveEvent<String>() }
    //网络错误
    val showNetworkError by lazy { SingleLiveEvent<String>() }
    //是否恢复了
    val restore by lazy { SingleLiveEvent<Void>() }
    //刷新结束
    val refreshComplete by lazy { SingleLiveEvent<Void>() }
}