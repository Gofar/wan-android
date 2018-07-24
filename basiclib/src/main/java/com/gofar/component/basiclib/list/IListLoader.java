package com.gofar.component.basiclib.list;

import io.reactivex.Observable;

/**
 * @author lcf
 * @date 24/7/2018 下午 2:59
 * @since 1.0
 */
public interface IListLoader<T> {
    Observable<T> getObservable(int page);

    void load(int page);

    void refresh();

    void loadMore();

    void handleSuccess(T data);

    void handleFailed(String errorMsg);
}
