package com.lanmei.peiyu.ui.mine.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.MineOrderAdapter;
import com.lanmei.peiyu.helper.TabSubHelper;
import com.xson.common.app.BaseActivity;
import com.xson.common.widget.CenterTitleToolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.InjectView;

/**
 * 我的订单
 */
public class MineOrderActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @InjectView(R.id.viewPager)
    ViewPager mViewPager;
    @InjectView(R.id.tabLayout)
    TabLayout mTabLayout;

    private TabSubHelper helper;

    @Override
    public int getContentViewId() {
        return R.layout.activity_mine_order;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("我的订单");
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        helper = new TabSubHelper(this,mTabLayout,getTitleList());
        helper.setOrderNum(0,6,0,0);

        mViewPager.setAdapter(new MineOrderAdapter(getSupportFragmentManager(),this));
        mViewPager.setOffscreenPageLimit(3);
//        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.addOnTabSelectedListener(this);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));;
//        mViewPager.setCurrentItem(getIntent().getIntExtra("type",0));
    }


    private List<String> getTitleList() {
        List<String> titles = new ArrayList<>();
        Collections.addAll(titles,getString(R.string.all),
                getString(R.string.wait_receiving),
                getString(R.string.wait_pay),
                getString(R.string.doned));
        return titles;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
