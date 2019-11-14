package com.example.administrator.myokhttp.config;

import com.example.okhttplib.config.MyOkHttpImp;
import com.example.okhttplib.config.OkhttpImp;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.HashMap;

/**
 * 此类存放数据接口
 */
public class Api {
    public static final int A = 0x700000;
    public static final int B = 0x700001;
    public static final int C = 0x700002;
    public static final int D = 0x700003;

    public static final String ROOT_URL = "http://api.renhuanok.top/";

    public static OkhttpImp getOkHttpImp(final MyOkHttpImp baseCall, final int tag) {
        return new OkhttpImp() {
            @Override
            public void onSuccess(Response<String> response) {
                baseCall.onSuccess(response.body(), tag);
            }

            @Override
            public void onError(Response<String> response) {
                baseCall.onError(response.body(), tag);
            }
        };
    }

    public static OkhttpImp getOkHttpImp(MyOkHttpImp baseCall) {
        return getOkHttpImp(baseCall, Api.A);
    }

    public static void getDemo(MyOkHttpImp baseCall) {
        new BaseOkHttp()
                .setUrl(ROOT_URL + "index.php/index/index")
                .setOkhttpImp(getOkHttpImp(baseCall))
                .get();
    }

    public static void getRequest(String result, MyOkHttpImp baseCall) {
        new BaseOkHttp()
                .setUrl("https://192.168.0.100:4782/hardware/data/query?hardware_code=" + result)
                .setOkhttpImp(getOkHttpImp(baseCall))
                .get();
    }
}
