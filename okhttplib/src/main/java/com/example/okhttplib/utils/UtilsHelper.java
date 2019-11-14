package com.example.okhttplib.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.okhttplib.BuildConfig;
import com.example.okhttplib.base.RBaseActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Objects;

import static android.content.Context.CLIPBOARD_SERVICE;

public class UtilsHelper {


    /**
     * 给数组或者List添加分隔符
     *
     * @param iterator  数组或者list
     * @param separator 分隔符
     * @return 字符串
     */
    public static String join(Iterator<?> iterator, String separator) {
        if (iterator == null) {
            return null;
        } else if (!iterator.hasNext()) {
            return "";
        } else {
            Object first = iterator.next();
            if (!iterator.hasNext()) {
                return Objects.toString(first, "");
            } else {
                StringBuilder buf = new StringBuilder(256);
                if (first != null) {
                    buf.append(first);
                }
                while (iterator.hasNext()) {
                    if (separator != null) {
                        buf.append(separator);
                    }
                    Object obj = iterator.next();
                    if (obj != null) {
                        buf.append(obj);
                    }
                }
                return buf.toString();
            }
        }
    }

    /**
     * 保留两位小数
     *
     * @param str 数字
     * @return 字符串
     */
    public static String keepTwo(float str) {
        return new DecimalFormat("0.00").format(new BigDecimal(str));
    }


    /**
     * 读取assets本地json
     *
     * @param fileName 文件名
     * @return 字符串
     */
    public static String getLocalJson(String fileName) {
        RBaseActivity rBaseActivity = (RBaseActivity) ActivityUtils.getTopActivity();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = rBaseActivity.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 复制到剪切板
     *
     * @param string 复制的内容
     */
    public static void copy(String string) {
        RBaseActivity rBaseActivity = (RBaseActivity) ActivityUtils.getTopActivity();
        ClipboardManager clipboardManager = (ClipboardManager) rBaseActivity.getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText("label", string));
        rBaseActivity.toast("已复制：" + string);
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
    public static int compareVersion(String version_local, String version_net) {
        try {
            if (version_local.equals(version_net)) {
                return 0;
            }
            String[] version1Array = version_local.split("\\.");//转义
            String[] version2Array = version_net.split("\\.");
            int index = 0;
            // 获取最小长度值
            int minLen = Math.min(version1Array.length, version2Array.length);
            int diff = 0;
            // 循环判断每位的大小
            while (index < minLen && (diff = Integer.parseInt(version1Array[index]) -
                    Integer.parseInt(version2Array[index])) == 0) {
                index++;
            }
            if (diff == 0) {
                // 如果位数不一致，比较多余位数
                for (int i = index; i < version1Array.length; i++) {
                    if (Integer.parseInt(version1Array[i]) > 0) {
                        return 1;
                    }
                }
                for (int i = index; i < version2Array.length; i++) {
                    if (Integer.parseInt(version2Array[i]) > 0) {
                        return -1;
                    }
                }
                return 0;
            } else {
                return diff > 0 ? 1 : -1;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    //记录用户首次点击返回键的时间
    private static long firstTime = 0;

    /**
     * 按两次返回桌面
     * 用法：
     * public boolean onKeyDown(int keyCode, KeyEvent event) {
     * return UtilsHelper.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
     * }
     */
    public static boolean onKeyDown(int keyCode, KeyEvent event) {
        RBaseActivity rBaseActivity = (RBaseActivity) ActivityUtils.getTopActivity();
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 1000) {
                rBaseActivity.toast("再按一次退出");
                firstTime = System.currentTimeMillis();
                return true;
            } else {
                return false;
            }
        }
        return true;
    }
}
