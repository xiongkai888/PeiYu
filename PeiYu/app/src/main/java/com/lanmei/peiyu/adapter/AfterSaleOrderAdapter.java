package com.lanmei.peiyu.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lanmei.peiyu.ui.mine.fragment.AfterSaleOrderListFragment;


/**
 * 售后工单
 */
public class AfterSaleOrderAdapter extends FragmentPagerAdapter {


    public AfterSaleOrderAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        AfterSaleOrderListFragment fragment = new AfterSaleOrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state",position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

}
