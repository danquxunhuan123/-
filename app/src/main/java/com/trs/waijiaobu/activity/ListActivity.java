package com.trs.waijiaobu.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.trs.waijiaobu.R;
import com.trs.waijiaobu.adapter.BaseAdapter;
import com.trs.waijiaobu.adapter.ListCommenAdapter;
import com.trs.waijiaobu.bean.Channel;
import com.trs.waijiaobu.bean.Document;
import com.trs.waijiaobu.fragment.PicsDialogFragment;
import com.trs.waijiaobu.presenter.inter.IListPresenter;
import com.trs.waijiaobu.presenter.IListPresenterImpl;
import com.trs.waijiaobu.util.StringUtil;
import com.trs.waijiaobu.view.IListView;

import java.util.List;

import butterknife.BindView;

public class ListActivity extends BaseActivity implements IListView, SwipeRefreshLayout.OnRefreshListener, BaseAdapter.OnLoadMoreListener, ListCommenAdapter.OnClickListener {

    @BindView(R.id.tv_cname)
    TextView cname;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    public static final String ARG_PARAM1 = "url";
    public static final String ARG_PARAM2 = "cname";
    private int pageCount = 0;
    private IListPresenter mPresenter;
    //    private String cname;
    private ListCommenAdapter adapter;
    private String url;
    private String subUrl;

    @Override
    protected void initView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        mPresenter = new IListPresenterImpl(this, this);
    }

    @Override
    protected void getData() {
        url = getIntent().getStringExtra(ARG_PARAM1);
        String title = getIntent().getStringExtra(ARG_PARAM2);
        subUrl = StringUtil.subUrlSuffix(url);

        if (!TextUtils.isEmpty(title))
            cname.setText(title);
        swipeRefreshLayout.setRefreshing(true);
        mPresenter.getListData(url);
    }

    @Override
    public void getListData(Object obj) {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);

        List mList = null;
        if (obj instanceof Channel)
            mList = ((Channel) obj).getGd();
        else if (obj instanceof Document)
            mList = ((Document) obj).getList_datas();

        if (adapter == null) {
            adapter = new ListCommenAdapter(null, mList, this);
            adapter.setOnLoadMoreListener(this);
            adapter.setOnItemClickListener(this);
            recycleView.setLayoutManager(new LinearLayoutManager(this));
            recycleView.setAdapter(adapter);
        } else {
            if (pageCount == 0)
                adapter.updateData(mList);
            else
                adapter.addData(mList);
        }
    }

    @Override
    public void onFailure() {
        pageCount--;
        if (adapter != null)
            adapter.loadMoreEnd();
    }

    @Override
    protected int flateLayout() {
        return R.layout.layout_activity_list;
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onRefresh() {
        pageCount = 0;
        mPresenter.getListData(url);
    }

    @Override
    public void OnLoadMore() {
        if (subUrl.startsWith("documents")) {
            String moreUrl = url;
            pageCount++;
            moreUrl = moreUrl.replace("documents", "documents_" + pageCount);
            mPresenter.getListData(moreUrl);
        } else {
            adapter.loadMoreEnd();
        }
    }

    @Override
    public void onClick(String finalu) {
        if (finalu.endsWith(".jpg")) {
            String[] list = new String[1];
            list[0] = finalu;
            PicsDialogFragment picFragment = PicsDialogFragment.newInstance(list, 0);
            picFragment.show(getSupportFragmentManager(), "PIC");
        } else if (finalu.endsWith(".doc")) {
//            ToastUtils.showShort("doc");
        }
    }
}
