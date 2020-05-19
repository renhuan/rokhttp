package com.renhuan.administrator.myokhttp.config

import android.text.TextUtils
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.lxj.xpopup.XPopup
import com.lzy.okgo.model.HttpHeaders
import com.lzy.okgo.model.Response
import com.renhuan.administrator.myokhttp.R
import com.renhuan.okhttplib.http.RBaseOkHttp
import com.renhuan.okhttplib.http.RBaseOkHttpImp
import com.renhuan.okhttplib.utils.Renhuan
import java.lang.reflect.ParameterizedType
import java.util.*

/**
 * http请求Base类
 */
open class BaseOkHttp<T> : RBaseOkHttp<T>() {

    private var code: Int = 0

    companion object {
        const val ANY_CLASS = "java.lang.Object"
    }

    /**
     * {@link com.android.pool.http.BaseCode}
     */
    fun setCode(code: Int): RBaseOkHttp<T> {
        this.code = code
        return this
    }

    private val loading by lazy {
        XPopup.Builder(Renhuan.getCurrentActivity())
                .hasShadowBg(false)
                .dismissOnTouchOutside(false)
                .asLoading()
    }


    override fun onRSuccess(response: Response<String>?, rBaseOkHttpImp: RBaseOkHttpImp?) {
        response?.body()?.let {
//            val obj = JSON.parseObject(it, BaseResponse<T>()::class.java)
//            when (obj?.errcode) {
//                Cons.Api.SUCCESS -> {
//                    val cls = (javaClass.genericSuperclass as ParameterizedType?)?.actualTypeArguments?.get(0) as Class<*>
//                    if (cls.name == ANY_CLASS) {
//                        rBaseOkHttpImp?.onSuccess(BaseCode(code))
//                    } else {
//                        rBaseOkHttpImp?.onSuccess(JSON.toJavaObject(obj.data as JSONObject, cls))
//                    }
//                }
//                else -> Renhuan.toast(obj.errmsg)
        }
    }

    override fun setHttpHead(httpHeaders: HttpHeaders): HttpHeaders? {
        return httpHeaders
    }

    override fun showLoading() {
        loading.show()
    }

    override fun hideLoading() {
        loading.dismiss()
    }
}
