package com.gofar.component.basiclib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.luojilab.component.componentlib.service.AutowiredService;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author lcf
 * @date 2018/6/28 15:35
 * @since 1.0
 */
public abstract class BaseActivity extends SupportActivity {
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AutowiredService.Factory.getInstance().create().autowire(this);
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

    /**
     * 是否使用EventBus
     *
     * @return True if use.
     */
    protected boolean useEventBus() {
        return false;
    }

    protected void addSubscribe(Disposable d) {
        mCompositeDisposable.add(d);
    }

    @Override
    protected void onDestroy() {
        mCompositeDisposable.dispose();
        if (useEventBus()){
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }
}
