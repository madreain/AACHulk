package com.madreain.libhulk.em

/**
 * @author madreain
 * @date 2020/3/17.
 * module：
 * description：网络请求展示
 */

enum class RequestDisplay {
    NULL, TOAST, REPLACE
    // NULL //无交互
    // TOAST //接口开始showDialogProgress()---->>接口结束 dismissDialog() 错误Toast
    //  REPLACE //接口开始showLoading()---->>接口结束 :成功：restore(),失败：showError();
}