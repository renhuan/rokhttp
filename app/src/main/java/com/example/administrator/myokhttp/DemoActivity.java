package com.example.administrator.myokhttp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.TimeUtils;
import com.example.administrator.myokhttp.base.BaseActivity;
import com.example.okhttplib.utils.ImagePickerHelper;
import com.yhao.floatwindow.FloatWindow;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DemoActivity extends BaseActivity {

//    @BindView(R.id.tv)
//    TextView tv;
//    @BindView(R.id.btn)
//    Button btn;

//    @Override
//    public int inflaterLayout() {
//        return R.layout.activity_demo;
//    }

    @Override
    public int inflaterLayout() {
        return 0;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        ButterKnife.bind(this);

        View inflate = getLayoutInflater().inflate(R.layout.layou_inflat, null);
        Chronometer tv = inflate.findViewById(R.id.tv);
        FloatWindow
                .with(getApplicationContext())
                .setView(inflate)
                .setDesktopShow(true)                        //桌面显示
                .build();

        tv.setText(TimeUtils.getNowString(new SimpleDateFormat("HH:mm:ss")));
        tv.setOnChronometerTickListener(chronometer -> tv.setText(TimeUtils.getNowString(new SimpleDateFormat("HH:mm:ss"))));
        tv.start();
    }

    @Override
    public void onSuccess(String json, int flag) {
//        tv.append(json);
    }

    @Override
    public void onError(String json, int flag) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String path = ImagePickerHelper.onActivityResult(requestCode, resultCode, data);
        if (!TextUtils.isEmpty(path)) {
            System.out.println("-----" + path);
        }
    }

//    @OnClick(R.id.btn)
//    public void onViewClicked() {
////        Api.getDemo(this);
//        ImagePickerHelper.setPickImage(BuildConfig.APPLICATION_ID + ".fileprovider");
//    }
}
