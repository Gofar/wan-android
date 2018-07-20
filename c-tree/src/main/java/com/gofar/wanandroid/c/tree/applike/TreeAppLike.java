package com.gofar.wanandroid.c.tree.applike;

import com.gofar.componentservice.tree.TreeService;
import com.gofar.wanandroid.c.tree.serviceimpl.TreeServiceImpl;
import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.component.componentlib.router.ui.UIRouter;

/**
 * @author lcf
 * @date 2018/6/29 16:33
 * @since 1.0
 */
public class TreeAppLike implements IApplicationLike {
    UIRouter mUIRouter = UIRouter.getInstance();
    Router mRouter=Router.getInstance();

    @Override
    public void onCreate() {
        mUIRouter.registerUI("tree");
        mRouter.addService(TreeService.class.getSimpleName(),new TreeServiceImpl());
    }

    @Override
    public void onStop() {
        mUIRouter.unregisterUI("tree");
        mRouter.removeService(TreeService.class.getSimpleName());
    }
}
