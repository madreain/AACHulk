package com.madreain.libhulk.utils

import org.greenrobot.eventbus.EventBus


/**
 * @author madreain
 * @date 2020-04-08.
 * module：EventBus工具类的封装
 * description：
 */
object EventBusUtils {
    /**
     * 注册EventBus
     *
     * @param subscriber 订阅者对象
     */
    fun register(subscriber: Any) {
        if (!isRegister(subscriber)) {
            try {
                EventBus.getDefault().register(subscriber)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 取消注册EventBus
     *
     * @param subscriber 订阅者对象
     */
    fun unRegister(subscriber: Any) {
        if (!isRegister(subscriber)) {
            try {
                EventBus.getDefault().unregister(subscriber)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 是否已经注册
     * @param subscriber
     * @return
     */
    private fun isRegister(subscriber: Any): Boolean {
        return EventBus.getDefault().isRegistered(subscriber)
    }

    /**
     * 发布订阅事件
     *
     * @param event 事件对象
     */
    fun post(event: Any?) {
        EventBus.getDefault().post(event)
    }

    /**
     * 发布粘性订阅事件
     *
     * @param event 事件对象
     */
    fun postSticky(event: Any?) {
        EventBus.getDefault().postSticky(event)
    }

    /**
     * 移除指定的粘性订阅事件
     *
     * @param eventType class的字节码，例如：String.class
     */
    fun <T> removeStickyEvent(eventType: Class<T>?) {
        val stickyEvent: T = EventBus.getDefault().getStickyEvent(eventType)
        if (stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent(stickyEvent)
        }
    }

    /**
     * 移除所有的粘性订阅事件
     */
    fun removeAllStickyEvents() {
        EventBus.getDefault().removeAllStickyEvents()
    }

    /**
     * 取消事件传送
     *
     * @param event 事件对象
     */
    fun cancelEventDelivery(event: Any?) {
        EventBus.getDefault().cancelEventDelivery(event)
    }
}