package com.gofar.wanandroid.c.project.applike;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.ui.UIRouter;

/**
 * @author lcf
 * @date 2018/6/29 16:31
 * @since 1.0
 */
public class ProjectAppLike implements IApplicationLike {
    UIRouter mUIRouter = UIRouter.getInstance();

    @Override
    public void onCreate() {
        mUIRouter.registerUI("project");
    }

    @Override
    public void onStop() {
        mUIRouter.unregisterUI("project");
    }
}
