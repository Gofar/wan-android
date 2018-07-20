package com.gofar.component.basiclib.base;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.gofar.component.basiclib.R;
import com.gofar.titlebar.TitleBar;

/**
 * @author lcf
 * @date 2018/6/28 16:18
 * @since 1.0
 */
public abstract class BaseCompatActivity extends BaseActivity {
    protected LinearLayout mRoot;
    protected TitleBar mToolbar;
    protected View mBarDivider;

    @Override
    protected int getLayoutId() {
        return R.layout.base_compat;
    }

    @Override
    protected void initView() {
        mRoot = findViewById(R.id.root);
        mToolbar = findViewById(R.id.title_Bar);
        mBarDivider = findViewById(R.id.bar_divider);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initToolBar(mToolbar);
        FrameLayout mLayoutContent = findViewById(R.id.layout_content);
        View customView = LayoutInflater.from(this).inflate(getContentLayoutId(), null);
        if (customView != null) {
            mLayoutContent.addView(customView);
            initContentView(customView);
        }
    }

    /**
     * 内容布局id
     *
     * @return
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化内容view
     *
     * @param content
     */
    protected abstract void initContentView(View content);

    /**
     * 配置ToolBar
     *
     * @param toolbar ToolBar
     */
    protected void initToolBar(TitleBar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
        toolbar.setCenterTitleColor(Color.BLACK);
    }
}
