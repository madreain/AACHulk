package com.madreain.aachulk.module.multi;

import androidx.annotation.Keep;
import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * @author madreain
 * @date
 * module：
 * description：
 */
@Keep
public class MultiListData : MultiItemEntity {

    var code: String? = null
    var name: String? = null

    override val itemType: Int
        get() = when (name) {
            "中国台湾" -> type_1
            else -> type_2
        }

    companion object {
        //模拟两种布局
        val type_1 = 1000
        val type_2 = 2000
    }

}
