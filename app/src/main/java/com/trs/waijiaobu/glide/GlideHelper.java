package com.trs.waijiaobu.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.trs.waijiaobu.R;
import com.trs.waijiaobu.util.ThreadUtil;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * creator：liufan
 * data: 2019/7/23
 */
public class GlideHelper {

    private static GlideHelper glideHelper;

    private GlideHelper() {
    }

    public static GlideHelper getInstance() {
        if (glideHelper == null) {
            synchronized (GlideHelper.class) {
                if (glideHelper == null) {
                    glideHelper = new GlideHelper();
                }
            }
        }
        return glideHelper;
    }

    public void load(ImageView view, String url) {
        Glide
                .with(view.getContext())
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(view);
    }

    public void loadResource(ImageView view, int resource) {
        Glide
                .with(view.getContext())
                .load(ContextCompat.getDrawable(view.getContext(), resource))
                .centerCrop()
//                .placeholder(R.drawable.loading_spinner)
                .into(view);
    }

    public void loadBitmap(Context context, String url, final OnLoadedListener listener) {
        if (TextUtils.isEmpty(url)) {
            ToastUtils.showShort("url is empty");
            return;
        }

        final FutureTarget<Bitmap> target = Glide.with(context).asBitmap().load(url).submit();
        ThreadUtil.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bitmap = target.get();
                    listener.onLoadedBitmap(bitmap);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void loadToFile(Context context, String url, final OnLoadedListener listener) {
        if (!TextUtils.isEmpty(url)) {
            final FutureTarget<File> target = Glide.with(context).asFile().load(url).submit();

            ThreadUtil.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        File file = target.get();
                        listener.onLoaded(file);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        } else {
            ToastUtils.showShort("url is empty");
        }
    }

    public void loadFitCenter(ImageView view, String url) {
        Glide
                .with(view.getContext())
                .load(url)
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .into(view);
    }

    public interface OnLoadedListener {
        void onLoaded(File file);

        void onLoadedBitmap(Bitmap bitmap);
    }
}
