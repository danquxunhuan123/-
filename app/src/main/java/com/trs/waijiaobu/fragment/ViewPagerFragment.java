package com.trs.waijiaobu.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.trs.waijiaobu.R;
import com.trs.waijiaobu.adapter.ViewPagerAdapter;
import com.trs.waijiaobu.bean.Channel;
import com.trs.waijiaobu.presenter.IIndicatorFragmentPresenterImpl;
import com.trs.waijiaobu.presenter.inter.IIndicatorFragmentPresenter;
import com.trs.waijiaobu.util.ProgressUtil;
import com.trs.waijiaobu.view.IIndicatorFragmentView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class ViewPagerFragment extends BaseFragment implements IIndicatorFragmentView
        , FragmentInit {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ViewPagerAdapter adapter;
    private List<String> mTitles = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> titleCache = new ArrayList<>();
    private Map<String, Fragment> maps = new HashMap<>();

    private IIndicatorFragmentPresenter mPresenter;

    public ViewPagerFragment() {
    }

    public static ViewPagerFragment newInstance(String param1, String param2) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//    @OnClick(R.id.btn_menu)
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn_menu:
//                final ChannelFragment dialogFragment = ChannelFragment.newInstance((ArrayList<String>) mTitles);
//                dialogFragment.show(getChildFragmentManager(), "CHANNEL");
//                dialogFragment.setOnDisMissListener(new ChannelFragment.OnDismissListener() {
//                    @Override
//                    public void onDismiss(List<TabChannel> list) {
//                        mTitles.clear();
//                        mFragments.clear();
//                        for (TabChannel channel : list) {
//                            String cname = channel.getCname();
//                            mTitles.add(cname);
//                            mFragments.add(maps.get(channel.getCname()));
//                        }
//
//                        boolean isUpdate = false;
//                        if (titleCache.size() != mTitles.size()) { //增删
//                            isUpdate = true;
//                        } else {
//                            for (String s : mTitles) {
//                                if (!titleCache.contains(s)) {
//                                    isUpdate = true;
//                                    break;
//                                }
//                            }
//
//                            for (int i = 0; i < mTitles.size(); i++) {
//                                if ((titleCache.get(i)).equals(mTitles.get(i))) {
//                                    continue;
//                                } else {
//                                    isUpdate = true;
//                                    break;
//                                }
//                            }
//                        }
//
//                        if (isUpdate) {
//                            titleCache.clear();
//                            titleCache.addAll(mTitles);
//                            adapter.notifyDataSetChanged();
//                        }
//
//
//                    }
//                });
//                break;
//        }
//    }


    @Override
    protected FragmentInit initInterface() {
        return this;
    }

    @Override
    public void initSomeThing() {
        mPresenter = new IIndicatorFragmentPresenterImpl(mContext, this);
    }

    @Override
    public void getData() {
        String url = getArguments().getString(ARG_PARAM1);
        mPresenter.getXW(url);
        ProgressUtil.getInstance(mContext).show();
    }

    @Override
    public void onDestroy() {
        ProgressUtil.getInstance(mContext).release();
        super.onDestroy();
    }

    @Override
    protected int flateLayout() {
        return R.layout.fragment_viewpager;
    }

    @Override
    public void getXW(Channel channel) {
        ProgressUtil.getInstance(mContext).dismiss();

        mTitles.clear();
        mFragments.clear();

        List<Channel.GdEntity> gd = channel.getGd();
        for (int i = 0; i < gd.size(); i++) {
            String cname = gd.get(i).getCname();
            String doc = gd.get(i).getDocuments();
            mTitles.add(cname);

            ListFragment listFragment = ListFragment.newInstance(doc, cname);
            mFragments.add(listFragment);
            maps.put(cname, listFragment);
        }

        titleCache.addAll(mTitles);

        if (adapter == null) {
            adapter = new ViewPagerAdapter(getChildFragmentManager(), mTitles, mFragments);
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(mFragments.size());
            tabLayout.setupWithViewPager(viewPager);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
