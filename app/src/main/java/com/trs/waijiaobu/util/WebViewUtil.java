package com.trs.waijiaobu.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blankj.utilcode.util.ToastUtils;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 创建：liufan
 */
public class WebViewUtil implements View.OnKeyListener {
    private WebView mWebView;
    private static WebViewUtil instance = null;

    private WebViewUtil(Context context) {
        mWebView = new WebView(context);
        mWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mWebView.getSettings().setSupportZoom(true);  //是否支持缩放，默认true
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(true); // 是否使用WebView内置的缩放组件，由浮动在窗口上的缩放控制和手势缩放控制组成，默认false
        mWebView.getSettings().setDisplayZoomControls(false);// 是否显示窗口悬浮的缩放控制，默认true
        mWebView.getSettings().setLoadWithOverviewMode(true);  // 是否启动概述模式浏览界面，当页面宽度超过WebView显示宽度时，缩小页面适应WebView。默认false
        mWebView.setHorizontalScrollBarEnabled(false);//水平不显示
        mWebView.setVerticalScrollBarEnabled(false); //垂直不显示
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setTextZoom(100);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.getSettings().setUseWideViewPort(true);  // 是否支持ViewPort的meta tag属性，如果页面有ViewPort meta tag 指定的宽度，则使用meta tag指定的值，否则默认使用宽屏的视图窗口
//        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.addJavascriptInterface(new JavaScriptInterface(context), "imagelistner"); //给图片设置点击监听的
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setOnKeyListener(this);
    }

    public static WebViewUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (WebViewUtil.class) {
                if (instance == null) {
                    instance = new WebViewUtil(context);
                }
            }
        }
        return instance;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            //按返回键操作并且能回退网页
            if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }
        }
        return false;
    }

    public void release() {
        mWebView.destroy();
        instance = null;
    }

    public void onResume() {
        mWebView.onResume();
    }

    public void onPause() {
        mWebView.onPause();
    }

    public WebView getWebview() {
        return mWebView;
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    public void loadDataWithBaseURL(String html) {
        // webview加载html标签
        mWebView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mWebView.getSettings().setBlockNetworkImage(true);
            listener.onPageStart();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mWebView.getSettings().setBlockNetworkImage(false);
            if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
                //设置wenView加载图片资源
                mWebView.getSettings().setBlockNetworkImage(false);
                mWebView.getSettings().setLoadsImagesAutomatically(true);
            }
            listener.onPageFinished();

            imgReset(view);//重置webview中img标签的图片大小
            addImageClickListner(view);   // 添加监听图片的点击js函数
        }

//        @Override
//        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//            super.onReceivedError(view, errorCode, description, failingUrl);
//        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);

            listener.onReceivedError();
        }
    }

    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private static void imgReset(WebView webView) {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()");
    }

    /**
     * 注入js脚本，设置图片点击函数openImage
     */
    private void addImageClickListner(WebView webView) {
        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        webView.loadUrl("javascript:(function(){"
                + "var objs = document.getElementsByTagName(\"img\"); "
                + "var arr = new Array();"
                + "for(var i=0; i < objs.length; i++)"
                + "{"
                + "arr.push(objs[i].src);"
                + "objs[i].index = i;"
                + "objs[i].onclick=function()"
                + "{"
//                +          "window.imagelistner.openImage(arr,this.src);  "
                + "window.imagelistner.openImage(arr,this.index);  "
                + "}"
                + "}"
                + "})()");
    }

    /**
     * 实现点击事件
     */
    private static class JavaScriptInterface {
        private Context context;

        public JavaScriptInterface(Context context) {
            this.context = context;
        }

        //点击图片回调方法
        //必须添加注解,否则无法响应
        @JavascriptInterface
        public void openImage(String[] imgs, int index) {

//            StringBuilder builder = new StringBuilder();
//            for (String s : imgs){
//                builder.append(s).append(",");
//            }
//            ToastUtils.showShort(index);
            mOnImgClickListener.onImgClick(imgs, index);
        }
    }

    public interface OnImgClickListener {
        void onImgClick(String[] imgs, int index);
    }

    private static OnImgClickListener mOnImgClickListener;

    public void setOnImgClickListener(OnImgClickListener listener) {
        mOnImgClickListener = listener;

    }

    public interface WebViewListener {
        void onPageStart();

        void onPageFinished();

        void onReceivedError();
    }

    private WebViewListener listener;

    public void setWebViewListener(WebViewListener listener) {
        this.listener = listener;
    }
}
