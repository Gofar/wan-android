package com.gofar.wanandroid.c.home;

import com.gofar.component.basiclib.entity.BaseListResponse;
import com.gofar.wanandroid.c.home.entity.HomeItemEntity;

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
     * @param page
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<BaseListResponse<HomeItemEntity>> homeList(@Path("page") int page);
}