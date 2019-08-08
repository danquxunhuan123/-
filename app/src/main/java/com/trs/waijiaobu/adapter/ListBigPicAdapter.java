package com.trs.waijiaobu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.trs.waijiaobu.R;
import com.trs.waijiaobu.activity.ListActivity;
import com.trs.waijiaobu.activity.NewsDetailActivity;
import com.trs.waijiaobu.bean.Document;
import com.trs.waijiaobu.glide.GlideHelper;
import com.trs.waijiaobu.util.SizeUtil;
import com.trs.waijiaobu.util.StringUtil;
import com.trs.waijiaobu.util.StringUtils;

import java.util.List;

/**
 * creator：liufan
 * data: 2019/7/22
 */
public class ListBigPicAdapter extends BaseAdapter {
    public ListBigPicAdapter(List listData, Context context) {
        super(listData, context);
    }

    @Override
    protected int getItemType(int position) {
        return 0;
    }

    @Override
    public MyHolder createHolder(ViewGroup parent, int viewType) {
        return MyHolder.getComViewHolder(parent.getContext(), R.layout.list_item_big_pic, null);

    }

    @Override
    protected void bindHolder(MyHolder holder, int position) {
        Document.List_datasEntity data = (Document.List_datasEntity) list.get(position);
        String cname = StringUtils.replaceStr(data.getTitle());
        ((TextView) holder.getView(R.id.tv_title)).setText(cname);
        ((TextView) holder.getView(R.id.tv_date)).setText(data.getUpdatedate());
//            ((TextView) holder.getView(R.id.tv_source)).setText("外交部");
        ImageView view = (ImageView) holder.getView(R.id.iv_pic);
        if (view == null)
            SizeUtil.setSpaceWandH(view, 16, 9, SizeUtils.dp2px(10) * 2);

        List<String> cimgs = data.getCimgs();
        if (cimgs != null && cimgs.size() > 0) {
            view.setVisibility(View.VISIBLE);
            GlideHelper.getInstance().load(view, cimgs.get(0));
        } else {
            view.setVisibility(View.GONE);
        }

        final String finalUrl = data.getUrl();
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String subUrl = StringUtil.subUrlSuffix(finalUrl);
                Intent intent = null;
//                if ("documents".equals(subUrl) || "channels".equals(subUrl)){
//                    intent = new Intent(context,ListActivity.class);
//                }else {
                intent = new Intent(context, NewsDetailActivity.class);
//                }

                intent.putExtra(ListActivity.ARG_PARAM1, finalUrl);
                context.startActivity(intent);
            }
        });
    }

}
