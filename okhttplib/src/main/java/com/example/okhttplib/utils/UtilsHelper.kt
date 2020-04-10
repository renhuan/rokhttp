package com.example.okhttplib.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.AssetManager
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup

import com.blankj.utilcode.util.ActivityUtils
import com.example.okhttplib.BuildConfig
import com.example.okhttplib.base.RBaseActivity

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.Objects

import android.content.Context.CLIPBOARD_SERVICE

object UtilsHelper {

    //记录用户首次点击返回键的时间
    private var firstTime: Long = 0

    /**
     * 读取assets本地json
     *
     * @param fileName 文件名
     * @return 字符串
     */
    fun loadLocalJson(fileName: String): String {
        val rBaseActivity = ActivityManager.getInstance().currentActivity() as RBaseActivity
        val stringBuilder = StringBuilder()
        try {
            val assetManager = rBaseActivity.assets
            val bf = BufferedReader(InputStreamReader(
                    assetManager.open(fileName)))
            var line: String
            while (bf.readLine() != null) {
                line = bf.readLine()
                stringBuilder.append(line)
            }
            bf.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return stringBuilder.toString()
    }

    /**
     * 复制到剪切板
     *
     * @param string 复制的内容
     */
    fun copy(string: String) {
        val rBaseActivity = ActivityManager.getInstance().currentActivity() as RBaseActivity
        val clipboardManager = rBaseActivity.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.primaryClip = ClipData.newPlainText("label", string)
        toast("已复制：$string")
    }


    /**
     * 比较本地和服务器的版本
     *
     * @param version_local 本地版本 AppUtils.getAppVersionName()
     * @param version_net   服务器版本
     * @return 本地 ==服务器  0
     * 本地 < 服务器  -1
     * 本地 > 服务器  1
     */
    fun compareVersion(version_local: String, version_net: String): Int {
        try {
            if (version_local == version_net) {
                return 0
            }
            val version1Array = version_local.split("\\.".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()//转义
            val version2Array = version_net.split("\\.".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            var index = 0
            // 获取最小长度值
            val minLen = Math.min(version1Array.size, version2Array.size)
            var diff = 0
            // 循环判断每位的大小
            while (index < minLen) {
                diff = Integer.parseInt(version1Array[index]) - Integer.parseInt(version2Array[index])
                if (diff == 0)
                    index++
            }
            if (diff == 0) {
                // 如果位数不一致，比较多余位数
                for (i in index until version1Array.size) {
                    if (Integer.parseInt(version1Array[i]) > 0) {
                        return 1
                    }
                }
                for (i in index until version2Array.size) {
                    if (Integer.parseInt(version2Array[i]) > 0) {
                        return -1
                    }
                }
                return 0
            } else {
                return if (diff > 0) 1 else -1
            }
        } catch (e: Exception) {
            return 0
        }

    }

    /**
     * 按两次返回桌面
     * 用法：
     * public boolean onKeyDown(int keyCode, KeyEvent event) {
     * return UtilsHelper.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
     * }
     */
    fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            return if (System.currentTimeMillis() - firstTime > 1000) {
                toast("再按一次退出")
                firstTime = System.currentTimeMillis()
                true
            } else {
                false
            }
        }
        return true
    }
}
