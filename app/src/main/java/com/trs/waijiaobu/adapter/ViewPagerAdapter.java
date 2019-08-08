package com.trs.waijiaobu.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.trs.waijiaobu.fragment.ListFragment;
import com.trs.waijiaobu.fragment.ViewPagerFragment;

import java.util.List;

/**
 * creator：liufan
 * data: 2019/7/22
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> mTtiles;
    private List<Fragment> mFragments;

    public ViewPagerAdapter(FragmentManager fm, List<String> titles, List<Fragment> fragments) {
        super(fm);
        mTtiles = titles;
        mFragments = fragments;
    }

    public void update(List<String> titles, List<Fragment> fragments) {
        mTtiles = titles;
        mFragments = fragments;

        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }


    @Override
    public int getCount() {
        return mTtiles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTtiles.get(position);
    }

//    @NonNull
//    @Override
//    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
//    }

//    @Override
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
//    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (mFragments.contains(object)){
            return mFragments.indexOf(object);
        }


//        return super.getItemPosition(object);

        return POSITION_NONE;
    }


}
