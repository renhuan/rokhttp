package com.renhuan.okhttplib

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.multidex.MultiDex
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.PathUtils
import com.blankj.utilcode.util.Utils
import com.readystatesoftware.chuck.ChuckInterceptor
import com.renhuan.okhttplib.utils.Renhuan
import com.simple.spiderman.SpiderMan
import com.tencent.mmkv.MMKV
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.unit.Subunits
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.cahce.CacheMode
import rxhttp.wrapper.param.RxHttp
import java.io.File

abstract class RApp : Application() {

    abstract fun init()

    /**
     * 设置崩溃页面的主题
     */
    abstract fun getSpiderTheme(): Int

    override fun onCreate() {
        super.onCreate()
        setTheme(applicationInfo.theme)
        initRequest()
        SpiderMan.init(this).setTheme(if (getSpiderTheme() == 0) R.style.SpiderManTheme_Dark else getSpiderTheme())
        MultiDex.install(this)
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
        MMKV.initialize(this)
        Renhuan.initialize(this)
        initAutoSize()
        init()
    }

    private fun initRequest() {
        //设置日志拦截器
        RxHttp.init(
                OkHttpClient.Builder()
                        .addInterceptor(ChuckInterceptor(this))
                        .build()
        )
        //缓存最大10M 缓存时间永久
        RxHttpPlugins.setCache(File(PathUtils.getExternalAppCachePath()), 10 * 1024 * 1024, CacheMode.ONLY_NETWORK, -1)
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
            ActivityUtils.addActivityLifecycleCallbacks(activity!!, null)
        }
    }

}
