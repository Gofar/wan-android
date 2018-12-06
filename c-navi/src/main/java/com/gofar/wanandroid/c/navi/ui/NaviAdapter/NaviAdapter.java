package com.gofar.wanandroid.c.navi.ui.NaviAdapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gofar.component.basiclib.base.BaseWebActivity;
import com.gofar.wanandroid.c.navi.R;
import com.gofar.wanandroid.c.navi.entity.NaviArticleEntity;

import java.util.List;

/**
 * @author lcf
 * @date 6/12/2018 上午 11:48
 * @since 1.0
 */
public class NaviAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_NAVI = 1;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public NaviAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_navi);
        addItemType(TYPE_NAVI, R.layout.item_navi_item);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                final NaviItem naviItem = (NaviItem) item;
                helper.setText(R.id.tv_navi, naviItem.getNaviItemEntity().getName())
                        .setImageResource(R.id.iv_arrow, naviItem.isExpanded() ? R.drawable.arrow_b : R.drawable.arrow_r);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (naviItem.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case TYPE_NAVI:
                final Navi navi = (Navi) item;
                helper.setText(R.id.tv_navi_item, navi.getNaviArticleEntity().getTitle());
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NaviArticleEntity entity = navi.getNaviArticleEntity();
                        Intent intent = new Intent(mContext, BaseWebActivity.class);
                        intent.putExtra("url", entity.getLink());
                        intent.putExtra("title", entity.getTitle());
                        mContext.startActivity(intent);
                    }
                });
                break;
            default:
                break;
        }
    }
}
