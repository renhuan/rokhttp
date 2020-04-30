package com.example.administrator.myokhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.okhttplib.utils.Renhuan
import com.example.okhttplib.utils.setOnSafeClickListener
import kotlinx.android.synthetic.main.activity_demo.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        btn.setOnSafeClickListener {
            Renhuan.toast("点击了")
        }
    }
}
