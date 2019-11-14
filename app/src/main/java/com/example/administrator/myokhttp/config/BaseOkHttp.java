package com.example.administrator.myokhttp.config;

import android.app.ProgressDialog;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.example.administrator.myokhttp.base.BaseActivity;
import com.example.okhttplib.config.OkhttpImp;
import com.example.okhttplib.config.RBaseOkHttp;
import com.lzy.okgo.model.HttpHeaders;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 此类实现接口成功后的success逻辑
 */
public class BaseOkHttp extends RBaseOkHttp {
//    private static XPopup xPopup;


    @Override
    public void onRSuccess(com.lzy.okgo.model.Response<String> response, OkhttpImp imp) {
        imp.onSuccess(response);
//        try {
//            JSONObject jsonObject = new JSONObject(response.body());
//            int code = jsonObject.getInt("code");
//            LogUtils.i(response.body());
//            if (code == 1) {
//                imp.onSuccess(response);
//            } else {
//                String message = jsonObject.getString("message");
//                ((BaseActivity) ActivityUtils.getTopActivity()).toast(message);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public HttpHeaders setHttpHead(HttpHeaders httpHeaders) {
        return null;
    }

    @Override
    public void showLoading() {
        dialog = new ProgressDialog(ActivityUtils.getTopActivity());
        dialog.setMessage("正在加载中");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void hideLoading() {
        if (dialog != null)
            dialog.dismiss();
    }

    ProgressDialog dialog;

}
