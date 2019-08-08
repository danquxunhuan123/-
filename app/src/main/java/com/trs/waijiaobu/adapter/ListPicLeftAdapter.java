package com.trs.waijiaobu.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.trs.waijiaobu.R;
import com.trs.waijiaobu.activity.ListActivity;
import com.trs.waijiaobu.activity.NewsDetailActivity;
import com.trs.waijiaobu.bean.Channel;
import com.trs.waijiaobu.bean.Document;
import com.trs.waijiaobu.glide.GlideHelper;
import com.trs.waijiaobu.glide.GlideImageLoader;
import com.trs.waijiaobu.util.SizeUtil;
import com.trs.waijiaobu.util.StringUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * creator：liufan
 * data: 2019/7/22
 */
public class ListPicLeftAdapter extends BaseAdapter {

    public ListPicLeftAdapter(List listData, Context context) {
        super(listData, context);
    }


    @Override
    protected int getItemType(int position) {
        return 0;
    }

    @Override
    public MyHolder createHolder(ViewGroup parent, int viewType) {
        return MyHolder.getComViewHolder(parent.getContext(), R.layout.list_pic_left_item, null);
    }

    @Override
    protected void bindHolder(MyHolder holder, int position) {
        Object obj = list.get(position);
        Channel.GdEntity channel = (Channel.GdEntity) obj;
        String cname = channel.getCname();
        String logo = channel.getLogo();

        ((TextView) holder.getView(R.id.tv_title)).setText(cname);
        ((TextView) holder.getView(R.id.tv_desc)).setText(channel.getDesc());
        ImageView view = (ImageView) holder.getView(R.id.iv_pic);
        if (!TextUtils.isEmpty(logo)) {
            view.setVisibility(View.VISIBLE);
            GlideHelper.getInstance().load(view, logo);
        } else {
            view.setVisibility(View.GONE);
        }

        final String finalUrl = channel.getDocuments();
//        final String finalCname = cname;
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subUrl = StringUtil.subUrlSuffix(finalUrl);
                Intent intent = null;
                if ("documents".equals(subUrl) || "channels".equals(subUrl)) {
                    intent = new Intent(context, ListActivity.class);
//                            intent.putExtra(ListActivity.ARG_PARAM2, finalCname);
                } else {
                    intent = new Intent(context, NewsDetailActivity.class);
                }

                intent.putExtra(ListActivity.ARG_PARAM1, finalUrl);
                context.startActivity(intent);
            }
        });
    }

}
