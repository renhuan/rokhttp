package com.example.okhttplib.base;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.okhttplib.R;


public abstract class RBaseTitleAcitivity extends RBaseActivity {


    protected Toolbar toolbar;
    protected TextView tvTitle;


    //重写 设置toolbar
    protected int setToolbar() {
        return R.layout.title;
    }

    //重写 是否有toolbar 默认有
    protected boolean isToolbar() {
        return true;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        if (isToolbar()) {
            FrameLayout contentView = findViewById(android.R.id.content);
            ViewGroup childAt = (ViewGroup) contentView.getChildAt(0);
            if (childAt instanceof LinearLayout) {
                View inflate = LayoutInflater.from(this).inflate(setToolbar(), null);
                toolbar = inflate.findViewById(R.id.toolbar);
                tvTitle = inflate.findViewById(R.id.tv_title);
                childAt.addView(inflate, 0);
            } else {
                throw new IllegalArgumentException("规定根布局为LinearLayout");
            }
        }
    }
}
