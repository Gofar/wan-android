package com.gofar.component.basiclib.base;

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

    @Override
    protected int getLayoutId() {
        return R.layout.base_compat;
    }

    @Override
    protected void initView() {
        mRoot = findViewById(R.id.root);
        mToolbar = findViewById(R.id.title_Bar);
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

    protected abstract void initContentView(View content);

    /**
     * 配置ToolBar
     *
     * @param toolbar ToolBar
     */
    protected void initToolBar(TitleBar toolbar) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
