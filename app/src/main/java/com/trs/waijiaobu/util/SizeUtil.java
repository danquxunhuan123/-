package com.trs.waijiaobu.util;

import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ScreenUtils;

/**
 * creator：liufan
 * data: 2019/7/26
 */
public class SizeUtil {
    public static void setWandH(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = ScreenUtils.getScreenWidth();
        layoutParams.height = (layoutParams.width * height / width);

        view.setLayoutParams(layoutParams);
    }

    public static void setSpaceWandH(View view, int width, int height, int space) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (ScreenUtils.getScreenWidth() - space);
        layoutParams.height = (layoutParams.width * height / width);

        view.setLayoutParams(layoutParams);
    }
}
