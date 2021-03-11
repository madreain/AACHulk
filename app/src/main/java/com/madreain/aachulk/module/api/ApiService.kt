package com.madreain.aachulk.module.api

import com.madreain.aachulk.module.multi.MultiListData
import com.madreain.aachulk.module.single.SingleData
import com.madreain.libhulk.network.model.ResponseBean
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
    suspend fun getCityList(): ResponseBean<List<SingleData>>

    @GET("api/address/search?type=0&")
    suspend fun searchCity(@Query("value") value: String): ResponseBean<List<SingleData>>

    @GET("api/address/search?type=0&")
    suspend fun searchMultiCity(@Query("value") value: String): ResponseBean<List<MultiListData>>

//    /**
//     * 玩 Android的api
//     */
//    @Headers(HulkConfig.DOMAIN_NAME_HEADER + HulkKey.WANANDROID_DOMAIN_NAME)
//    @GET("wxarticle/chapters/json" + IDENTIFICATION_PATH_SIZE + 0)
//    suspend fun getWxArticle(): WanAndroidBaseRes<List<ChangeBaseUrlData>>
//
//    /**
//     * gank的api
//     */
//    @Headers(HulkConfig.DOMAIN_NAME_HEADER + HulkKey.GANK_DOMAIN_NAME)
//    @GET("api/v2/categories/GanHuo" + IDENTIFICATION_PATH_SIZE + 0)
//    suspend fun getCategories(): GankBaseRes<List<ChangeBaseUrlTwoListData>>


}