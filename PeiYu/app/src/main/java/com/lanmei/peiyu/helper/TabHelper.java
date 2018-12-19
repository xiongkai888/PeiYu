package com.lanmei.peiyu.helper;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.xson.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xkai on 2018/1/2.
 * tab帮助类
 */

public class TabHelper {

    private Context context;
    private List<String> titleList;
    private TabLayout tabLayout;
    private int colorId =  R.color.colorPrimaryDark;//选中的颜色ID

    int[] imageArray = {R.mipmap.tab_home_on, R.mipmap.tab_home_off,R.mipmap.tab_classify_on, R.mipmap.tab_classify_off, R.mipmap.tab_shopping_on, R.mipmap.tab_shopping_off, R.mipmap.tab_shopping_on, R.mipmap.tab_shopping_off, R.mipmap.tab_mine_on, R.mipmap.tab_mine_off};

    /**
     * @param context
     * @param tabLayout
     */
    public TabHelper(Context context, TabLayout tabLayout) {
        this.context = context;
        this.tabLayout = tabLayout;
        titleList = getTitleList();
        setupTabIcons();
    }


    private List<String> getTitleList() {
        List<String> titles = new ArrayList<>();
        Collections.addAll(titles,context.getString(R.string.home),
                context.getString(R.string.classify),
                context.getString(R.string.news),
                context.getString(R.string.shopping_cart),
                context.getString(R.string.mine));
        return titles;
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main_tab, null);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText(titleList.get(position));
        ImageView img_title = (ImageView) view.findViewById(R.id.img_title);
        if (position == 0) {
//            txt_title.setTextColor(Color.parseColor("#0cc215"));
            txt_title.setTextColor(context.getResources().getColor(colorId));
            img_title.setImageResource(imageArray[position]);
        } else {
//            txt_title.setTextColor(Color.parseColor("#000000"));
            txt_title.setTextColor(context.getResources().getColor(R.color.black));
            img_title.setImageResource(imageArray[position * 2 + 1]);
        }
        return view;
    }

    public void setOrderNum(int num){
        TabLayout.Tab tab = tabLayout.getTabAt(3);
        View view = tab.getCustomView();
        TextView orderNumTv = (TextView) view.findViewById(R.id.order_num_tv);
        if (num == 0){
            orderNumTv.setVisibility(View.GONE);
        }else {
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
//            TabLayout.Tab tab = tabLayout.newTab();
//            tabLayout.addTab(tab);
//            tab.setCustomView(getTabView(i));
            tabLayout.getTabAt(i).setCustomView(getTabView(i));
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
        ImageView img_title = (ImageView) view.findViewById(R.id.img_title);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        int position = tab.getPosition();
        img_title.setImageResource(imageArray[position * 2]);
        txt_title.setTextColor(context.getResources().getColor(colorId));
    }

    //默认tab字体颜色和icon
    private void changeTabNormal(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        ImageView img_title = view.findViewById(R.id.img_title);
        TextView txt_title = view.findViewById(R.id.txt_title);
        int position = tab.getPosition();
        img_title.setImageResource(imageArray[position * 2 + 1]);
        txt_title.setTextColor(Color.parseColor("#000000"));
    }

}
