package com.lanmei.peiyu.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lanmei.peiyu.bean.GoodsDetailsBean;
import com.lanmei.peiyu.ui.classify.fragment.GoodsCommentFragment;
import com.lanmei.peiyu.ui.classify.fragment.GoodsDetailFragment;
import com.lanmei.peiyu.ui.classify.fragment.GoodsInfoFragment;

/**
 * item页ViewPager的内容适配器
 */
public class GoodsDetailsPagerAdapter extends FragmentPagerAdapter {

    GoodsDetailsBean bean;//商品信息bean

    public GoodsDetailsPagerAdapter(FragmentManager fm, GoodsDetailsBean bean) {
        super(fm);
        this.bean = bean;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        switch (position){
            case 0:
                fragment = new GoodsInfoFragment();
                break;
            case 1:
                fragment = new GoodsDetailFragment();
                break;
            case 2:
                fragment = new GoodsCommentFragment();
                break;
        }
        bundle.putSerializable("bean",bean);
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
                return "商品";
            case 1:
                return "详情";
            case 2:
                return "评价";
        }
        return super.getPageTitle(position);
    }
}
