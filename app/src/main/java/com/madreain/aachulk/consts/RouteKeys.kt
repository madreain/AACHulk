package com.madreain.aachulk.consts

/***
 * 统一的key
 */
object RouteKeys {
    //通用Dialog
    const val CommonTitle = "commontitle"
    const val CommonDesc = "commondesc"
    const val CommonLeft = "commonleft"
    const val CommonRight = "commonright"
    const val CommonRemind = "commonremind"
    const val CommonExternalArea = "commonExternalArea"

    //arouter参数传递
    const val CustomKey = "CustomKey"

    //多个baseUrl
    const val WANANDROID_DOMAIN_NAME: String = "wanandroid"
    const val WANANDROID_API: String = "https://www.wanandroid.com"
    const val GANK_DOMAIN_NAME: String = "gank"
    const val GANK_API: String = "https://gank.io"

    //群组id
    const val TargetId = "targetId"

    //会话未读数
    const val ConversationUnreadNum = "ConversationUnreadNum"
    const val FirstJoinGroup = "FirstJoinGroup"
    const val GroupCountDownTime = "GroupCountDownTime"
    const val GroupStatus = "GroupStatus"

    //加载一次性加载多少条  每页消息的数量. 每页数量最多 20 条.
    const val LoadMaxMsg = 20

    //最多加载多少
    const val LoadMax = 10

    //im输入的最大内容
    const val ImMaxMsg = 100

    //web通用的
    const val WebUrl = "WebUrl"
    const val WebTitle = "WebTitle"
    const val WebCacheMode = "WebCacheMode"
    const val Phone = "phone"
    const val FetchPreventTime = "fetchPreventTime"

    //Update
    const val UpdateData = "UpdateData"
    const val UpdateIsIgnore = "UpdateIsIgnore"
}