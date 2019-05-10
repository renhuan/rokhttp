package com.example.administrator.myokhttp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myokhttp.base.BaseActivity;
import com.example.administrator.myokhttp.config.Api;

import butterknife.BindView;
import butterknife.OnClick;

public class DemoActivity extends BaseActivity {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.btn)
    Button btn;

    @Override
    public int inflaterLayout() {
        return R.layout.activity_demo;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    @Override
    public void onSuccess(String json, int flag) {
        tv.append(json);
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
