package com.gofar.wanandroid.c.home.ui;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofar.wanandroid.c.home.R;
import com.gofar.wanandroid.c.home.entity.HomeItemEntity;

/**
 * @author lcf
 * @date 2018/6/28 17:37
 * @since 1.0
 */
public class HomeItemAdapter extends BaseQuickAdapter<HomeItemEntity,BaseViewHolder>{
    public HomeItemAdapter() {
        super(R.layout.item_home_entity);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeItemEntity item) {

    }
}
