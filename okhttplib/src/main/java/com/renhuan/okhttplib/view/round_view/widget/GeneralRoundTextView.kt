package com.renhuan.okhttplib.view.round_view.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.renhuan.okhttplib.R
import com.renhuan.okhttplib.view.round_view.abs.GeneralRoundViewImpl
import com.renhuan.okhttplib.view.round_view.abs.IRoundView

class GeneralRoundTextView : TextView, IRoundView {
    private lateinit var generalRoundViewImpl: GeneralRoundViewImpl

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(this, context, attrs)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        generalRoundViewImpl.onLayout(changed, left, top, right, bottom)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        generalRoundViewImpl.beforeDispatchDraw(canvas)
        super.dispatchDraw(canvas)
        generalRoundViewImpl.afterDispatchDraw(canvas)
    }

    private fun init(view: View, context: Context, attributeSet: AttributeSet?) {
        generalRoundViewImpl = GeneralRoundViewImpl(
                view,
                context,
                attributeSet,
                R.styleable.GeneralRoundTextView,
                R.styleable.GeneralRoundTextView_tv_corner_radius
        )
    }

    override fun setCornerRadius(cornerRadius: Float) {
        generalRoundViewImpl.setCornerRadius(cornerRadius)
    }
}
