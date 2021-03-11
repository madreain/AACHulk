package com.madreain.aachulk.consts

/**
 * event消息总线
 */
object EventsKey {

    //聊天来消息了
    const val EVENT_INCOMMING_MSG = "EVENT_INCOMMING_MSG"
    //来消息了刷新相关信息
    const val EVENT_INCOMMING_MSG_REFRESH_INFO = "EVENT_INCOMMING_MSG_REFRESH_INFO"
    //会话变化
    const val EVENT_CONVERSATION_CHANGE = "EVENT_CONVERSATION_CHANGE"
    //标签选择
    const val SELECT_TAG = "SELECT_TAG"
    //创建话题
    const val CREATE_SUBJECT = "CREATE_SUBJECT"
    //刷新所有Conversation
    const val REFRESH_ALL_CONVERSATION_STATUS = "REFRESH_ALL_CONVERSATION_STATUS"
    //刷新单个Conversation
    const val REFRESH_SINGLE_CONVERSATION_STATUS = "REFRESH_SINGLE_CONVERSATION_STATUS"
    //微信登录
    const val EVENT_WX_LOGIN = "EVENT_WX_LOGIN"
    //登录成功
    const val EVENT_LOGIN_SUCCESS = "EVENT_LOGIN_SUCCESS"
    //清除会话未读数
    const val CLEAR_MESSAGE = "CLEAR_MESSAGE"
    //socket长链接
    const val EVENT_SOCKET = "EVENT_SOCKET"
    //socket长链接发送到groupchat
    const val EVENT_SOCKET_GROUPCHAT = "EVENT_SOCKET_GROUPCHAT"
    //弹幕窗口未开启
    const val BARRAGE_NO_OPEN = "BARRAGE_NO_OPEN"
    //刷新弹幕
    const val BARRAGE_REFRESH = "BARRAGE_REFRESH"

}