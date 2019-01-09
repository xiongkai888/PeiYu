package com.lanmei.peiyu.ui.mine.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.MyTeamAdapter;
import com.xson.common.app.BaseActivity;
import com.xson.common.widget.CenterTitleToolbar;

import butterknife.InjectView;


/**
 * 我的团队
 */
public class MyTeamActivity extends BaseActivity {


    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.viewPager)
    ViewPager mViewPager;
    @InjectView(R.id.tabLayout)
    TabLayout mTabLayout;

    @Override
    public int getContentViewId() {
        return R.layout.activity_mine_order;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.my_team);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        mViewPager.setAdapter(new MyTeamAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

}
