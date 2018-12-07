package com.gofar.wanandroid.c.navi.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gofar.component.basiclib.base.BaseFragment;
import com.gofar.component.basiclib.entity.BaseResponse;
import com.gofar.component.basiclib.network.Api;
import com.gofar.component.basiclib.widget.RefreshRecycleView;
import com.gofar.wanandroid.c.navi.NaviApi;
import com.gofar.wanandroid.c.navi.R;
import com.gofar.wanandroid.c.navi.entity.NaviArticleEntity;
import com.gofar.wanandroid.c.navi.entity.NaviItemEntity;
import com.gofar.wanandroid.c.navi.ui.NaviAdapter.Navi;
import com.gofar.wanandroid.c.navi.ui.NaviAdapter.NaviAdapter;
import com.gofar.wanandroid.c.navi.ui.NaviAdapter.NaviItem;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lcf
 * @date 6/12/2018 上午 11:35
 * @since 1.0
 */
public class NaviFragment extends BaseFragment {
    private RefreshRecycleView mRvNavi;

    private NaviAdapter mAdapter;

    public static NaviFragment newInstance() {
        Bundle args = new Bundle();
        NaviFragment fragment = new NaviFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navi;
    }

    @Override
    protected void initView(View parent) {
        mRvNavi = parent.findViewById(R.id.rv_navi);
        mRvNavi.setEnableRefresh(true);
        mRvNavi.setEnableLoadMore(false);
        mRvNavi.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getNavi();
            }
        });
        mRvNavi.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNavi();
            }
        });

        mAdapter = new NaviAdapter(new ArrayList<MultiItemEntity>());
        mRvNavi.setAdapter(mAdapter);
        mRvNavi.setLayoutManager(new LinearLayoutManager(mContext));
        mRvNavi.getRecyclerView().addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRvNavi.autoRefresh();
    }

    private List<MultiItemEntity> generate(List<NaviItemEntity> data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        List<MultiItemEntity> list = new ArrayList<>();
        for (NaviItemEntity itemEntity : data) {
            NaviItem naviItem = new NaviItem(itemEntity);
            List<NaviArticleEntity> articleEntityList = itemEntity.getArticles();
            if (articleEntityList != null) {
                for (NaviArticleEntity articleEntity : articleEntityList) {
                    Navi navi = new Navi(articleEntity);
                    naviItem.addSubItem(navi);
                }
            }
            list.add(naviItem);
        }
        return list;
    }

    private void getNavi(){
        mCompositeDisposable.add(Api.getInstance().build(NaviApi.class).navi()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<BaseResponse<List<NaviItemEntity>>>() {
            @Override
            public void accept(BaseResponse<List<NaviItemEntity>> listBaseResponse) throws Exception {
                boolean success = listBaseResponse.isSuccess();
                mRvNavi.finishRefresh(success);
                if (success){
                    mAdapter.setNewData(generate(listBaseResponse.getData()));
                    mAdapter.expandAll();
                }
                if (mAdapter.getData().isEmpty()){
                    mRvNavi.showEmpty();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
                mRvNavi.finishRefresh(false);
                if (mAdapter.getData().isEmpty()){
                    mRvNavi.showError("网络异常");
                }
            }
        }));
    }
}
