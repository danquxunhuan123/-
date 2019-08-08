package com.trs.waijiaobu.fragment;

import android.support.v4.app.Fragment;

import com.trs.waijiaobu.Constant;

/**
 * creator：liufan
 * data: 2019/7/31
 */
public interface FragmentFactory {
    Fragment getFragment(int tag);

    class FragmentCreator implements FragmentFactory{

        @Override
        public Fragment getFragment(int tag) {
            Fragment fragment = null;
            switch (tag){
                case 0:
                    fragment = ViewPagerFragment.newInstance(Constant.XW, "");
                    break;
                case 1:
                    fragment = ViewPagerFragment.newInstance(Constant.LSFW, "");
                    break;
                case 2:
                    fragment = ViewPagerFragment.newInstance(Constant.ZL, "");
                    break;
                case 3:
                    fragment = ViewPagerFragment.newInstance(Constant.XWFW, "");
                    break;
                case 4:
                    fragment = ListFragment.newInstance(Constant.WB, "");
                    break;
            }
            return fragment;
        }
    }
}
