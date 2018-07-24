package com.gofar.wanandroid.c.tree.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofar.wanandroid.c.tree.R;
import com.gofar.wanandroid.c.tree.entity.TreeEntity;

import java.util.List;

/**
 * @author lcf
 * @date 24/7/2018 上午 11:34
 * @since 1.0
 */
public class TreeAdapter extends BaseQuickAdapter<TreeEntity, BaseViewHolder> {
    public TreeAdapter(@Nullable List<TreeEntity> data) {
        super(R.layout.item_tree, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TreeEntity item) {
        helper.setText(R.id.tv_title, item.getName());
        List<TreeEntity> children = item.getChildren();
        if (children != null && !children.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (TreeEntity child : children) {
                sb.append(child.getName()).append("  ");
            }
            helper.setText(R.id.tv_content, sb.toString());
        }
    }
}
