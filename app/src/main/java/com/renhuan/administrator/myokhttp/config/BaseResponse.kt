package com.renhuan.administrator.myokhttp.config

/**
 * created by renhuan
 * time : 2020/5/18 14:54
 * describe :
 */
class BaseResponse<T> {
    var errcode = 0
    var errmsg: String? = ""
    var data: T? = null
}