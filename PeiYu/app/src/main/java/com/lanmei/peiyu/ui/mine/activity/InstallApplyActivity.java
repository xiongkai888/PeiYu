package com.lanmei.peiyu.ui.mine.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.InstallApplyAdapter;
import com.lanmei.peiyu.helper.TabSubHelper;
import com.xson.common.app.BaseActivity;
import com.xson.common.utils.IntentUtil;
import com.xson.common.widget.CenterTitleToolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.InjectView;

/**
 * 安装申请
 */
public class InstallApplyActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {

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
        actionbar.setTitle(R.string.install_apply);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        helper = new TabSubHelper(this,mTabLayout,getTitleList());
        helper.setOrderNum(0,6,0,0,0);

        mViewPager.setAdapter(new InstallApplyAdapter(getSupportFragmentManager()));
//        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.addOnTabSelectedListener(this);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                IntentUtil.startActivity(this,ApplyInstallActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<String> getTitleList() {
        List<String> titles = new ArrayList<>();
        Collections.addAll(titles,getString(R.string.all),
                "施工申报",
                "设备发货",
                "施工安装",
                "竣工验收");
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
