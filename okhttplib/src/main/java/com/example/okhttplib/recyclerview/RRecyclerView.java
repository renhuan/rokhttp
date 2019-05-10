package com.example.okhttplib.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.okhttplib.R;

public class RRecyclerView extends RecyclerView {


    /**
     * 默认 : LinearLayoutManager
     * 默认分割线 ：颜色透明 宽度1px
     * 颜色格式：
     * Color.parseColor("#000000")
     * Color.RED
     * ContextCompat.getColor(this, R.color.mainStyle)
     */
    private Context context;
    //默认空布局
    public int emptyView = R.layout.r_empty_view;
    private BaseQuickAdapter adapters;

    public RRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public RRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setRLinearLayoutManager();
        addItemDecoration(new LinearDividerItemDecoration(LinearDividerItemDecoration.VERTICAL_LIST, 1));
    }


    /**
     * 设置 LayoutManager
     *
     * @return
     */
    public RRecyclerView setRLinearLayoutManager() {
        setLayoutManager(new LinearLayoutManager(context));
        return this;
    }


    public RRecyclerView setRLinearHorizontalLayoutManager() {
        setRLinearHorizontalLayoutManager(false);
        return this;
    }

    /**
     * @param rightSlide false 左边开始  可以往左滑
     *                   trur  右边开始 可以往右滑动
     * @return
     */
    public RRecyclerView setRLinearHorizontalLayoutManager(boolean rightSlide) {
        setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, rightSlide));
        return this;
    }

    public RRecyclerView setRGridLayoutManager(int count) {
        setLayoutManager(new GridLayoutManager(context, count));
        return this;
    }

    /**
     * 设置 分割线
     *
     * @return
     */
    public RRecyclerView addRLinearDivider(int height, int listDividerColor) {
        addItemDecoration(new LinearDividerItemDecoration(LinearDividerItemDecoration.VERTICAL_LIST, height, listDividerColor));
        return this;
    }

    public RRecyclerView addRLinearHorizontalDivider(int height, int listDividerColor) {
        addItemDecoration(new LinearDividerItemDecoration(LinearDividerItemDecoration.HORIZONTAL_LIST, height, listDividerColor));
        return this;
    }

    public RRecyclerView addRGridDivider(int height) {
        addRGridDivider(height, Color.parseColor("#eeeeed"));
        return this;
    }

    public RRecyclerView addRGridDivider(int height, int listDividerColor) {
        addRGridDivider(height, height, listDividerColor);
        return this;
    }

    public RRecyclerView addRGridDivider(int width, int height, int listDividerColor) {
        addItemDecoration(new GridDividerItemDecoration(width, height, listDividerColor));
        return this;
    }

    /**
     * 设置空布局
     *
     * @param emptyView
     * @return
     */
    public RRecyclerView setREmptyView(int emptyView) {
        this.emptyView = emptyView;
        return this;
    }

    public RRecyclerView setRNestedScrollingEnabled(boolean enabled) {
        setNestedScrollingEnabled(enabled);
        return this;
    }


    /**
     * 必须倒数第二个设置
     *
     * @param adapters
     * @return
     */


    public RRecyclerView setRAdapter(BaseQuickAdapter adapters) {
        this.adapters = adapters;
        if (adapters.getData().size() == 0) {
            adapters.setEmptyView(emptyView, this);
        }
        setAdapter(adapters);
        return this;
    }

    /**
     * 必须倒数第一个设置
     *
     * @param
     * @return
     */
    public RRecyclerView setROnItemClickListener(BaseQuickAdapter.OnItemClickListener onItemClickListener) {
        if (adapters == null) {
            throw new NullPointerException("必须先setRAdapter()后，才能使用此方法");
        }
        adapters.setOnItemClickListener(onItemClickListener);
        return this;
    }

    /**
     * @param
     * @return
     */
    public RRecyclerView setROnItemChildClickListener(BaseQuickAdapter.OnItemChildClickListener onItemChildClickListener) {
        if (adapters == null) {
            throw new NullPointerException("必须先setRAdapter()后，才能使用此方法");
        }
        adapters.setOnItemChildClickListener(onItemChildClickListener);
        return this;
    }


}
