package com.trs.waijiaobu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(flateLayout());
        unbinder = ButterKnife.bind(this);
        initView();
        getData();
    }

    protected void initView() {

    }

    protected void getData(){};


    protected abstract int flateLayout();

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }
}
