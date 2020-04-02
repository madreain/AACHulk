package com.madreain.libhulk.utils

import android.util.Log

/**
 * @author madreain
 * @date 2019-07-04.
 * module： 日志相关工具类
 * description：
 */
class LogUtils private constructor() {
    class Builder {
        private var logSwitch = true
        private var log2FileSwitch = false
        private var logFilter = 'v'
        private var tag = "TAG"
        fun setLogSwitch(logSwitch: Boolean): Builder {
            this.logSwitch = logSwitch
            return this
        }

        fun setLog2FileSwitch(log2FileSwitch: Boolean): Builder {
            this.log2FileSwitch = log2FileSwitch
            return this
        }

        fun setLogFilter(logFilter: Char): Builder {
            this.logFilter = logFilter
            return this
        }

        fun setTag(tag: String): Builder {
            this.tag = tag
            return this
        }

        fun create() {
            Companion.logSwitch = logSwitch
            Companion.log2FileSwitch = log2FileSwitch
            Companion.logFilter = logFilter
            Companion.tag = tag
        }
    }

    companion object {
        private var logSwitch = true
        private var log2FileSwitch = false
        private var logFilter = 'v'
        private var tag = "TAG"
        private val dir: String? = null
        /**
         * Verbose日志
         *
         * @param msg 消息
         */
        fun v(msg: Any) {
            log(tag, msg.toString(), null, 'i')
        }

        /**
         * Verbose日志
         *
         * @param tag 标签
         * @param msg 消息
         */
        fun v(tag: String, msg: Any) {
            log(tag, msg.toString(), null, 'i')
        }

        /**
         * Verbose日志
         *
         * @param tag 标签
         * @param msg 消息
         * @param tr  异常
         */
        fun v(tag: String, msg: Any, tr: Throwable?) {
            log(tag, msg.toString(), tr, 'v')
        }

        /**
         * Debug日志
         *
         * @param msg 消息
         */
        fun d(msg: Any) {
            log(tag, msg.toString(), null, 'd')
        }

        /**
         * Debug日志
         *
         * @param tag 标签
         * @param msg 消息
         */
        fun d(tag: String, msg: Any) {
            log(tag, msg.toString(), null, 'd')
        }

        /**
         * Debug日志
         *
         * @param tag 标签
         * @param msg 消息
         * @param tr  异常
         */
        fun d(tag: String, msg: Any, tr: Throwable?) {
            log(tag, msg.toString(), tr, 'd')
        }

        /**
         * Info日志
         *
         * @param msg 消息
         */
        fun i(msg: Any) {
            log(tag, msg.toString(), null, 'i')
        }

        /**
         * Info日志
         *
         * @param tag 标签
         * @param msg 消息
         */
        fun i(tag: String, msg: Any) {
            log(tag, msg.toString(), null, 'i')
        }

        /**
         * Info日志
         *
         * @param tag 标签
         * @param msg 消息
         * @param tr  异常
         */
        fun i(tag: String, msg: Any, tr: Throwable?) {
            log(tag, msg.toString(), tr, 'i')
        }

        /**
         * Warn日志
         *
         * @param msg 消息
         */
        fun w(msg: Any) {
            log(tag, msg.toString(), null, 'w')
        }

        /**
         * Warn日志
         *
         * @param tag 标签
         * @param msg 消息
         */
        fun w(tag: String, msg: Any) {
            log(tag, msg.toString(), null, 'w')
        }

        /**
         * Warn日志
         *
         * @param tag 标签
         * @param msg 消息
         * @param tr  异常
         */
        fun w(tag: String, msg: Any, tr: Throwable?) {
            log(tag, msg.toString(), tr, 'w')
        }

        /**
         * Error日志
         *
         * @param msg 消息
         */
        fun e(msg: Any) {
            log(tag, msg.toString(), null, 'e')
        }

        /**
         * Error日志
         *
         * @param tag 标签
         * @param msg 消息
         */
        fun e(tag: String, msg: Any) {
            log(tag, msg.toString(), null, 'e')
        }

        /**
         * Error日志
         *
         * @param tag 标签
         * @param msg 消息
         * @param tr  异常
         */
        fun e(tag: String, msg: Any, tr: Throwable?) {
            log(tag, msg.toString(), tr, 'e')
        }

        /**
         * 根据tag, msg和等级，输出日志
         *
         * @param tag  标签
         * @param msg  消息
         * @param tr   异常
         * @param type 日志类型
         */
        private fun log(
            tag: String,
            msg: String,
            tr: Throwable?,
            type: Char
        ) {
            if (logSwitch) {
                if ('e' == type && ('e' == logFilter || 'v' == logFilter)) {
                    Log.e(generateTag(tag), msg, tr)
                } else if ('w' == type && ('w' == logFilter || 'v' == logFilter)) {
                    Log.w(generateTag(tag), msg, tr)
                } else if ('d' == type && ('d' == logFilter || 'v' == logFilter)) {
                    Log.d(generateTag(tag), msg, tr)
                } else if ('i' == type && ('d' == logFilter || 'v' == logFilter)) {
                    Log.i(generateTag(tag), msg, tr)
                }
            }
        }


        /**
         * 产生tag
         *
         * @return tag
         */
        private fun generateTag(tag: String): String {
            val stacks =
                Thread.currentThread().stackTrace
            val caller = stacks[4]
            val format = "Tag[$tag] %s[%s, %d]"
            var callerClazzName = caller.className
            callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1)
            return String.format(
                format,
                callerClazzName,
                caller.methodName,
                caller.lineNumber
            )
        }
    }

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }
}