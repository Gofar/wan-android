package com.gofar.wanandroid;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * @author lcf
 * @date 2018/4/19 15:50
 * @since 1.0
 */
public class App extends Application{

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
