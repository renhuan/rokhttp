package com.example.administrator.myokhttp.base;

import android.view.View;

import com.example.okhttplib.base.RBaseFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends RBaseFragment {

    Unbinder bind;

    @Override
    public void init(View view) {
        bind = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        bind.unbind();
        super.onDestroyView();

    }
}
