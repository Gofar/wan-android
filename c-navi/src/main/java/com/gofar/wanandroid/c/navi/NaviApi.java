package com.gofar.wanandroid.c.navi;

import com.gofar.component.basiclib.entity.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author lcf
 * @date 27/7/2018 下午 5:49
 * @since 1.0
 */
public interface NaviApi {

    @GET("navi/json")
    Observable<BaseResponse> navi();
}
