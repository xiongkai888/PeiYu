package com.lanmei.peiyu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.lanmei.peiyu.adapter.MainPagerAdapter;
import com.lanmei.peiyu.event.LoginQuitEvent;
import com.lanmei.peiyu.helper.TabHelper;
import com.lanmei.peiyu.ui.shopping.shop.DBShopCartHelper;
import com.lanmei.peiyu.ui.shopping.shop.ShowShopCountEvent;
import com.xson.common.app.BaseActivity;
import com.xson.common.widget.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
        EventBus.getDefault().register(this);
        mViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        mViewPager.setNoScroll(true);
        mViewPager.setOffscreenPageLimit(4);
        mTabLayout.setupWithViewPager(mViewPager);

        tabHelper = new TabHelper(this, mTabLayout);
    }

    public void setOrderNum(int num) {
        tabHelper.setOrderNum(num);
    }

    //(加入购物车、删除购物车)显示购物车数量事件
    @Subscribe
    public void showShopCarCountEvent(ShowShopCountEvent event) {
        int count = DBShopCartHelper.getInstance(getApplicationContext()).getShopCarListCount();
        tabHelper.setOrderNum(count);
    }

    //退出登录
    @Subscribe
    public void loginQuitEvent(LoginQuitEvent event) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public static final String ACTION_SHOW_HOME = "0";
    public static final String ACTION_SHOW_CLASSIFY = "1";
    public static final String ACTION_SHOW_SHOPPING = "3";

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mViewPager.setCurrentItem(Integer.valueOf(intent.getAction()));
    }

    public static void showHome(Context context) {//至首页
        Intent intent = new Intent(ACTION_SHOW_HOME);
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void showShopping(Context context) {//至购物车
        Intent intent = new Intent(ACTION_SHOW_SHOPPING);
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void showClassify(Context context) {//至分类
        Intent intent = new Intent(ACTION_SHOW_CLASSIFY);
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
    }

}
