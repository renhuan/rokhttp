package com.example.okhttplib.utils;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Objects;

public class StringUtils {


    /***
     * 给 数组 或者 List 添加分隔符
     * @param iterator
     * @param separator
     * @return
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
     * @param str
     * @return
     */
    public static String keepTwo(float str) {
        return new DecimalFormat("0.00").format(str);
    }
}
