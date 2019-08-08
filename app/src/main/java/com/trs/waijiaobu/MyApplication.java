package com.trs.waijiaobu;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * creator：liufan
 * data: 2019/7/22
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
    }
}
