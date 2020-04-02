package com.madreain.aachulk.module.multi;

import androidx.databinding.DataBindingUtil
import com.madreain.aachulk.R
import com.madreain.aachulk.databinding.ItemActivityMulti2Binding
import com.madreain.aachulk.databinding.ItemActivityMultiBinding
import com.madreain.aachulk.databinding.ItemListBinding
import com.madreain.libhulk.base.BaseMultiAdapter
import com.madreain.libhulk.view.baseviewholder.HulkViewHolder
import java.util.*

/**
 * @author madreain
 * @date
 * module：
 * description：
 */

public class MultiAdapter : BaseMultiAdapter<MultiListData>(ArrayList()) {


    override fun onItemViewHolderCreated(viewHolder: HulkViewHolder, viewType: Int) {
        when (viewType) {
            MultiListData.type_1 -> {
                DataBindingUtil.bind<ItemActivityMultiBinding>(viewHolder.itemView)
            }
            MultiListData.type_2 -> {
                DataBindingUtil.bind<ItemActivityMulti2Binding>(viewHolder.itemView)
            }
        }
    }

    /**
     * 布局
     */
    override fun convert(helper: HulkViewHolder, item: MultiListData) {
        when (item.itemType) {
            MultiListData.type_1 -> {
                val itemListBinding = helper.getBinding<ItemActivityMultiBinding>()
                if (itemListBinding != null) {
                    itemListBinding.multiListData = item
                }
            }
            MultiListData.type_2 -> {
                val itemListBinding = helper.getBinding<ItemActivityMulti2Binding>()
                if (itemListBinding != null) {
                    itemListBinding.multiListData = item
                }
            }
        }
    }

    override fun addItemType() {
        addItemType(MultiListData.type_1, R.layout.item_activity_multi)
        addItemType(MultiListData.type_2, R.layout.item_activity_multi2)
    }

}