package com.lanmei.peiyu.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lanmei.peiyu.ui.mine.fragment.InstallApplyListFragment;


/**
 * 安装申请
 */
public class InstallApplyAdapter extends FragmentPagerAdapter {


    public InstallApplyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        InstallApplyListFragment fragment = new InstallApplyListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state",position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

}
