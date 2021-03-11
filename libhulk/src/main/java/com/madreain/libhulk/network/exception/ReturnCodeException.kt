package com.madreain.libhulk.network.exception

class ReturnCodeException(val code: Int?=null, val msg: String) : Exception(msg)