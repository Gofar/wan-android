package com.gofar.component.basiclib.list;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import ezy.ui.layout.LoadingLayout;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author lcf
 * @date 2018/6/25 17:15
 * @since 1.0
 */
public class BaseListLoader<T> {

    public static final int DEFAULT_START = 0;

    private CompositeDisposable mCompositeDisposable;
    private RefreshLayout mRefreshLayout;
    private LoadingLayout mLoadingLayout;
    private RecyclerView mRecyclerView;
    private BaseQuickAdapter<T, ? extends BaseViewHolder> mAdapter;

    private int mPage = DEFAULT_START;

    public BaseListLoader(CompositeDisposable compositeDisposable, RefreshLayout refreshLayout, LoadingLayout loadingLayout, RecyclerView recyclerView, BaseQuickAdapter<T, ? extends BaseViewHolder> adapter) {
        mCompositeDisposable = compositeDisposable;
        mRefreshLayout = refreshLayout;
        mLoadingLayout = loadingLayout;
        mRecyclerView = recyclerView;
        mAdapter = adapter;
    }

    private void init() {

    }
}
