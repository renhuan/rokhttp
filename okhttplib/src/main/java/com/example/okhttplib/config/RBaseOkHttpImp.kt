package com.example.okhttplib.config

import com.lzy.okgo.model.Response

import org.json.JSONException

interface RBaseOkHttpImp {

    //数据请求成功
    fun onSuccess(json: String?, requestCode: Int?)

    //数据请求错误
    fun onError(json: String?, requestCode: Int?)

}
