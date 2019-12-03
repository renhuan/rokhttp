package com.example.okhttplib.view

import android.content.Context
import androidx.appcompat.widget.AppCompatEditText
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet

import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ToastUtils


/**
 * 价格edittext
 * 1-输入0显示0.
 * 2-保留两位小时
 * 3-限制不超过多少 默认100000.00
 */
class PriceEditTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : AppCompatEditText(context, attrs), TextWatcher {

    private var limit = 100000.00

    init {
        filters = arrayOf<InputFilter>(object : InputFilter {
            internal var decimalNumber = 2//小数点后保留位数

            override//source:即将输入的内容 dest：原来输入的内容
            fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
                val sourceContent = source.toString()
                val lastInputContent = dest.toString()

                //验证删除等按键
                if (TextUtils.isEmpty(sourceContent)) {
                    return ""
                }
                //以小数点"."开头，默认为设置为“0.”开头
                if (sourceContent == "." && lastInputContent.length == 0) {
                    return "0."
                }
                //输入“0”，默认设置为以"0."开头
                if (sourceContent == "0" && lastInputContent.length == 0) {
                    return "0."
                }
                //小数点后保留两位
                if (lastInputContent.contains(".")) {
                    val index = lastInputContent.indexOf(".")
                    if (dend - index >= decimalNumber + 1) {
                        return ""
                    }

                }
                return null
            }
        })
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        try {
            if (java.lang.Double.valueOf(s.toString()) > limit) {
                setText("0.00")
                KeyboardUtils.hideSoftInput(this)
                ToastUtils.showShort("超过当前最大数值")
            }
            if (java.lang.Double.valueOf(s.toString()) < 0) {
                setText("0.00")
                KeyboardUtils.hideSoftInput(this)
                ToastUtils.showShort("数量不能小于0")
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }

    }


    override fun afterTextChanged(s: Editable) {

    }

    fun setLimit(limit: Double) {
        this.limit = limit
    }
}
