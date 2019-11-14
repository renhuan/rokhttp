package com.example.administrator.myokhttp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.myokhttp.base.BaseActivity;
import com.example.administrator.myokhttp.config.Api;
import com.example.okhttplib.recyclerview.RRecyclerView;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private final static int REQUEST_CODE = 998;
    private List<Bean> list = new ArrayList<>();
    RRecyclerView recyclerview;
    TextView tv;
    Button btn;

    @Override
    public int inflaterLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onSuccess(String json, int flag) {
        List<Bean> beans = JSON.parseArray(json, Bean.class);
        list.clear();
        list.addAll(beans);
        recyclerview.adapters.notifyDataSetChanged();
    }

    @Override
    public void onError(String json, int flag) {

    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        recyclerview = findViewById(R.id.recyclerview);
        tv = findViewById(R.id.tv);
        btn = findViewById(R.id.btn);
        tv.setOnClickListener(v -> {
            PermissionUtils.permission(PermissionConstants.STORAGE, PermissionConstants.CAMERA).callback(new PermissionUtils.FullCallback() {
                @Override
                public void onGranted(List<String> permissionsGranted) {
                    Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }

                @Override
                public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {

                }
            }).request();
        });

        recyclerview.setRAdapter(new BaseQuickAdapter<Bean, BaseViewHolder>(R.layout.item_bean, list) {
            @Override
            protected void convert(@NonNull BaseViewHolder helper, Bean item) {
                helper.setText(R.id.tv, "编号:" + item.getHardware_code() + " 步数:" + item.getFootstep() + " 时间:" + item.getCreate_time());
            }
        }).setROnItemClickListener((adapter, view, position) -> {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("详情")//设置对话框的标题
                    .setMessage(list.get(position).toString())//设置对话框的内容
                    .create();
            dialog.show();
        });
        btn.setOnClickListener(v -> Api.getRequest(content, this));
    }

    String content = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                content = data.getStringExtra(Constant.CODED_CONTENT);
                tv.setText(content);
                Api.getRequest(content, this);
            }
        }
    }
}
