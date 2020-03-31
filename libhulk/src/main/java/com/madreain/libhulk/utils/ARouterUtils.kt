package com.madreain.libhulk.utils

import android.net.Uri
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author madreain
 * @date 2019-07-04.
 * module：ARouter工具类封装
 * description：
 */
class ARouterUtils {
    /**
     * 设置跳转的路由
     * @param path
     * @param group
     * @return
     */
    fun build(path: String?, group: String?): Postcard {
        return ARouter.getInstance().build(path, group)
    }

    companion object {
        /**
         * Activity/Fragment类里面进行Arouter 注入
         * @param thiz
         */
        fun inject(thiz: Any?) {
            ARouter.getInstance().inject(thiz)
        }

        /**
         * 设置跳转的路由
         * @param url
         * @return
         */
        fun build(url: String?): Postcard {
            return ARouter.getInstance().build(url)
        }

        /**
         * 设置跳转的路由
         * @param uri
         * @return
         */
        fun build(uri: Uri?): Postcard {
            return ARouter.getInstance().build(uri)
        }

        /**
         * 在Appilication的onTerminte进行销毁
         */
        fun destory() {
            ARouter.getInstance().destroy()
        }
    }
}