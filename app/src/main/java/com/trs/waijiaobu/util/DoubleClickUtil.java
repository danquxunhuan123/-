package com.trs.waijiaobu.util;

import android.view.View;

/**
 * creator：liufan
 * data: 2019/8/15
 */
public class DoubleClickUtil {
    private static long oldTime = 0;

    public static void setCheckDoubleClickListener(View view, View.OnClickListener listener) {
        long lastTime = System.currentTimeMillis();
        if ((lastTime - oldTime) < 500)
            return;
        else{
            oldTime = lastTime;
            listener.onClick(view);
        }
    }
}
