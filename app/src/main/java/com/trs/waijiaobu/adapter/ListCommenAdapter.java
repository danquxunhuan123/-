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
import com.trs.waijiaobu.util.StringUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * creator：liufan
 * data: 2019/7/22
 */
public class ListCommenAdapter extends BaseAdapter {
    private List<Document.List_datasEntity> bannerData;
    private List<String> imgUrls;
    private List<String> titles;
    private final int TYPE_BANNER = 1; //banner type
    private final int TYPE_NORMAL = 0; //normal type
    private final int TYPE_MANY_PIC = 2; //many pic type

    public ListCommenAdapter(List<Document.List_datasEntity> top_datas, List listData, Context context) {
        super(listData, context);

        initBannerData(top_datas);
    }

    private void initBannerData(List<Document.List_datasEntity> top_datas) {
        if (top_datas != null && top_datas.size() > 0) {
            this.bannerData = top_datas;
            imgUrls = new ArrayList<>();
            titles = new ArrayList<>();
            for (Document.List_datasEntity entity : top_datas) {
                imgUrls.add(entity.getCimgs().get(0));
                titles.add(entity.getTitle());
            }
        }
    }

    public void setBannerData(List<Document.List_datasEntity> top_datas) {
        initBannerData(top_datas);
    }

    @Override
    protected int getItemType(int position) {
        if (bannerData != null && bannerData.size() > 0) {
            if (position == 0)
                return TYPE_BANNER;
        }

        if (list.get(position) instanceof Document.List_datasEntity) {
            Document.List_datasEntity entity = (Document.List_datasEntity) list.get(position);
            if (entity.getCimgs().size() > 1) {
                return TYPE_MANY_PIC;
            }
        }

        return TYPE_NORMAL;
    }

    @Override
    public MyHolder createHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_BANNER:
                return MyHolder.getComViewHolder(parent.getContext(), R.layout.list_banner, null);
            case TYPE_MANY_PIC:
                return MyHolder.getComViewHolder(parent.getContext(), R.layout.list_item_many_pic, null);
            case TYPE_NORMAL:
                return MyHolder.getComViewHolder(parent.getContext(), R.layout.list_commen_item, null);
            default:
                return MyHolder.getComViewHolder(parent.getContext(), R.layout.list_commen_item, null);
        }
    }

    @Override
    protected void bindHolder(MyHolder holder, int position) {
        switch (getItemType(position)) {
            case TYPE_BANNER:
                Banner banner = (Banner) holder.getView(R.id.banner);
                if (banner == null)
                    SizeUtil.setWandH(banner, 16, 9);


                banner.setDelayTime(5 * 1000);
                banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
                banner.setImageLoader(new GlideImageLoader());
                banner.setImages(imgUrls);
                banner.setBannerTitles(titles);
                banner.start();

                banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Intent intent = new Intent(context, NewsDetailActivity.class);
                        intent.putExtra(ListActivity.ARG_PARAM1, bannerData.get(position).getUrl());
                        context.startActivity(intent);
                    }
                });
                break;
            case TYPE_MANY_PIC:
                Document.List_datasEntity data = (Document.List_datasEntity) list.get(position);

                ((TextView) holder.getView(R.id.tv_title)).setText(data.getTitle());

                ((TextView) holder.getView(R.id.tv_date)).setText(data.getUpdatedate());

                ((TextView) holder.getView(R.id.tv_source)).setText("");

                LinearLayout contain = (LinearLayout) holder.getView(R.id.ll_contain_pic);
                contain.removeAllViews();
                List<String> imgs = data.getCimgs();
                for (int i = 0; i < imgs.size(); i++) {
                    ImageView img = new ImageView(context);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.width = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(40)) / 3;
                    params.height = params.width * 2 / 3;
                    if (i != imgs.size() - 1)
                        params.setMargins(0, 0, SizeUtils.dp2px(10), 0);
                    img.setLayoutParams(params);

                    GlideHelper.getInstance().load(img, imgs.get(i));
                    contain.addView(img);
                }

                String u = data.getUrl();
                final String finalu = u;
//                final String finalname = name;
                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String subUrl = StringUtil.subUrlSuffix(finalu);
                        Intent intent = null;
                        if ("documents".equals(subUrl) || "channels".equals(subUrl)) {
                            intent = new Intent(context, ListActivity.class);
//                            intent.putExtra(ListActivity.ARG_PARAM2, finalCname);
                        } else {
                            intent = new Intent(context, NewsDetailActivity.class);
                        }

                        intent.putExtra(ListActivity.ARG_PARAM1, finalu);
                        context.startActivity(intent);
                    }
                });
                break;
            case TYPE_NORMAL:
                Object obj = list.get(position);
                String cname = null;
                String date = null;
                String source = null;
                String url = null;
                String logo = null;

                if (obj instanceof Document.List_datasEntity) {
                    Document.List_datasEntity document = (Document.List_datasEntity) obj;
                    cname = document.getTitle();
                    date = document.getUpdatedate();
//                    source = "外交部";
                    List<String> cimgs = document.getCimgs();
                    if (cimgs != null && cimgs.size() > 0)
                        logo = cimgs.get(0);
                    url = document.getUrl();

                } else if (obj instanceof Channel.GdEntity) {
                    Channel.GdEntity channel = (Channel.GdEntity) obj;
                    cname = channel.getCname();
//                    date = channel.getLmt();
                    date = "";
//                    source = "外交部";
//            cimgs = data.getCimgs();
                    logo = channel.getLogo();
                    url = channel.getDocuments();
                }

                cname = StringUtils.replaceStr(cname);
                ((TextView) holder.getView(R.id.tv_title)).setText(cname);
                ((TextView) holder.getView(R.id.tv_date)).setText(date);
                ((TextView) holder.getView(R.id.tv_source)).setText(source);
                ImageView view = (ImageView) holder.getView(R.id.iv_pic);
                if (!TextUtils.isEmpty(logo)) {
                    view.setVisibility(View.VISIBLE);
                    GlideHelper.getInstance().load(view, logo);
                } else {
                    view.setVisibility(View.GONE);
                }

                final String finalUrl = url;
//                final String finalCname = cname;
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
                break;
            default:
                break;
        }
    }

}
