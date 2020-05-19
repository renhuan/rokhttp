package com.renhuan.okhttplib.utils

import android.app.Activity
import java.util.*

/**
 * created by renhuan
 * time : 2020/4/26 13:41
 * describe :  Activity的管理
 */
class RActivityUtils {
    private var activityStack: Stack<Activity> = Stack()

    companion object {
        private var instance = RActivityUtils()
        fun getInstance(): RActivityUtils {
            return instance
        }
    }

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
        activity?.let { activityStack.remove(it) }
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
        activityStack.forEach { finishActivity(it) }
        activityStack.clear()
    }


}