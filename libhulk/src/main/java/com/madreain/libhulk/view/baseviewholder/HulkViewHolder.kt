package com.madreain.libhulk.view.baseviewholder

import android.view.View
import androidx.annotation.IntDef
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @author madreain
 * @date 2020/3/23.
 * module：
 * description：继承BaseViewHolder，自定义通用方法
 *
 * https://www.jianshu.com/p/b343fcff51b0根据里面的注意事项介绍：
 * 需要单独建一个外部类继承BaseViewHolder，否则部分机型会出现ClassCastException，
 * 如果是内部类的构造方法要是public，定义的那个类也最好是public。
 */
class HulkViewHolder(view: View) : BaseViewHolder(view) {
    @IntDef(View.VISIBLE, View.INVISIBLE, View.GONE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    internal annotation class Visibility

    fun setVisibility(viewId: Int, @Visibility visibility: Int): HulkViewHolder {
        val view: View = getView(viewId)
        view.visibility = visibility
        return this
    }


}