package com.trs.waijiaobu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trs.waijiaobu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liufan on 2018/5/17.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<MyHolder> {
    protected List<T> list;
    private List<T> listMore;
    private int LOAD_MORE_TYPE = 100;
    protected Context context;
    private OnLoadMoreListener listener;
    private final int notifyLoadMore = 0;
    private boolean firstLoad = true;
    private final String LOAD_MORE = "加载更多...";
    private final String NO_MORE_DATA = "无更多数据";
    private final String NO_DATA = "暂无数据";
    private T t;
    private int loadTimes;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == notifyLoadMore) {
                listMore = null;
                notifyItemRangeChanged(getItemCount() - 1, getItemCount());
            }
        }
    };

    public BaseAdapter(List<T> listData, Context context) {
        this.context = context;
        list = listData;

        if (list == null)
            list = new ArrayList<>();

        t = (T) new Object();
        list.add(t);
    }

    public void updateData(List<T> listData) {
        list = listData;

        if (list == null)
            list = new ArrayList<>();

        list.add(t);
        firstLoad = true;
        loadTimes = 0;
        notifyDataSetChanged();
    }

    public int getSize() {
        return list.size() - 1;
    }

    public void addData(List<T> listData) {
        listMore = listData;
        list.addAll(list.size() - 1, listMore);
        notifyDataSetChanged();
    }

    public void loadMoreEnd() {
        handler.sendEmptyMessageDelayed(notifyLoadMore, 500);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size() - 1)
            return LOAD_MORE_TYPE;
        else
            return getItemType(position);
    }

    protected abstract int getItemType(int position);

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == LOAD_MORE_TYPE)
            return MyHolder.getComViewHolder(parent.getContext(), R.layout.list_load_more_layout, parent);
        else
            return createHolder(parent, viewType);
    }

    public abstract MyHolder createHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        if (getItemViewType(position) == LOAD_MORE_TYPE) {
            if (listener != null) {//开启了加载更多
                holder.getItemView().setVisibility(View.VISIBLE); //显示加载更多布局
                if (firstLoad) { //第一次加载更多，不需要判断
                    firstLoad = false;
                    loadTimes++;
                    listener.OnLoadMore();
                } else { //第二次判断是否数据为空，不为空加载
                    if (listMore != null && listMore.size() > 0) {
                        ((TextView) holder.getView(R.id.tv_load_more)).setText(LOAD_MORE);
                        (holder.getView(R.id.progress)).setVisibility(View.VISIBLE);
                        loadTimes++;
                        listener.OnLoadMore();
                    } else {
                        if (loadTimes > 1) {
                            ((TextView) holder.getView(R.id.tv_load_more)).setText(NO_MORE_DATA);
                            (holder.getView(R.id.progress)).setVisibility(View.GONE);
                        } else {
                            if (getItemCount() <= 1) {
                                (holder.getView(R.id.progress)).setVisibility(View.GONE);
                                ((TextView) holder.getView(R.id.tv_load_more)).setText(NO_DATA);
                            } else {
                                holder.getItemView().setVisibility(View.GONE);
                            }
                        }

                    }
                }
            } else
                holder.getItemView().setVisibility(View.GONE);
        } else {
            bindHolder(holder, position);
        }
    }

    protected abstract void bindHolder(MyHolder holder, int position);

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnLoadMoreListener {
        void OnLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.listener = listener;
    }
}
