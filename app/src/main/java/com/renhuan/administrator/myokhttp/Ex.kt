package com.renhuan.administrator.myokhttp

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View

/**
 * created by renhuan
 * time : 2020/8/13 15:33
 * describe :
 */

// 添加点击缩放效果
@SuppressLint("ClickableViewAccessibility")
fun View.addClickScale(scale: Float = 0.93f, duration: Long = 150) {
    setOnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                animate().scaleX(scale).scaleY(scale).setDuration(duration).start()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                animate().scaleX(1f).scaleY(1f).setDuration(duration).start()
            }
        }
        onTouchEvent(event)
    }
}