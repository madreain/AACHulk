package com.madreain.aachulk.module.api

import com.madreain.aachulk.module.base.BaseRes
import com.madreain.aachulk.module.dashboard.DashboardData
import com.madreain.aachulk.module.detailList.DetailListData
import com.madreain.aachulk.module.list.ListData
import com.madreain.aachulk.module.notifications.NotificationsData
import com.madreain.aachulk.module.refreshSingle.RefreshSingleData
import com.madreain.aachulk.module.single.SingleData
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author madreain
 * @date 2020-03-16.
 * module：接口
 * description： 这里用的接口来自https://github.com/MZCretin/RollToolsApi，感谢MZCretin
 */
interface ApiService {

    @GET("api/address/list")
    suspend fun getCityList(): BaseRes<List<SingleData>>

    @GET("api/address/list")
    suspend fun getRefreshCityList(): BaseRes<List<RefreshSingleData>>

    @GET("api/address/search?type=0&")
    suspend fun searchCity(@Query("value") value: String): BaseRes<List<ListData>>

    @GET("api/address/search?type=0&")
    suspend fun searDetailchCity(@Query("value") value: String): BaseRes<List<DetailListData>>

    @GET("api/address/list")
    suspend fun getNotificationsCityList(): BaseRes<List<NotificationsData>>

    @GET("api/address/search?type=0&")
    suspend fun searDashboardchCity(@Query("value") value: String): BaseRes<List<DashboardData>>


}