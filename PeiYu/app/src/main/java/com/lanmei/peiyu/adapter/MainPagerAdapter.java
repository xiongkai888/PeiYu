package com.lanmei.peiyu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lanmei.peiyu.ui.classify.ClassifyFragment;
import com.lanmei.peiyu.ui.home.HomeFragment;
import com.lanmei.peiyu.ui.mine.MineFragment;
import com.lanmei.peiyu.ui.news.NewsFragment;
import com.lanmei.peiyu.ui.shopping.ShoppingFragment;


public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();//首页
            case 1:
                return new ClassifyFragment();//分类
            case 2:
                return new NewsFragment();//资讯
            case 3:
                return new ShoppingFragment();//购物车
            case 4:
                return new MineFragment();//我的
        }
        return new HomeFragment();
    }

    @Override
    public int getCount() {
        return 5;
    }

}
