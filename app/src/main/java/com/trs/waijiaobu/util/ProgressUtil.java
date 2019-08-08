package com.trs.waijiaobu.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * creator：liufan
 * data: 2019/6/27
 */
public class ProgressUtil {
    private static ProgressUtil instance = null;
    private ProgressDialog progressDialog = null;
    private String loading = "正在加载...";

    public static ProgressUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (WebViewUtil.class) {
                if (instance == null) {
                    instance = new ProgressUtil(context);
                }
            }
        }
        return instance;
    }

    private ProgressUtil(Context context){
        progressDialog = new ProgressDialog(context);
        //设置进度条风格，风格为圆形，旋转的
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //设置ProgressDialog 提示信息
        progressDialog.setMessage(loading);
        //设置ProgressDialog 的进度条是否不明确
        progressDialog.setIndeterminate(false);
        //设置ProgressDialog 是否可以按退回按键取消
        progressDialog.setCancelable(true);
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void show(){
        progressDialog.show();
    }

    public void dismiss(){
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void release(){
        instance = null;
    }
}
