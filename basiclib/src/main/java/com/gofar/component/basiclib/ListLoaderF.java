package com.gofar.component.basiclib;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofar.component.basiclib.entity.BaseListResponse;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
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
 * @date 2018/6/7 14:39
 * @since 1.0
 */
public class ListLoaderF<T> {

    public static final int STATE_FIRST_LOAD = 0;
    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOAD_MORE = 2;
    /**
     * The default start index of list.
     */
    public static final int DEFAULT_INDEX = 0;

    private LoadingLayout mLoadingLayout;
    private RefreshLayout mRefreshLayout;
    private BaseQuickAdapter<T, ? extends BaseViewHolder> mAdapter;
    private int mState = STATE_FIRST_LOAD;
    private int mPageIndex = DEFAULT_INDEX;
    private OnListLoadListener<T> mLoadListener;

    public ListLoaderF(LoadingLayout loadingLayout, RefreshLayout refreshLayout, BaseQuickAdapter<T, ? extends BaseViewHolder> adapter) {
        mLoadingLayout = loadingLayout;
        mRefreshLayout = refreshLayout;
        mAdapter = adapter;

        init();
    }

    private void init() {
        if (mRefreshLayout != null) {
            mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    mState = STATE_LOAD_MORE;
                    mPageIndex++;
                    startLoad();
                }

                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    mState = STATE_REFRESH;
                    mPageIndex = DEFAULT_INDEX;
                    startLoad();
                }
            });
        }
        if (mLoadingLayout != null) {
            mLoadingLayout.setRetryListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mState = STATE_FIRST_LOAD;
                    mPageIndex = DEFAULT_INDEX;
                    startLoad();
                }
            });
        }
    }

    private void startLoad() {
        if (mLoadListener != null) {
            mLoadListener.load(ListLoaderF.this, mPageIndex);
        }
    }

    /**
     * load
     *
     * @param observable Observable
     * @return Disposable
     */
    public Disposable load(Observable<BaseListResponse<T>> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseListResponse<T>>() {
                    @Override
                    public void accept(BaseListResponse<T> baseListResponse) throws Exception {
                        if (baseListResponse.isSuccess()) {
                            List<T> dataList = baseListResponse.getDataList();
                            if (dataList == null) {
                                noData();
                            } else {
                                hasData(dataList);
                            }
                        } else {
                            error(baseListResponse.getErrorMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        error("请检查网络连接");
                    }
                });
    }

    /**
     * Whether is refresh or first load.
     * @return true if the index is 0.
     */
    private boolean isRefresh() {
        return mPageIndex == DEFAULT_INDEX;
    }

    /**
     * load success,the return data is null.
     */
    private void noData() {
        if (isRefresh()) {
            mAdapter.setNewData(null);
            if (mState == STATE_REFRESH) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.setNoMoreData(true);
            }
            mLoadingLayout.showEmpty();
        } else {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    /**
     * load success,the return data is not null.
     *
     * @param data return data
     */
    private void hasData(List<T> data) {
        if (isRefresh()) {
            mAdapter.setNewData(data);
            if (mState == STATE_REFRESH) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.setNoMoreData(false);
            } else {
                mLoadingLayout.showContent();
            }
        } else {
            mAdapter.addData(data);
            mRefreshLayout.finishLoadMore();
        }
    }

    /**
     * load failed or net error.
     *
     * @param errorMsg error message
     */
    private void error(String errorMsg) {
        if (mState == STATE_FIRST_LOAD) {
            mAdapter.setNewData(null);
            mLoadingLayout.setErrorText(errorMsg);
            mLoadingLayout.showError();
        } else if (mState == STATE_REFRESH) {
            mRefreshLayout.finishRefresh(false);
            mRefreshLayout.setNoMoreData(false);
        } else {
            mRefreshLayout.finishLoadMore(false);
        }
    }

    /**
     * When there is no data,show empty text.
     * @param emptyText empty text
     */
    public void setEmptyText(String emptyText) {
        mLoadingLayout.setEmptyText(emptyText);
    }

    /**
     * When there is no data,show empty image.
     * @param emptyImage empty image res id
     */
    public void setEmptyImage(@DrawableRes int emptyImage) {
        mLoadingLayout.setEmptyImage(emptyImage);
    }

    /**
     * Set OnListLoadListener.
     * @param loadListener OnListLoadListener
     */
    public void setLoadListener(OnListLoadListener<T> loadListener) {
        mLoadListener = loadListener;
    }

    /**
     *
     * @param <T>
     */
    public interface OnListLoadListener<T> {
        /**
         * load data
         *
         * @param loaderF   ListLoaderF
         * @param pageIndex page indexF
         */
        void load(ListLoaderF<T> loaderF, int pageIndex);
    }
}
