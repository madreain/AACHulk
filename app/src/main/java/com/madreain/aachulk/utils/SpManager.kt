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
        const val KEY_IM_TOKEN = "KEY_IM_TOKEN"
        const val KEY_USERID = "KEY_USERID"
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


}
