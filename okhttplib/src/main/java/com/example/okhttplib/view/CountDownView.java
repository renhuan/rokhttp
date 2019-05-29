package com.example.okhttplib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by renhuan on
 * 倒计时控件 button
 */

@SuppressLint("DefaultLocale")
public class CountDownView extends android.support.v7.widget.AppCompatButton {

    private Timer timer;
    private int second = 60;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                setText(String.format("%d秒", second));
                second--;
                if (second == 0) {
                    timer.cancel();
                    timer = null;
                    setClickable(true);
                    setText("重新获取");
                    second = 60;
                }
            }
        }
    };

    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    /***
     * 开始计时
     */
    public void countDown() {
        setClickable(false);
        setText(String.format("%d", second));
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        }, 0, 1000);
    }
}
