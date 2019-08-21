package com.trs.waijiaobu.okhttp.callback;

import android.annotation.SuppressLint;
import android.content.Context;
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
    private final String RESPONSE = "response";
    private final int SUCCESS = 0;
    private final int ERROR = 1;
    private Context mContext;

    public MyCallback(Context context) {
        mContext = context;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

//            ProgressUtil.getInstance(mContext).dismiss();
            switch (msg.what) {
                case SUCCESS:
                    OnResponse((Call) msg.obj, msg.getData().getString(RESPONSE));
                    break;
                case ERROR:
                    OnFailure((Call) msg.obj, msg.getData().getString(RESPONSE));
                    break;
            }
        }
    };

    @Override
    public void onFailure(Call call, IOException e) {
        e.printStackTrace();
        sendMessage(RESPONSE, ERROR, call, e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        sendMessage(RESPONSE, SUCCESS, call, response.body().string());
    }

    private void sendMessage(String key, int code, Object obj, String response) {
        Message msg = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putString(key, response);
        msg.what = code;
        msg.obj = obj;
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    public abstract void OnResponse(Call call, String json);

    public abstract void OnFailure(Call call, String errorMsg);
}
