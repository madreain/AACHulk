package com.madreain.libhulk.mvvm

/**
 * @author madreain
 * @date 2020/3/13.
 * module：
 * description：
 */
interface IListView<T> : IView {
    /**
     * 刷新结束
     */
    fun refreshComplete()

    /**
     * 显示数据
     * @param datas
     * @param pageNum
     */
    fun showListData(datas: List<T>?, pageNum: Int)
}