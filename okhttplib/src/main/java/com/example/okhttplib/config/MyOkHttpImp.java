package com.example.okhttplib.config;

import com.lzy.okgo.model.Response;

import org.json.JSONException;

public interface MyOkHttpImp {

    //数据请求成功
    void onSuccess(String json, int flag);

    //数据请求错误
    void onError(String json, int flag);

    //数据请求前
    void onBefore(int flag);

}
