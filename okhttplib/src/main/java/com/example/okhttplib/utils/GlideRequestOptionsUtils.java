package com.example.okhttplib.utils;

import com.bumptech.glide.request.RequestOptions;
import com.example.okhttplib.R;

/**
 * 默认配置图片glide  RequestOptions
 */
public class GlideRequestOptionsUtils {
    private static RequestOptions options;

    public static RequestOptions requestOptions() {
        if (options == null) {
            options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.placehold)
                    .error(R.drawable.placehold);
        }
        return options;
    }

}
