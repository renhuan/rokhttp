package com.renhuan.administrator.myokhttp

import android.app.Activity
import com.renhuan.okhttplib.base.RBaseActivity
import com.renhuan.okhttplib.utils.Renhuan
import com.renhuan.okhttplib.utils.boolean
import com.tencent.mmkv.MMKV
import kotlin.properties.ReadWriteProperty


/**
 * created by renhuan
 * time : 2020/5/8 15:01
 * describe :
 */

object MMKVHelper {

    val mmkv: MMKV by lazy { MMKV.defaultMMKV() }

    fun isLogin(): ReadWriteProperty<Any, Boolean> {
        return mmkv.boolean(key = "boolean", defaultValue = false)
    }
}