package com.gofar.wanandroid.c.tree.ui;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofar.component.basiclib.base.BaseListFragment;
import com.gofar.component.basiclib.entity.BaseListResponse;
import com.gofar.wanandroid.c.tree.entity.TreeEntity;

import io.reactivex.Observable;

/**
 * @author lcf
 * @date 20/7/2018 下午 5:44
 * @since 1.0
 */
public class TreeFragment extends BaseListFragment<TreeEntity>{

    @Override
    protected BaseQuickAdapter<TreeEntity, ? extends BaseViewHolder> getAdapter() {
        return null;
    }

    @Override
    protected Observable<BaseListResponse<TreeEntity>> getObservable(int page) {
        return null;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    protected void initialize() {

    }
}
