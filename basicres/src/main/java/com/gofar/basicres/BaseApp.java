package com.gofar.basicres;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.stetho.Stetho;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

/**
 * @author lcf
 * @date 2018/6/29 11:21
 * @since 1.0
 */
public class BaseApp extends Application {
    private static BaseApp mApp;

    static {
        // 设置全局默认的header
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                // 是否下拉Header的时候向下平移列表或者内容
                layout.setEnableHeaderTranslationContent(false);
                return new MaterialHeader(context).setColorSchemeResources(R.color.black);
            }
        });
        // 设置全局默认的footer
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }

    public static BaseApp getApp() {
        return mApp;
    }
}
