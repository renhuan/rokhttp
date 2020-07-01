package com.renhuan.okhttplib.eventbus

import org.greenrobot.eventbus.EventBus

object REventBus {

    fun register(subscriber: Any) {
        EventBus.getDefault().register(subscriber)
    }

    fun unregister(subscriber: Any) {
        EventBus.getDefault().unregister(subscriber)
    }

    fun sendEvent(event: Any) {
        EventBus.getDefault().post(event)
    }

    fun sendStickyEvent(event: Any) {
        EventBus.getDefault().postSticky(event)
    }
}
