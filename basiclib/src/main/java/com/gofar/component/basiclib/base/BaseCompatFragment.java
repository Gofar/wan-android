package com.gofar.component.basiclib.base;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.gofar.component.basiclib.R;
import com.gofar.titlebar.TitleBar;

/**
 * @author lcf
 * @date 2018/6/28 14:48
 * @since 1.0
 */
public abstract class BaseCompatFragment extends BaseFragment {
    protected LinearLayout mRoot;
    protected TitleBar mToolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.base_compat;
    }

    @Override
    protected void initView(View parent) {
        mRoot = parent.findViewById(R.id.root);
        mToolbar = parent.findViewById(R.id.title_Bar);
        initToolBar(mToolbar);
        FrameLayout mLayoutContent = parent.findViewById(R.id.layout_content);
        View customView = LayoutInflater.from(mContext).inflate(getContentLayoutId(), null);
        if (customView != null) {
            mLayoutContent.addView(mLayoutContent);
        }
    }

    /**
     * 内容布局id
     *
     * @return
     */
    protected abstract int getContentLayoutId();

    /**
     * 配置ToolBar
     *
     * @param toolbar ToolBar
     */
    protected abstract void initToolBar(TitleBar toolbar);
}
