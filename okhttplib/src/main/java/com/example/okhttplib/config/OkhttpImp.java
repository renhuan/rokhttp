package com.example.okhttplib.config;

import com.lzy.okgo.model.Response;


public interface OkhttpImp {
    void onStart(com.lzy.okgo.request.base.Request<String, ? extends com.lzy.okgo.request.base.Request> request);

    void onSuccess(Response<String> response);

    void onError(Response<String> response);
}
