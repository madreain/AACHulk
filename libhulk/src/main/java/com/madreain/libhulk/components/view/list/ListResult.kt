package com.madreain.libhulk.components.view.list

data class ListResult<T>(val success: Boolean, val datas: List<T>?, val lastPageValue: Long?)