package com.trs.waijiaobu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trs.waijiaobu.R;
import com.trs.waijiaobu.bean.TabChannel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * creator：liufan
 * data: 2019/8/1
 */
public class ChannelAdapter extends RecyclerView.Adapter<MyHolder> {
    private List<TabChannel> listData;
    private Context mContext;
    private boolean isEditor = false;
    private int type_size_0; //我的频道栏目数

    public ChannelAdapter(List<TabChannel> listData, Context context) {
        this.listData = listData;
        this.mContext = context;
        type_size_0 = listData.size() - 2;

    }

    @Override
    public int getItemViewType(int position) {
        return listData.get(position).getType();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 1)
            return MyHolder.getComViewHolder(mContext, R.layout.list_item_channel_title, null);
        else
            return MyHolder.getComViewHolder(mContext, R.layout.list_item_channel, null);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        TabChannel channel = listData.get(position);
        switch (getItemViewType(position)) {
            case 0:
            case 2:
                ((TextView) holder.getView(R.id.tv_channel_name)).setText(channel.getCname());

                if (channel.getType() == 0)
                    if (isEditor)
                        holder.getView(R.id.btn_move).setVisibility(View.VISIBLE);
                    else
                        holder.getView(R.id.btn_move).setVisibility(View.GONE);
                else
                    holder.getView(R.id.btn_move).setVisibility(View.GONE);


                if (channel.getType() == 0)
                    holder.getView(R.id.btn_move).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TabChannel remove = listData.remove(position);
                            remove.setType(2);
//                            myTab.remove(remove);
                            listData.add(remove);

                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, getItemCount());

                            type_size_0--;
                        }
                    });
                else holder.getView(R.id.btn_move).setOnClickListener(null);

                if (channel.getType() == 2)
                    holder.getView(R.id.tv_channel_name).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int index = 1 + type_size_0;
                            TabChannel remove = listData.remove(position);
                            remove.setType(0);
                            listData.add(index, remove);
//                            myTab.add(remove);
                            notifyItemInserted(index);
                            notifyItemRangeChanged(index, getItemCount());

                            type_size_0++;
                        }
                    });
                else holder.getView(R.id.tv_channel_name).setOnClickListener(null);

                break;
            case 1:
                TextView text = (TextView) holder.getView(R.id.tv_channel_name);
                final TextView textEditor = (TextView) holder.getView(R.id.tv_channel_idtor);

                text.setText(channel.getCname());
                if ("我的频道".equals(text.getText().toString()))
                    textEditor.setVisibility(View.VISIBLE);
                else
                    textEditor.setVisibility(View.GONE);

                textEditor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isEditor) {
                            isEditor = false;
                            textEditor.setText("编辑");
                        } else {
                            isEditor = true;
                            textEditor.setText("完成");
                        }

                        notifyDataSetChanged();
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public List<TabChannel> getData() {
        List<TabChannel> myTab = new ArrayList<>();
        for (int i = 1; i <= type_size_0; i++) {
            myTab.add(listData.get(i));
        }
        return myTab;
    }
}
