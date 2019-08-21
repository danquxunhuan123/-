package com.trs.waijiaobu.glide;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.trs.waijiaobu.okhttp.OkHttpClientHelper;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * creator：liufan
 * data: 2019/8/14
 */
@GlideModule
public class OkHttpAppGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        OkHttpClient client = OkHttpClientHelper.getInstance(context).getClient();
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
    }
}
