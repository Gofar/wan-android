package com.gofar.wanandroid;

import android.support.design.widget.BottomNavigationView;
import android.view.View;

import com.gofar.component.basiclib.base.BaseCompatActivity;
import com.gofar.titlebar.TitleBar;
import com.gofar.wanandroid.utils.BottomNavigationViewHelper;
import com.luojilab.router.facade.annotation.RouteNode;

@RouteNode(path = "/main", desc = "主页")
public class MainActivity extends BaseCompatActivity {

    @Override
    protected void initialize() {

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initContentView(View content) {
        BottomNavigationView bottomNavigationView = content.findViewById(R.id.bottom_navigation_view);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initToolBar(TitleBar toolbar) {
        toolbar.setCenterTitle("首页");
    }
}
