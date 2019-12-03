package com.example.okhttplib

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.CrashUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.example.okhttplib.utils.ActivityManager
import com.lzy.okgo.BuildConfig
import com.lzy.okgo.OkGo

abstract class RApp : Application() {

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
        Utils.init(this)
        OkGo.getInstance().init(this)
        LogUtils.getConfig().isLogSwitch = BuildConfig.DEBUG
        initActivity()
        if (isCrash) {
            initCrash()
        }
        init()

    }

    private fun initActivity() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
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
                OkGo.getInstance().cancelTag(ActivityManager.getInstance().currentActivity())
                ActivityManager.getInstance().finishActivity(activity)
            }
        })
    }

    private fun initCrash() {
        CrashUtils.init { _, _ -> AppUtils.relaunchApp() }
    }


}
