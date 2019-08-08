package com.trs.waijiaobu.model;

import com.trs.waijiaobu.okhttp.OkHttpClientHelper;

import okhttp3.Callback;

/**
 * creator：liufan
 * data: 2019/7/23
 */
public class IModelImpl implements IModel {

    @Override
    public void getChannel(String url, Callback callback) {
        OkHttpClientHelper.getInstance().get(url,callback);
    }

    @Override
    public void getChannelList(String url, Callback callback) {
        OkHttpClientHelper.getInstance().get(url,callback);
    }

    @Override
    public void getDetailData(String url, Callback callback) {
        OkHttpClientHelper.getInstance().get(url,callback);
    }
}
