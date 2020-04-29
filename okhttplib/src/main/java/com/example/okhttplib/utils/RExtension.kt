package com.example.okhttplib.utils

import android.os.Handler
import android.widget.TextView

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
fun Handler.postDelayed(delayMillis: Long, period: Long = 0L, action: () -> Unit): Runnable {
    return object : Runnable {
        override fun run() {
            action()
            if (period != 0L) {
                postDelayed(this, period)
            }
        }
    }.apply<Runnable> { postDelayed(this, delayMillis) }
}
