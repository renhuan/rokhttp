package com.renhuan.okhttplib

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.multidex.MultiDex
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.CrashUtils
import com.renhuan.okhttplib.utils.RActivityUtils
import com.renhuan.okhttplib.utils.Renhuan
import com.lzy.okgo.OkGo
import com.tencent.mmkv.MMKV

abstract class RApp : Application() {

    /**
     * 是否crash 重启  默认true
     */
    open val isCrash: Boolean
        get() = true

    abstract fun init()

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this);
        OkGo.getInstance().init(this)
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
        MMKV.initialize(this)
        Renhuan.initialize(this)
        if (isCrash) {
            initCrash()
        }
        init()
    }


    private fun initCrash() {
        CrashUtils.init { _, _ -> AppUtils.relaunchApp() }
    }

    private val activityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity?) {
        }

        override fun onActivityResumed(activity: Activity?) {
        }

        override fun onActivityStarted(activity: Activity?) {
        }

        override fun onActivityDestroyed(activity: Activity?) {
            RActivityUtils.getInstance().finishActivity(activity)
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }

        override fun onActivityStopped(activity: Activity?) {
        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            RActivityUtils.getInstance().addActivity(activity!!)
        }
    }


    /**
     * 取消网络请求
     */
    companion object {
        fun cancelHttp(activity: Activity?) {
            OkGo.getInstance().cancelTag(activity)
        }
    }
}
