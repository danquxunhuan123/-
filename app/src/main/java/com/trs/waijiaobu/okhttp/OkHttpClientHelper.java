package com.trs.waijiaobu.okhttp;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.Map;

import kotlin.ReplaceWith;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * creator：liufan
 * data: 2019/7/23
 */
public class OkHttpClientHelper {
    private final String HTTP_LOG_TAG = "okhttp";
    private static OkHttpClientHelper mOkHttpClient;
    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client;

    private OkHttpClientHelper() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d(HTTP_LOG_TAG, message);
            }
        });
        logging.level(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder().addInterceptor(logging).build();
    }

    public static OkHttpClientHelper getInstance() {
        if (mOkHttpClient == null) {
            synchronized (OkHttpClientHelper.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClientHelper();
                }
            }
        }
        return mOkHttpClient;
    }

    public void get(String url, Callback callback) {
        if (TextUtils.isEmpty(url)) return;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void post(String url, Callback callback, Map<String, String> params) {
        String json = parseParams(params);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    private String parseParams(Map<String, String> params) {
        StringBuilder builder = new StringBuilder("{");

        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.append("\"")
                    .append(entry.getKey())
                    .append("\"")
                    .append(":")
                    .append("\"")
                    .append(entry.getValue())
                    .append("\"")
                    .append(",");

        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("}");
        return builder.toString();
    }
}
