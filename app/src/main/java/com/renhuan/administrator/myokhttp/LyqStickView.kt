package com.renhuan.administrator.myokhttp

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout

/**
 * created by renhuan
 * time : 2020/8/4 10:19
 * describe :
 */
class LyqStickView(context: Context, attributeSet: AttributeSet) : CoordinatorLayout(context, attributeSet) {
    init {
        View.inflate(context,R.layout.lyq_stick,null)
    }
}