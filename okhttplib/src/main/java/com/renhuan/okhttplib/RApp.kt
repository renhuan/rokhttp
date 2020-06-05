package com.renhuan.okhttplib

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.multidex.MultiDex
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.Utils
import com.lzy.okgo.OkGo
import com.lzy.okgo.interceptor.HttpLoggingInterceptor
import com.readystatesoftware.chuck.ChuckInterceptor
import com.renhuan.okhttplib.utils.Renhuan
import com.simple.spiderman.SpiderMan
import com.tencent.mmkv.MMKV
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.unit.Subunits
import okhttp3.OkHttpClient
import java.util.logging.Level

abstract class RApp : Application() {

    abstract fun init()

    /**
     * 设置崩溃页面的主题
     */
    abstract fun getSpiderTheme(): Int

    override fun onCreate() {
        super.onCreate()
        setTheme(applicationInfo.theme)
        SpiderMan.init(this).setTheme(if (getSpiderTheme() == 0) R.style.SpiderManTheme_Dark else getSpiderTheme())
        OkGo.getInstance().init(this).okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ChuckInterceptor(this))
            .addInterceptor(HttpLoggingInterceptor("okgo").apply {
                setPrintLevel(HttpLoggingInterceptor.Level.BODY)
                setColorLevel(Level.INFO)
            })
            .build()
        MultiDex.install(this)
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
        MMKV.initialize(this)
        Renhuan.initialize(this)
        initAutoSize()
        init()
    }

    /**
     * 布局使用MM单位
     */
    private fun initAutoSize() {
        AutoSizeConfig.getInstance()
            .unitsManager
            .setSupportDP(false)
            .setSupportSP(false)
            .supportSubunits = Subunits.MM
    }


    private val activityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity?) {
        }

        override fun onActivityResumed(activity: Activity?) {
        }

        override fun onActivityStarted(activity: Activity?) {
        }

        override fun onActivityDestroyed(activity: Activity?) {
            ActivityUtils.removeActivityLifecycleCallbacks(activity!!)
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }

        override fun onActivityStopped(activity: Activity?) {
        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            ActivityUtils.addActivityLifecycleCallbacks(activity!!, object : Utils.ActivityLifecycleCallbacks() {})
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
