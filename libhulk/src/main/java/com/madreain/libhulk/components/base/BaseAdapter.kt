package com.madreain.libhulk.components.base

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

abstract class BaseAdapter<T>(layoutResId: Int, data: MutableList<T>) :

    BaseQuickAdapter<T, BaseViewHolder>(layoutResId, data) {

    open fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

}