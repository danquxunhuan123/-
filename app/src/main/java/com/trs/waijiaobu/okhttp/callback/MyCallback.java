package com.trs.waijiaobu.okhttp.callback;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * creator：liufan
 * data: 2019/7/23
 */
public abstract class MyCallback implements Callback {

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    OnResponse((Call) msg.obj,msg.getData().getString("string"));
                    break;
            }
        }
    };
    @Override
    public void onFailure(Call call, IOException e) {
        e.printStackTrace();
        OnFailure(call,e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Message msg =Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putString("string",response.body().string());
        msg.what = 0;
        msg.obj = call;
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    public abstract void OnResponse(Call call, String json);
    public abstract void OnFailure(Call call, IOException e);
}
