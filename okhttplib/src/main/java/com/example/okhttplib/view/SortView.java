package com.example.okhttplib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;

public class SortView extends View {

    private int color_select = Color.parseColor("#DFB05F");
    private int color_unselect = Color.parseColor("#999999");
    private Paint paint;
    private static final int default_width = 100;
    private static final int default_height = 50;
    private static final int FROM_DEFAULTS = 0;
    private static final int FROM_LOW_2_HEIGTH = 1;
    private static final int FROM_HEIGHT_2_LOW = 2;
    private int tag = FROM_DEFAULTS;//记录点击选择的状态 从大到小  从小到大  还是默认
    private int position = 0;//记录点击的下标
    private String[] title;
    private SparseArray<Region> sparseArray = new SparseArray<>();
    public OnClickListeners onClickListeners;

    public SortView(Context context) {
        this(context, null);
    }

    public SortView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SortView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color_unselect);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setTextSize(SizeUtils.dp2px(12));
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(default_width, default_height);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(default_width, height);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, default_height);
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        if (title == null) {
            return;
        }

        int width = (getWidth() - paddingLeft - paddingRight) / title.length;
        int height = getHeight() - paddingTop - paddingBottom;

        for (int i = 0; i < title.length; i++) {
            paint.setColor(color_unselect);
            //绘文字
            if (tag != FROM_DEFAULTS && position == i) {
                paint.setColor(color_select);
            }

            Rect rect = new Rect();
            paint.getTextBounds(title[i], 0, title[i].length(), rect);
            int text_width = rect.width();
            int text_height = rect.height();
            int[] centerXY = new int[2];
            centerXY[0] = (width / 2) + (width * i) + paddingLeft;
            centerXY[1] = height / 2 + paddingTop;
            canvas.drawText(title[i], centerXY[0] - text_width / 2, centerXY[1] + text_height / 2, paint);

            //绘制三角符号
            if (tag == FROM_LOW_2_HEIGTH && position == i) {
                paint.setColor(color_select);
            } else if (tag == FROM_HEIGHT_2_LOW && position == i) {
                paint.setColor(color_unselect);
            }
            Path path_up = new Path();
            path_up.moveTo(centerXY[0] + text_width / 2 + 25, centerXY[1] - text_height / 2 + 6);
            path_up.lineTo(centerXY[0] + text_width / 2 + 15, centerXY[1] + 1);
            path_up.lineTo(centerXY[0] + text_width / 2 + 35, centerXY[1] + 1);
            canvas.drawPath(path_up, paint);

            if (tag == FROM_LOW_2_HEIGTH && position == i) {
                paint.setColor(color_unselect);
            } else if (tag == FROM_HEIGHT_2_LOW && position == i) {
                paint.setColor(color_select);
            }
            Path path_down = new Path();
            path_down.moveTo(centerXY[0] + text_width / 2 + 25, centerXY[1] + text_height / 2 + 1);
            path_down.lineTo(centerXY[0] + text_width / 2 + 15, centerXY[1] + 6);
            path_down.lineTo(centerXY[0] + text_width / 2 + 35, centerXY[1] + 6);
            canvas.drawPath(path_down, paint);


            //下标和点击区域存进去存入hashmap
            sparseArray.put(i, new Region(new Rect(centerXY[0] - 70, centerXY[1] - 70, centerXY[0] + 70, centerXY[1] + 70)));
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();

                for (int i = 0; i < sparseArray.size(); i++) {
                    if (sparseArray.get(i).contains(x, y)) {
                        //如果能点击 且点击的不是同一个下标 恢复默认
                        if (i != position) {
                            tag = FROM_DEFAULTS;
                            position = i;
                        }
                        if (tag == FROM_DEFAULTS) {
                            tag = FROM_LOW_2_HEIGTH;
                            onClickListeners.mFromDown(i);
                        } else if (tag == FROM_LOW_2_HEIGTH) {
                            tag = FROM_HEIGHT_2_LOW;
                            onClickListeners.mFromUp(i);
                        } else if (tag == FROM_HEIGHT_2_LOW) {
                            tag = FROM_DEFAULTS;
                            onClickListeners.mFromDefalut(i);
                        }
                        invalidate();
                        return true;
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setOnClickListeners(OnClickListeners onClickListeners) {
        this.onClickListeners = onClickListeners;
    }


    public interface OnClickListeners {

        //默认
        void mFromDefalut(int position);

        //从大到小
        void mFromUp(int position);

        //从小到大
        void mFromDown(int position);

    }
}
