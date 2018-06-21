package com.gofar.component.basiclib;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofar.component.basiclib.entity.BaseListResponse;
import com.gofar.component.basiclib.list.ListLoader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import ezy.ui.layout.LoadingLayout;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author lcf
 * @date 2018/6/7 17:06
 * @since 1.0
 */
public abstract class BaseListFragment<T> extends SupportFragment {
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private RefreshLayout mRefreshLayout;
    private LoadingLayout mLoadingLayout;
    private RecyclerView mRecyclerView;

    private ListLoader<T> mListLoader;
    private BaseQuickAdapter<T, ? extends BaseViewHolder> mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_list, container, false);
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mLoadingLayout = view.findViewById(R.id.loadingLayout);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        initView();
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mListLoader.loadFirst();
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mListLoader = new ListLoader<T>(mLoadingLayout, mRefreshLayout, mAdapter, mCompositeDisposable) {
            @Override
            protected Observable<BaseListResponse<T>> getObservable(int pageIndex) {
                return getData(pageIndex);
            }
        };
    }

    /**
     * @param pageIndex
     * @return
     */
    protected abstract Observable<BaseListResponse<T>> getData(int pageIndex);

}
