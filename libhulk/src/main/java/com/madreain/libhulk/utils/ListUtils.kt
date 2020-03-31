package com.madreain.libhulk.utils

import java.util.*

/**
 * @author madreain
 * @date 2020/3/16.
 * module： 集合判断工具类
 * description：
 */
object ListUtils {
    /**
     * 判断集合list数据是否为空
     *
     * @param list
     * @return
     */
    fun <T> isEmpty(list: List<*>?): Boolean {
        return if (list == null || list.size == 0) true else false
    }

    /**
     * 判断集合list数据是否为非空
     *
     * @param list
     * @return
     */
    fun <T> isNotEmpty(list: List<T>?): Boolean {
        return !isEmpty<Any>(list)
    }

    fun clearList(list: MutableList<*>) {
        if (!isEmpty<Any>(list)) {
            list.clear()
        }
    }

    /**
     * 获取集合list数据长度
     *
     * @param list
     * @return
     */
    fun <T> getCount(list: List<T>?): Int {
        return list?.size ?: 0
    }

    /**
     * 反转数据
     *
     * @param sourceList
     * @return
     */
    fun <V> invertList(sourceList: List<V>): List<V> {
        if (isEmpty<Any>(sourceList)) {
            return sourceList
        }
        val invertList: MutableList<V> = ArrayList(sourceList.size)
        for (i in sourceList.indices.reversed()) {
            invertList.add(sourceList[i])
        }
        return invertList
    }
}