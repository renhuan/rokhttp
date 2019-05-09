package com.example.administrator.myokhttp.base;

import com.example.okhttplib.base.RBaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 此类可以写项目的相关逻辑
 */
public abstract class BaseActivity extends RBaseActivity {
    private Unbinder bind;

    @Override
    public void init() {
        //注册黄油刀
        bind = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        bind.unbind();
        super.onDestroy();
    }
}
