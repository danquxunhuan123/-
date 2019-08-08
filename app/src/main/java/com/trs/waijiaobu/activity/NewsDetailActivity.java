package com.trs.waijiaobu.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.trs.waijiaobu.R;
import com.trs.waijiaobu.bean.Detail;
import com.trs.waijiaobu.glide.GlideHelper;
import com.trs.waijiaobu.presenter.IDetailPresenter;
import com.trs.waijiaobu.presenter.IDetailPresenterImpl;
import com.trs.waijiaobu.presenter.IDetailView;
import com.trs.waijiaobu.util.AndroidShareHelper;
import com.trs.waijiaobu.util.ProgressUtil;
import com.trs.waijiaobu.util.ReadFromFile;
import com.trs.waijiaobu.util.StringUtil;
import com.trs.waijiaobu.util.StringUtils;
import com.trs.waijiaobu.util.WebViewUtil;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.io.File;

import butterknife.BindView;

public class NewsDetailActivity extends BaseActivity implements IDetailView, WebViewUtil.WebViewListener, WebViewUtil.OnImgClickListener {
    @BindView(R.id.linear_content)
    LinearLayout mLinearLayout;

    private String localHtmlModel;
    public static final String ARG_PARAM1 = "url";
    private WebViewUtil mWebViewUtil;
    private WebView webview;
    private IDetailPresenter mPresenter;
    private Detail mDetail;
    private final String reg = "(?i)\\<p[^\\>]*\\>";

    private String url;

    @Override
    protected int flateLayout() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initView() {
        mWebViewUtil = WebViewUtil.getInstance(this);
        webview = mWebViewUtil.getWebview();
        mLinearLayout.addView(webview, 1);
        mWebViewUtil.setWebViewListener(this);
        mWebViewUtil.setOnImgClickListener(this);
    }

    private void initPlayer(String mVideoUrl, String mTitle) {
        NiceVideoPlayer mNiceVideoPlayer = new NiceVideoPlayer(this);
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // or NiceVideoPlayer.TYPE_NATIVE
        mNiceVideoPlayer.setUp(mVideoUrl, null);

        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle(mTitle);
//        controller.setImage(mImageUrl);
        mNiceVideoPlayer.setController(controller);
    }

    @Override
    protected void getData() {
        url = getIntent().getStringExtra(ARG_PARAM1);
        if (url.endsWith(".json")) {
            mPresenter = new IDetailPresenterImpl(this);
            localHtmlModel = ReadFromFile.getFromAssets(this, "xhwDetailedView.html");
            mPresenter.getData(url);
        } else {
            mWebViewUtil.loadUrl(url);
        }
    }

    @Override
    public void onPageStart() {
        ProgressUtil.getInstance(this).show();
    }

    @Override
    public void onPageFinished() {
        ProgressUtil.getInstance(this).dismiss();
    }

    @Override
    public void onReceivedError() {
        ProgressUtil.getInstance(this).dismiss();
    }

    public void share(View view) {
        /*GlideHelper.getInstance().loadToFile(
                this, mDetail.getDatas().getShareimg(), new GlideHelper.OnLoadedListener() {
                    @Override
                    public void onLoaded(File file) {
//                        Uri uri = FileProvider.getUriForFile(NewsDetailActivity.this, AppUtils.getAppPackageName() + ".fileprovider", file);
//                        AndroidShareHelper.shareTxtAndPic(NewsDetailActivity.this, uri, mDetail.getDatas().getTitle());
                        AndroidShareHelper.shareTxt(NewsDetailActivity.this, mDetail.getDatas().getSharelink());
                    }

                    @Override
                    public void onLoadedBitmap(Bitmap bitmap) {
                    }
               });*/

        if (url.endsWith(".json")){
            if (!TextUtils.isEmpty(mDetail.getDatas().getSharelink()))
//                AndroidShareHelper.shareMsg(this,mDetail.getDatas().getTitle(),
//                        mDetail.getDatas().getBody(),null);
                AndroidShareHelper.shareTxt(NewsDetailActivity.this, mDetail.getDatas().getSharelink());
            else {
                ToastUtils.showShort("sharelink is empty");
            }
        }else
            AndroidShareHelper.shareTxt(NewsDetailActivity.this,url);
    }

    public void back(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebViewUtil.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebViewUtil.onPause();
    }

    @Override
    protected void onDestroy() {
        mLinearLayout.removeView(webview);
        mWebViewUtil.release();
        ProgressUtil.getInstance(this).release();
        super.onDestroy();
    }

    @Override
    public void getData(Object obj) {
        Detail detail = (Detail) obj;

        this.mDetail = detail;
        String body = detail.getDatas().getBody();
        String replaceBody = RegexUtils.getReplaceAll(body, reg, "");
        String updatedate = detail.getDatas().getUpdatedate();
        String source = detail.getDatas().getSource();
        String title = detail.getDatas().getTitle();
        localHtmlModel = localHtmlModel.replaceAll("#TITLE#", StringUtils.replaceStr(title));
        localHtmlModel = localHtmlModel.replaceAll("#SOURCE#", source);
        localHtmlModel = localHtmlModel.replaceAll("#TIME#", updatedate);
        localHtmlModel = localHtmlModel.replace("#CONTENT#", replaceBody);
        mWebViewUtil.loadDataWithBaseURL(localHtmlModel);
    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();  // 释放掉播放器
    }

    @Override
    public void onBackPressed() {
        // 在全屏或者小窗口时按返回键要先退出全屏或小窗口，
        // 所以在Activity中onBackPress要交给NiceVideoPlayer先处理。
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }

    @Override
    public void onImgClick(String[] imgs, int index) {
        Intent intent = new Intent(this, PicDetailActivity.class);
        intent.putExtra(PicDetailActivity.ARG_PARAM1, imgs);
        intent.putExtra(PicDetailActivity.ARG_PARAM2, index);
        startActivity(intent);
    }
}
