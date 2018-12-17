package com.lanmei.peiyu;

import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.lanmei.peiyu.adapter.MainPagerAdapter;
import com.lanmei.peiyu.helper.TabHelper;
import com.xson.common.app.BaseActivity;
import com.xson.common.widget.NoScrollViewPager;

import butterknife.InjectView;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.viewPager)
    NoScrollViewPager mViewPager;
    @InjectView(R.id.tabLayout)
    TabLayout mTabLayout;
    TabHelper tabHelper;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        mViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        mViewPager.setNoScroll(true);
        mViewPager.setOffscreenPageLimit(4);
        mTabLayout.setupWithViewPager(mViewPager);

        tabHelper = new TabHelper(this, mTabLayout);
        tabHelper.setOrderNum(10);
    }
}
