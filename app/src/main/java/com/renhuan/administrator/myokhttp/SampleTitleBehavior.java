package com.renhuan.administrator.myokhttp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

/**
 * created by renhuan
 * time : 2020/10/29 17:54
 * describe :
 */
class SampleTitleBehavior extends CoordinatorLayout.Behavior<View> {

    public SampleTitleBehavior() {
    }

    public SampleTitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        System.out.println("------" + dependency.getY() + " " + child.getHeight());
        child.setAlpha(0.5f);
        return true;
    }
}
