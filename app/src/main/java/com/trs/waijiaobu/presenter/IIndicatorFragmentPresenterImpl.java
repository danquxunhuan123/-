package com.trs.waijiaobu.presenter;

import com.google.gson.Gson;
import com.trs.waijiaobu.bean.Channel;
import com.trs.waijiaobu.model.IModel;
import com.trs.waijiaobu.model.IModelImpl;
import com.trs.waijiaobu.okhttp.callback.MyCallback;
import com.trs.waijiaobu.view.IIndicatorFragmentView;

import java.io.IOException;

import okhttp3.Call;

/**
 * creator：liufan
 * data: 2019/7/23
 */
public class IIndicatorFragmentPresenterImpl implements IIndicatorFragmentPresenter {
    IModel mModel;
    IIndicatorFragmentView view;


    public IIndicatorFragmentPresenterImpl(IIndicatorFragmentView view) {
        mModel = new IModelImpl();
        this.view = view;
    }

    @Override
    public void getXW(String url) {
        mModel.getChannel(url, new MyCallback() {
            @Override
            public void OnResponse(Call call, String json) {
                Gson gson = new Gson();
                Channel channel = gson.fromJson(json, Channel.class);
                view.getXW(channel);
            }

            @Override
            public void OnFailure(Call call, IOException e) {

            }
        });
    }
}
