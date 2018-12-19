package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lanmei.peiyu.ui.news.fragment.NewsListFragment;


/**
 * 售后工单
 */
public class NewsAdapter extends FragmentPagerAdapter {


    public NewsAdapter(FragmentManager fm, Context context) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                bundle.putString("status","0");
                break;
            case 1:
                bundle.putString("status","1");
                break;
            case 2:
                bundle.putString("status","3");
                break;
            case 3:
                bundle.putString("status","4");
                break;

        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "推荐";
            case 1:
                return "公司资讯";
            case 2:
                return "行业资讯";
            case 3:
                return "安装指南";
        }
        return "";
    }
}
