package com.trs.waijiaobu.model;

import android.content.Context;

import com.trs.waijiaobu.okhttp.OkHttpClientHelper;

import okhttp3.Callback;

/**
 * creator：liufan
 * data: 2019/7/23
 */
public class IModelImpl implements IModel {

    @Override
    public void getChannel(Context context, String url, Callback callback) {
        OkHttpClientHelper.getInstance(context).get(url,callback);
    }

    @Override
    public void getChannelList(Context context, String url, Callback callback) {
        OkHttpClientHelper.getInstance(context).get(url,callback);
    }

    @Override
    public void getDetailData(Context context, String url, Callback callback) {
        OkHttpClientHelper.getInstance(context).get(url,callback);
    }
}
