package com.gofar.wanandroid.c.tree.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gofar.component.basiclib.base.BaseFragment;
import com.gofar.component.basiclib.entity.BaseResponse;
import com.gofar.component.basiclib.network.Api;
import com.gofar.component.basiclib.widget.RefreshRecycleView;
import com.gofar.wanandroid.c.tree.R;
import com.gofar.wanandroid.c.tree.TreeApi;
import com.gofar.wanandroid.c.tree.entity.TreeEntity;
import com.gofar.wanandroid.c.tree.ui.adapter.TreeAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lcf
 * @date 20/7/2018 下午 5:44
 * @since 1.0
 */
public class TreeFragment extends BaseFragment {
    private RefreshRecycleView mRefreshRecycleView;

    private TreeAdapter mTreeAdapter;

    @Override
    protected void initialize() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tree;
    }

    @Override
    protected void initView(View parent) {
        mRefreshRecycleView = parent.findViewById(R.id.refresh_rv);
        mRefreshRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        mRefreshRecycleView.getRecyclerView().addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRefreshRecycleView.setEnableRefresh(true);
        mRefreshRecycleView.setEnableLoadMore(false);
        mRefreshRecycleView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getTree();
            }
        });
        mRefreshRecycleView.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTree();
            }
        });

        mTreeAdapter = new TreeAdapter(new ArrayList<TreeEntity>());
        mRefreshRecycleView.getRecyclerView().setAdapter(mTreeAdapter);
        mTreeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TreeEntity entity = mTreeAdapter.getData().get(position);
                Intent intent = new Intent(mContext,TreeDetailsActivity.class);
                intent.putExtra("data", entity);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefreshRecycleView.autoRefresh();
    }

    private void getTree() {
        mCompositeDisposable.add(Api.getInstance().build(TreeApi.class).tree()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<List<TreeEntity>>>() {
                    @Override
                    public void accept(BaseResponse<List<TreeEntity>> listBaseResponse) throws Exception {
                        boolean success = listBaseResponse.isSuccess();
                        mRefreshRecycleView.finishRefresh(success);
                        if (success) {
                            mTreeAdapter.setNewData(listBaseResponse.getData());
                        }
                        if (mTreeAdapter.getData().isEmpty()) {
                            mRefreshRecycleView.showEmpty();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        mRefreshRecycleView.finishRefresh(false);
                        if (mTreeAdapter.getData().isEmpty()) {
                            mRefreshRecycleView.showError("");
                        }
                    }
                }));
    }
}
