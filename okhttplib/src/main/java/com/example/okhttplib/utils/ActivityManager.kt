package com.example.okhttplib.utils

import android.app.Activity
import android.content.Context
import java.util.*
import android.content.Context.ACTIVITY_SERVICE
import okhttp3.internal.Internal.instance
import kotlin.system.exitProcess

class ActivityManager {
    private var activityStack: Stack<Activity> = Stack()

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        activityStack.lastElement()?.finish()
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */

    fun currentActivity(): Activity? {
        return activityStack.lastElement()
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
        activity?.let {
            activityStack.remove(it)
            it.finish()
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {
        activityStack
                .filter { it.javaClass == cls }
                .forEach { finishActivity(it) }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        activityStack.forEach {
            finishActivity(it)
        }
        activityStack.clear()
    }

    companion object {
        private var instance: ActivityManager? = null
        fun getInstance(): ActivityManager {
            if (instance == null) {
                instance = ActivityManager()
            }
            return instance as ActivityManager
        }


    }

}