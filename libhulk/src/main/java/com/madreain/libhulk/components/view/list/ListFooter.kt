package com.madreain.libhulk.components.view.list

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState

class ListFooter(context: Context?, attrs: AttributeSet?) : ClassicsFooter(context, attrs) {

    init {
        mTitleText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f)
        mTitleText.setTextColor(0xff999999.toInt())
    }

    fun setNoMoreText(noMoreText: String) {
        mTextNothing = noMoreText
    }

    override fun onFinish(layout: RefreshLayout, success: Boolean): Int {
//        super.onFinish(layout, success)
        return 0
    }

    override fun onStateChanged(
        refreshLayout: RefreshLayout,
        oldState: RefreshState,
        newState: RefreshState
    ) {
        val arrowView: View = mArrowView
        if (!mNoMoreData) {
            when (newState) {
                RefreshState.None -> {
                    mProgressView.visibility = View.GONE
                    arrowView.visibility = View.VISIBLE
                    mTitleText.text = mTextPulling
                    arrowView.animate().rotation(180f)
                }
                RefreshState.PullUpToLoad -> {
                    mTitleText.text = mTextPulling
                    arrowView.animate().rotation(180f)
                }
                RefreshState.Loading, RefreshState.LoadReleased -> {
                    arrowView.visibility = View.GONE
                    mTitleText.text = mTextLoading
                }
                RefreshState.ReleaseToLoad -> {
                    mTitleText.text = mTextRelease
                    arrowView.animate().rotation(0f)
                }
                RefreshState.Refreshing -> {
                    mTitleText.text = mTextRefreshing
                    arrowView.visibility = View.GONE
                }
            }
        }
    }
}