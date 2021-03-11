package com.madreain.aachulk.utils

import android.content.Context
import com.blankj.utilcode.util.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Author:  Young
 * Date :   2019-07-22
 * Description:
 */
object SpManager {

    object PreferenceField {
        const val NAME = "aachulk"
        const val KEY_TOKEN = "KEY_TOKEN"
        const val KEY_TARGETID = "KEY_TARGETID"
        const val KEY_IM_TOKEN = "KEY_IM_TOKEN"
        const val KEY_USERID = "KEY_USERID"
        const val KEY_HAS_AGREEMENT_DIALOG_SHOW = "KEY_HAS_AGREEMENT_DIALOG_SHOW"
        const val KEY_RULEINFO = "KEY_RULEINFO"
        const val KEY_CHANNEL = "KEY_CHANNEL"
        const val KEY_EXPRESSION = "KEY_EXPRESSION"
        const val KEY_PUSH_ID = "KEY_PUSH_ID"
        const val KEY_GUIDE = "KEY_GUIDE"
        const val KEY_PUSH_INTO = "KEY_PUSH_INTO"
        const val KEY_IGNORE_VERSION = "KEY_IGNORE_VERSION"
        const val KEY_LAST_DOWNLOAD_ID = "KEY_LAST_DOWNLOAD_ID"//DownloadManagerID
        const val KEY_FIRST_LOGIN = "KEY_FIRST_LOGIN"//用户首次登陆（退出登录也算）
    }

    private val sp =
        Utils.getApp().getSharedPreferences(
            PreferenceField.NAME, Context.MODE_PRIVATE
        )

    /**
     * 设置Token
     */
    var token: String?
        get() = sp.getString(
            PreferenceField.KEY_TOKEN, null
        )
        set(token) = sp.edit().putString(
            PreferenceField.KEY_TOKEN, token
        ).apply()

    /**
     * push的消息id
     */
    var targetId: String?
        get() = sp.getString(
            PreferenceField.KEY_TARGETID, null
        )
        set(token) = sp.edit().putString(
            PreferenceField.KEY_TARGETID, token
        ).apply()

    /**
     * 设置Token
     */
    var imToken: String?
        get() = sp.getString(
            PreferenceField.KEY_IM_TOKEN, null
        )
        set(token) = sp.edit().putString(
            PreferenceField.KEY_IM_TOKEN, token
        ).apply()

    /**
     * 融云的ID和服务器的id是统一的
     */
    var userId: String?
        get() = sp.getString(
            PreferenceField.KEY_USERID, null
        )
        set(token) = sp.edit().putString(
            PreferenceField.KEY_USERID, token
        ).apply()

    /**
     * 通知栏上次开启的时间
     */
    var hasAgreementDialogShow: Boolean
        get() = sp.getBoolean(
            PreferenceField.KEY_HAS_AGREEMENT_DIALOG_SHOW, false
        )
        set(show) = sp.edit().putBoolean(
            PreferenceField.KEY_HAS_AGREEMENT_DIALOG_SHOW, show
        )
            .apply()

    /**
     * 表情
     */
    var expression: MutableList<String>?
        get() {
            val text =
                sp.getString(
                    PreferenceField.KEY_EXPRESSION, null
                )
            return if (text == null) {
                null
            } else
                Gson().fromJson(
                    text,
                    object : TypeToken<MutableList<String>>() {}.type
                )
        }
        set(config) {
            if (config == null) {
                sp.edit().putString(
                    PreferenceField.KEY_EXPRESSION, null
                ).apply()
            } else {
                sp.edit().putString(
                    PreferenceField.KEY_EXPRESSION, Gson().toJson(config)
                )
                    .apply()
            }
        }

    /**
     * 通知栏上次开启的时间
     */
    var channel: String?
        get() = sp.getString(
            PreferenceField.KEY_CHANNEL, null
        )
        set(c) = sp.edit().putString(
            PreferenceField.KEY_CHANNEL, c
        ).apply()

    /**
     * notification id
     */
    var pushID: Int
        get() = sp.getInt(
            PreferenceField.KEY_PUSH_ID, 100000
        )
        set(id) = sp.edit()
            .putInt(PreferenceField.KEY_PUSH_ID, id)
            .apply()

    /**
     * 是否现实引导页
     */
    var showGuide: Boolean
        get() = sp.getBoolean(
            PreferenceField.KEY_GUIDE, true
        )
        set(showGuide) = sp.edit().putBoolean(
            PreferenceField.KEY_GUIDE, showGuide
        ).apply()

    /**
     * 点击了通知栏
     */
    var pushInto: Boolean
        get() = sp.getBoolean(
            PreferenceField.KEY_PUSH_INTO, false
        )
        set(showGuide) = sp.edit().putBoolean(
            PreferenceField.KEY_PUSH_INTO, showGuide
        ).apply()


    /**
     * 用户首次登陆（退出登录也算）
     */
    var firstLogin: Boolean
        get() = sp.getBoolean(
            PreferenceField.KEY_FIRST_LOGIN, true
        )
        set(showGuide) = sp.edit().putBoolean(
            PreferenceField.KEY_FIRST_LOGIN, showGuide
        ).apply()

    /**
     * 忽略版本
     */
    var ignoreVersion: String?
        get() = sp.getString(
            PreferenceField.KEY_IGNORE_VERSION, null
        )
        set(version) = sp.edit().putString(
            PreferenceField.KEY_IGNORE_VERSION, version
        ).apply()

    /**
     * downloadmanager最后一次下载ID
     */
    var lastDownloadManager: Long
        get() = sp.getLong(
            PreferenceField.KEY_LAST_DOWNLOAD_ID, -1
        )
        set(c) = sp.edit()
            .putLong(PreferenceField.KEY_LAST_DOWNLOAD_ID, c)
            .apply()


}
