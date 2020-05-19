package com.renhuan.okhttplib.http

import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.NetworkUtils
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.HttpHeaders
import com.lzy.okgo.model.Response
import com.lzy.okgo.request.base.Request
import com.renhuan.okhttplib.RApp
import com.renhuan.okhttplib.utils.Renhuan


/**
 * http请求Base类
 * 第一种 build模式
 */

abstract class RBaseOkHttp<T> {

    private var isShowLoading = true                                            //网络请求是否显示loading
    private var url: String? = null                                             //网络请求的地址
    private var hashMap = hashMapOf<String, String>()   //网络请求参数
    private var cacheMode = CacheMode.DEFAULT                                   //网络请求缓存模式
    private var rBaseOkHttpImp: RBaseOkHttpImp? = null                          //网络请求回调


    private val stringCallback: StringCallback
        get() = object : StringCallback() {
            override fun onSuccess(response: Response<String>) {
                if (isShowLoading) {
                    hideLoading()
                }
                onRSuccess(response, rBaseOkHttpImp)
            }

            override fun onError(response: Response<String>) {
                if (isShowLoading) {
                    hideLoading()
                }
                Renhuan.toast("请求失败,code=${response.code()}\nmessage=${response.exception.message}")
            }

            override fun onStart(request: Request<String, out Request<*, *>>?) {
                //隐藏软键盘
                if (KeyboardUtils.isSoftInputVisible(Renhuan.getCurrentActivity()!!)) {
                    KeyboardUtils.hideSoftInput(Renhuan.getCurrentActivity()!!)
                }
                //请求网络前  先检查是否有网络连接
                if (!NetworkUtils.isConnected()) {
                    Renhuan.toast("当前无网络连接，请检查网络")
                    RApp.cancelHttp(Renhuan.getCurrentActivity())
                    return
                }

                if (isShowLoading) {
                    when (cacheMode) {
                        CacheMode.FIRST_CACHE_THEN_REQUEST, CacheMode.DEFAULT, CacheMode.NO_CACHE -> showLoading()
                        else -> {
                        }
                    }
                }
            }

            override fun onCacheSuccess(response: Response<String>?) {
                onRSuccess(response, rBaseOkHttpImp)
            }
        }

    fun isShowLoading(isShowLoading: Boolean): RBaseOkHttp<T> {
        this.isShowLoading = isShowLoading
        return this
    }

    fun setUrl(url: String): RBaseOkHttp<T> {
        this.url = url
        return this
    }

    open fun setParameter(hashMap: HashMap<String, String>): RBaseOkHttp<T> {
        this.hashMap = hashMap
        return this
    }

    fun setCallBack(rBaseOkHttpImp: RBaseOkHttpImp): RBaseOkHttp<T> {
        this.rBaseOkHttpImp = rBaseOkHttpImp
        return this
    }


    /**
     * NO_CACHE：                 不使用缓存，该模式下cacheKey、cacheTime 参数均无效
     * DEFAULT：                  按照HTTP协议的默认缓存规则，例如有304响应头时缓存。
     * REQUEST_FAILED_READ_CACHE：先请求网络，如果请求网络失败，则读取缓存，如果读取缓存失败，本次请求失败。
     * IF_NONE_CACHE_REQUEST：    如果缓存不存在才请求网络，否则使用缓存。
     * FIRST_CACHE_THEN_REQUEST： 先使用缓存，不管是否存在，仍然请求网络。
     *
     * @return
     */
    fun setCache(cacheMode: CacheMode): RBaseOkHttp<T> {
        this.cacheMode = cacheMode
        return this
    }

    fun get() {
        OkGo.get<String>(url)
            .params(hashMap)
            .cacheKey(url)
            .cacheMode(cacheMode)
            .headers(setHttpHead(HttpHeaders()))
            .tag(Renhuan.getCurrentActivity())
            .execute(stringCallback)
    }

    fun post() {
        OkGo.post<String>(url)
            .params(hashMap)
            .cacheKey(url)
            .cacheMode(cacheMode)
            .headers(setHttpHead(HttpHeaders()))
            .tag(Renhuan.getCurrentActivity())
            .execute(stringCallback)
    }


    /**
     * 数据处理的方法给子类去重写
     *
     * @param response
     * @param myOkHttpImp
     * @param code
     */
    abstract fun onRSuccess(response: Response<String>?, rBaseOkHttpImp: RBaseOkHttpImp?)

    /**
     * 定义httphead  给子类去重写
     *
     * @return
     */
    abstract fun setHttpHead(httpHeaders: HttpHeaders): HttpHeaders?

    /***
     * 显示loading  给子类去重写
     */
    abstract fun showLoading()

    /***
     * 隐藏loading  给子类去重写
     */
    abstract fun hideLoading()

}
