package com.trs.waijiaobu.okhttp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.Map;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
    private Context mContext;

    private OkHttpClientHelper(Context context) {
        mContext = context;
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d(HTTP_LOG_TAG, message);
            }
        });
        logging.level(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .sslSocketFactory(HttpsTrustManager.createSSLSocketFactory())
                .hostnameVerifier(new HttpsTrustManager.TrustAllHostnameVerifier())
                .build();
//                .followRedirects(true)
//                .followSslRedirects(true)
//                .retryOnConnectionFailure(true)
//                .cache(null)
    }

    public static OkHttpClientHelper getInstance(Context context) {
        if (mOkHttpClient == null) {
            synchronized (OkHttpClientHelper.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClientHelper(context);
                }
            }
        }
        return mOkHttpClient;
    }

    public OkHttpClient getClient() {
        return client;
    }

    public void get(String url, Callback callback) {
        boolean connected = NetworkUtils.isConnected();
        if (!connected) {
            ToastUtils.showShort("请检查网络，稍后重试");
            return;
        }

        if (TextUtils.isEmpty(url)) return;

//        ProgressUtil.getInstance(mContext).show();
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
