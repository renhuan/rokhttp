package com.example.okhttplib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;


/***
 * 如果需要懒加载 使用方式
 *
 * 1.开启懒加载
 *     @Override
 *     protected boolean isUselazy() {
 *         return true;
 *     }
 *
 * 2.懒加载回调
 *     @Override
 *     public void fetchData() {
 *
 *     }
 */

public abstract class RLazyLoadFragment extends RBaseFragment {
    /**
     * 是否初始化过布局
     */
    protected boolean isViewInitiated;
    /**
     * 当前界面是否可见
     */
    protected boolean isVisibleToUser;
    /**
     * 是否加载过数据
     */
    protected boolean isDataInitiated;


    //默认不启用懒加载
    protected boolean isUselazy() {
        return false;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isUselazy()) {
            isViewInitiated = true;
            prepareFetchData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isUselazy()) {
            this.isVisibleToUser = isVisibleToUser;
            if (isVisibleToUser) {
                prepareFetchData();
            }
        }
    }

    /**
     * 懒加载 返回函数
     */
    protected void fetchData() {

    }

    public void prepareFetchData() {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated)) {
            fetchData();
            isDataInitiated = true;
        }
    }
}
