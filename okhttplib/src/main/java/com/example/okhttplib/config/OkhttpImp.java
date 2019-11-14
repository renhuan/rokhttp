package com.example.okhttplib.config;

import com.lzy.okgo.model.Response;


public interface OkhttpImp {

    void onSuccess(Response<String> response);

    void onError(Response<String> response);

}
