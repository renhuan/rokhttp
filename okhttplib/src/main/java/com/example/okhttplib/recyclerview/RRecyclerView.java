package com.example.okhttplib.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.okhttplib.R;

import java.util.Collection;
import java.util.List;

public class RRecyclerView extends RecyclerView {


    private final LinearDividerItemDecoration itemDecoration;
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
        addItemDecoration(itemDecoration = new LinearDividerItemDecoration(LinearDividerItemDecoration.VERTICAL_LIST, 1));
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

    public RRecyclerView removeItemDecoration() {
        removeItemDecoration(itemDecoration);
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
     * 创建adapter后  才能使用以下方法
     *
     * @param adapters
     * @return
     */


    public RRecyclerView setRAdapter(BaseQuickAdapter adapters) {
        this.adapters = adapters;
        if (adapters.getData().size() == 0) {
            adapters.setEmptyView(emptyView, this);
        }
        adapters.isUseEmpty(false);
        setAdapter(adapters);
        return this;
    }

    public RRecyclerView setROpenLoadAnimation(int type) {
        if (adapters != null) {
            adapters.openLoadAnimation(type);
        }
        return this;
    }

    public RRecyclerView setROnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener) {
        if (adapters != null) {
            adapters.setOnLoadMoreListener(requestLoadMoreListener, this);
        }
        return this;
    }

    /**
     * 直接addData即可
     *
     * @param isUseEmpty
     * @return
     */
    @Deprecated
    public RRecyclerView isUseEmpty(boolean isUseEmpty) {
        if (adapters != null) {
            adapters.isUseEmpty(isUseEmpty);
        }
        return this;
    }


    public RRecyclerView addData(List newData) {
        if (adapters != null) {
            if (adapters.getData().size() == 0 && newData.size() == 0) {
                adapters.isUseEmpty(true);
            }
            if (newData.size() == 0) {
                adapters.loadMoreEnd();
            } else {
                adapters.loadMoreComplete();
            }
            adapters.addData(newData);
        }
        return this;
    }

    public RRecyclerView setNewData(List newData) {
        if (adapters != null) {
            adapters.setNewData(newData);
        }
        return this;
    }

    public RRecyclerView setROnItemClickListener(BaseQuickAdapter.OnItemClickListener onItemClickListener) {
        if (adapters != null) {
            adapters.setOnItemClickListener(onItemClickListener);
        }
        return this;
    }

    public RRecyclerView setROnItemLongClickListener(BaseQuickAdapter.OnItemLongClickListener onItemClickListener) {
        if (adapters != null) {
            adapters.setOnItemLongClickListener(onItemClickListener);
        }
        return this;
    }

    public RRecyclerView setROnItemChildClickListener(BaseQuickAdapter.OnItemChildClickListener onItemChildClickListener) {
        if (adapters != null) {
            adapters.setOnItemChildClickListener(onItemChildClickListener);
        }
        return this;
    }

}
