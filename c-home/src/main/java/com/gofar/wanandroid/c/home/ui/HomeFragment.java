package com.gofar.wanandroid.c.home.ui;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofar.component.basiclib.base.BaseCompatListFragment;
import com.gofar.component.basiclib.entity.BaseListResponse;
import com.gofar.component.basiclib.network.Api;
import com.gofar.wanandroid.c.home.HomeApi;
import com.gofar.wanandroid.c.home.entity.HomeItemEntity;

import io.reactivex.Observable;

/**
 * @author lcf
 * @date 2018/6/28 17:28
 * @since 1.0
 */
public class HomeFragment extends BaseCompatListFragment<HomeItemEntity> {
    private HomeItemAdapter mHomeItemAdapter;

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean needToolbar() {
        return false;
    }

    @Override
    public BaseQuickAdapter<HomeItemEntity, ? extends BaseViewHolder> getAdapter() {
        mHomeItemAdapter = new HomeItemAdapter();
        return mHomeItemAdapter;
    }

    @Override
    public Observable<BaseListResponse<HomeItemEntity>> getObservable(int page) {
        return Api.getInstance().build(HomeApi.class).homeList(page);
    }

}
