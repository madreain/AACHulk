package com.madreain.aachulk.module.detailList

import androidx.databinding.DataBindingUtil
import com.madreain.aachulk.R
import com.madreain.aachulk.databinding.ItemDetailListBinding
import com.madreain.libhulk.base.BaseAdapter
import com.madreain.libhulk.view.baseviewholder.HulkViewHolder
import java.util.*

/**
 * @author madreain
 * @date 2020/3/23.
 * module：
 * description：
 */
class DetailListAdapter : BaseAdapter<DetailListData>(R.layout.item_detail_list, ArrayList()) {

    override fun onItemViewHolderCreated(viewHolder: HulkViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemDetailListBinding>(viewHolder.itemView)
    }

    override fun convert(helper: HulkViewHolder, item: DetailListData) {
        val itemListBinding = helper.getBinding<ItemDetailListBinding>()
        if (itemListBinding != null) {
            itemListBinding.detailListData = item
        }
    }

}