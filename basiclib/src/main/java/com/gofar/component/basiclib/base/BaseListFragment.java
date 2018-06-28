package com.gofar.component.basiclib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofar.component.basiclib.R;
import com.gofar.component.basiclib.entity.BaseListResponse;
import com.gofar.component.basiclib.list.BaseListLoader;
import com.gofar.component.basiclib.widget.RefreshRecycleView;
import com.gofar.titlebar.TitleBar;

import io.reactivex.Observable;

/**
 * @author lcf
 * @date 2018/6/28 11:02
 * @since 1.0
 */
public abstract class BaseListFragment<T> extends BaseFragment
        implements BaseQuickAdapter.OnItemClickListener {
    protected LinearLayout mRoot;
    protected TitleBar mToolbar;
    protected RefreshRecycleView mRefreshRecycleView;
    protected BaseListLoader<T> mBaseListLoader;
    protected BaseQuickAdapter<T, ? extends BaseViewHolder> mBaseAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.base_list_layout;
    }

    @Override
    protected void initView(View parent) {
        mRoot = parent.findViewById(R.id.root);
        mToolbar = parent.findViewById(R.id.titleBar);
        if (needToolbar()) {
            initToolbar(mToolbar);
        } else {
            mRoot.removeView(mToolbar);
        }
        mRefreshRecycleView = parent.findViewById(R.id.refreshRecyclerView);
        mRefreshRecycleView.setLayoutManager(getLayoutManager());
        mBaseAdapter = getAdapter();
        mBaseAdapter.setOnItemClickListener(this);
        mBaseListLoader = new BaseListLoader<T>(mCompositeDisposable, mRefreshRecycleView, mBaseAdapter, needRefresh(), needLoadMore()) {
            @Override
            protected Observable<BaseListResponse<T>> getObservable(int page) {
                return BaseListFragment.this.getObservable(page);
            }
        };
    }

    @Override
    protected void initData() {

    }

    protected void initToolbar(TitleBar toolbar) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (needLazyLoad()) {
            mBaseListLoader.autoRefresh();
        }
    }

    protected boolean needToolbar() {
        return true;
    }

    protected boolean needLazyLoad() {
        return true;
    }

    protected boolean needRefresh() {
        return true;
    }

    private boolean needLoadMore() {
        return true;
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(mContext);
    }

    /**
     * 创建适配器
     *
     * @return adapter extends BaseQuickAdapter.
     */
    protected abstract BaseQuickAdapter<T, ? extends BaseViewHolder> getAdapter();

    /**
     * 拿到网络请求的Observable
     *
     * @param page 页码
     * @return Observable
     */
    protected abstract Observable<BaseListResponse<T>> getObservable(int page);

}
