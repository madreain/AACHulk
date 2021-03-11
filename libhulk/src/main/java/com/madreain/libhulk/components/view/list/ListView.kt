package com.madreain.libhulk.components.view.list

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.madreain.libhulk.R
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.common_view_list.view.*

open class ListView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    var requestPageNum: Long = 0


    lateinit var emptyView: View

    lateinit var recyclerView: RecyclerView
    lateinit var refreshLayout: SmartRefreshLayout

    private lateinit var refreshFooter: ListFooter

    var hasMore: Boolean = true

    private fun initEmpty() {
        val emptyView = LayoutInflater.from(context).inflate(
            R.layout.common_view_empty_page, this, false
        )
        val layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        emptyView.visibility = View.GONE
        refreshEmpty.addView(emptyView, layoutParams)
        this.emptyView = emptyView
    }

    private fun initRefresh() {
        val contentView = LayoutInflater.from(context).inflate(
            R.layout.common_view_list, this, false
        )
        recyclerView = contentView.recyclerView
        refreshLayout = contentView.smartRefreshLayout
        refreshFooter = contentView.refreshFooter
        recyclerView.layoutManager = LinearLayoutManager(context)
        refreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(mrefreshLayout: RefreshLayout) {
                onDataLoad?.let {
                    it(requestPageNum, false)
                }
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                onDataLoad?.let {
                    requestPageNum = 0
                    it(requestPageNum, false)
                }
            }
        })
        val layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        addView(contentView, layoutParams)
    }

    init {
        initRefresh()
        initEmpty()
    }

    fun <T : RecyclerView.ViewHolder> setAdapter(adapter: RecyclerView.Adapter<T>) {
        recyclerView.adapter = adapter
    }

    private var onDataLoad: ((page: Long, isFirst: Boolean) -> Unit)? = null


    fun setOnDataLoadListener(onDataLoad: (page: Long, isFirst: Boolean) -> Unit) {
        this.onDataLoad = onDataLoad
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> attachData(listResult: ListResult<T>) {
        if (listResult.success) {
            if (requestPageNum == 0L) {
                if (listResult.datas != null && listResult.datas.isNotEmpty()) {
                    emptyView.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    refreshLayout.setEnableLoadMore(true)
                    (recyclerView.adapter as BaseQuickAdapter<T, BaseViewHolder>).setNewData(
                        listResult.datas as MutableList<T>
                    )
                } else {
                    emptyView.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    refreshLayout.setEnableLoadMore(false)
                    (recyclerView.adapter as BaseQuickAdapter<T, BaseViewHolder>).setNewData(
                        listResult.datas as MutableList<T>
                    )
                }
            } else {
                (recyclerView.adapter as BaseQuickAdapter<T, BaseViewHolder>).addData(listResult.datas as MutableList<T>)
            }
            requestPageNum = if (listResult.lastPageValue == null) 0 else listResult.lastPageValue
            refreshLayout.finishRefresh()
            if (listResult.lastPageValue != null && listResult.lastPageValue != 0L) {
                hasMore = true
                refreshLayout.finishLoadMore()
            } else {
                hasMore = false
                refreshLayout.finishLoadMoreWithNoMoreData()
            }
        } else {
            refreshLayout.finishRefresh()
            if (hasMore)
                refreshLayout.finishLoadMore()
            else {
                refreshLayout.finishLoadMoreWithNoMoreData()
            }
        }
    }


    fun setEmpty(view: View) {
        refreshEmpty.removeAllViews()
        val layoutParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.gravity = Gravity.CENTER
        view.visibility = View.GONE
        this.emptyView = view
        refreshEmpty.addView(view, layoutParams)
    }

    fun setEmpty(text: String, resId: Int = R.drawable.common_page_empty) {
        emptyView.findViewById<TextView>(R.id.tvEmpty).text = text
        emptyView.findViewById<ImageView>(R.id.ivEmpty).setImageResource(resId)
    }


    /**
     * 自动刷新
     */
    fun autoRefresh() {
        smartRefreshLayout.autoRefresh()
    }

    /**
     * 这个方法设置得在setOnDataLoadListener之后
     */
    fun autoRefreshNoAnimation() {
        onDataLoad?.let {
            requestPageNum = 0
            it(requestPageNum, true)
        }
    }


    fun setListPaddingTop(paddingTop: Float) {
        recyclerView.setPadding(
            recyclerView.paddingLeft,
            ConvertUtils.dp2px(paddingTop),
            recyclerView.paddingRight,
            recyclerView.paddingBottom
        )
    }

    fun <T> getData(): List<T> {
        return (recyclerView.adapter as BaseQuickAdapter<T, BaseViewHolder>).data
    }

    fun setNoMoreText(noMoreText: String) {
        refreshFooter.setNoMoreText(noMoreText)
    }

    /**
     * 设置是否可以刷新
     */
    fun setRefreshEnable(enable: Boolean) {
        refreshLayout.setEnableRefresh(enable)
    }


    /**
     * 设置是否启用上拉加载更多
     */
    fun setEnableLoadMore(enable: Boolean) {
        refreshLayout.setEnableLoadMore(enable)
    }


}