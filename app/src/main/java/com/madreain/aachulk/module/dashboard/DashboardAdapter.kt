package com.madreain.aachulk.module.dashboard

import androidx.databinding.DataBindingUtil
import com.madreain.aachulk.R
import com.madreain.aachulk.databinding.ItemDashboardBinding
import com.madreain.aachulk.databinding.ItemListBinding
import com.madreain.libhulk.base.BaseAdapter
import com.madreain.libhulk.view.baseviewholder.HulkViewHolder
import java.util.*

/**
 * @author madreain
 * @date 2020/3/23.
 * module：
 * description：
 */
class DashboardAdapter : BaseAdapter<DashboardData>(R.layout.item_dashboard, ArrayList()) {

    override fun onItemViewHolderCreated(viewHolder: HulkViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemDashboardBinding>(viewHolder.itemView)
    }

    override fun convert(helper: HulkViewHolder, item: DashboardData) {
        val itemListBinding = helper.getBinding<ItemDashboardBinding>()
        if (itemListBinding != null) {
            itemListBinding.dashboardData = item
        }
    }

}