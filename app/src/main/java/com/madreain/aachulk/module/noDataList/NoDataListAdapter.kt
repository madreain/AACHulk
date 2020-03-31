package com.madreain.aachulk.module.noDataList

import androidx.databinding.DataBindingUtil
import com.madreain.aachulk.R
import com.madreain.aachulk.databinding.ItemListBinding
import com.madreain.aachulk.module.list.ListData
import com.madreain.libhulk.base.BaseAdapter
import com.madreain.libhulk.view.baseviewholder.HulkViewHolder
import java.util.*

/**
 * @author madreain
 * @date 2020/3/23.
 * module：
 * description：
 */
class NoDataListAdapter : BaseAdapter<ListData>(R.layout.item_list, ArrayList()) {

    override fun onItemViewHolderCreated(viewHolder: HulkViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemListBinding>(viewHolder.itemView)
    }

    override fun convert(helper: HulkViewHolder, item: ListData) {
        val itemListBinding = helper.getBinding<ItemListBinding>()
        if (itemListBinding != null) {
            itemListBinding.listData = item
        }
    }

}