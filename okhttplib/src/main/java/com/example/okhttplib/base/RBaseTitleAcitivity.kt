package com.example.okhttplib.base

import android.os.Bundle

import androidx.appcompat.widget.Toolbar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView

import com.example.okhttplib.R


abstract class RBaseTitleAcitivity : RBaseActivity() {


    var toolbar: Toolbar? = null
    var tvTitle: TextView? = null

    //重写 是否有toolbar 默认有
    open fun isToolbar(): Boolean {
        return true
    }

    override fun init(savedInstanceState: Bundle?) {
        if (isToolbar()) {
            val contentView = findViewById<FrameLayout>(android.R.id.content)
            val childAt = contentView.getChildAt(0) as ViewGroup
            if (childAt is LinearLayout) {
                val inflate = LayoutInflater.from(this).inflate(R.layout.title, null)
                toolbar = inflate.findViewById(R.id.toolbar)
                tvTitle = inflate.findViewById(R.id.tv_title)
                childAt.addView(inflate, 0)
            } else {
                throw IllegalArgumentException("规定根布局为LinearLayout")
            }
        }
    }
}
