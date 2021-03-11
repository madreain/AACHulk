package com.madreain.libhulk.extensions

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.madreain.libhulk.utils.ClickUtils
import com.madreain.libhulk.utils.Utils

/**
 * 设置点击事件，防抖动
 */
fun View.clickDelay(onClick: (view: View) -> Unit) {
    setOnClickListener {
        if (ClickUtils.isClickAvailable())
            onClick(it)
    }
}

fun View.clickDelay(onClickListener: View.OnClickListener?) {
    setOnClickListener {
        if (ClickUtils.isClickAvailable())
            onClickListener?.onClick(it)
    }
}

/**
 * 设置TextView左边的drawable
 */
fun TextView.leftDrawable(
    resId: Int
) {
    setCompoundDrawablesWithIntrinsicBounds(
        ContextCompat.getDrawable(context, resId),
        null,
        null,
        null
    )
}

/**
 * 设置TextView上边的drawable
 */
fun TextView.topDrawable(
    resId: Int
) {
    setCompoundDrawablesWithIntrinsicBounds(
        null,
        ContextCompat.getDrawable(context, resId),
        null,
        null
    )
}

/**
 * 设置TextView右边的drawable
 */
fun TextView.rightDrawable(
    resId: Int
) {
    setCompoundDrawablesWithIntrinsicBounds(
        null,
        null,
        ContextCompat.getDrawable(context, resId),
        null
    )
}

/**
 * 设置TextView下边的drawable
 */
fun TextView.bottomDrawable(
    resId: Int
) {
    setCompoundDrawablesWithIntrinsicBounds(
        null,
        null,
        null,
        ContextCompat.getDrawable(context, resId)
    )
}

/**
 * 设置文字颜色资源
 */
fun TextView.textColorRes(id: Int) {
    setTextColor(ContextCompat.getColor(Utils.context, id))
}


/**
 * ImageView加载圆形图片
 */
fun ImageView.loadCircle(
    url: String?
) {
    Glide.with(Utils.context)
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}


/**
 * ImageView加载方形图片
 */
fun ImageView.loadRectangle(
    url: String?,
    radius: Int = 0
) {
    var glide = Glide.with(Utils.context).load(url)
    if (radius != 0) {
        glide = glide.transform(CenterCrop(), RoundedCorners(Utils.dp2px(radius.toFloat())))
    }
    glide.into(this)
}