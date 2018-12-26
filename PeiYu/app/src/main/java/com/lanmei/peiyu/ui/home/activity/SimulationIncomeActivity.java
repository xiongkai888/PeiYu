package com.lanmei.peiyu.ui.home.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.lanmei.peiyu.R;
import com.xson.common.app.BaseActivity;
import com.xson.common.widget.CenterTitleToolbar;

import butterknife.InjectView;

/**
 * 模拟收入
 */
public class SimulationIncomeActivity extends BaseActivity{

    @InjectView(R.id.toolbar)
    CenterTitleToolbar toolbar;



    @Override
    public int getContentViewId() {
        return R.layout.activity_simulation_income;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.simulation_income);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);
    }



}
