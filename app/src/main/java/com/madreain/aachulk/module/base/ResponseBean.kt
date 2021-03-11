package com.madreain.aachulk.module.base

import com.madreain.libhulk.network.exception.ReturnCodeException
import com.madreain.libhulk.network.exception.ReturnEmptyException
import com.madreain.libhulk.network.model.BaseResponseBean

class ResponseBean<T>(code: Int?, message: String, private val data: T?) :
    BaseResponseBean(code, message) {

    fun asResult(): T {
        //成功
        if (code == 0) {
            if (data != null) {
                return data
            } else {
                //状态码错误
                throw ReturnEmptyException()
            }
        } else {
            //状态码错误
            throw ReturnCodeException(code, message)
        }
    }


    fun asResultNullable(): T? {
        //成功
        if (code == 0) {
            return data
        } else {
            //状态码错误
            throw ReturnCodeException(code, message)
        }
    }


}