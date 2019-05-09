package com.example.administrator.myokhttp;

import android.annotation.SuppressLint;
import android.app.Application;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        OkGo.getInstance().init(this);
        initCrash();
    }

    @SuppressLint("MissingPermission")
    private void initCrash() {
        CrashUtils.init(new CrashUtils.OnCrashListener() {
            @Override
            public void onCrash(String crashInfo, Throwable e) {
                AppUtils.relaunchApp();
            }
        });
    }
}
