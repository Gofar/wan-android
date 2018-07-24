package com.gofar.wanandroid.c.home;

import com.gofar.component.basiclib.entity.BaseListResponse;
import com.gofar.component.basiclib.entity.BaseResponse;
import com.gofar.wanandroid.c.home.entity.BannerEntity;
import com.gofar.basicres.entity.FeedArticleEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author lcf
 * @date 2018/6/6 10:43
 * @since 1.0
 */
public interface HomeApi {

    /**
     * 首页list
     *
     * @param page 页码，从0开始
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<BaseListResponse<FeedArticleEntity>> feedList(@Path("page") int page);

    /**
     * 首页Banner
     *
     * @return
     */
    @GET("banner/json")
    Observable<BaseResponse<List<BannerEntity>>> banner();

    /**
     * 常用网站
     *
     * @return
     */
    @GET("friend/json")
    Observable<BaseResponse> friendWebsite();

    /**
     * 搜索热词
     *
     * @return
     */
    @GET("hotkey/json")
    Observable<BaseResponse> hotkey();
}