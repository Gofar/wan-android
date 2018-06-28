package com.gofar.component.basiclib.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofar.component.basiclib.R;
import com.gofar.component.basiclib.entity.BaseListResponse;
import com.gofar.component.basiclib.list.BaseListLoader;
import com.gofar.component.basiclib.widget.RefreshRecycleView;

import io.reactivex.Observable;

/**
 * @author lcf
 * @date 2018/6/28 16:20
 * @since 1.0
 */
public abstract class BaseCompatListActivity<T> extends BaseCompatActivity
        implements BaseQuickAdapter.OnItemClickListener, IListLoader<T> {

    protected RefreshRecycleView mRefreshRecycleView;
    protected BaseListLoader<T> mBaseListLoader;
    protected BaseQuickAdapter<T, ? extends BaseViewHolder> mBaseAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.base_recycler_list;
    }

    @Override
    protected void initView() {
        super.initView();
        mRefreshRecycleView = findViewById(R.id.refresh_rv);
        mRefreshRecycleView.setLayoutManager(getLayoutManager());
        mBaseAdapter = getAdapter();
        mBaseAdapter.setOnItemClickListener(this);
        mBaseListLoader = new BaseListLoader<T>(mCompositeDisposable, mRefreshRecycleView, mBaseAdapter, needRefresh(), needLoadMore()) {
            @Override
            protected Observable<BaseListResponse<T>> getObservable(int page) {
                return BaseCompatListActivity.this.getObservable(page);
            }
        };
    }

    @Override
    protected void initData() {
        mBaseListLoader.autoRefresh();
    }

    @Override
    public boolean needRefresh() {
        return true;
    }

    @Override
    public boolean needLoadMore() {
        return true;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }
}
