AACHulk

---
> AACHulk是以Google的ViewModel+LiveData框架为基础，
结合Okhttp+Retrofit+BaseRecyclerViewAdapterHelper+SmartRefreshLayout+ARouter打造的一款快速开发框架，
开发语言是Kotlin，再结合[AACHulkTemplate模版开发](https://github.com/madreain/AACHulkTemplate)进行开发，
避免一些繁琐的操作，提供开发效率

[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](https://www.apache.org/licenses/LICENSE-2.0)

## 功能介绍

1.支持服务器地址、MOCK服务器地址、成功码、各种超时时间、各种拦截器等的配置

2.支持自定义各种非正常态View替换

3.支持接口调用出错时重试

4.支持Activity、Fragment展示，满足业务需求

5.支持多布局适配器

6.支持ViewModel的多业务下的复用

7.支持通用代码生成[AACHulkTemplate模版](https://github.com/madreain/AACHulkTemplate)

## 第三方库

1. [`Okhttp` 一个用于Android、Kotlin和Java的HTTP客户端](https://github.com/square/okhttp)
2. [`Retrofit` 为Android和Java提供安全的HTTP客户端](https://github.com/square/retrofit)
3. [`BaseRecyclerViewAdapterHelper` 功能强大、灵活的万能适配器](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
4. [`SmartRefreshLayout` Android智能下拉刷新框架](https://github.com/scwang90/SmartRefreshLayout)
5. [`ARouter` 帮助 Android App 进行组件化改造的路由框架](https://github.com/alibaba/ARouter)

## 基础功能

1.添加依赖

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

2.继承HulkApplication，配置相关配置项

```
    LibConfig.builder()
                .setBaseUrl(BuildConfig.BASE_URL)//baseurl
                .setMockUrl(BuildConfig.MOCK_URL)//mockurl
                .setRetSuccess(1)//成功状态码
                .addOkHttpInterceptor(RequestHeaderInterceptor())//统一请求头处理
                .addOkHttpInterceptor(MockInterceptor())//mockurl的地址配置
                .addRetCodeInterceptors(SessionInterceptor())//互踢操作、封号等需设置固定code码的统一处理
                .init(this)
```
上面这些配置项的配置可参考demo进行自身项目的配置

这里还可根据[SmartRefreshLayout相关文档](https://github.com/scwang90/SmartRefreshLayout)配置统一样式，也可单独设置，也可自定义，根据自身项目选择

3.继承BaseResponseBean，根据自身项目封装统一的数据接受

5.编写ApiService，放接口

6.编写通用的Toolbar(自行选择)
因受kotlin-android-extensions这个插件可能只管自己module的资源文件的影响，没法将通用的toolbar.xml写在libhulk中供app使用，因此只能在app项目中写通用的toolbar.xml

⚠️ 如果大佬们有好的实现方法欢迎指教

️🔥️🔥️🔥 [AACHulkTemplate模版](https://github.com/madreain/AACHulkTemplate),此模版使用得保证ApiService、toolbar.xml已创建，使用者也可根据自身项目进行修改

## 快速开发

AACHulkTemplate模版用起来是相当香的，接下来讲一下自已手动的步骤，以SingleActivity举例

1.新建SingleActivity继承BaseActivity

```

class SingleActivity : BaseActivity(R.layout.activity_single) {

    private val singleViewModel by viewModels<SingleViewModel>()

    override fun init(savedInstanceState: Bundle?) {
        //ActionBar相关设置
        ActionBarUtils.setSupportActionBarWithBack(toolbar, null, View.OnClickListener {
            onBackPressed()
        })
        ActionBarUtils.setToolBarTitleText(toolbar, "单数据展示界面")

    }

}
```

2.ARoute的配置

根据自身项目需求来决定是否配置ARoute来进行路由控制

```
@Route(path = RouteUrls.Single)
```


3.创建对应的对象

```
@Keep
data class SingleData(
    var code: String,
    var name: String
)
```

4.新建SingleViewModel继承ViewModel

```
class SingleViewModel : ViewModel() {


    fun cityList(page: IPage, onSuccess: (data: List<SingleData>?) -> Unit) {
        NetHelper.request(page, block = {
            NetHelper.getService(ApiService::class.java).getCityList().asResult()
        }, onSuccess = {
            onSuccess(it)
        }, onError = {
            ToastUtils.showLong(it.message)
        })
    }

}

```

5.调用接口，数据的处理

```
        //请求接口
        singleViewModel.cityList(this, onSuccess = {
            it?.let {
                tv.text = it[0].name
            }
        })
```


到此为止，简单的一个接口调用到数据展示就完成了

⚠️⚠️⚠️ 带适配器的demo参考[ListActivity](https://github.com/madreain/AACHulk/tree/master/app/src/main/java/com/madreain/aachulk/module/list)

## 用法进阶

1.自定义各种非正常态View替换

重写buildPageInit方法，创建IPageInit，可以通过重写实现IPageInit方法，去创建适合自己app的默认界面、异常错误界面

封装好的带下拉刷新和加载更多的ListView支持setEmpty设置不同的空界面展示


2.拦截器

2.1 请求头拦截器

```
class RequestHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(processRequest(chain.request()))
    }

    private fun processRequest(request: Request): Request {
        if (request == null) return request
        val newBuilder = request.newBuilder()
        //设置传递过来的相关的headers
        return newBuilder.headers(addHeaders()).build()
    }

    fun addHeaders(): Headers {
        return Headers.Builder()
            .add("app_id", "wpkxpsejggapivjf")
            .add("app_secret", "R3FzaHhSSXh4L2tqRzcxWFBmKzBvZz09")
            .build()
    }


}
```
2.2  MOCK拦截器

实际应用：可应用于App中接口前期的MOCK

```
@EverythingIsNonNull
class MockInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val originalUrl = request.url.toString()
        val path =
            originalUrl.substring(LibConfig.getBaseUrl().length)
        return if (sMockUrls.contains(path)) {
            request =
                request.newBuilder().url("${LibConfig.getMockUrl()}$path")
                    .build()
            chain.proceed(request)
        } else {
            chain.proceed(request)
        }
    }

    companion object {
        //todo 这里写待调试的时候的需用用mock的url地址
        private val sMockUrls =
            listOf(
                ""
            )
    }
}

```

2.3  非正常态响应码拦截器

实际应用：可应用于App中互踢操作、封号等需设置固定code码的统一处理

```
class SessionInterceptor : ErrorInterceptor(-100) {
    override fun interceptor(throwable: Throwable): Boolean {
        // TODO: 2021/3/9 互踢的操作
        return true
    }


}
```

3.消息总线

针对大家提出的问题，这里采用了LiveEventBus(缺点:不支持线程分发)去替换原先的EventBus，去掉了在HulkConfig设置setEventBusOpen的开关设置，大家可根据自身项目去选择适合自己的消息总线

[`LiveEventBus` 消息总线，基于LiveData，具有生命周期感知能力，支持Sticky，支持AndroidX，支持跨进程，支持跨APP](https://github.com/JeremyLiao/LiveEventBus)

具体实现方法参考官方文档

4.Lottie的使用

刷新头、loading的样式都使用了强大的动画库Lottie，这样可根据app让ui设计出酷炫且适合的动画

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
