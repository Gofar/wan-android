package com.gofar.component.basiclib.list;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lcf
 * @date 24/7/2018 下午 2:51
 * @since 1.0
 */
public abstract class AbstractListLoader<T> implements IListLoader<T> {

    /**
     * 默认分页起始页码
     */
    public static final int DEFAULT_START = 0;

    private CompositeDisposable mCompositeDisposable;
    private int mPage = DEFAULT_START;

    @Override
    public void load(int page) {
        mCompositeDisposable.add(getObservable(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(T t) throws Exception {
                        handleSuccess(t);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        handleFailed("");
                    }
                }));
    }

    @Override
    public void refresh() {
        mPage = DEFAULT_START;
        load(mPage);
    }

    @Override
    public void loadMore() {
        mPage++;
        load(mPage);
    }

    public boolean isRefresh() {
        return mPage == DEFAULT_START;
    }
}
