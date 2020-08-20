package com.renhuan.administrator.myokhttp

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.widget.AppCompatButton
import com.renhuan.okhttplib.utils.Renhuan
import javax.inject.Inject

/**
 * created by renhuan
 * time : 2020/7/8 12:03
 * describe :
 */
class ScaleButton(context: Context, attributeSet: AttributeSet) : AppCompatButton(context, attributeSet) {

    private val scaleAnimator by lazy { ValueAnimator.ofFloat(1f, 0.93f) }

    private val view: View = this

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                scaleAnimator.removeAllUpdateListeners()
                scaleAnimator.apply {
                    duration = 100
                    addUpdateListener {
                        view.scaleX = it.animatedValue as Float
                        view.scaleY = it.animatedValue as Float
                    }
                }.start()
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                scaleAnimator.reverse()
            }
        }
        return super.onTouchEvent(event)
    }

}