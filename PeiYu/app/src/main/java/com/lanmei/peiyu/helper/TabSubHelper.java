package com.lanmei.peiyu.helper;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.xson.common.utils.StringUtils;

import java.util.List;

/**
 * Created by xkai on 2018/1/2.
 * tab帮助类
 */

public class TabSubHelper {

    private Context context;
    private List<String> titleList;
    private TabLayout tabLayout;
    private int colorId = R.color.red;//选中的颜色ID

    /**
     * @param context
     * @param tabLayout
     */
    public TabSubHelper(Context context, TabLayout tabLayout, List<String> titleList) {
        this.context = context;
        this.tabLayout = tabLayout;
        this.titleList = titleList;
        setupTabIcons();
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tab_sub, null);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText(titleList.get(position));
        if (position == 0) {
            txt_title.setTextColor(context.getResources().getColor(colorId));
        } else {
            txt_title.setTextColor(context.getResources().getColor(R.color.black));
        }
        return view;
    }

    /**
     *
     * @param num
     */
    public void setOrderNum(int... num) {
        //num的个数应大于等于size
        int size = tabLayout.getTabCount();
        for (int i = 0; i < size; i++) {
            setOrderNum(i,num[i]);
        }

    }

    private void setOrderNum(int position,int num){
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View view = tab.getCustomView();
        TextView orderNumTv = (TextView) view.findViewById(R.id.order_num_tv);
        if (num == 0) {
            orderNumTv.setVisibility(View.GONE);
        } else {
            orderNumTv.setVisibility(View.VISIBLE);
            orderNumTv.setText(String.valueOf(num));
        }
    }

    public void setupTabIcons() {
        if (StringUtils.isEmpty(titleList)) {
            return;
        }
        int size = titleList.size();
        for (int i = 0; i < size; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tabLayout.addTab(tab);
            tab.setCustomView(getTabView(i));
//            tabLayout.getTabAt(i).setCustomView(getTabView(i));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabSelect(tab);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabNormal(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //选中时tab字体颜色和icon
    private void changeTabSelect(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setTextColor(context.getResources().getColor(colorId));
    }

    //默认tab字体颜色和icon
    private void changeTabNormal(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        TextView txt_title = view.findViewById(R.id.txt_title);
        txt_title.setTextColor(Color.parseColor("#000000"));
    }

}
