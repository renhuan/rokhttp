package com.renhuan.administrator.myokhttp.sms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.TextView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.renhuan.administrator.myokhttp.MainActivity;
import com.renhuan.administrator.myokhttp.R;
import com.renhuan.okhttplib.utils.Renhuan;

import java.util.List;

public class MainActivity5 extends AppCompatActivity {
    private TextView mTextView;
    //所有短信
    public static final String URI = "content://sms/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        init();
    }

    private void init() {
        mTextView = (TextView) findViewById(R.id.textView);

        PermissionUtils.permission(PermissionConstants.SMS).callback(new PermissionUtils.SimpleCallback() {
            @Override
            public void onGranted() {
                //注册内容观察者
                SMSContentObserver smsContentObserver =
                        new SMSContentObserver(new Handler(), MainActivity5.this);

                getContentResolver().registerContentObserver
                        (Uri.parse(URI), true, smsContentObserver);

                //回调
                smsContentObserver.setOnReceivedMessageListener(message -> {
                            mTextView.append("\n手机号：" + message.getPhoneNumber() +
                                    "\n时间：" + TimeUtils.millis2String(Long.valueOf(message.getDate())) +
                                    "\n内容：" + message.getSmsbody() + "\n");
                        }
                );
            }

            @Override
            public void onDenied() {
                finish();
            }

        }).request();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return Renhuan.INSTANCE.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}