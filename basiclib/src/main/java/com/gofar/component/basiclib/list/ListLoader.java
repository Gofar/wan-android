package com.gofar.component.basiclib.list;

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
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lcf
 * @date 2018/6/7 16:54
 * @since 1.0
 */
public abstract class ListLoader<T> {
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
    private CompositeDisposable mCompositeDisposable;
    private int mState = STATE_FIRST_LOAD;
    private int mPageIndex = DEFAULT_INDEX;

    public ListLoader(LoadingLayout loadingLayout, BaseQuickAdapter<T, ? extends BaseViewHolder> adapter, CompositeDisposable compositeDisposable) {
       this(loadingLayout,null,adapter,compositeDisposable);
    }

    public ListLoader(LoadingLayout loadingLayout, RefreshLayout refreshLayout
            , BaseQuickAdapter<T, ? extends BaseViewHolder> adapter
            , CompositeDisposable compositeDisposable) {
        mLoadingLayout = loadingLayout;
        mRefreshLayout = refreshLayout;
        mAdapter = adapter;
        mCompositeDisposable = compositeDisposable;

        init();
    }

    private void init() {
        if (mRefreshLayout != null) {
            mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    mState = STATE_LOAD_MORE;
                    mPageIndex++;
                    load(getObservable(mPageIndex));
                }

                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    mState = STATE_REFRESH;
                    mPageIndex = DEFAULT_INDEX;
                    load(getObservable(mPageIndex));
                }
            });
        }
        if (mLoadingLayout != null) {
            mLoadingLayout.setRetryListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mState = STATE_FIRST_LOAD;
                    mPageIndex = DEFAULT_INDEX;
                    load(getObservable(mPageIndex));
                }
            });
        }
    }

    /**
     * Return Observable
     * @param pageIndex page index
     * @return Observable
     */
    protected abstract Observable<BaseListResponse<T>> getObservable(int pageIndex);

    public void loadFirst(){
        mState = STATE_FIRST_LOAD;
        mPageIndex = DEFAULT_INDEX;
        load(getObservable(mPageIndex));
    }
    /**
     * load
     *
     * @param observable Observable
     * @return Disposable
     */
    public void load(Observable<BaseListResponse<T>> observable) {
        mCompositeDisposable.add(observable.subscribeOn(Schedulers.io())
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
                }));
    }

    /**
     * Whether is refresh or first load.
     *
     * @return true if the index is 0.
     */
    private boolean isFirst() {
        return mPageIndex == DEFAULT_INDEX;
    }

    /**
     * load success,the return data is null.
     */
    private void noData() {
        if (isFirst()) {
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
        if (isFirst()) {
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
     *
     * @param emptyText empty text
     */
    public void setEmptyText(String emptyText) {
        mLoadingLayout.setEmptyText(emptyText);
    }

    /**
     * When there is no data,show empty image.
     *
     * @param emptyImage empty image res id
     */
    public void setEmptyImage(@DrawableRes int emptyImage) {
        mLoadingLayout.setEmptyImage(emptyImage);
    }
}
