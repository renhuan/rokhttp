package com.renhuan.okhttplib.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.renhuan.okhttplib.R
import com.renhuan.okhttplib.utils.Renhuan
import com.renhuan.okhttplib.utils.postDelayed

/**
 * created by renhuan
 * time : 2020/5/9 13:07
 * describe : 倒计时控件
 */

class CountDownView(context: Context, attrs: AttributeSet? = null) : AppCompatButton(context, attrs) {

    /**
     * 倒计时最大值
     */
    private val maxTime = 60
    private var second = maxTime
    private var runnable: Runnable? = null

    /***
     * 开始计时
     */
    fun startCountDown() {
        isClickable = false
        runnable = Renhuan.getHandler().postDelayed(0, 1000) {
            text = second.toString()
            if (second-- == 0) {
                isClickable = true
                text = "重新获取"
                second = maxTime
                Renhuan.getHandler().removeCallbacks(runnable)
            }
        }
    }
}
