package com.trs.waijiaobu.glide;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

/**
 * creator：liufan
 * data: 2019/7/24
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        GlideHelper.getInstance().load(imageView, (String) path);
    }
}
