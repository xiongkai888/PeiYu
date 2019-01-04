package com.lanmei.peiyu.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lanmei.peiyu.bean.NewsTabBean;
import com.lanmei.peiyu.ui.news.fragment.NewsListFragment;
import com.xson.common.utils.StringUtils;

import java.util.List;

/**
 * Created by xkai on 2017/4/25.
 */

public class NewsTabAdapter extends FragmentStatePagerAdapter {

    List<NewsTabBean> list;

    public void setList(List<NewsTabBean> list) {
        this.list = list;
    }

    public NewsTabAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("cid",list.get(position).getId());
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getCount() {
        return StringUtils.isEmpty(list)?0:list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getName();
    }

}
