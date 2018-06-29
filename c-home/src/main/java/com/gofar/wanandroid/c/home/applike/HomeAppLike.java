package com.gofar.wanandroid.c.home.applike;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.ui.UIRouter;

/**
 * @author lcf
 * @date 2018/6/29 16:27
 * @since 1.0
 */
public class HomeAppLike implements IApplicationLike {
    UIRouter mUIRouter = UIRouter.getInstance();

    @Override
    public void onCreate() {
        mUIRouter.registerUI("home");
    }

    @Override
    public void onStop() {
        mUIRouter.unregisterUI("home");
    }
}
