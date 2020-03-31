package com.madreain.libhulk.http


class ResponseThrowable : Exception {
    var code: String
    var errMsg: String

    constructor(mcode: String, merrMsg: String, e: Throwable? = null) : super(e) {
        code = mcode
        errMsg = merrMsg
    }

}

