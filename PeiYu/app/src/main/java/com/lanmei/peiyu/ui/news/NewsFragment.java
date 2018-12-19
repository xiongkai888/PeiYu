package com.lanmei.peiyu.ui.news;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.NewsAdapter;
import com.xson.common.app.BaseFragment;

import butterknife.InjectView;

/**
 * Created by xkai on 2018/7/13.
 * 资讯
 */

public class NewsFragment extends BaseFragment{

    @InjectView(R.id.viewPager)
    ViewPager mViewPager;
    @InjectView(R.id.tabLayout)
    TabLayout mTabLayout;

    public int getContentViewId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        mViewPager.setAdapter(new NewsAdapter(getChildFragmentManager(),context));
//        mViewPager.setOffscreenPageLimit(3);
//        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
