package com.example.okhttplib.base

import android.os.Bundle


/***
 * 如果需要懒加载 使用方式
 *
 * 1.开启懒加载
 * @Override
 * protected boolean isUselazy() {
 * return true;
 * }
 *
 * 2.懒加载回调
 * @Override
 * public void fetchData() {
 *
 * }
 */

abstract class RLazyLoadFragment : RBaseFragment() {
    /**
     * 是否初始化过布局
     */
    protected var isViewInitiated: Boolean = false
    /**
     * 当前界面是否可见
     */
    protected var isVisibleToUser: Boolean = false
    /**
     * 是否加载过数据
     */
    protected var isDataInitiated: Boolean = false


    //默认不启用懒加载
    open val isUselazy: Boolean
        get() = false


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (isUselazy) {
            isViewInitiated = true
            prepareFetchData()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isUselazy) {
            this.isVisibleToUser = isVisibleToUser
            if (isVisibleToUser) {
                prepareFetchData()
            }
        }
    }

    /**
     * 懒加载 返回函数
     */
    protected open fun fetchData() {

    }

    fun prepareFetchData() {
        if (isVisibleToUser && isViewInitiated && !isDataInitiated) {
            fetchData()
            isDataInitiated = true
        }
    }
}
