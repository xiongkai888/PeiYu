package com.lanmei.peiyu.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lanmei.peiyu.ui.mine.fragment.MineOrderListFragment;


/**
 * 我的订单
 */
public class MineOrderAdapter extends FragmentPagerAdapter {


    public MineOrderAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        MineOrderListFragment fragment = new MineOrderListFragment();
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                bundle.putInt("state",9);//9全部0待支付2待收货3已完成
                break;
            case 1:
                bundle.putInt("state",0);
                break;
            case 2:
                bundle.putInt("state",2);
                break;
            case 3:
                bundle.putInt("state",3);
                break;

        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

}
