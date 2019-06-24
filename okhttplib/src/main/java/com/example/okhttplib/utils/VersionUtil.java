package com.example.okhttplib.utils;


/**
 * 本地 ==服务器  0
 * 本地 < 服务器  -1
 * 本地 > 服务器  1
 * AppUtils.getAppVersionName()
 */
public class VersionUtil {
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

}
