package com.example.okhttplib.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.okhttplib.base.RBaseActivity;

public class ToolbarView extends Toolbar {
    public ToolbarView(Context context) {
        this(context, null);
    }

    public ToolbarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RBaseActivity topActivity = (RBaseActivity) ActivityUtils.getTopActivity();
                topActivity.finishActivity();
            }
        });
    }
}
