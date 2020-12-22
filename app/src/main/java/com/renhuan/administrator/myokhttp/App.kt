package com.renhuan.administrator.myokhttp

import android.R
import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.lzy.ninegrid.NineGridView
import com.lzy.ninegrid.NineGridView.ImageLoader
import com.renhuan.okhttplib.RApp
import com.renhuan.okhttplib.utils.Renhuan


/**
 * created by renhuan
 * time : 2020/12/14 10:20
 * describe :
 */
class App : RApp() {
    override fun init() {
        NineGridView.setImageLoader(GlideImageLoader())
    }

    /** Glide 加载  */
    private class GlideImageLoader : ImageLoader {
        override fun onDisplayImage(context: Context?, imageView: ImageView, url: String) {
            Renhuan.glide(imageView, url, true)
        }

        override fun getCacheImage(url: String): Bitmap? {
            return null
        }
    }
}