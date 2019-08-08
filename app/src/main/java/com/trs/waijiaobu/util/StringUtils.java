package com.trs.waijiaobu.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LiuFan on 2017/12/3.
 */

public class StringUtils {
    public static String replaceStr(String str) {
        str = str.replace("<br>", "");
        str = str.replace("&quot;", "");
        str = str.replace("&quot;", "");
        str = str.replace("&lt;br /&gt;", "<br />");
        str = str.replace("&lt;/font&gt;", "</font>");
        str = str.replace("&lt;BR&gt;", " ");
        str = str.replace("&lt;br&gt;", " ");
        str = str.replace("&nbsp;", "");
        str = str.replace("&lt;font style=&quot;FONT-SIZE: 24px&quot;&gt;", "");
        str = str.replace("&lt;/font&gt;&lt;br /&gt;&lt;font style=&quot;FONT-SIZE: 34px&quot;&gt;&lt;strong&gt;", " ");
        str = str.replace("&lt;br /&gt;", " ");
        str = str.replace("&lt;/strong&gt;&lt;/font&gt;", "");
        return str.replace("&amp;nbsp;", " ");
    }

    /**
     * 得到网页中图片的地址
     */
    public static List<String> getImgStr(String htmlStr) {
        List<String> pics = new ArrayList<>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        //     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
//        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        String regEx_img = "<embed.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }

    /**
     * 过滤标签
     */
    public static String filterImg(String html) {
        int beginIndex = html.indexOf("<embed");
        int endIndex = html.indexOf("</embed>");
        html = html.replace(html.substring(beginIndex, endIndex + "</embed>".length()), "");
        return html;
    }
}
