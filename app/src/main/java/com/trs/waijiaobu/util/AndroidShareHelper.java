package com.trs.waijiaobu.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * creator：liufan
 * data: 2019/7/25
 */
public class AndroidShareHelper {

    public static void shareTxt(Context mContext, String text) {
        Intent textIntent = new Intent(Intent.ACTION_SEND);
        textIntent.setType("text/plain");
        textIntent.putExtra(Intent.EXTRA_TEXT, text);
        mContext.startActivity(Intent.createChooser(textIntent, "分享"));
    }

    public static void sharePic(Context mContext, Uri uri) {
//        String path = getResourcesUri(R.drawable.shu_1);
//        Intent imageIntent = new Intent(Intent.ACTION_SEND);
//        imageIntent.setType("image/jpeg");
//        imageIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
//        startActivity(Intent.createChooser(imageIntent, "分享"));
    }

    /**
     * 分享功能
     *
     * @param context
     *            上下文
     * @param activityTitle
     *            Activity的名字
     * @param msgTitle
     *            消息标题
     * @param msgText
     *            消息内容
     */
    public static void shareMsg(Context mContext,String msgTitle, String msgText,
                         Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (uri != null){
            intent.setType("image/jpg");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
        }else
            intent.setType("text/plain"); // 纯文本
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(Intent.createChooser(intent, "分享"));
    }


    public static void shareWechat() {
//        Intent wechatIntent = new Intent(Intent.ACTION_SEND);
//        wechatIntent.setPackage("com.tencent.mm");
//        wechatIntent.setType("text/plain");
//        wechatIntent.putExtra(Intent.EXTRA_TEXT, "分享到微信的内容");
//        startActivity(wechatIntent);
    }

    public static void shareQQ() {
//        Intent qqIntent = new Intent(Intent.ACTION_SEND);
//        qqIntent.setPackage("com.tencent.mobileqq");
//        qqIntent.setType("text/plain");
//        qqIntent.putExtra(Intent.EXTRA_TEXT, "分享到微信的内容");
//        startActivity(qqIntent);
    }

    public static void shareManyPic() {
//        ArrayList<Uri> imageUris = new ArrayList<>();
//        Uri uri1 = Uri.parse(getResourcesUri(R.drawable.dog));
//        Uri uri2 = Uri.parse(getResourcesUri(R.drawable.shu_1));
//        imageUris.add(uri1);
//        imageUris.add(uri2);
//        Intent mulIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
//        mulIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
//        mulIntent.setType("image/jpeg");
//        startActivity(Intent.createChooser(mulIntent,"多文件分享"));
    }
}
