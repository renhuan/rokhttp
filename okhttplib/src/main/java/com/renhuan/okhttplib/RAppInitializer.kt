package com.renhuan.okhttplib

import android.content.Context
import androidx.multidex.MultiDex
import androidx.startup.Initializer
import com.blankj.utilcode.util.PathUtils
import com.readystatesoftware.chuck.ChuckInterceptor
import com.renhuan.okhttplib.utils.Renhuan
import com.simple.spiderman.SpiderMan
import com.tencent.mmkv.MMKV
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.cahce.CacheMode
import rxhttp.wrapper.param.RxHttp
import java.io.File

/**
 * created by renhuan
 * time : 2020/8/26 09:43
 * describe :
 */
class RAppInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        //设置日志拦截器
        RxHttp.init(
                OkHttpClient.Builder()
                        .addInterceptor(ChuckInterceptor(context))
                        .build()
        )
        //缓存最大10M 缓存时间永久
        RxHttpPlugins.setCache(File(PathUtils.getExternalAppCachePath()), 10 * 1024 * 1024, CacheMode.ONLY_NETWORK, -1)
        SpiderMan.init(context).setTheme(R.style.SpiderManTheme_Dark)
        MultiDex.install(context)
        MMKV.initialize(context)
        Renhuan.initialize(context)
    }


    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

}