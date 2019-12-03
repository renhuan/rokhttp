package com.example.administrator.myokhttp

import android.os.Bundle
import com.example.okhttplib.base.RBaseActivity

class MainActivity : RBaseActivity() {
    override fun inflaterLayout(): Int {
        return R.layout.activity_main
    }

    override fun init(savedInstanceState: Bundle?) {
    }

    override fun onSuccess(json: String?, requestCode: Int?) {
    }

    override fun onError(json: String?, requestCode: Int?) {
    }
//    override fun inflaterLayout(): Int {
//        return R.layout.activity_main
//    }
//
//    override fun init(savedInstanceState: Bundle?) {
//        println("ddd")
//    }
//
//    override fun onSuccess(json: String?, requestCode: Int?) {
//    }
//
//    override fun onError(json: String?, requestCode: Int?) {
//        var t = 11
//    }
}
