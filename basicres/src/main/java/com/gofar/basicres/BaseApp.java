package com.gofar.basicres;

import android.app.Application;

/**
 * @author lcf
 * @date 2018/6/29 11:21
 * @since 1.0
 */
public class BaseApp extends Application{
    private static BaseApp mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp=this;
    }

    public static BaseApp getApp() {
        return mApp;
    }
}
