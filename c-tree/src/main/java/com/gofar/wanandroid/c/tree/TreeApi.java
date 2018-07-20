package com.gofar.wanandroid.c.tree;

import com.gofar.component.basiclib.entity.BaseResponse;
import com.gofar.wanandroid.c.tree.entity.TreeEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author lcf
 * @date 20/7/2018 下午 5:33
 * @since 1.0
 */
public interface TreeApi {
    /**
     * 体系数据
     * @return
     */
    @GET("tree/json")
    Observable<BaseResponse<List<TreeEntity>>> tree();

    /**
     * 知识体系下的文字列表
     *
     * @param cid  体系
     * @param page 页码，从0开始
     */
    @GET("article/list/{page}/json?cid={cid}")
    Observable<BaseResponse> treeList(@Path("page") int page, @Path("cid") int cid);
}
