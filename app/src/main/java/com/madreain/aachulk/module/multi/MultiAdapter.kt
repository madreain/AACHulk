package com.madreain.aachulk.module.multi;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.madreain.aachulk.R
import kotlinx.android.synthetic.main.item_activity_multi.view.tv
import java.util.*

/**
 * @author madreain
 * @date
 * module：
 * description：
 */

public class MultiAdapter : BaseMultiItemQuickAdapter<MultiListData, BaseViewHolder>(ArrayList()) {

    init {
        addItemType(MultiListData.type_1, R.layout.item_activity_multi)
        addItemType(MultiListData.type_2, R.layout.item_activity_multi2)
    }

    /**
     * 布局
     */
    override fun convert(helper: BaseViewHolder, item: MultiListData) {
        when (item.itemType) {
            MultiListData.type_1 -> {
                helper.itemView.tv.text = item.name
            }
            MultiListData.type_2 -> {
                helper.itemView.tv.text = item.name
            }
        }
    }


}