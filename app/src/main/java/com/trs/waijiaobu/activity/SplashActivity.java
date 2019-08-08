package com.trs.waijiaobu.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.trs.waijiaobu.R;
import com.trs.waijiaobu.glide.GlideHelper;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.content)
    LinearLayout content;

    @Override
    protected int flateLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        ImageView img = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        img.setLayoutParams(params);
        GlideHelper.getInstance().loadResource(img,R.drawable.splash);
        content.addView(img);
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void getData() {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        }.sendEmptyMessageDelayed(0,3000);
    }
}
