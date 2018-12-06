package com.gofar.wanandroid;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;

import com.gofar.component.basiclib.base.BaseCompatActivity;
import com.gofar.componentservice.home.HomeService;
import com.gofar.componentservice.navigation.NaviService;
import com.gofar.componentservice.tree.TreeService;
import com.gofar.titlebar.TitleBar;
import com.gofar.wanandroid.utils.BottomNavigationViewHelper;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.router.facade.annotation.RouteNode;

import java.util.ArrayList;
import java.util.List;

@RouteNode(path = "/main", desc = "主页")
public class MainActivity extends BaseCompatActivity {

    private List<Fragment> mFragments;
    private int mLastIndex = -1;

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
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab_main_home:
                        mToolbar.setCenterTitle("首页");
                        switchFragment(0);
                        break;
                    case R.id.tab_main_tree:
                        mToolbar.setCenterTitle("知识体系");
                        switchFragment(1);
                        break;
                    case R.id.tab_main_navigation:
                        mToolbar.setCenterTitle("导航");
                        switchFragment(2);
                        break;
                    case R.id.tab_main_project:
                        mToolbar.setCenterTitle("项目");
                        switchFragment(3);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        mFragments = new ArrayList<>();
        mFragments.add(getHomeFragment());
        mFragments.add(getTreeFragment());
        mFragments.add(getNaviFragment());
        mFragments.add(getHomeFragment());

        switchFragment(0);
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initToolBar(TitleBar toolbar) {
        toolbar.setCenterTitle("首页");
        toolbar.setCenterTitleColor(Color.BLACK);
    }

    private Fragment getHomeFragment() {
        Router router = Router.getInstance();
        if (router.getService(HomeService.class.getSimpleName()) != null) {
            HomeService service = (HomeService) router.getService(HomeService.class.getSimpleName());
            return service.getHomeFragment();
        }
        return null;
    }

    private Fragment getTreeFragment() {
        Router router = Router.getInstance();
        if (router.getService(TreeService.class.getSimpleName()) != null) {
            TreeService service = (TreeService) router.getService(TreeService.class.getSimpleName());
            return service.getTreeFragment();
        }
        return null;
    }

    private Fragment getNaviFragment(){
        Router router=Router.getInstance();
        if (router.getService(NaviService.class.getSimpleName())!=null){
            NaviService service= (NaviService) router.getService(NaviService.class.getSimpleName());
            return service.getNaviFragment();
        }
        return null;
    }

    private void switchFragment(int position) {
        if (position == mLastIndex) {
            return;
        }
        if (position > mFragments.size()) {
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mLastIndex != -1) {
            ft.hide(mFragments.get(mLastIndex));
        }
        Fragment newFragment = mFragments.get(position);
        if (!newFragment.isAdded()) {
            ft.add(R.id.fl_content, newFragment);
        } else {
            ft.show(mFragments.get(position));
        }
        mLastIndex = position;
        ft.commit();
    }
}
