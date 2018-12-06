package com.gofar.wanandroid.c.navi.ui.NaviAdapter;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gofar.wanandroid.c.navi.entity.NaviItemEntity;

/**
 * @author lcf
 * @date 6/12/2018 上午 11:42
 * @since 1.0
 */
public class NaviItem extends AbstractExpandableItem<Navi> implements MultiItemEntity {
    private NaviItemEntity mNaviItemEntity;

    public NaviItem(NaviItemEntity naviItemEntity) {
        mNaviItemEntity = naviItemEntity;
    }

    public NaviItemEntity getNaviItemEntity() {
        return mNaviItemEntity;
    }

    public void setNaviItemEntity(NaviItemEntity naviItemEntity) {
        mNaviItemEntity = naviItemEntity;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return NaviAdapter.TYPE_LEVEL_0;
    }
}
