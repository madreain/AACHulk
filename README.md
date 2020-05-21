AACHulk

---
> AACHulk是以Google的ViewModel+DataBinding+LiveData+Lifecycles框架为基础，
结合Okhttp+Retrofit+BaseRecyclerViewAdapterHelper+SmartRefreshLayout+ARouter打造的一款快速开发框架，
开发语言是Kotlin，再结合[AACHulkTemplate模版开发](https://github.com/madreain/AACHulkTemplate)进行开发，
避免一些繁琐的操作，提供开发效率

[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](https://www.apache.org/licenses/LICENSE-2.0)

## 功能介绍

1.支持多服务器地址、多成功码、各种超时时间、各种拦截器、Arouter等的配置

2.支持自定义各种非正常态View替换

3.支持接口调用出错时重试

4.支持多种Activity、Fragment展示，满足业务需求

5.支持多布局适配器

6.支持通用代码生成[AACHulkTemplate模版](https://github.com/madreain/AACHulkTemplate)

## 第三方库

1. [`Okhttp` 一个用于Android、Kotlin和Java的HTTP客户端](https://github.com/square/okhttp)
2. [`Retrofit` 为Android和Java提供安全的HTTP客户端](https://github.com/square/retrofit)
3. [`BaseRecyclerViewAdapterHelper` 功能强大、灵活的万能适配器](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
4. [`SmartRefreshLayout` Android智能下拉刷新框架](https://github.com/scwang90/SmartRefreshLayout)
5. [`ARouter` 帮助 Android App 进行组件化改造的路由框架](https://github.com/alibaba/ARouter)

## 基础功能

1.主项目启用dataBinding

```
    dataBinding {
        enabled true
    }
```

2.添加依赖

在project的build.grade加入

```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
        google()
        jcenter()
    }
}
```

在主项目app的build.grade加入

```
api 'com.madreain:libhulk:1.0.4'
```

3.继承HulkApplication，配置相关配置项

```
    HulkConfig.builder() //这里只需要选择设置一个
//            .setRetSuccess(BuildConfig.CODE_SUCCESS)
            .setRetSuccessList(BuildConfig.CODELIST_SUCCESS)
            //设置多baseurl的retcode
            .addRetSuccess(HulkKey.WANANDROID_DOMAIN_NAME, BuildConfig.WANANDROID_CODELIST_SUCCESS)
            .addRetSuccess(HulkKey.GANK_DOMAIN_NAME, BuildConfig.GANK_CODELIST_SUCCESS)
            .setBaseUrl(BuildConfig.BASE_URL)
            //设置多baseurl
            .addDomain(HulkKey.WANANDROID_DOMAIN_NAME, HulkKey.WANANDROID_API)
            .addDomain(HulkKey.GANK_DOMAIN_NAME, HulkKey.GANK_API)
            .setLogOpen(BuildConfig.OPEN_LOG)
            .setArouterOpen(BuildConfig.OPEN_AROUTER)
            .addOkHttpInterceptor(RequestHeaderInterceptor()) //请求头拦截器
            .addOkHttpInterceptor(
                BuildConfig.OPEN_LOG,
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            ) //okhttp请求日志开关+消息拦截器.md
            .addRetCodeInterceptors(SessionInterceptor()) // returnCode非正常态拦截器
            .setRetrofit(
                ApiClient.getInstance().getRetrofit(
                    ApiClient.getInstance().getOkHttpClient(
                        HulkConfig.getOkHttpInterceptors()
                    )
                )
            )
            .build()
```
上面这些配置项的配置可参考demo进行自身项目的配置

这里还可根据[SmartRefreshLayout相关文档](https://github.com/scwang90/SmartRefreshLayout)配置统一样式，也可单独设置，也可自定义，根据自身项目选择

4.继承IRes，根据自身项目封装统一的数据接受

5.编写ApiService，放接口

6.编写通用的Toolbar(自行选择)
因受kotlin-android-extensions这个插件可能只管自己module的资源文件的影响，没法将通用的toolbar.xml写在libhulk中供app使用，因此只能在app项目中写通用的toolbar.xml

⚠️ 如果大佬们有好的实现方法欢迎指教

️🔥️🔥️🔥 [AACHulkTemplate模版](https://github.com/madreain/AACHulkTemplate),此模版使用得保证ApiService、toolbar.xml已创建，使用者也可根据自身项目进行修改

## 快速开发

AACHulkTemplate模版用起来是相当香的，接下来讲一下自已手动的步骤，以SingleActivity举例

1.新建SingleActivity继承BaseActivity

```
class SingleActivity : BaseActivity<BaseViewModel, ViewDataBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_single
    }

    override fun getReplaceView(): View {
        return layout
    }

    override fun init(savedInstanceState: Bundle?) {

    }

    /**
     * 设置SmartRefreshLayout
     */
    override fun getSmartRefreshLayout(): SmartRefreshLayout? {
        return null
    }

    override fun refreshData() {

    }

}
```

ViewDataBinding将会用在activity_single.xml中关联ActivitySingleBinding替换掉
BaseViewModel将会用新建的SingleViewModel继承BaseViewModel替换掉

2.创建对应的对象

```
@Keep
class SingleData {
    var code: String? = null
    var name: String? = null
}
```

3.关联ViewDataBing

在activity_single.xml中关联ActivitySingleBinding

```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <import type="java.util.List" />

        <import type="com.madreain.aachulk.module.single.SingleData" />

        <variable
            name="singleDataS"
            type="List&lt;SingleData>" />

        <variable
            name="singleData"
            type="SingleData" />

    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/single_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context="com.madreain.aachulk.module.main.MainActivity">

        <include
            android:id="@+id/tbar"
            layout="@layout/toolbar" />

        <androidx.constraintlayout.widget.ConstraintLayout
            android:id="@+id/layout"
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <TextView
                android:id="@+id/tv"
                android:layout_width="@dimen/dp60"
                android:layout_height="wrap_content"
                android:background="@color/colorPrimary"
                android:text="@{singleData.code,default=`接口调用之前`}"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintLeft_toLeftOf="parent"
                app:layout_constraintRight_toRightOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                tools:text="接口调用结果" />

        </androidx.constraintlayout.widget.ConstraintLayout>


    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```

4.新建SingleViewModel继承BaseViewModel

```
class SingleViewModel : BaseViewModel<ApiService>() {

    public override fun onStart() {
        cityList()
    }

    //这里举例的是相关接口的调用，具体可参考demo
    var result = MutableLiveData<List<SingleData>>()
    private fun cityList() {
        launchOnlyresult(
            //调用接口方法
            block = {
                getApiService().getCityList()
            },
            //重试
            reTry = {
                //调用重试的方法
                cityList()
            },
            //成功
            success = {
                //成功回调
                result.value = it
            }, type = RequestDisplay.REPLACE
        )
    }
}

```

5.替换ViewDataBinding、BaseViewModel
ActivitySingleBinding替换掉ViewDataBinding
SingleViewModel替换掉BaseViewModel

6.调用接口

```
        //请求接口
        mViewModel.onStart()
        //接口请求的数据变化
        mViewModel.result.observe(this, Observer {
            mBinding!!.singleDataS = it
            mBinding!!.singleData = it[0]
        })
```

7.ARoute的配置

根据自身项目需求来决定是否配置ARoute来进行路由控制

```
@Route(path = "/aachulk/ui/SingleActivity")
```

到此为止，简单的一个接口调用到数据展示就完成了

⚠️⚠️⚠️ 带适配器的demo参考[ListActivity](https://github.com/madreain/AACHulk/tree/master/app/src/main/java/com/madreain/aachulk/module/list)

## 用法进阶

1.自定义各种非正常态View替换

以demo中的MyVaryViewHelperController举例，只是修改了showLoading，其他的都可根据自身项目需求进行修改
```
class MyVaryViewHelperController private constructor(private val helper: VaryViewHelper) :
    IVaryViewHelperController {

    //是否已经调用过restore方法
    private var hasRestore: Boolean = false

    constructor(replaceView: View) : this(VaryViewHelper(replaceView)) {}

    override fun showNetworkError(onClickListener: View.OnClickListener?) {
        showNetworkError("网络状态异常，请刷新重试", onClickListener)
    }

    override fun showNetworkError(
        msg: String?,
        onClickListener: View.OnClickListener?
    ) {
        hasRestore = false
        val layout = helper.inflate(R.layout.hulk_page_error)
        val againBtn =
            layout.findViewById<Button>(R.id.pager_error_loadingAgain)
        val tv_title = layout.findViewById<TextView>(R.id.tv_title)
        tv_title.visibility = View.GONE
        val tv_msg = layout.findViewById<TextView>(R.id.tv_msg)
        tv_msg.text = msg
        if (null != onClickListener) {
            againBtn.setOnClickListener(onClickListener)
        }
        helper.showView(layout)
    }

    override fun showCustomView(
        drawableInt: Int,
        title: String?,
        msg: String?,
        btnText: String?,
        listener: View.OnClickListener?
    ) {
        hasRestore = false
        val layout = helper.inflate(R.layout.hulk_page_error)
        val iv_flag =
            layout.findViewById<ImageView>(R.id.iv_flag)
        val tv_title = layout.findViewById<TextView>(R.id.tv_title)
        val tv_msg = layout.findViewById<TextView>(R.id.tv_msg)
        val againBtn =
            layout.findViewById<Button>(R.id.pager_error_loadingAgain)
        iv_flag.setImageResource(drawableInt)
        if (TextUtils.isEmpty(title)) {
            tv_title.visibility = View.GONE
        } else {
            tv_title.visibility = View.VISIBLE
            tv_title.text = title
        }
        if (TextUtils.isEmpty(msg)) {
            tv_msg.visibility = View.GONE
        } else {
            tv_msg.visibility = View.VISIBLE
            tv_msg.text = msg
        }
        if (TextUtils.isEmpty(btnText)) {
            againBtn.visibility = View.GONE
        } else {
            againBtn.text = btnText
            if (null != listener) {
                againBtn.setOnClickListener(listener)
            }
        }
        helper.showView(layout)
    }

    override fun showEmpty(emptyMsg: String?) {
        hasRestore = false
        val layout = helper.inflate(R.layout.hulk_page_no_data)
        val textView = layout.findViewById<TextView>(R.id.tv_no_data)
        if (!TextUtils.isEmpty(emptyMsg)) {
            textView.text = emptyMsg
        }
        helper.showView(layout)
    }

    override fun showEmpty(
        emptyMsg: String?,
        onClickListener: View.OnClickListener?
    ) {
        hasRestore = false
        val layout = helper.inflate(R.layout.hulk_page_no_data_click)
        val againBtn =
            layout.findViewById<Button>(R.id.pager_error_loadingAgain)
        val textView = layout.findViewById<TextView>(R.id.tv_no_data)
        if (!TextUtils.isEmpty(emptyMsg)) {
            textView.text = emptyMsg
        }
        if (null != onClickListener) {
            againBtn.setOnClickListener(onClickListener)
            //            againBtn.setVisibility(View.VISIBLE);
            againBtn.visibility = View.GONE //按钮都隐藏，空页面没有刷新 2018.9.5
        } else {
            againBtn.visibility = View.GONE
        }
        helper.showView(layout)
    }

    override fun showLoading() {
        hasRestore = false
        val layout = helper.inflate(R.layout.view_page_loading)
        helper.showView(layout)
    }

    override fun showLoading(msg: String?) {
        hasRestore = false
        val layout = helper.inflate(R.layout.view_page_loading)
        val tv_msg = layout.findViewById<TextView>(R.id.tv_msg)
        tv_msg.text = msg
        helper.showView(layout)
    }

    override fun restore() {
        hasRestore = true
        helper.restoreView()
    }

    override val isHasRestore: Boolean
        get() = hasRestore

}
```

2.拦截器

2.1 请求头拦截器

```
class RequestHeaderInterceptor : Interceptor {
    //统一请求头的封装根据自身项目添加
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authorised: Request
        val headers = Headers.Builder()
            .add("app_id", "wpkxpsejggapivjf")
            .add("app_secret", "R3FzaHhSSXh4L2tqRzcxWFBmKzBvZz09")
            .build()
        authorised = request.newBuilder().headers(headers).build()
        return chain.proceed(authorised)
    }
}
```

2.2  非正常态响应码拦截器

实际应用：可应用于App中用户的互踢

```
class SessionInterceptor : IReturnCodeErrorInterceptor {
    //和接口定义互踢的相关参数返回，然后在doWork方法进行跳转
    override fun intercept(returnCode: String?): Boolean {
        return "-100" == returnCode
    }

    override fun doWork(returnCode: String?, msg: String?) {

    }

}
```

3.多BaseUrl以及多状态码

3.1  设置多BaseUrl

```
.addDomain(HulkKey.WANANDROID_DOMAIN_NAME, HulkKey.WANANDROID_API)
```

设置了多BaseUrl，就要设置对应的状态码，否则会报未设置状态码异常

3.2  设置对应的状态码

```
.addRetSuccess(HulkKey.WANANDROID_DOMAIN_NAME, BuildConfig.WANANDROID_CODELIST_SUCCESS)

```

3.3 设置调用接口方法的currentDomainName

```
 fun getWxArticle() {
        launchOnlyresult(
            //调用接口方法
            block = {
                getApiService().getWxArticle()
            },
            //重试
            reTry = {
                //调用重试的方法
                getWxArticle()
            },
            //成功
            success = {
                //成功回调
            },
            currentDomainName = HulkKey.WANANDROID_DOMAIN_NAME,
            type = RequestDisplay.REPLACE
        )
    }
```

上面这些配置项的配置可参考demo进行自身项目的配置

[多BaseUrl的设计思路参考的RetrofitUrlManager的实现方式](https://github.com/JessYanCoding/RetrofitUrlManager)

4.消息总线

针对大家提出的问题，这里采用了LiveEventBus(缺点:不支持线程分发)去替换原先的EventBus，去掉了在HulkConfig设置setEventBusOpen的开关设置，大家可根据自身项目去选择适合自己的消息总线

[`LiveEventBus` 消息总线，基于LiveData，具有生命周期感知能力，支持Sticky，支持AndroidX，支持跨进程，支持跨APP](https://github.com/JeremyLiao/LiveEventBus)

具体实现方法参考官方文档

## 相关资料

🌟🌟🌟
推荐Carson_Ho大佬的[Kotlin：这是一份全面 & 详细的 类使用 的语法学习指南](https://blog.csdn.net/carson_ho/article/details/105356518)

## 感谢

感谢本框架所使用到的所有三方库的作者，以及所有为开源做无私贡献的开发者和组织，使我们能更好的工作和学习,本人也会将业余时间回报给开源社区

## 关于我

* **Email**: <madreain6@gmail.com>
* **掘金**: <https://juejin.im/user/57ff05970e3dd90057e3e208>

## License

```
   Copyright [2020] [madreain]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
