//package com.madreain.aachulk.module.base
//
//import androidx.annotation.Keep
//import com.madreain.libhulk.mvvm.IRes
//
///**
// * @author madreain
// * @date 2020-03-16.
// * module：接口返回封装类
// * description：
// */
//@Keep
//data class GankBaseRes<T>(
//    val msg: String,
//    val status: String,
//    val `data`: T,
//    val version: String
//) : IRes<T> {
//    override fun getHulkMsg() = msg
//    override fun getHulkCode() = status
//    override fun getHulkResult() = data
//    override fun getHulkVersion() = version
//}