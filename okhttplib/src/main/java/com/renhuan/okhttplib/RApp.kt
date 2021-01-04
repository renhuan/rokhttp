package com.renhuan.okhttplib

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.multidex.MultiDex
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.PathUtils
import com.readystatesoftware.chuck.ChuckInterceptor
import com.readystatesoftware.chuck.internal.ui.MainActivity
import com.renhuan.okhttplib.utils.Renhuan
import com.renhuan.okhttplib.utils.SecurityHttpsHelper
import com.tencent.mmkv.MMKV
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.cahce.CacheMode
import rxhttp.wrapper.param.RxHttp
import java.io.File
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession


abstract class RApp : Application() {

    abstract fun init()

    //是否重写 自定义Crash页面
    abstract fun errorActivity(): Class<out Activity>?

    override fun onCreate() {
        super.onCreate()
        setTheme(applicationInfo.theme)
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
        initCrash()
        //设置日志拦截器
        RxHttp.setDebug(true)
        RxHttp.init(
            OkHttpClient.Builder()
                .hostnameVerifier(HostnameVerifier { _, _ -> true })
                .sslSocketFactory(SecurityHttpsHelper.getSslSocketFactory(), SecurityHttpsHelper.getTrustManager())
                .addInterceptor(ChuckInterceptor(this))
                .build()
        )
        //缓存最大10M 缓存时间永久
        RxHttpPlugins.setCache(
            File(PathUtils.getExternalAppCachePath()), 10 * 1024 * 1024, CacheMode.ONLY_NETWORK, -1
        )
        MultiDex.install(this)
        MMKV.initialize(this)
        Renhuan.initialize(this)
        init()

    }

    private fun initCrash() {
        CaocConfig.Builder.create()
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT)
            .enabled(true)
            .showErrorDetails(true)
            .showRestartButton(true)
            .logErrorOnRestart(false)
            .trackActivities(false)
            .minTimeBetweenCrashesMs(2000)
            .errorActivity(errorActivity())
            .apply()
    }


    private val activityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityResumed(activity: Activity) {
        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityDestroyed(activity: Activity) {
            ActivityUtils.removeActivityLifecycleCallbacks(activity)
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityStopped(activity: Activity) {
        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            ActivityUtils.addActivityLifecycleCallbacks(activity, null)
        }
    }
}
