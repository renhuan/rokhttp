package com.example.okhttplib.config;

import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
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

    public abstract void onRSuccess(Response<String> response, OkhttpImp imp);//数据处理的方法给子类去重写

    private boolean isShowLoading = true;
    private String url;
    private HashMap<String, String> hashMap = new HashMap<>();
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


    public RBaseOkHttp get() {
        OkGo.<String>get(url).params(hashMap).execute(getStringCallback(okhttpImp, isShowLoading));
        return this;
    }

    public RBaseOkHttp post() {
        OkGo.<String>post(url).params(hashMap).execute(getStringCallback(okhttpImp, isShowLoading));
        return this;
    }


    private StringCallback getStringCallback(final OkhttpImp okhttpImp, final boolean isShowLoading) {
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
                MyException(response);
                okhttpImp.onError(response);
            }

            @Override
            public void onStart(Request<String, ? extends Request> request) {
                if (isShowLoading) {
                    showLoading(tag);
                }
                okhttpImp.onStart(request);
            }
        };
    }

    /**
     * 异常处理
     *
     * @param response
     */
    private static void MyException(Response<String> response) {
        int code = response.code();
        if (code == 404) {
            ToastUtils.showShort("404 当前链接不存在");
        } else if (code == 500) {
            ToastUtils.showShort("500 服务器错误");
        } else if (response.getException() instanceof SocketTimeoutException) {
            ToastUtils.showShort("请求超时");
        } else if (response.getException() instanceof SocketException) {
            ToastUtils.showShort("服务器异常");
        }
    }

    /***
     * 显示loading
     * @param tag
     */
    public void showLoading(String tag) {

    }

    /***
     * 隐藏loading
     * @param tag
     */
    public void hideLoading(String tag) {

    }

}
