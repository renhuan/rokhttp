package com.example.okhttplib.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class ViewUtils {

    /**
     * 获取状态栏view
     *
     * @param context
     * @return
     */
    public static View getStatusView(Context context) {
        View view = new View(context);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return view;
    }
}
