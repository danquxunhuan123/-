package com.trs.waijiaobu.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.trs.waijiaobu.bean.Channel;
import com.trs.waijiaobu.bean.Detail;
import com.trs.waijiaobu.model.IModel;
import com.trs.waijiaobu.model.IModelImpl;
import com.trs.waijiaobu.okhttp.callback.MyCallback;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;

/**
 * creator：liufan
 * data: 2019/7/24
 */
public class IDetailPresenterImpl implements IDetailPresenter {
    IDetailView mView;
    private IModel mModel;

    public IDetailPresenterImpl(IDetailView view) {
        this.mView = view;
        mModel = new IModelImpl();
    }

    @Override
    public void getData(String url) {
        mModel.getDetailData(url, new MyCallback() {
            @Override
            public void OnResponse(Call call, String json) {
                if (json.startsWith("{")){
                    Gson gson = new Gson();
                    Detail detail = gson.fromJson(json, Detail.class);
                    mView.getData(detail);
                }else {
                    ToastUtils.showShort("error json format");
                }
            }

            @Override
            public void OnFailure(Call call, IOException e) {

            }
        });
    }
}
