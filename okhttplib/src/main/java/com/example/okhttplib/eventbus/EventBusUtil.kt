package com.example.okhttplib.eventbus

import org.greenrobot.eventbus.EventBus

object EventBusUtil {

    fun register(subscriber: Any) {
        EventBus.getDefault().register(subscriber)
    }

    fun unregister(subscriber: Any) {
        EventBus.getDefault().unregister(subscriber)
    }

    fun sendEvent(event: Event<*>) {
        EventBus.getDefault().post(event)
    }

    fun sendStickyEvent(event: Event<*>) {
        EventBus.getDefault().postSticky(event)
    }
}
