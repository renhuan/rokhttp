package com.example.okhttplib.config;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.example.okhttplib.base.RBaseActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;


/**
 * http请求Base类
 * 第一种 build模式
 * 第二种 构造方法
 */

public abstract class RBaseOkHttp {

    private boolean isShowLoading = true;//网络请求是否显示loading
    private String url; //网络请求的地址
    private HashMap<String, String> hashMap = new HashMap<>();//网络请求参数
    private CacheMode cacheMode = CacheMode.DEFAULT;//网络请求缓存模式
    private OkhttpImp okhttpImp;

    public RBaseOkHttp isShowLoading(boolean isShowLoading) {
        this.isShowLoading = isShowLoading;
        return this;
    }

    public RBaseOkHttp setUrl(String url) {
        this.url = url;
        return this;
    }

    public RBaseOkHttp setOkhttpImp(OkhttpImp okhttpImp) {
        this.okhttpImp = okhttpImp;
        return this;
    }

    public RBaseOkHttp setParameter(HashMap<String, String> hashMap) {
        this.hashMap = hashMap;
        return this;
    }


    /**
     * NO_CACHE：不使用缓存，该模式下cacheKey、cacheTime 参数均无效
     * DEFAULT：按照HTTP协议的默认缓存规则，例如有304响应头时缓存。
     * REQUEST_FAILED_READ_CACHE：先请求网络，如果请求网络失败，则读取缓存，如果读取缓存失败，本次请求失败。
     * IF_NONE_CACHE_REQUEST：如果缓存不存在才请求网络，否则使用缓存。
     * FIRST_CACHE_THEN_REQUEST：先使用缓存，不管是否存在，仍然请求网络。
     *
     * @return
     */
    public RBaseOkHttp setCache(CacheMode cacheMode) {
        this.cacheMode = cacheMode;
        return this;
    }

    public RBaseOkHttp get() {
        OkGo.<String>get(url).params(hashMap).cacheKey(url).cacheMode(cacheMode).headers(setHttpHead(new HttpHeaders())).tag(ActivityUtils.getTopActivity()).execute(getStringCallback());
        return this;
    }

    public RBaseOkHttp post() {
        OkGo.<String>post(url).params(hashMap).cacheKey(url).cacheMode(cacheMode).headers(setHttpHead(new HttpHeaders())).tag(ActivityUtils.getTopActivity()).execute(getStringCallback());
        return this;
    }

    private StringCallback getStringCallback() {
        final String tag = String.valueOf(System.currentTimeMillis());
        return new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (isShowLoading) {
                    hideLoading(tag);
                }
                onRSuccess(response, okhttpImp);
            }

            @Override
            public void onError(Response<String> response) {
                if (isShowLoading) {
                    hideLoading(tag);
                }
                doException(response);
                okhttpImp.onError(response);
            }

            @Override
            public void onStart(Request<String, ? extends Request> request) {
                if (!NetworkUtils.isConnected()) {
                    ((RBaseActivity) ActivityUtils.getTopActivity()).toast("当前无网络连接，请检查网络");
                    OkGo.getInstance().cancelTag(ActivityUtils.getTopActivity());
                    return;
                }
                if (isShowLoading) {
                    if (CacheMode.FIRST_CACHE_THEN_REQUEST.equals(cacheMode)
                        || CacheMode.DEFAULT.equals(cacheMode)
                        || CacheMode.NO_CACHE.equals(cacheMode)) {
                        showLoading(tag);
                    }
                }
                okhttpImp.onStart(request);
            }

            @Override
            public void onCacheSuccess(Response<String> response) {
                onRSuccess(response, okhttpImp);
            }
        };
    }


    /**
     * 数据处理的方法给子类去重写
     *
     * @param response
     */
    public abstract void onRSuccess(Response<String> response, OkhttpImp imp);

    /**
     * 叫个子类去重写  定义httphead
     *
     * @return
     */
    public abstract HttpHeaders setHttpHead(HttpHeaders httpHeaders);

    /***
     * 显示loading  给子类去重写
     * @param tag
     */
    public abstract void showLoading(String tag);

    /***
     * 隐藏loading  给子类去重写
     * @param tag
     */
    public abstract void hideLoading(String tag);

    /**
     * 异常处理
     *
     * @param response
     */
    private static void doException(Response<String> response) {
        RBaseActivity baseActivity = (RBaseActivity) ActivityUtils.getTopActivity();
        int code = response.code();
        if (code == 404) {
            baseActivity.toast("404链接错误");
        } else if (code == 500) {
            baseActivity.toast("500服务器错误");
        } else if (code == 502) {
            baseActivity.toast("502服务器错误");
        } else if (response.getException() instanceof SocketTimeoutException) {
            baseActivity.toast("请求超时");
        } else if (response.getException() instanceof SocketException) {
            baseActivity.toast("服务器异常");
        }
    }


}
