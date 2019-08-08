package com.trs.waijiaobu.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trs.waijiaobu.R;
import com.trs.waijiaobu.adapter.ChannelAdapter;
import com.trs.waijiaobu.adapter.ItemTouchHelperCallback;
import com.trs.waijiaobu.bean.TabChannel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ChannelFragment extends DialogFragment {

    private List<TabChannel> list  = new ArrayList<>();

    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;

    public static final String PARAMS1 = "titles";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        if (dialog != null) {
            //添加动画
            dialog.getWindow().setWindowAnimations(R.style.dialogSlideAnim);
        }
        return inflater.inflate(R.layout.channel_layout, null);
    }

    private Unbinder bind;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, view);
        initData();
    }

    public static ChannelFragment newInstance(ArrayList<String> names) {
        ChannelFragment dialogFragment = new ChannelFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(PARAMS1, names);
//        bundle.putSerializable(ConstanceValue.DATA_UNSELECTED, (Serializable) unselectedDatas);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @OnClick(R.id.btn_cancle)
    public void onClick(View view) {
        dismiss();
    }

    private ChannelAdapter adapter;

    private void initData() {
        list.clear();
        TabChannel tab = new TabChannel();
        tab.setCname("我的频道");
        tab.setType(1);
        list.add(tab);

        List<String> titles = getArguments().getStringArrayList(PARAMS1);
        for (String title : titles) {
            TabChannel channel = new TabChannel();
            channel.setCname(title);
            channel.setType(0);
            list.add(channel);
        }

        TabChannel tabCommend = new TabChannel();
        tabCommend.setCname("频道推荐");
        tabCommend.setType(1);
        list.add(tabCommend);

        adapter = new ChannelAdapter(list, getActivity());
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                int type = adapter.getItemViewType(i);
                return type == 1 ? 4 : 1;
            }
        });
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelperCallback() {
            @Override
            protected void onItemMove(int adapterPosition, int targetAdapterPosition) {
                Collections.swap(list, adapterPosition, targetAdapterPosition);
                adapter.notifyItemMoved(adapterPosition, targetAdapterPosition);
                adapter.notifyItemRangeChanged(adapterPosition, adapter.getItemCount());
            }
        });
        touchHelper.attachToRecyclerView(mRecyclerView);

    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        bind.unbind();
        mOnDismissListener.onDismiss(adapter.getData());
    }

    private OnDismissListener mOnDismissListener;
    public void setOnDisMissListener(OnDismissListener listener){
        mOnDismissListener = listener;
    }

    public interface OnDismissListener{
        void onDismiss(List<TabChannel> list);
    }

}
