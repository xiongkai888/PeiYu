package com.lanmei.peiyu.ui.mine.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.MinePowerStationEquipmentAdapter;
import com.xson.common.app.BaseActivity;
import com.xson.common.widget.CenterTitleToolbar;

import butterknife.InjectView;

/**
 * 我的电站
 */
public class MinePowerStationActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    @InjectView(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;//下拉刷新
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int getContentViewId() {
        return R.layout.activity_mine_power_station;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.mine_power_station);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.color1ca637,R.color.color83AE05,R.color.yellow);


        MinePowerStationEquipmentAdapter adapter = new MinePowerStationEquipmentAdapter(this);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onRefresh() {
        toolbar.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        },1000);
    }
}
