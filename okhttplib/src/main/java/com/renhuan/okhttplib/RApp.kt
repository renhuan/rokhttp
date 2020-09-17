package com.renhuan.okhttplib

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils

abstract class RApp : Application() {

    abstract fun init()

    override fun onCreate() {
        super.onCreate()
        setTheme(applicationInfo.theme)
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
        init()
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
