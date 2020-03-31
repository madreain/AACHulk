package com.madreain.libhulk.utils

import java.io.Closeable
import java.io.IOException

/**
 * @author madreain
 * @date 2019-07-04.
 * module： 关闭相关工具类
 * description：
 */
class CloseUtils private constructor() {
    companion object {
        /**
         * 关闭IO
         *
         * @param closeables closeable
         */
        fun closeIO(vararg closeables: Closeable?) {
            if (closeables == null) return
            for (closeable in closeables) {
                if (closeable != null) {
                    try {
                        closeable.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        /**
         * 安静关闭IO
         *
         * @param closeables closeable
         */
        fun closeIOQuietly(vararg closeables: Closeable?) {
            if (closeables == null) return
            for (closeable in closeables) {
                if (closeable != null) {
                    try {
                        closeable.close()
                    } catch (ignored: IOException) {
                    }
                }
            }
        }
    }

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }
}