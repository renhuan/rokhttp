package com.example.okhttplib.utils

import com.bumptech.glide.request.RequestOptions
import com.example.okhttplib.R

/**
 * 默认配置图片glide  RequestOptions
 */
object GlideRequestOptionsUtils {
    private var options: RequestOptions? = null

    fun requestOptions(): RequestOptions {
        if (options == null) {
            options = RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.loading)
        }
        return options as RequestOptions
    }

}
