package com.trs.waijiaobu.fragment;

import android.os.Bundle;

import com.trs.waijiaobu.R;

public class FourFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FourFragment() {
    }

    public static FourFragment newInstance(String param1, String param2) {
        FourFragment fragment = new FourFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void getData() {

    }

    @Override
    protected int flateLayout() {
        return R.layout.fragment_viewpager;
    }

}
