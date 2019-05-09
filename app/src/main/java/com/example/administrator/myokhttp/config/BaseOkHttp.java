package com.example.administrator.myokhttp.config;

import android.text.TextUtils;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.administrator.myokhttp.base.BaseActivity;
import com.example.okhttplib.base.RBaseActivity;
import com.example.okhttplib.config.OkhttpImp;
import com.example.okhttplib.config.RBaseOkHttp;
import com.lxj.xpopup.XPopup;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 此类实现接口成功后的success逻辑
 */
public class BaseOkHttp extends RBaseOkHttp {
    private static XPopup xPopup;


    @Override
    public void onRSuccess(com.lzy.okgo.model.Response<String> response, OkhttpImp imp) {
        try {
            JSONObject jsonObject = new JSONObject(response.body());
            int code = jsonObject.getInt("code");
            LogUtils.i(response.body());
            if (code == 1) {
                imp.onSuccess(response);
            } else {
                String message = jsonObject.getString("message");
                ((BaseActivity) ActivityUtils.getTopActivity()).toast(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HttpHeaders setHttpHead(HttpHeaders httpHeaders) {
        return null;
    }


    @Override
    public void showLoading(String tag) {
        xPopup = XPopup.get(ActivityUtils.getTopActivity()).asLoading();
        xPopup.show(tag);
    }

    @Override
    public void hideLoading(String tag) {
        if (xPopup != null)
            xPopup.dismiss(tag);
    }
}
