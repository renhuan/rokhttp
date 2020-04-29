package com.example.okhttplib.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.okhttplib.R
import com.example.okhttplib.base.RBaseActivity
import com.lzy.okgo.OkGo
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.math.BigDecimal

/**
 * created by renhuan
 * time : 2020/4/25 16-17
 * describe :  常用工具类
 */
object Renhuan {

    private lateinit var context: Context
    private lateinit var handler: Handler

    fun initialize(c: Context) {
        context = c
        handler = Handler(Looper.getMainLooper())
    }


    /**
     * 获取创建在主线程上的Handler对象。
     */
    fun getHandler(): Handler {
        return handler
    }

    /**
     * 获取全局Context，在代码的任意位置都可以调用，随时都能获取到全局Context对象。
     */
    fun getContext(): Context {
        return context
    }

    /**
     * 获取资源文件中定义的字符串。
     */
    fun getString(resId: Int): String {
        return context.resources.getString(resId)
    }

    /**
     * 获取资源文件中定义的颜色。
     */
    fun getColor(colorResId: Int): Int {
        return ContextCompat.getColor(Renhuan.context, colorResId)
    }

    /**
     * dp 转 px
     */
    fun getDp2px(dp: Float): Float {
        return SizeUtils.dp2px(dp).toFloat()
    }

    /**  吐司toast   */
    fun toast(s: String) {
        ToastUtils.showShort(s)
    }


    /**  保留几位小数   */
    fun keepDecimal(str: Double, length: Int): String {
        return String.format("%.${length}f", BigDecimal(str))
    }


    /**  复制到剪切板   */
    fun copy(string: String) {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.primaryClip = ClipData.newPlainText("label", string)
        toast("已复制：$string")
    }

    /**  读取assets本地json   */
    fun readJsonFromAssets(fileName: String): String {
        val rBaseActivity = RActivityUtils.getInstance().currentActivity() as RBaseActivity
        val stringBuilder = StringBuilder()
        try {
            val assetManager = rBaseActivity.assets
            val bf = BufferedReader(
                InputStreamReader(
                    assetManager.open(fileName)
                )
            )
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
     * 比较本地和服务器的版本
     * @param versionLocal 本地版本 AppUtils.getAppVersionName()
     * @param versionNet   服务器版本
     * @return
     * 本地 ==服务器  0
     * 本地 < 服务器  -1
     * 本地 > 服务器  1
     */
    fun compareVersion(versionLocal: String, versionNet: String): Int {
        try {
            if (versionLocal == versionNet) {
                return 0
            }
            val version1Array = versionLocal.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()//转义
            val version2Array =
                versionNet.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            var index = 0
            // 获取最小长度值
            val minLen = version1Array.size.coerceAtMost(version2Array.size)
            var diff = 0
            // 循环判断每位的大小
            while (index < minLen) {
                diff =
                    Integer.parseInt(version1Array[index]) - Integer.parseInt(version2Array[index])
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
    //记录用户首次点击返回键的时间
    private var firstTime: Long = 0
    fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN) {
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


    /**
     * 取消网络请求
     */
    fun cancelHttp(activity: Activity?) {
        OkGo.getInstance().cancelTag(activity)
    }


    /**
     * 图片加载
     */
    fun glide(view: ImageView, url: String) {
        Glide
            .with(getContext())
            .load(url)
            .apply(RequestOptions().apply {
                placeholder(R.drawable.loading)
                error(R.drawable.loading)
            })
            .into(view)
    }


    fun logi(s: String) {
        LogUtils.i(s)
    }

    fun loge(s: String) {
        LogUtils.e(s)
    }
}