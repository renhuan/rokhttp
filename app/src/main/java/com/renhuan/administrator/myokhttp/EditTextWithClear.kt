package com.renhuan.administrator.myokhttp

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import com.blankj.utilcode.util.SizeUtils
import com.renhuan.okhttplib.utils.Renhuan

/**
 * created by renhuan
 * time : 2020/10/13 10:01
 * describe :
 */
class EditTextWithClear @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private var iconDrawable: Drawable? = null
    private var mWidth: Float = 0f
    private var mHeight: Float = 0f

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.EditTextWithClear, defStyleAttr, 0)
                .apply {
                    try {
                        val iconID = getResourceId(R.styleable.EditTextWithClear_rh_drawableRight, 0)
                        if (iconID != 0) {
                            iconDrawable = Renhuan.getDrawable(iconID)
                        }
                        mWidth = getDimension(R.styleable.EditTextWithClear_rh_drawableWidth, 0f)
                        mHeight = getDimension(R.styleable.EditTextWithClear_rh_drawableHeight, 0f)
                    } finally {
                        recycle()
                    }
                }
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        toggleClearIcon()
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { e ->
            iconDrawable?.let {
                if (e.action == MotionEvent.ACTION_UP
                        && e.x > width - it.intrinsicWidth - 20
                        && e.x < width + 20
                        && e.y > height / 2 - it.intrinsicHeight / 2 - 20
                        && e.y < height / 2 + it.intrinsicHeight / 2 + 20
                ) {
                    text?.clear()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        toggleClearIcon()
    }

    private fun toggleClearIcon() {
        val icon = if (isFocused && text?.isNotEmpty() == true) iconDrawable else null
        //默认的图片大小
        if (mWidth == 0f && mHeight == 0f) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)
        } else {
            //自己设置宽高大小
            icon?.setBounds(0, 0, mWidth.toInt(), mHeight.toInt())
            setCompoundDrawables(null, null, icon, null)
        }

    }
}