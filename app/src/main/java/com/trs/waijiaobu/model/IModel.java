package com.trs.waijiaobu.model;

import okhttp3.Callback;

/**
 * creator：liufan
 * data: 2019/7/23
 */
public interface IModel {
    void getChannel(String url, Callback callback);

    void getChannelList(String url, Callback callback);

    void getDetailData(String url, Callback callback);
}
