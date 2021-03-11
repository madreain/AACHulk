package com.madreain.libhulk.utils

import kotlin.math.abs

class ClickUtils {

    companion object {
        private var lastTime: Long = 0

        // 防重复点击时间间隔
        private const val delayTime: Long = 500


        fun isClickAvailable(): Boolean {
            val b = abs(System.currentTimeMillis() - lastTime) >= delayTime
            if (b) {
                lastTime = System.currentTimeMillis()
            }
            return b
        }
    }
}