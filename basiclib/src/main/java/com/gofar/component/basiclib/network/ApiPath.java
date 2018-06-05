package com.gofar.component.basiclib.network;

import com.gofar.component.basiclib.BuildConfig;

/**
 * @author lcf
 * @date 2018/6/5 17:13
 * @since 1.0
 */
public class ApiPath {

    public static String BASE_URL = getBaseUrl();

    private static String getBaseUrl() {
        if (BuildConfig.DEBUG) {
            return "http://www.wanandroid.com";
        } else {
            return "http://www.wanandroid.com";
        }
    }
}
