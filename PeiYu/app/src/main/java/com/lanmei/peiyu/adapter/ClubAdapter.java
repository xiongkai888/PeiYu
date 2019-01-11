package com.lanmei.peiyu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lanmei.peiyu.ui.mine.fragment.ConsumeDetailsFragment;
import com.lanmei.peiyu.ui.mine.fragment.WithdrawFragment;


/**
 * 会员卡（消费记录等信息）
 */
public class ClubAdapter extends FragmentPagerAdapter {

    public ClubAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ConsumeDetailsFragment.newInstance();
//            case 1:
//                return TopUpFragment.newInstance();
            case 1:
                return WithdrawFragment.newInstance();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "消费明细";
                break;
//            case 1:
//                title = "充值记录";
//                break;
            case 1:
                title = "提现";
                break;
        }
        return title;
    }
}
