package com.madreain.aachulk.utils

import android.graphics.drawable.Drawable
import android.widget.TextView

/**
 * @author madreain
 * @date 2020/3/27.
 * module：
 * description：
 */
class DrawableUtils {
    companion object {
        fun setDrawableTop(
            tv: TextView,
            drawable: Drawable
        ) {
            drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            tv.setCompoundDrawables(null, drawable, null, null)
        }

        fun setDrawableLeft(
            tv: TextView,
            drawable: Drawable
        ) {
            drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            tv.setCompoundDrawables(drawable, null, null, null)
        }

        fun setDrawableRight(
            tv: TextView,
            drawable: Drawable
        ) {
            drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            tv.setCompoundDrawables(null, null, drawable, null)
        }

        fun setDrawableNull(tv: TextView) {
            tv.setCompoundDrawables(null, null, null, null)
        }
    }
}