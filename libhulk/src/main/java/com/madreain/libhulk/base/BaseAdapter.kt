package com.madreain.libhulk.base

import com.chad.library.adapter.base.BaseQuickAdapter
import com.madreain.libhulk.view.baseviewholder.HulkViewHolder

/**
 * @author madreain
 * @date 2020/3/23.
 * module：继承BaseQuickAdapter，自定义通用方法
 * description：
 */
abstract class BaseAdapter<T>(layoutResId: Int, data: MutableList<T>) :

    BaseQuickAdapter<T, HulkViewHolder>(layoutResId, data) {

    open fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

}