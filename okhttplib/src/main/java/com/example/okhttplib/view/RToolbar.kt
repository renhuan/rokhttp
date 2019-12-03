package com.example.okhttplib.view

import android.content.Context
import androidx.appcompat.widget.Toolbar
import android.util.AttributeSet
import android.view.View

import com.blankj.utilcode.util.ActivityUtils
import com.example.okhttplib.base.RBaseActivity
import com.example.okhttplib.utils.ActivityManager
import com.wuyr.activitymessenger.ActivityMessenger

class RToolbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : Toolbar(context, attrs) {

    init {
        setNavigationOnClickListener {
            ActivityManager.getInstance().finishActivity()
        }
    }
}
