package com.gofar.component.basiclib.base;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofar.component.basiclib.entity.BaseListResponse;

import io.reactivex.Observable;

/**
 * @author lcf
 * @date 2018/6/28 16:55
 * @since 1.0
 */
public interface IListLoader<T> {
    /**
     * 是否需要下拉刷新
     *
     * @return True if need.
     */
    boolean needRefresh();

    /**
     * 是否需要加载更多
     *
     * @return True if need.
     */
    boolean needLoadMore();

    /**
     * RecyclerView LayoutManager
     *
     * @return LayoutManager
     */
    RecyclerView.LayoutManager getLayoutManager();


    /**
     * 创建适配器
     *
     * @return adapter extends BaseQuickAdapter.
     */
    BaseQuickAdapter<T, ? extends BaseViewHolder> getAdapter();

    /**
     * 拿到网络请求的Observable
     *
     * @param page 页码
     * @return Observable
     */
    Observable<BaseListResponse<T>> getObservable(int page);
}
