package com.trs.waijiaobu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;
    protected Context mContext;
    FragmentInit mInit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        mInit = initInterface();
        initView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(flateLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    protected FragmentInit initInterface() {
        return null;
    }

    private void initView() {
        mInit.initSomeThing();
        mInit.getData();

        /*boolean connected = NetworkUtils.isConnected();
        if (!connected) {
            final LinearLayout view = (LinearLayout) getView();
            LogUtils.dTag("view_____",view);
            final TextView errorView = new TextView(getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            errorView.setLayoutParams(params);

            errorView.setTextSize(mContext.getResources().getDimension(R.dimen.sp_14));
            errorView.setTextColor(Color.BLACK);
            errorView.setText("请检查网络，点击重试");

            errorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean connected = NetworkUtils.isConnected();
                    if (connected) {
                        view.removeView(errorView);
                        if (mInit != null) {
                            mInit.initSomeThing();
                            mInit.getData();
                        }
                    } else Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();
                }
            });
            view.addView(errorView);
        }else {
            mInit.initSomeThing();
            mInit.getData();
        }*/
    }

//    protected void init() {
//
//    }
//
//    protected void getData() {
//    }


    protected abstract int flateLayout();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
