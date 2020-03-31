package com.madreain.libhulk.utils

import android.content.Context

/**
 * @author madreain
 * @date 2019-07-04.
 * module： Utils初始化相关
 * description：
 */
class Utils private constructor() {
    companion object {
        private var context: Context? = null
        /**
         * 初始化工具类
         *
         * @param context 上下文
         */
        fun init(context: Context) {
            Companion.context = context.applicationContext
        }

        /**
         * 获取ApplicationContext
         *
         * @return ApplicationContext
         */
        fun getContext(): Context? {
            if (context != null) return context
            throw NullPointerException("u should init first")
        }
    }

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }
}