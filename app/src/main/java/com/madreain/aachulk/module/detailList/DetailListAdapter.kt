package com.madreain.aachulk.module.detailList

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.madreain.aachulk.R
import com.madreain.aachulk.module.single.SingleData
import com.madreain.libhulk.components.base.BaseAdapter
import kotlinx.android.synthetic.main.item_list.view.*
import java.util.*

/**
 * @author madreain
 * @date 2020/3/23.
 * module：
 * description：
 */
class DetailListAdapter : BaseAdapter<SingleData>(R.layout.item_list, ArrayList()) {

    override fun convert(helper: BaseViewHolder, item: SingleData) {
        helper.itemView.tv.text = item.toString()
    }

}