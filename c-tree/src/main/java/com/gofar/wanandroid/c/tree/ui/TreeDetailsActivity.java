package com.gofar.wanandroid.c.tree.ui;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gofar.component.basiclib.base.BaseCompatActivity;
import com.gofar.titlebar.TitleBar;
import com.gofar.wanandroid.c.tree.R;
import com.gofar.wanandroid.c.tree.entity.TreeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lcf
 * @date 27/7/2018 下午 4:34
 * @since 1.0
 */
public class TreeDetailsActivity extends BaseCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mVp;

    private String mTreeName;
    private List<TreeEntity> mTreeList;
    private List<Fragment> mFragments;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_tree_details;
    }

    @Override
    protected void initContentView(View content) {
        mTabLayout = content.findViewById(R.id.tab_layout);
        mVp = content.findViewById(R.id.vp);
    }

    @Override
    protected void initialize() {
        TreeEntity treeEntity = getIntent().getParcelableExtra("data");
        if (treeEntity != null) {
            mTreeName = treeEntity.getName();
            mTreeList = treeEntity.getChildren();
        }
    }

    @Override
    protected void initData() {
        mFragments = new ArrayList<>();
        if (mTreeList != null && !mTreeList.isEmpty()) {
            for (TreeEntity entity : mTreeList) {
                mFragments.add(TreeListFragment.newInstance(entity.getId()));
            }
        }
        mVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTreeList.get(position).getName();
            }
        });

        mTabLayout.setupWithViewPager(mVp);
    }

    @Override
    protected void initToolBar(TitleBar toolbar) {
        super.initToolBar(toolbar);
        toolbar.setCenterTitle(mTreeName);
    }
}
