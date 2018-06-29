package com.gofar.wanandroid.c.navi.applike;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.ui.UIRouter;

/**
 * @author lcf
 * @date 2018/6/29 16:29
 * @since 1.0
 */
public class NavigationAppLike implements IApplicationLike{
    UIRouter mUIRouter=UIRouter.getInstance();
    @Override
    public void onCreate() {
        mUIRouter.registerUI("navigation");
    }

    @Override
    public void onStop() {
        mUIRouter.unregisterUI("navigation");
    }
}
