package com.trs.waijiaobu.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.trs.waijiaobu.R;
import com.trs.waijiaobu.glide.GlideHelper;
import com.trs.waijiaobu.glide.PinchImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * creator：liufan
 * data: 2019/8/15
 */
public class PicsDialogFragment extends DialogFragment implements ViewPager.OnPageChangeListener {
    public static final String ARG_PARAM1 = "pics";
    public static final String ARG_PARAM2 = "index";
    private ViewPager mViewPager;
    private int picCount;
    private List<ImageView> imgs;

    public static PicsDialogFragment newInstance(String[] pics,int index) {
        PicsDialogFragment picFragment = new PicsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequenceArray(ARG_PARAM1,pics);
        bundle.putInt(ARG_PARAM2,index);
        picFragment.setArguments(bundle);
        return picFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        if (dialog != null) {
            //添加动画
            dialog.getWindow().setWindowAnimations(R.style.dialogSlideAnim);
        }
        View inflate = inflater.inflate(R.layout.dialog_pic_layout, null);
        mViewPager = inflate.findViewById(R.id.view_pager);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        int index = getArguments().getInt(ARG_PARAM2, 0);
        String[] pics = getArguments().getStringArray(ARG_PARAM1);
        picCount = pics.length;

        imgs = new ArrayList<>();
        for (int i = 0; i < pics.length; i++) {
            PinchImageView imageView = new PinchImageView(getContext());
//            ImageView imageView = new ImageView(getContext());
            ViewPager.LayoutParams params = new ViewPager.LayoutParams();
            params.width = ViewPager.LayoutParams.MATCH_PARENT;
            params.height = ViewPager.LayoutParams.WRAP_CONTENT;
            imageView.setLayoutParams(params);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            GlideHelper.getInstance().loadFitCenter(imageView, pics[i]);
            imgs.add(imageView);
        }
        PicAdapter adapter = new PicAdapter(imgs);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(index);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

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
