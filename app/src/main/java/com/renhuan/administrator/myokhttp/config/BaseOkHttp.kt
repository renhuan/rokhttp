package com.renhuan.administrator.myokhttp.config

import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.GsonUtils
import com.lxj.xpopup.XPopup
import com.lzy.okgo.model.HttpHeaders
import com.lzy.okgo.model.Response
import com.renhuan.administrator.myokhttp.App
import com.renhuan.administrator.myokhttp.R
import com.renhuan.okhttplib.http.RBaseOkHttp
import com.renhuan.okhttplib.http.RBaseOkHttpImp
import com.renhuan.okhttplib.utils.Renhuan
import com.renhuan.okhttplib.utils.postDelayed
import java.lang.reflect.ParameterizedType


/**
 * created by renhuan
 * time : 2020/5/15 10:10
 * describe : T 如果为Any 则类class为ANY_CLASS 否者为T.class
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
//        XPopup.Builder(Renhuan.getCurrentActivity())
//            .hasShadowBg(false)
//            .dismissOnTouchOutside(false)
//            .asLoading()
//            .bindLayout(R.layout.loading)
    }

    override fun onRError(rBaseOkHttpImp: RBaseOkHttpImp?) {
        rBaseOkHttpImp?.onError()
    }

    override fun onRSuccess(response: Response<String>?, rBaseOkHttpImp: RBaseOkHttpImp?) {
        response?.body()?.let {
            val obj = GsonUtils.fromJson(it, BaseResponse<T>()::class.java)
            when (obj?.errcode) {
//                Cons.Api.SUCCESS -> {
//                    val cls = (javaClass.genericSuperclass as ParameterizedType?)?.actualTypeArguments?.get(0) as Class<*>
//                    if (cls.name == ANY_CLASS) {
//                        rBaseOkHttpImp?.onSuccess(BaseCode(code))
//                    } else {
//                        rBaseOkHttpImp?.onSuccess(GsonUtils.fromJson(GsonUtils.toJson(obj.data!!), cls))
//                    }
//                }
//                Cons.Api.LOGIN_AGAIN -> {
//                    Renhuan.toast("code：${obj.errcode},请重新登录")
//                    App.appLoginout()
//                }
//                else -> {
//                    rBaseOkHttpImp?.onError()
//                    Renhuan.toast(obj.errmsg)
//                }
            }
        }
    }


    override fun setParameter(hashMap: HashMap<String, String>): RBaseOkHttp<T> {
//        hashMap["_token"] = App.getToken()!!
        return super.setParameter(hashMap)
    }

    override fun setHttpHead(httpHeaders: HttpHeaders): HttpHeaders? {
        return httpHeaders
    }

    override fun showLoading() {
//        loading.show()
    }

    override fun hideLoading() {
//        loading.dismiss()
    }
}