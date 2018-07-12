package com.gofar.component.basiclib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofar.component.basiclib.R;
import com.gofar.component.basiclib.entity.BaseListResponse;
import com.gofar.component.basiclib.list.BaseListLoader;
import com.gofar.component.basiclib.widget.RefreshRecycleView;
import com.gofar.titlebar.TitleBar;

import io.reactivex.Observable;

/**
 * @author lcf
 * @date 2018/6/28 15:09
 * @since 1.0
 */
public abstract class BaseCompatListFragment<T> extends BaseCompatFragment
        implements BaseQuickAdapter.OnItemClickListener, IListLoader<T> {

    protected RefreshRecycleView mRefreshRecycleView;
    protected BaseListLoader<T> mBaseListLoader;
    protected BaseQuickAdapter<T, ? extends BaseViewHolder> mBaseAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.base_recycler_list;
    }

    @Override
    protected void initView(View parent) {
        super.initView(parent);
        mRefreshRecycleView = parent.findViewById(R.id.refresh_rv);
        mRefreshRecycleView.setLayoutManager(getLayoutManager());
        mBaseAdapter = getAdapter();
        mBaseAdapter.setOnItemClickListener(this);
        mBaseListLoader = getBaseListLoader();
    }

    @Override
    protected void initToolBar(TitleBar toolbar) {
        if (!needToolbar()) {
            mRoot.removeView(toolbar);
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (needLazyLoad()) {
            mBaseListLoader.autoRefresh();
        }
    }

    protected boolean needToolbar() {
        return true;
    }

    protected boolean needLazyLoad() {
        return true;
    }

    @Override
    public boolean needRefresh() {
        return true;
    }

    @Override
    public boolean needLoadMore() {
        return true;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(mContext);
    }

    protected BaseListLoader<T> getBaseListLoader() {
        return new BaseListLoader<T>(mCompositeDisposable, mRefreshRecycleView, mBaseAdapter, needRefresh(), needLoadMore()) {
            @Override
            protected Observable<BaseListResponse<T>> getObservable(int page) {
                return BaseCompatListFragment.this.getObservable(page);
            }
        };
    }
}
