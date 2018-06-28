package com.gofar.component.basiclib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author lcf
 * @date 2018/6/28 15:35
 * @since 1.0
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        setContentView(getLayoutId());
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        initView();
        initData();
    }

    /**
     * 做些初始化动作
     */
    protected abstract void initialize();

    /**
     * 设置布局文件
     *
     * @return LayoutRes
     */
    protected abstract int getLayoutId();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    protected boolean useEventBus() {
        return true;
    }
}
