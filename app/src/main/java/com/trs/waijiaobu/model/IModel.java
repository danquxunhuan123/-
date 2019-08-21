package com.trs.waijiaobu.model;

import android.content.Context;

import okhttp3.Callback;

/**
 * creator：liufan
 * data: 2019/7/23
 */
public interface IModel {
    void getChannel(Context context, String url, Callback callback);

    void getChannelList(Context context, String url, Callback callback);

    void getDetailData(Context context, String url, Callback callback);
}
