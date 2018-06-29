package com.gofar.wanandroid.c.tree.applike;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.ui.UIRouter;

/**
 * @author lcf
 * @date 2018/6/29 16:33
 * @since 1.0
 */
public class TreeAppLike implements IApplicationLike {
    UIRouter mUIRouter = UIRouter.getInstance();

    @Override
    public void onCreate() {
        mUIRouter.registerUI("tree");
    }

    @Override
    public void onStop() {
        mUIRouter.unregisterUI("tree");
    }
}
