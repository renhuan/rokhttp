package com.example.okhttplib.view;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.okhttplib.base.RBaseActivity;

public class RToolbar extends Toolbar {
    public RToolbar(Context context) {
        this(context, null);
    }

    public RToolbar(Context context, @Nullable AttributeSet attrs) {
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
