package com.gofar.wanandroid.c.navi.entity;

import java.util.List;

/**
 * @author lcf
 * @date 6/12/2018 上午 11:29
 * @since 1.0
 */
public class NaviItemEntity {

    /**
     * articles:Array[22],
     * cid : 272
     * name : 常用网站
     */

    private List<NaviArticleEntity> articles;
    private int cid;
    private String name;

    public List<NaviArticleEntity> getArticles() {
        return articles;
    }

    public void setArticles(List<NaviArticleEntity> articles) {
        this.articles = articles;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
