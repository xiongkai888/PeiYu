package com.lanmei.peiyu.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lanmei.peiyu.ui.mine.fragment.TeamListFragment;

/**
 * 我的团队
 */
public class MyTeamAdapter extends FragmentPagerAdapter {


    public MyTeamAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        TeamListFragment fragment = new TeamListFragment();
        bundle.putInt("agent",position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "全部";
            case 1:
                return "一级会员";
            case 2:
                return "二级会员";
        }

        return null;
    }

}
