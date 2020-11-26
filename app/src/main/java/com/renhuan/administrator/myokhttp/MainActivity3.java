package com.renhuan.administrator.myokhttp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class MainActivity3 extends AppCompatActivity {


    private AppBarLayout appBar;
    private LinearLayout titleStatu;
    private Toolbar toolBar;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        appBar = findViewById(R.id.appBar);
        titleStatu = findViewById(R.id.titleStatu);
        toolBar = findViewById(R.id.toolBar);
        tv = findViewById(R.id.tv);
        toolBar.inflateMenu(R.menu.demo_menu);
        appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            int collapMaxHeight = appBar.getMeasuredHeight() - toolBar.getMeasuredHeight();
            if (Math.abs(verticalOffset) >= collapMaxHeight - 50) {
                titleStatu.animate().scaleX(0.85f).setDuration(100).start();
                titleStatu.animate().scaleY(0.85f).setDuration(100).start();
                tv.setTextColor(Color.RED);
            } else {
                titleStatu.setScaleX(1f);
                titleStatu.setScaleY(1f);
                tv.setTextColor(Color.BLACK);
            }
        });
    }
}