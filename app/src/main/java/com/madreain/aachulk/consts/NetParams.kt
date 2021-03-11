package com.madreain.aachulk.consts

/**
 * 全部所有的枚举
 */
object NetParams {

    //类名以接口路径命名
    object Login {
        //类名以参数名命名
        enum class TYPE(val type: Int) {
            WX(0),
            ALI(1)
        }
    }

}