package com.renhuan.okhttplib.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.renhuan.okhttplib.R
import kotlinx.android.synthetic.main.settingitem.view.*


class SettingItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    //右边风格 0-什么都不显示  1-显示图标 2-显示checkbox 3显示switch
    companion object {
        const val SHOW_NOTHING = 0
        const val SHOW_ICON = 1
        const val SHOW_CHECK = 2
        const val SHOW_SWITCH = 3
    }

    //左边文字
    private var leftText: String? = ""

    //右边文字
    private var rightText: String? = ""

    //左边图标
    private var leftIcon: Drawable? = null

    //右边风格 0-什么都不显示  1-显示图标 2-显示checkbox 3显示switch
    private var rightStyle: Int? = null

    /*点击事件*/
    private var mOnLSettingItemClick: OnLSettingItemClick? = null
    private var mOnLSettingItemLongClick: OnLSettingItemLongClick? = null

    init {
        getCustomStyle(context, attrs)
        View.inflate(context, R.layout.settingitem, this)
        refreshData()
        initListener()
    }

    private fun initListener() {
        rootViews.setOnClickListener { mOnLSettingItemClick?.click(this, false) }
        rootViews.setOnLongClickListener {
            mOnLSettingItemLongClick?.clickLong(this)
            true
        }
        right_check.setOnCheckedChangeListener { _, isChecked -> mOnLSettingItemClick?.click(this, isChecked) }
        right_switch.setOnCheckedChangeListener { _, isChecked -> mOnLSettingItemClick?.click(this, isChecked) }
    }

    private fun refreshData() {
        left_text.text = leftText
        right_text.text = rightText
        if (leftIcon == null) {
            left_icon.visibility = View.GONE
        } else {
            left_icon.visibility = View.VISIBLE
            left_icon.setImageDrawable(leftIcon)
        }
        when (rightStyle) {
            SHOW_NOTHING -> {
                right_icon.visibility = View.GONE
                right_check.visibility = View.GONE
                right_switch.visibility = View.GONE
            }
            SHOW_ICON -> {
                right_icon.visibility = View.VISIBLE
                right_check.visibility = View.GONE
                right_switch.visibility = View.GONE
            }
            SHOW_CHECK -> {
                right_icon.visibility = View.GONE
                right_check.visibility = View.VISIBLE
                right_switch.visibility = View.GONE
            }
            SHOW_SWITCH -> {
                right_icon.visibility = View.GONE
                right_check.visibility = View.GONE
                right_switch.visibility = View.VISIBLE
            }
        }
    }

    /**
     * 初始化自定义属性
     */
    private fun getCustomStyle(context: Context, attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView)
        leftText = a.getString(R.styleable.SettingItemView_rh_leftText)
        rightText = a.getString(R.styleable.SettingItemView_rh_rightText)
        leftIcon = a.getDrawable(R.styleable.SettingItemView_rh_leftIcon)
        rightStyle = a.getInt(R.styleable.SettingItemView_rh_rightStyle, SHOW_ICON)
        a.recycle()
    }


    /**
     * 设置右边checkbox和switch的状态
     */
    fun setRightCheck(isCheck: Boolean) {
        when (rightStyle) {
            SHOW_CHECK -> right_check.isChecked = isCheck
            SHOW_SWITCH -> right_switch.isChecked = isCheck
        }
        refreshData()
    }

    /**
     * 设置左边图片
     */
    fun setLeftIcon(leftIcon: Drawable) {
        this.leftIcon = leftIcon
        refreshData()
    }

    /**
     * 设置左边文字
     */
    fun setLeftText(leftText: String) {
        this.leftText = leftText
        refreshData()
    }

    /**
     * 设置右边边文字
     */
    fun setRigthText(rightText: String) {
        this.rightText = rightText
        refreshData()
    }

    /**
     * 设置右边风格
     */
    fun setRightStyle(style: Int) {
        this.rightStyle = style
        refreshData()
    }

    /**
     * 设置点击事件
     */
    fun setOnSettingItemClick(mOnLSettingItemClick: OnLSettingItemClick?) {
        this.mOnLSettingItemClick = mOnLSettingItemClick
    }

    /**
     * 设置长按点击事件
     */
    fun setOnSettingItemLongClick(OnLSettingItemLongClick: OnLSettingItemLongClick) {
        this.mOnLSettingItemLongClick = OnLSettingItemLongClick
    }

    interface OnLSettingItemClick {
        fun click(v: View, isChecked: Boolean)
    }

    interface OnLSettingItemLongClick {
        fun clickLong(v: View)
    }
}

