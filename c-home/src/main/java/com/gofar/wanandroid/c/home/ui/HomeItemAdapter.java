package com.gofar.wanandroid.c.home.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofar.component.basiclib.image.glide.GlideImageLoader;
import com.gofar.wanandroid.c.home.R;
import com.gofar.wanandroid.c.home.entity.FeedArticleEntity;

/**
 * @author lcf
 * @date 2018/6/28 17:37
 * @since 1.0
 */
public class HomeItemAdapter extends BaseQuickAdapter<FeedArticleEntity, BaseViewHolder> {
    public HomeItemAdapter() {
        super(R.layout.item_feed_article_data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FeedArticleEntity item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_author, item.getAuthor())
                .setText(R.id.tv_date, item.getNiceDate());
        ImageView imageView = helper.getView(R.id.iv_pic);
        String url = item.getEnvelopePic();
        if (!TextUtils.isEmpty(url)) {
            imageView.setVisibility(View.VISIBLE);
            GlideImageLoader.load(mContext, url, imageView, R.drawable.place_holder);
        } else {
            imageView.setImageResource(0);
            imageView.setVisibility(View.GONE);
        }
    }
}
