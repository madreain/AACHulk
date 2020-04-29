package com.madreain.libhulk.mvvm

/**
 * @author madreain
 * @date 2020/3/13.
 * module：
 * description：
 */
interface IRes<T> {
    fun getHulkMsg(): String
    fun getHulkCode(): String
    fun getHulkResult(): T
    fun getHulkVersion(): String
}