package com.lanmei.peiyu.ui.mine.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.ClubAdapter;
import com.xson.common.app.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 会员卡（消费明细、充值记录）
 */
public class ClubActivity extends BaseActivity {

//    public static boolean no_bound_card = true;//只弹框一次（是否绑定银行卡）

    @InjectView(R.id.tabLayout)
    TabLayout mTabLayout;
    @InjectView(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    public int getContentViewId() {
        return R.layout.activity_club;
    }


    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        mViewPager.setAdapter(new ClubAdapter(getSupportFragmentManager()));
//        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        ClubActivity.no_bound_card = true;
//    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        onBackPressed();
    }
}
