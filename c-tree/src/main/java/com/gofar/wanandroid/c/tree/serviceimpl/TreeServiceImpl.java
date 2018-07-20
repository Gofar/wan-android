package com.gofar.wanandroid.c.tree.serviceimpl;

import android.support.v4.app.Fragment;

import com.gofar.componentservice.tree.TreeService;
import com.gofar.wanandroid.c.tree.ui.TreeFragment;

/**
 * @author lcf
 * @date 20/7/2018 下午 5:47
 * @since 1.0
 */
public class TreeServiceImpl implements TreeService {
    @Override
    public Fragment getTreeFragment() {
        return new TreeFragment();
    }
}
