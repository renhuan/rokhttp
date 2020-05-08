package com.renhuan.okhttplib.config

interface RBaseOkHttpImp {

    //数据请求成功
    fun onSuccess(json: String?, requestCode: Int?)

    //数据请求错误
    fun onError(json: String?, requestCode: Int?)

}
