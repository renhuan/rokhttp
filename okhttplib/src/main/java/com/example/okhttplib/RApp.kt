package com.example.okhttplib

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.CrashUtils
import com.blankj.utilcode.util.Utils
import com.example.okhttplib.utils.ActivityManager
import com.lzy.okgo.OkGo
import com.tencent.mmkv.MMKV

abstract class RApp : Application(), Application.ActivityLifecycleCallbacks {

    /**
     * 是否crash 重启  默认true
     *
     * @return
     */
    open val isCrash: Boolean
        get() = true


    abstract fun init()

    override fun onCreate() {
        super.onCreate()
        OkGo.getInstance().init(this)
        registerActivityLifecycleCallbacks(this)
        MMKV.initialize(this)
        if (isCrash) {
            initCrash()
        }
        init()
    }

    private fun initCrash() {
        CrashUtils.init { _, _ -> AppUtils.relaunchApp() }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        ActivityManager.getInstance().addActivity(activity)
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        ActivityManager.getInstance().finishActivity(activity)
    }
}
