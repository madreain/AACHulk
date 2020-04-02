package com.madreain.libhulk.utils

import android.text.TextUtils
import java.util.regex.Pattern

/**
 * @author madreain
 * @date 2020/3/16.
 * module：
 * description：字符串相关工具类
 */
class StringUtils private constructor() {
    companion object {
        /**
         * 去除换行等特殊符号，一般用于http url
         *
         * @param src
         * @return
         */
        fun replaceBlank(src: String?): String {
            var dest = ""
            if (src != null) {
                val pattern =
                    Pattern.compile("\t|\r|\n|\\s*")
                val matcher = pattern.matcher(src)
                dest = matcher.replaceAll("")
            }
            return dest
        }

        /**
         * 是否包含Emoji表情
         *
         * @param source
         * @return
         */
        fun hasEmoji(source: String?): Boolean {
            if (!TextUtils.isEmpty(source)) {
                val emoji = Pattern.compile(
                    "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                    Pattern.UNICODE_CASE or Pattern.CASE_INSENSITIVE
                )
                val emojiMatcher = emoji.matcher(source)
                return emojiMatcher.find()
            }
            return false
        }

        /**
         * 判断字符串是否为null或长度为0
         *
         * @param s 待校验字符串
         * @return `true`: 空<br></br> `false`: 不为空
         */
        fun isEmpty(s: CharSequence?): Boolean {
            return s == null || s.length == 0
        }

        /**
         * 判断字符串是否为null或全为空格
         *
         * @param s 待校验字符串
         * @return `true`: null或全空格<br></br> `false`: 不为null且不全空格
         */
        fun isSpace(s: String?): Boolean {
            return s == null || s.trim { it <= ' ' }.length == 0
        }

        /**
         * 判断两字符串是否相等
         *
         * @param a 待校验字符串a
         * @param b 待校验字符串b
         * @return `true`: 相等<br></br>`false`: 不相等
         */
        fun equals(a: CharSequence?, b: CharSequence?): Boolean {
            if (a === b) return true
            var length: Int = 0
            return if (a != null && b != null && a.length.also { length = it } == b.length) {
                if (a is String && b is String) {
                    a == b
                } else {
                    for (i in 0 until length) {
                        if (a[i] != b[i]) return false
                    }
                    true
                }
            } else false
        }

        /**
         * 判断两字符串忽略大小写是否相等
         *
         * @param a 待校验字符串a
         * @param b 待校验字符串b
         * @return `true`: 相等<br></br>`false`: 不相等
         */
        fun equalsIgnoreCase(a: String, b: String?): Boolean {
            return a === b || b != null && a.length == b.length && a.regionMatches(
                0,
                b,
                0,
                b.length,
                ignoreCase = true
            )
        }

        /**
         * null转为长度为0的字符串
         *
         * @param s 待转字符串
         * @return s为null转为长度为0字符串，否则不改变
         */
        fun null2Length0(s: String?): String {
            return s ?: ""
        }

        /**
         * 返回字符串长度
         *
         * @param s 字符串
         * @return null返回0，其他返回自身长度
         */
        fun length(s: CharSequence?): Int {
            return s?.length ?: 0
        }

        /**
         * 首字母大写
         *
         * @param s 待转字符串
         * @return 首字母大写字符串
         */
        fun upperFirstLetter(s: String): String {
            if (isEmpty(s) || !Character.isLowerCase(
                    s[0]
                )
            ) return s
            return (s[0].toInt() - 32).toString() + s.substring(1)
        }

        /**
         * 首字母小写
         *
         * @param s 待转字符串
         * @return 首字母小写字符串
         */
        fun lowerFirstLetter(s: String): String {
            if (isEmpty(s) || !Character.isUpperCase(
                    s[0]
                )
            ) return s
            return (s[0].toInt() + 32).toString() + s.substring(1)
        }

        /**
         * 反转字符串
         *
         * @param s 待反转字符串
         * @return 反转字符串
         */
        fun reverse(s: String): String {
            val len = length(s)
            if (len <= 1) return s
            val mid = len shr 1
            val chars = s.toCharArray()
            var c: Char
            for (i in 0 until mid) {
                c = chars[i]
                chars[i] = chars[len - i - 1]
                chars[len - i - 1] = c
            }
            return String(chars)
        }

        /**
         * 转化为半角字符
         *
         * @param s 待转字符串
         * @return 半角字符串
         */
        fun toDBC(s: String): String {
            if (isEmpty(s)) return s
            val chars = s.toCharArray()
            var i = 0
            val len = chars.size
            while (i < len) {
                if (chars[i].toInt() == 12288) {
                    chars[i] = ' '
                } else if (65281 <= chars[i].toInt() && chars[i].toInt() <= 65374) {
                    chars[i] = (chars[i] - 65248)
                } else {
                    chars[i] = chars[i]
                }
                i++
            }
            return String(chars)
        }

        /**
         * 转化为全角字符
         *
         * @param s 待转字符串
         * @return 全角字符串
         */
        fun toSBC(s: String): String {
            if (isEmpty(s)) return s
            val chars = s.toCharArray()
            var i = 0
            val len = chars.size
            while (i < len) {
                if (chars[i] == ' ') {
                    chars[i] = 12288.toChar()
                } else if (33 <= chars[i].toInt() && chars[i].toInt() <= 126) {
                    chars[i] = (chars[i] + 65248)
                } else {
                    chars[i] = chars[i]
                }
                i++
            }
            return String(chars)
        }
    }

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }
}