package com.gofar.component.basiclib;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofar.component.basiclib.entity.BaseListResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lcf
 * @date 2018/6/6 11:14
 * @since 1.0
 */
public class ListLoader<T> {

    public static final int DEFAULT_INDEX = 0;

    private Activity mActivity;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private BaseQuickAdapter<T, ? extends BaseViewHolder> mAdapter;
    private CompositeDisposable mCompositeDisposable;
    private int mPageIndex = DEFAULT_INDEX;

    public ListLoader(Activity activity, RecyclerView recyclerView, BaseQuickAdapter<T, ? extends BaseViewHolder> adapter) {
        this(activity, recyclerView, adapter, true);
    }

    public ListLoader(Activity activity, RecyclerView recyclerView, BaseQuickAdapter<T, ? extends BaseViewHolder> adapter, boolean loadMoreEnable) {
        this(activity, null, recyclerView, adapter, false, true);
    }

    public ListLoader(Activity activity, SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView, BaseQuickAdapter<T, ? extends BaseViewHolder> adapter) {
        this(activity, swipeRefreshLayout, recyclerView, adapter, true, true);
    }

    public ListLoader(Activity activity, SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView, BaseQuickAdapter<T, ? extends BaseViewHolder> adapter, boolean refreshEnable, boolean loadMoreEnable) {
        mActivity = activity;
        mSwipeRefreshLayout = swipeRefreshLayout;
        mRecyclerView = recyclerView;
        mAdapter = adapter;

        initialize(refreshEnable, loadMoreEnable);
    }

    private void initialize(boolean refreshEnable, boolean loadMoreEnable) {
        if (refreshEnable) {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                }
            });
        }
        mRecyclerView.setAdapter(mAdapter);
        if (loadMoreEnable) {
            mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {

                }
            }, mRecyclerView);
        }
    }

    public void setRefreshEnable(boolean refreshEnable) {
        mSwipeRefreshLayout.setEnabled(refreshEnable);
    }

    public void setLoadMoreEnable(boolean loadMoreEnable) {
        mAdapter.setEnableLoadMore(loadMoreEnable);
    }

    public void load(Observable<BaseListResponse<T>> observable) {
        if (observable == null) {
            //mAdapter.setEmptyView();
        } else {
            mCompositeDisposable.add(observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<BaseListResponse<T>>() {
                        @Override
                        public void accept(BaseListResponse<T> baseListResponse) throws Exception {
                            if (isRefreshOrFirstLoad()) {
                                if (mSwipeRefreshLayout != null) {
                                    mSwipeRefreshLayout.setRefreshing(false);
                                }
                            }
                            if (baseListResponse.isSuccess()) {
                                BaseListResponse.DataWrapperEntity<T> wrapperEntity = baseListResponse.getData();
                                if (wrapperEntity != null) {
                                    List<T> data = wrapperEntity.getDatas();
                                    if (data == null || data.isEmpty()) {
                                        if (isRefreshOrFirstLoad()) {
                                            //mAdapter.setEmptyView();
                                        } else {
                                            mAdapter.loadMoreEnd();
                                        }
                                    } else {
                                        if (isRefreshOrFirstLoad()) {
                                            mAdapter.setNewData(data);
                                        } else {
                                            mAdapter.addData(data);
                                        }
                                    }
                                }
                            } else {
                                if (isRefreshOrFirstLoad()) {
                                    //mAdapter.setEmptyView();
                                } else {
                                    mAdapter.loadMoreFail();
                                }
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                            if (isRefreshOrFirstLoad()) {
                                if (mSwipeRefreshLayout != null) {
                                    mSwipeRefreshLayout.setRefreshing(false);
                                }
                                //mAdapter.setEmptyView();
                            } else {
                                mAdapter.loadMoreFail();
                            }

                        }
                    }));
        }

    }

    private boolean isRefreshOrFirstLoad() {
        return DEFAULT_INDEX == mPageIndex;
    }
}
