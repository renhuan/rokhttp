package com.renhuan.administrator.myokhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.BarUtils
import com.renhuan.okhttplib.utils.Renhuan
import kotlinx.android.synthetic.main.activity_main2.*
import kotlin.math.sqrt

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        BarUtils.setStatusBarLightMode(this, true)
    }
}
