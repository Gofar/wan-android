package com.gofar.component.basiclib.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.CompositeDisposable;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author lcf
 * @date 2018/6/21 17:19
 * @since 1.0
 */
public abstract class BaseFragment extends SupportFragment {

    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    protected Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (useEventBus()){
            if (!EventBus.getDefault().isRegistered(this)){
                EventBus.getDefault().register(this);
            }
        }
        initView(view);
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
     *
     * @param parent
     */
    protected abstract void initView(View parent);

    /**
     * 初始化数据
     */
    protected abstract void initData();

    protected boolean useEventBus(){
        return true;
    }

    @Override
    public void onDestroy() {
        if (useEventBus()){
            EventBus.getDefault().unregister(this);
        }
        mCompositeDisposable.dispose();
        super.onDestroy();
    }
}
