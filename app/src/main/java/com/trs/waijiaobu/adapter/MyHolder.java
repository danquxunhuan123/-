package com.trs.waijiaobu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

/**
 * Created by Liufan on 2018/5/17.
 */

public class MyHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> views;
    private View mConvertView;

    private MyHolder(View itemView) {
        super(itemView);
        mConvertView = itemView;
        views = new SparseArray<>();
    }

    public static MyHolder getComViewHolder(Context context,int layoutId, ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new MyHolder(itemView);
    }

    /**
     * 缓存+提取
     */
    public View getView(int id){
        View view = views.get(id);
        if (view == null) {
            view = mConvertView.findViewById(id);
            views.put(id, view);
        }
        return view;
    }

    public View getItemView(){
        return mConvertView;
    }
}
