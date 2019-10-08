package com.example.okhttplib.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;


/**
 * 价格edittext
 * 1-输入0显示0.
 * 2-保留两位小时
 * 3-限制不超过多少 默认100000.00
 */
public class PriceEditTextView extends AppCompatEditText implements TextWatcher {


    public PriceEditTextView(Context context) {
        this(context, null);
    }

    public PriceEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFilters(new InputFilter[]{new InputFilter() {
            int decimalNumber = 2;//小数点后保留位数

            @Override
            //source:即将输入的内容 dest：原来输入的内容
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String sourceContent = source.toString();
                String lastInputContent = dest.toString();

                //验证删除等按键
                if (TextUtils.isEmpty(sourceContent)) {
                    return "";
                }
                //以小数点"."开头，默认为设置为“0.”开头
                if (sourceContent.equals(".") && lastInputContent.length() == 0) {
                    return "0.";
                }
                //输入“0”，默认设置为以"0."开头
                if (sourceContent.equals("0") && lastInputContent.length() == 0) {
                    return "0.";
                }
                //小数点后保留两位
                if (lastInputContent.contains(".")) {
                    int index = lastInputContent.indexOf(".");
                    if (dend - index >= decimalNumber + 1) {
                        return "";
                    }

                }
                return null;
            }
        }});
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        try {
            if (Double.valueOf(s.toString()) > limit) {
                setText("0.00");
                KeyboardUtils.hideSoftInput(this);
                ToastUtils.showShort("超过当前最大数值");
            }
            if (Double.valueOf(s.toString()) < 0) {
                setText("0.00");
                KeyboardUtils.hideSoftInput(this);
                ToastUtils.showShort("数量不能小于0");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void afterTextChanged(Editable s) {

    }

    private double limit = 100000.00;

    public void setLimit(double limit) {
        this.limit = limit;
    }
}
