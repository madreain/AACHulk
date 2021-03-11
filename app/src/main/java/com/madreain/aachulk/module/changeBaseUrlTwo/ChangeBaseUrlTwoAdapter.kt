//package com.madreain.aachulk.module.changeBaseUrlTwo;
//
//import com.madreain.aachulk.R;
//import androidx.databinding.DataBindingUtil
//import com.madreain.aachulk.databinding.ItemActivityChangeBaseUrlTwoBinding
//import com.madreain.libhulk.base.BaseAdapter
//import com.madreain.libhulk.view.baseviewholder.HulkViewHolder
//import java.util.*
//
///**
// * @author madreain
// * @date
// * module：
// * description：
// */
//
//public class ChangeBaseUrlTwoAdapter :
//    BaseAdapter<ChangeBaseUrlTwoListData>(R.layout.item_activity_change_base_url_two, ArrayList()) {
//
//    override fun onItemViewHolderCreated(viewHolder: HulkViewHolder, viewType: Int) {
//        DataBindingUtil.bind<ItemActivityChangeBaseUrlTwoBinding>(viewHolder.itemView)
//    }
//
//    override fun convert(helper: HulkViewHolder, item: ChangeBaseUrlTwoListData) {
//        val itemListBinding = helper.getBinding<ItemActivityChangeBaseUrlTwoBinding>()
//        if (itemListBinding != null) {
//            itemListBinding.changeBaseUrlTwoListData = item
//        }
//    }
//
//}