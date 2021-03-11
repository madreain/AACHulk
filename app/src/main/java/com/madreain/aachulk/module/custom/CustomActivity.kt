package com.madreain.aachulk.module.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.madreain.aachulk.R
import com.madreain.aachulk.consts.RouteKeys
import com.madreain.aachulk.consts.RouteUrls
import com.madreain.aachulk.module.list.ListAdapter
import com.madreain.aachulk.module.list.ListViewModel
import com.madreain.aachulk.module.single.SingleData
import com.madreain.aachulk.utils.ActionBarUtils
import com.madreain.aachulk.view.CustomInitView
import com.madreain.libhulk.components.base.BaseActivity
import com.madreain.libhulk.components.base.IPageInit
import com.madreain.libhulk.components.view.InitView
import com.madreain.libhulk.components.view.list.ListResult
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @author madreain
 * @date 2020/3/31.
 * module：
 * description：
 */
@Route(path = RouteUrls.Custom)
public class CustomActivity :
    BaseActivity(R.layout.activity_list) {

    @Autowired(name = RouteKeys.CustomKey)
    @JvmField
    var customValue: String? = null

    private val listViewModel by viewModels<ListViewModel>()

    override fun init(savedInstanceState: Bundle?) {
        ToastUtils.showLong("这是Arouter传递过来的值: " + customValue)
        //ActionBar相关设置
        ActionBarUtils.setSupportActionBarWithBack(toolbar, null, View.OnClickListener {
            onBackPressed()
        })
        ActionBarUtils.setToolBarTitleText(toolbar, "自定义view展示,这里只修改了自定义的loading")
        //自定义设置空的默认界面
        listView.setEmpty(
            LayoutInflater.from(this)
                .inflate(R.layout.custom_view_empty_page, null)
        )
        val listAdapter = ListAdapter()
        listView.setAdapter(listAdapter)
        listView.setOnDataLoadListener { page, isFirst ->
            //请求接口
            listViewModel.searchCity(this, "广德",
                onSuccess = {
                    listView.attachData(
                        ListResult(
                            true,
                            it,
                            listViewModel.nextPage
                        )
                    )
                }, onError = {
                    listView.attachData(
                        ListResult<SingleData>(false, null, null)
                    )
                })
        }
        listView.autoRefreshNoAnimation()
    }

    /**
     * 自定义错误、loading
     */
    override fun buildPageInit(): IPageInit? {
        val initView = CustomInitView(this)
        findViewById<FrameLayout>(android.R.id.content).addView(
            initView,
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        initView.dismissLoadOrError()
        return initView
    }

}