package com.gofar.component.basiclib.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gofar.component.basiclib.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import ezy.ui.layout.LoadingLayout;

/**
 * @author lcf
 * @date 2018/6/25 17:27
 * @since 1.0
 */
public class RefreshRecycleView extends FrameLayout {

    private RefreshLayout mRefreshLayout;
    private LoadingLayout mLoadingLayout;
    private RecyclerView mRecyclerView;

    public RefreshRecycleView(@NonNull Context context) {
        super(context);
    }

    public RefreshRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RefreshRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_refresh_recycler, this);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mLoadingLayout = findViewById(R.id.loadingLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
    }

    public RefreshLayout getRefreshLayout() {
        return mRefreshLayout;
    }

    public LoadingLayout getLoadingLayout() {
        return mLoadingLayout;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void autoRefresh() {
        mRefreshLayout.autoRefresh();
    }

    public void setEnableRefresh(boolean enabled) {
        mRefreshLayout.setEnableRefresh(enabled);
    }

    public void setEnableLoadMore(boolean enabled) {
        mRefreshLayout.setEnableLoadMore(enabled);
    }

    public void setAdapter(BaseQuickAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mRefreshLayout.setOnRefreshListener(listener);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mRefreshLayout.setOnLoadMoreListener(listener);
    }

    public void setOnRetryListener(OnClickListener listener) {
        mLoadingLayout.setRetryListener(listener);
    }

    public void finishRefresh(boolean success) {
        mRefreshLayout.finishRefresh(success);
    }

    public void finishLoadMore(boolean success) {
        mRefreshLayout.finishLoadMore(success);
    }

    public void finishLoadMoreEnd() {
        mRefreshLayout.finishLoadMoreWithNoMoreData();
    }

    public void showEmpty() {
        mLoadingLayout.showEmpty();
    }

    public void showError() {
        mLoadingLayout.showEmpty();
    }

    public void showError(String errorMsg){
        mLoadingLayout.setErrorText(errorMsg);
        mLoadingLayout.showError();
    }

    public void showError(String errorMsg, @DrawableRes int errorResId){
        mLoadingLayout.setErrorText(errorMsg);
        mLoadingLayout.setErrorImage(errorResId);
        mLoadingLayout.showError();
    }
}