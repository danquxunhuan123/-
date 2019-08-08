package com.trs.waijiaobu.util;

/**
 * creator：liufan
 * data: 2019/7/24
 */
public class StringUtil {
    public static String subUrlSuffix(String url) {
        try {
            return url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException exception) {
            return url;
        }
    }
}
