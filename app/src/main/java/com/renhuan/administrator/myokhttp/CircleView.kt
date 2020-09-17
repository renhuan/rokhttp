package com.renhuan.administrator.myokhttp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.ScreenUtils
import com.renhuan.okhttplib.utils.Renhuan
import kotlin.math.tan

/**
 * created by renhuan
 * time : 2020/8/26 11:07
 * describe :
 */
class CircleView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    var mPaint = Paint()

    //选项的个数
    var num = 7

    //每个角度
    var angleByOne = 40f

    //每个选项的长度
    var length = 200f

    //三角形的长度
    var triangleLength = 40


    var color = arrayListOf("#44BFEB", "#E07832", "#1A5BD3", "#59B33A", "#E63D30", "#BD5A9D", "#E94C4C")

    init {
        mPaint.color = Renhuan.getColor(R.color.pink)
        mPaint.style = Paint.Style.FILL
        mPaint.isAntiAlias = true
        setLayerType(LAYER_TYPE_HARDWARE, mPaint)
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(ScreenUtils.getScreenWidth() / 2f, ScreenUtils.getScreenHeight() / 2f)


        for (i in 0 until num) {
            mPaint.color = Color.parseColor(color[i])

            //长扇形
            canvas.drawArc(RectF(-length * 2, -length * 2, length * 2, length * 2), 0f, angleByOne, true, mPaint)

            //短扇形
            mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            canvas.drawArc(RectF(-length, -length, length, length), 0f, angleByOne, true, mPaint)
            mPaint.xfermode = null


            canvas.drawText("1个", 100f, 100f, mPaint)

            //三角形
            val path = Path()
            val x = length + 10
            val y = length - 35
            path.moveTo(x, y)
            path.lineTo(x + triangleLength, y + triangleLength)
            path.lineTo(x, y + triangleLength)
            path.close()
            canvas.drawPath(path, mPaint)

            canvas.rotate(360f / num)
        }
    }
}