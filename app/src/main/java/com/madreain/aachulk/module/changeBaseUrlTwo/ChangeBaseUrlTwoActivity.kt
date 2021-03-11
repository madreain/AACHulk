//package com.madreain.aachulk.module.changeBaseUrlTwo;
//
//import android.os.Bundle;
//import android.view.View;
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.alibaba.android.arouter.facade.annotation.Route
//
//import com.madreain.aachulk.R;
//import com.madreain.aachulk.consts.ARouterUri
//import com.madreain.libhulk.base.BaseListActivity;
//import com.madreain.aachulk.databinding.ActivityChangeBaseUrlTwoBinding
//import com.madreain.aachulk.utils.ActionBarUtils
//import com.scwang.smartrefresh.layout.SmartRefreshLayout
//
//import kotlinx.android.synthetic.main.activity_change_base_url_two.*
//import kotlinx.android.synthetic.main.toolbar.*
//
///**
// * @author madreain
// * @date
// * module：
// * description：
// */
//
//@Route(path = ARouterUri.ChangeBaseUrlTwoActivity)
//public class ChangeBaseUrlTwoActivity :
//    BaseListActivity<ChangeBaseUrlTwoViewModel, ActivityChangeBaseUrlTwoBinding, ChangeBaseUrlTwoAdapter, ChangeBaseUrlTwoListData>() {
//
//
//    override fun getLayoutId(): Int {
//        return R.layout.activity_change_base_url_two;
//    }
//
//    override fun getReplaceView(): View {
//        return activity_change_base_url_two
//    }
//
//    override fun init(savedInstanceState: Bundle?) {
//        //ActionBar相关设置
//        ActionBarUtils.setSupportActionBarWithBack(toolbar, null, View.OnClickListener {
//            onBackPressed()
//        })
//        ActionBarUtils.setToolBarTitleText(toolbar, "多个BaseUrl-2")
//        //请求接口
//        mViewModel.onStart()
//    }
//
//    /**
//     * list相关方法
//     */
//    override fun loadPageListData(pageNo: Int) {
//        //请求接口
//        mViewModel.getCategories(pageNo)
//    }
//
//    override fun getSmartRefreshLayout(): SmartRefreshLayout {
//        return smartRefreshLayout
//    }
//
//    override fun getRecyclerView(): RecyclerView {
//        return recyclerView
//    }
//
//    override fun getLayoutManager(): RecyclerView.LayoutManager {
//        return LinearLayoutManager(this)
//    }
//
//    override fun getAdapter() {
//        adapter = ChangeBaseUrlTwoAdapter()
//    }
//}
