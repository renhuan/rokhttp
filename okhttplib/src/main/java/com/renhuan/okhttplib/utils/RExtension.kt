package com.renhuan.okhttplib.utils

import android.os.Handler
import android.view.View
import android.widget.TextView
import com.alibaba.fastjson.JSON

/**  获取 Textview 的值   */
fun TextView.text(): String {
    return this.text.toString().trim()
}

/**
 * 判断 TextView 是否为空
 * 用法：phone.checkEmpty("手机号不能为空") ?: return,
 */
fun TextView.checkEmpty(message: String): String? {
    val text = this.text.toString().trim()
    if (text.isEmpty()) {
        Renhuan.toast(message)
        return null
    }
    return text
}

/**
 *  Handler 延迟函数和定时器
 *  1--- period==0的时候 就只延迟功能(默认)
 *  2--- period!=0的时候 就定时器的功能
 * */
inline fun Handler.postDelayed(delayMillis: Long, period: Long = 0L, crossinline action: () -> Unit): Runnable {
    return object : Runnable {
        override fun run() {
            if (period != 0L) {
                postDelayed(this, period)
            }
            action()
        }
    }.apply<Runnable> { postDelayed(this, delayMillis) }
}

/**
 * 安全点击 防止多次点击
 */

inline fun View.setOnSafeClickListener(crossinline action: () -> Unit) {
    var lastClick = 0L
    setOnClickListener {
        val gap = System.currentTimeMillis() - lastClick
        lastClick = System.currentTimeMillis()
        if (gap < 1000) return@setOnClickListener
        action()
    }
}

/**
 * fastjson 解析parseObject
 */
fun <T> Class<T>.parseObject(json: String?): T {
    return JSON.parseObject(json, this)
}