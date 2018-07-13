package com.gofar.wanandroid.c.home.ui;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofar.component.basiclib.base.BaseCompatListFragment;
import com.gofar.component.basiclib.base.WebActivity;
import com.gofar.component.basiclib.entity.BaseListResponse;
import com.gofar.component.basiclib.entity.BaseResponse;
import com.gofar.component.basiclib.image.BannerImageLoader;
import com.gofar.component.basiclib.list.BaseListLoader;
import com.gofar.component.basiclib.network.Api;
import com.gofar.wanandroid.c.home.HomeApi;
import com.gofar.wanandroid.c.home.R;
import com.gofar.wanandroid.c.home.entity.BannerEntity;
import com.gofar.wanandroid.c.home.entity.FeedArticleEntity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lcf
 * @date 2018/6/28 17:28
 * @since 1.0
 */
public class HomeFragment extends BaseCompatListFragment<FeedArticleEntity> {
    private HomeItemAdapter mHomeItemAdapter;
    private Banner mBanner;

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        FeedArticleEntity entity = mHomeItemAdapter.getData().get(position);
        Intent intent = new Intent(mContext, WebActivity.class);
        intent.putExtra("url", entity.getApkLink());
        startActivity(intent);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean needToolbar() {
        return false;
    }

    @Override
    public BaseQuickAdapter<FeedArticleEntity, ? extends BaseViewHolder> getAdapter() {
        mRefreshRecycleView.getRecyclerView().addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mHomeItemAdapter = new HomeItemAdapter();
        mHomeItemAdapter.addHeaderView(getHeader());
        return mHomeItemAdapter;
    }

    @Override
    public Observable<BaseListResponse<FeedArticleEntity>> getObservable(int page) {
        return Api.getInstance().build(HomeApi.class).homeList(page);
    }

    @Override
    protected BaseListLoader<FeedArticleEntity> getBaseListLoader() {
        return new BaseListLoader<FeedArticleEntity>(mCompositeDisposable, mRefreshRecycleView, mBaseAdapter, needRefresh(), needLoadMore()) {
            @Override
            protected Observable<BaseListResponse<FeedArticleEntity>> getObservable(int page) {
                return HomeFragment.this.getObservable(page);
            }

            @Override
            public void refresh() {
                super.refresh();
                loadBanner();
            }
        };
    }

    private View getHeader() {
        View header = LayoutInflater.from(mContext).inflate(R.layout.layout_banner, null);
        mBanner = header.findViewById(R.id.banner);
        return header;
    }

    private void loadBanner() {
        mCompositeDisposable.add(Api.getInstance().build(HomeApi.class).banner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<List<BannerEntity>>>() {
                    @Override
                    public void accept(BaseResponse<List<BannerEntity>> listBaseResponse) throws Exception {
                        if (listBaseResponse.isSuccess()) {
                            showBannerData(listBaseResponse.getData());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));
    }

    private void showBannerData(List<BannerEntity> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        List<String> bannerTitles = new ArrayList<>();
        final List<String> bannerUrls = new ArrayList<>();
        List<String> bannerImageUrls = new ArrayList<>();
        for (BannerEntity entity : list) {
            bannerTitles.add(entity.getTitle());
            bannerUrls.add(entity.getUrl());
            bannerImageUrls.add(entity.getImagePath());
        }
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(new BannerImageLoader());
        //设置图片集合
        mBanner.setImages(bannerImageUrls);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(bannerTitles);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(list.size() * 400);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("url", bannerUrls.get(position));
                startActivity(intent);
            }
        });
        mBanner.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBanner != null) {
            mBanner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }
}
