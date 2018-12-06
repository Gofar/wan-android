package com.gofar.wanandroid.c.navi.serviceimpl;

import android.support.v4.app.Fragment;

import com.gofar.componentservice.navigation.NaviService;
import com.gofar.wanandroid.c.navi.ui.NaviFragment;

/**
 * @author lcf
 * @date 6/12/2018 下午 3:05
 * @since 1.0
 */
public class NaviServiceImpl implements NaviService {
    @Override
    public Fragment getNaviFragment() {
        return NaviFragment.newInstance();
    }
}
