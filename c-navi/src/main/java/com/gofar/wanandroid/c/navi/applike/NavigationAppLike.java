package com.gofar.wanandroid.c.navi.applike;

import com.gofar.componentservice.navigation.NaviService;
import com.gofar.wanandroid.c.navi.serviceimpl.NaviServiceImpl;
import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.component.componentlib.router.ui.UIRouter;

/**
 * @author lcf
 * @date 2018/6/29 16:29
 * @since 1.0
 */
public class NavigationAppLike implements IApplicationLike {
    UIRouter mUIRouter = UIRouter.getInstance();
    Router mRouter = Router.getInstance();

    @Override
    public void onCreate() {
        mUIRouter.registerUI("navigation");
        mRouter.addService(NaviService.class.getSimpleName(), new NaviServiceImpl());
    }

    @Override
    public void onStop() {
        mUIRouter.unregisterUI("navigation");
        mRouter.removeService(NaviService.class.getSimpleName());
    }
}
