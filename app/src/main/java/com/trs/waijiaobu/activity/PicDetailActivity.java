package com.trs.waijiaobu.activity;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trs.waijiaobu.R;
import com.trs.waijiaobu.glide.GlideHelper;
import com.trs.waijiaobu.util.AndroidShareHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PicDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    public static final String ARG_PARAM1 = "pics";
    public static final String ARG_PARAM2 = "index";
    @BindView(R.id.ll_pic_desc)
    LinearLayout picDesc;
    @BindView(R.id.tv_pic_title)
    TextView tvPicTitle;
    @BindView(R.id.tv_pic_count)
    TextView tvPicCount;
    @BindView(R.id.tv_pic_desc)
    TextView tvPicDesc;
    private List<ImageView> imgs;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private int picCount;

    @Override
    protected void getData() {
        String[] pics = (String[]) getIntent().getCharSequenceArrayExtra(ARG_PARAM1);
        picCount = pics.length;
        int index = getIntent().getIntExtra(ARG_PARAM2, 0);

        imgs = new ArrayList<>();
        for (int i = 0; i < pics.length; i++) {
            ImageView imageView = new ImageView(this);
            ViewPager.LayoutParams params = new ViewPager.LayoutParams();
            params.width = ViewPager.LayoutParams.MATCH_PARENT;
            params.height = ViewPager.LayoutParams.WRAP_CONTENT;
            imageView.setLayoutParams(params);

            GlideHelper.getInstance().loadFitCenter(imageView, pics[i]);
            imgs.add(imageView);
        }
        PicAdapter adapter = new PicAdapter(imgs);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(index);

        if (picCount > 0) {
            picDesc.setVisibility(View.VISIBLE);
            tvPicCount.setText(String.format(getString(R.string.pic_index), index + 1, picCount));
            tvPicTitle.setText("");
            tvPicDesc.setText("");
        } else
            picDesc.setVisibility(View.GONE);
    }

    @Override
    protected int flateLayout() {
        return R.layout.activity_pic_detail;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        tvPicCount.setText(String.format(getString(R.string.pic_index), i + 1, picCount));
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    public void back(View view) {
        finish();
    }

    public void share(View view) {
        GlideHelper.getInstance().loadToFile(
                this, "", new GlideHelper.OnLoadedListener() {
                    @Override
                    public void onLoaded(File file) {
//                        Uri uri = FileProvider.getUriForFile(NewsDetailActivity.this, AppUtils.getAppPackageName() + ".fileprovider", file);
//                        AndroidShareHelper.shareTxtAndPic(NewsDetailActivity.this, uri, mDetail.getDatas().getTitle());
                        AndroidShareHelper.shareTxt(PicDetailActivity.this, "");
                    }

                    @Override
                    public void onLoadedBitmap(Bitmap bitmap) {
                    }
                });
    }


    class PicAdapter extends PagerAdapter {

        List<ImageView> views;

        public PicAdapter(List<ImageView> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(views.get(position));
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = views.get(position);
            container.addView(imageView);
            return imageView;
        }
    }
}
