package com.gofar.wanandroid.c.home.applike;

import com.gofar.componentservice.home.HomeService;
import com.gofar.wanandroid.c.home.serviceimpl.HomeServiceImpl;
import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.component.componentlib.router.ui.UIRouter;

/**
 * @author lcf
 * @date 2018/6/29 16:27
 * @since 1.0
 */
public class HomeAppLike implements IApplicationLike {
    UIRouter mUIRouter = UIRouter.getInstance();
    Router mRouter = Router.getInstance();

    @Override
    public void onCreate() {
        mUIRouter.registerUI("home");
        mRouter.addService(HomeService.class.getSimpleName(), new HomeServiceImpl());
    }

    @Override
    public void onStop() {
        mUIRouter.unregisterUI("home");
        mRouter.removeService(HomeService.class.getSimpleName());
    }
}
