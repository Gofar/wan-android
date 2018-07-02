package com.gofar.wanandroid.c.home.serviceimpl;

import android.support.v4.app.Fragment;

import com.gofar.componentservice.home.HomeService;
import com.gofar.wanandroid.c.home.ui.HomeFragment;

/**
 * @author lcf
 * @date 2018/7/2 17:00
 * @since 1.0
 */
public class HomeServiceImpl implements HomeService {
    @Override
    public Fragment getHomeFragment() {
        return new HomeFragment();
    }
}
