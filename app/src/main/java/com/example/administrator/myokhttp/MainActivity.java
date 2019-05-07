package com.example.administrator.myokhttp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.FragmentUtils;
import com.example.administrator.myokhttp.base.BaseActivity;
import com.example.administrator.myokhttp.config.Api;
import com.example.okhttplib.config.MyOkHttpImp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MyOkHttpImp {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.fl)
    FrameLayout fl;

    @Override
    public int inflaterLayout() {
        return R.layout.activity_main;
    }


    @Override
    public void init() {
        super.init();
        FragmentUtils.add(getSupportFragmentManager(), new DemoFragment(), R.id.fl);
    }

    @Override
    public void onSuccess(String json, int flag) {
        tv.setText(json);
    }

    @Override
    public void onError(String json, int flag) {

    }

    @Override
    public void onBefore(int flag) {

    }


    @OnClick(R.id.btn)
    public void onViewClicked() {
        Api.getDemo(this);
    }
}
