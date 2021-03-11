//package com.madreain.aachulk.module.dialog
//
//import android.os.Bundle
//import android.view.Gravity
//import android.view.View
//import android.view.Window
//import androidx.databinding.ViewDataBinding
//import com.madreain.aachulk.R
//import com.madreain.aachulk.consts.HulkKey
//import com.madreain.aachulk.utils.DrawableUtils
//import com.madreain.libhulk.base.BaseDialogFragment
//import com.madreain.libhulk.utils.StringUtils
//import com.scwang.smartrefresh.layout.SmartRefreshLayout
//import kotlinx.android.synthetic.main.fragment_dialog.*
//
//class DialogFragment : BaseDialogFragment<DialogViewModel, ViewDataBinding>() {
//
//    var title: String? = null
//    var desc: String? = null
//    var left: String? = null
//    var right: String? = null
//    var remind: String? = null
//    //false可消失
//    var externalArea = false
//    var isRemind = false
//
//    override fun getLayoutId(): Int {
//        return R.layout.fragment_dialog
//    }
//
//    override fun getReplaceView(): View {
//        return fl_parent
//    }
//
//    override fun onStart() {
//        super.onStart()
//        dialog?.let {
//            // 下面这些设置必须在此方法(onStart())中才有效
//            val window = it.window
//            window?.let {
//                // 如果不设置这句代码, 那么弹框就会与四边都有一定的距离
//                it.setBackgroundDrawableResource(android.R.color.transparent)
//                // 设置动画
//                it.setWindowAnimations(R.style.CommonDialog)
//                val params = it.attributes
//                //背景透明
//                params.dimAmount = 0.4f
//                params.gravity = Gravity.CENTER
//                // 如果不设置宽度,那么即使你在布局中设置宽度为 match_parent 也不会起作用
//                params.width = resources.displayMetrics.widthPixels
//                it.attributes = params
//            }
//        }
//    }
//
//    override fun init(savedInstanceState: Bundle?) {
//        // 去掉默认的标题
//        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        //数据的获取
//        val bundle = arguments
//        if (bundle != null) {
//            title = bundle.getString(HulkKey.CommonTitle)
//            desc = bundle.getString(HulkKey.CommonDesc)
//            left = bundle.getString(HulkKey.CommonLeft)
//            right = bundle.getString(HulkKey.CommonRight)
//            remind = bundle.getString(HulkKey.CommonRemind)
//            externalArea = bundle.getBoolean(HulkKey.CommonExternalArea)
//        }
//        //样式的展示
//        //标题
//        //样式的展示
//        // 标题
//        if (StringUtils.isEmpty(title)) {
//            tv_title.setVisibility(View.GONE)
//        } else {
//            tv_title.setText(title)
//        }
//        //内容
//        //内容
//        if (StringUtils.isEmpty(desc)) {
//            tv_desc.setVisibility(View.GONE)
//        } else {
//            tv_desc.setText(desc)
//        }
//        //左边隐藏
//        //左边隐藏
//        if (StringUtils.isEmpty(left)) {
//            tv_left.setVisibility(View.GONE)
//            v_vertical.setVisibility(View.GONE)
//        } else {
//            tv_left.setText(left)
//        }
//        //右边隐藏
//        //右边隐藏
//        if (StringUtils.isEmpty(right)) {
//            tv_right.setVisibility(View.GONE)
//            v_vertical.setVisibility(View.GONE)
//        } else {
//            tv_right.setText(right)
//        }
//        //提醒
//        //提醒
//        if (StringUtils.isEmpty(remind)) {
//            tv_remind.setVisibility(View.GONE)
//        } else {
//            tv_remind.setText(remind)
//        }
////点击外部区域是否消失
//        //点击外部区域是否消失
//        dialog?.let {
//            if (externalArea) {
//                it.setCanceledOnTouchOutside(false)
//            } else {
//                it.setCanceledOnTouchOutside(true)
//            }
//        }
//        /**
//         * 相关监听事件
//         */
//        tv_left.setOnClickListener {
//            onLeftClick?.let { it1 -> it1(isRemind) }
//            dismiss()
//        }
//        tv_right.setOnClickListener {
//            onRightClick?.let { it1 -> it1(isRemind) }
//            dismiss()
//        }
//        tv_remind.setOnClickListener {
//            //未选中到选中
//            //未选中到选中
//            if (!isRemind) {
//                context?.let {
//                    DrawableUtils.setDrawableLeft(
//                        tv_remind,
//                        it.resources.getDrawable(R.drawable.ic_select)
//                    )
//                }
//            } else {
//                context?.let {
//                    DrawableUtils.setDrawableLeft(
//                        tv_remind,
//                        it.resources.getDrawable(R.drawable.ic_unselect)
//                    )
//                }
//            }
//            isRemind = !isRemind
//        }
//    }
//
//    override fun getSmartRefreshLayout(): SmartRefreshLayout? {
//        return null
//    }
//
//    override fun refreshData() {
//
//    }
//
//    private var onLeftClick: ((isRemind: Boolean) -> Unit)? = null
//    private var onRightClick: ((isRemind: Boolean) -> Unit)? = null
//
//    fun setOnLeftRightClickListener(
//        onLeftClick: (isRemind: Boolean) -> Unit,
//        onRightClick: (isRemind: Boolean) -> Unit
//    ) {
//        this.onLeftClick = onLeftClick
//        this.onRightClick = onRightClick
//    }
//
//}