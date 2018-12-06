package com.gofar.wanandroid.c.navi.ui.NaviAdapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gofar.wanandroid.c.navi.entity.NaviArticleEntity;

/**
 * @author lcf
 * @date 6/12/2018 下午 1:57
 * @since 1.0
 */
public class Navi implements MultiItemEntity {
    private NaviArticleEntity mNaviArticleEntity;

    public Navi(NaviArticleEntity naviArticleEntity) {
        mNaviArticleEntity = naviArticleEntity;
    }

    public NaviArticleEntity getNaviArticleEntity() {
        return mNaviArticleEntity;
    }

    public void setNaviArticleEntity(NaviArticleEntity naviArticleEntity) {
        mNaviArticleEntity = naviArticleEntity;
    }

    @Override
    public int getItemType() {
        return NaviAdapter.TYPE_NAVI;
    }
}
