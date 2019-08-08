package com.trs.waijiaobu.presenter;

import com.google.gson.Gson;
import com.trs.waijiaobu.bean.Channel;
import com.trs.waijiaobu.bean.Document;
import com.trs.waijiaobu.model.IModel;
import com.trs.waijiaobu.model.IModelImpl;
import com.trs.waijiaobu.okhttp.callback.MyCallback;
import com.trs.waijiaobu.util.StringUtil;
import com.trs.waijiaobu.view.IListView;

import java.io.IOException;

import okhttp3.Call;

/**
 * creator：liufan
 * data: 2019/7/23
 */
public class IListPresenterImpl implements IListPresenter {
    IListView mView;

    private IModel mModel;

    public IListPresenterImpl(IListView view) {


        mModel = new IModelImpl();
        mView = view;
    }

    @Override
    public void getListData(final String url) {
        mModel.getChannelList(url, new MyCallback() {
            @Override
            public void OnResponse(Call call, String json) {
                if (json.startsWith("{")) {
                    Gson gson = new Gson();
                    String subUrl = StringUtil.subUrlSuffix(url);
                    Object object = null;
                    if ("channels".equals(subUrl)) {
                        object = gson.fromJson(json, Channel.class);
                    } else if (subUrl.startsWith("documents")) {  //"documents".equals(subUrl)
                        object = gson.fromJson(json, Document.class);
                    }

                    if (object != null)
                        mView.getListData(object);
                } else {
                    mView.onFailure();
//                    ToastUtils.showShort("error json format");
                }
            }

            @Override
            public void OnFailure(Call call, IOException e) {
                mView.onFailure();
            }
        });
    }
}
