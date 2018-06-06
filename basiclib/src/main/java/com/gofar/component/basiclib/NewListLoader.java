package com.gofar.component.basiclib;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofar.component.basiclib.entity.BaseListResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import ezy.ui.layout.LoadingLayout;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lcf
 * @date 2018/6/6 17:07
 * @since 1.0
 */
public class NewListLoader<T> {
    public static final int STATE_FIRST_LOAD = 0;
    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOAD_MORE = 2;

    public static final int DEFAULT_INDEX = 0;

    private SmartRefreshLayout mSmartRefreshLayout;
    private LoadingLayout mLoadingLayout;
    private RecyclerView mRecyclerView;
    private BaseQuickAdapter<T, ? extends BaseViewHolder> mAdapter;
    private int mPageIndex = DEFAULT_INDEX;
    private int mState = STATE_FIRST_LOAD;

    public NewListLoader(SmartRefreshLayout smartRefreshLayout, LoadingLayout loadingLayout, RecyclerView recyclerView, BaseQuickAdapter<T, ? extends BaseViewHolder> adapter) {
        mSmartRefreshLayout = smartRefreshLayout;
        mLoadingLayout = loadingLayout;
        mRecyclerView = recyclerView;
        mAdapter = adapter;
    }

    private void init() {
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mState = STATE_LOAD_MORE;
                mPageIndex++;
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mState = STATE_REFRESH;
                mPageIndex = DEFAULT_INDEX;
            }
        });

        mLoadingLayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mState = STATE_LOAD_MORE;
                mPageIndex++;
            }
        });
    }

    private void load(Observable<BaseListResponse<T>> observable) {
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseListResponse<T>>() {
                    @Override
                    public void accept(BaseListResponse<T> baseListResponse) throws Exception {
                        if (baseListResponse.isSuccess()) {
                            List<T> dataList = baseListResponse.getDataList();
                            if (dataList == null) {
                                if (isAll()){
                                    mSmartRefreshLayout.finishRefresh();
                                    mSmartRefreshLayout.finishLoadMore();
                                    mLoadingLayout.showEmpty();
                                }else {
                                    mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
                                }
                            } else {
                                if (isAll()){
                                    mSmartRefreshLayout.finishRefresh();
                                    mSmartRefreshLayout.finishLoadMore();
                                    mAdapter.setNewData(dataList);
                                }else {
                                    mAdapter.addData(dataList);
                                    mSmartRefreshLayout.finishLoadMore();
                                }
                            }
                        } else {
                            if (isRefresh()){
                                mSmartRefreshLayout.finishRefresh(false);
                            }else if (isFirstLoad()){
                                mLoadingLayout.showError();
                            }else {
                                mSmartRefreshLayout.finishLoadMore(false);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        if (isRefresh()){
                            mSmartRefreshLayout.finishRefresh(false);
                        }else if (isFirstLoad()){
                            mLoadingLayout.showError();
                        }else {
                            mSmartRefreshLayout.finishLoadMore(false);
                        }
                    }
                });

    }

    private boolean isAll() {
        return mPageIndex == DEFAULT_INDEX;
    }

    private boolean isRefresh() {
        return mPageIndex == 0 && mSmartRefreshLayout.getState() == RefreshState.Refreshing;
    }

    private boolean isFirstLoad() {
        return mPageIndex == 0 && mSmartRefreshLayout.getState() == RefreshState.Loading;
    }

    private boolean isRefreshOrFirstLoad() {
        return mPageIndex == 0 &&
                (mSmartRefreshLayout.getState() == RefreshState.Refreshing
                        || mSmartRefreshLayout.getState() == RefreshState.Loading);
    }

    private void handleData(List<T> data) {
    }

    private void showEmpty() {
        mLoadingLayout.showEmpty();
    }

    private void showError() {
        mLoadingLayout.showError();
    }

}
