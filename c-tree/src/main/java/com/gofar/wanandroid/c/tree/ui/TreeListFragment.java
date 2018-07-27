package com.gofar.wanandroid.c.tree.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofar.basicres.adapter.FeedItemAdapter;
import com.gofar.basicres.entity.FeedArticleEntity;
import com.gofar.component.basiclib.base.BaseListFragment;
import com.gofar.component.basiclib.base.BaseWebActivity;
import com.gofar.component.basiclib.entity.BaseListResponse;
import com.gofar.component.basiclib.network.Api;
import com.gofar.wanandroid.c.tree.TreeApi;

import io.reactivex.Observable;

/**
 * @author lcf
 * @date 27/7/2018 下午 4:56
 * @since 1.0
 */
public class TreeListFragment extends BaseListFragment<FeedArticleEntity> {
    private int mCid;
    private FeedItemAdapter mFeedItemAdapter;

    public static TreeListFragment newInstance(int cid) {
        Bundle args = new Bundle();
        args.putInt("cid", cid);
        TreeListFragment fragment = new TreeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BaseQuickAdapter<FeedArticleEntity, ? extends BaseViewHolder> getAdapter() {
        mRefreshRecycleView.getRecyclerView().addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mFeedItemAdapter = new FeedItemAdapter();
        return mFeedItemAdapter;
    }

    @Override
    protected Observable<BaseListResponse<FeedArticleEntity>> getObservable(int page) {
        return Api.getInstance().build(TreeApi.class).treeList(page, mCid);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        FeedArticleEntity entity=mFeedItemAdapter.getData().get(position);
        Intent intent=new Intent(mContext, BaseWebActivity.class);
        intent.putExtra("url", entity.getLink());
        intent.putExtra("title", entity.getTitle());
        startActivity(intent);
    }

    @Override
    protected void initialize() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCid = bundle.getInt("cid", 0);
        }
    }

    @Override
    protected boolean needToolbar() {
        return false;
    }
}
