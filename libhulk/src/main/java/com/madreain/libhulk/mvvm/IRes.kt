package com.madreain.libhulk.mvvm

/**
 * @author madreain
 * @date 2020/3/13.
 * module：
 * description：
 */
interface IRes<T> {
    fun getMsg(): String?

    fun getCode(): String?

    fun getResult(): T

    fun getVersion(): String?


}