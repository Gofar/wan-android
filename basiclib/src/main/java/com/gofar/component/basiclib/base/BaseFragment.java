package com.gofar.component.basiclib.base;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author lcf
 * @date 2018/6/21 17:19
 * @since 1.0
 */
public abstract class BaseFragment extends SupportFragment {

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

}
