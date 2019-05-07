package com.example.administrator.myokhttp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myokhttp.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


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
}
