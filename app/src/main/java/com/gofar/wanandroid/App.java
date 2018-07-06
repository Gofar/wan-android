package com.gofar.wanandroid;

import com.gofar.basicres.BaseApp;
import com.luojilab.component.componentlib.router.ui.UIRouter;

/**
 * @author lcf
 * @date 2018/4/19 15:50
 * @since 1.0
 */
public class App extends BaseApp {

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(base);
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        UIRouter.getInstance().registerUI("app");
    }
}
