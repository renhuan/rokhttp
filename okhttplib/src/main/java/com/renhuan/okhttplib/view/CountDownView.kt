package com.renhuan.okhttplib.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet

import java.util.Timer
import java.util.TimerTask

/**
 * Created by renhuan on
 * 倒计时控件 button
 */

@SuppressLint("DefaultLocale")
class CountDownView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : androidx.appcompat.widget.AppCompatButton(context, attrs) {

    private var timer: Timer? = null
    private var second = 60
    @SuppressLint("HandlerLeak")
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == 1) {
                text = String.format("%d秒", second)
                second--
                if (second == 0) {
                    timer!!.cancel()
                    timer = null
                    isClickable = true
                    text = "重新获取"
                    second = 60
                }
            }
        }
    }

    /***
     * 开始计时
     */
    fun countDown() {
        isClickable = false
        text = String.format("%d", second)
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                handler.sendEmptyMessage(1)
            }
        }, 0, 1000)
    }
}
