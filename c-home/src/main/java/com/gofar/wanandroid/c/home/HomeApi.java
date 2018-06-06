package com.gofar.wanandroid.c.home;

import com.gofar.component.basiclib.entity.BaseResponse;

import io.reactivex.Observable;

/**
 * @author lcf
 * @date 2018/6/6 10:43
 * @since 1.0
 */
public interface HomeApi {

    Observable<BaseResponse> homeList();
}