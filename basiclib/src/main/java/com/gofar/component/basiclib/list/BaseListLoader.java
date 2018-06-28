package com.gofar.component.basiclib.list;

import android.support.annotation.NonNull;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofar.component.basiclib.entity.BaseListResponse;
import com.gofar.component.basiclib.widget.RefreshRecycleView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lcf
 * @date 2018/6/25 17:15
 * @since 1.0
 */
public abstract class BaseListLoader<T> {
    /**
     * 默认分页起始页码
     */
    public static final int DEFAULT_START = 0;

    private CompositeDisposable mCompositeDisposable;
    private RefreshRecycleView mRefreshRecycleView;
    private BaseQuickAdapter<T, ? extends BaseViewHolder> mAdapter;

    private int mPage = DEFAULT_START;

    public BaseListLoader(CompositeDisposable compositeDisposable, RefreshRecycleView refreshRecycleView
            , BaseQuickAdapter<T, ? extends BaseViewHolder> adapter) {
        this(compositeDisposable, refreshRecycleView, adapter, true, true);
    }

    public BaseListLoader(CompositeDisposable compositeDisposable, RefreshRecycleView refreshRecycleView
            , BaseQuickAdapter<T, ? extends BaseViewHolder> adapter
            , boolean enableRefresh, boolean enableLoadMore) {
        mCompositeDisposable = compositeDisposable;
        mRefreshRecycleView = refreshRecycleView;
        mAdapter = adapter;

        init(enableRefresh, enableLoadMore);
    }

    /**
     * 初始化
     *
     * @param enableRefresh  是否能下拉刷新
     * @param enableLoadMore 是否能加载更多
     */
    private void init(boolean enableRefresh, boolean enableLoadMore) {
        mRefreshRecycleView.setEnableRefresh(enableRefresh);
        mRefreshRecycleView.setEnableLoadMore(enableLoadMore);
        mRefreshRecycleView.setAdapter(mAdapter);
        mRefreshRecycleView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refresh();
            }
        });
        mRefreshRecycleView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMore();
            }
        });
        mRefreshRecycleView.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoRefresh();
            }
        });
    }

    public void autoRefresh() {
        mRefreshRecycleView.autoRefresh();
    }

    /**
     * 刷新
     */
    public void refresh() {
        mPage = DEFAULT_START;
        load(mPage);
    }

    /**
     * 加载更多
     */
    public void loadMore() {
        mPage++;
        load(mPage);
    }

    /**
     * 拿到网络请求的Observable
     *
     * @param page 页码
     * @return Observable
     */
    protected abstract Observable<BaseListResponse<T>> getObservable(int page);

    public void load(int page) {
        mCompositeDisposable.add(getObservable(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseListResponse<T>>() {
                    @Override
                    public void accept(BaseListResponse<T> baseListResponse) throws Exception {
                        if (baseListResponse.isSuccess()) {
                            List<T> data = baseListResponse.getDataList();
                            handleSuccess(data);
                        } else {
                            handleFailed(baseListResponse.getErrorMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        handleFailed("");
                    }
                })
        );
    }

    private boolean isRefresh() {
        return mPage == DEFAULT_START;
    }

    private void handleSuccess(List<T> data) {
        if (isRefresh()) {
            mRefreshRecycleView.finishRefresh(true);
            mAdapter.setNewData(data);
            if (mAdapter.getData().isEmpty()) {
                mRefreshRecycleView.showEmpty();
            }
        } else {
            if (data == null) {
                mRefreshRecycleView.finishLoadMoreEnd();
            } else {
                mAdapter.addData(data);
                mRefreshRecycleView.finishLoadMore(true);
            }
        }
    }

    private void handleFailed(String errorMsg) {
        if (isRefresh()) {
            mRefreshRecycleView.finishRefresh(false);
            if (mAdapter.getData().isEmpty()) {
                mRefreshRecycleView.showError(errorMsg);
            }
        } else {
            mRefreshRecycleView.finishLoadMore(false);
            mPage--;
        }
    }
}
