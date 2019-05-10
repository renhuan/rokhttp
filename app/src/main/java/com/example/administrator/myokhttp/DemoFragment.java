package com.example.administrator.myokhttp;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.myokhttp.base.BaseFragment;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DemoFragment extends BaseFragment {


    @BindView(R.id.tv)
    TextView tv;

    @Override
    public int inflaterLayout() {
        return R.layout.fragment_demo;
    }

    @Override
    public void init(View view) {
        super.init(view);
        tv.setText("ddd");
    }

    @Override
    public void onSuccess(String json, int flag) {

    }

    @Override
    public void onError(String json, int flag) {

    }

    @Override
    public void onBefore(int flag) {

    }
}
