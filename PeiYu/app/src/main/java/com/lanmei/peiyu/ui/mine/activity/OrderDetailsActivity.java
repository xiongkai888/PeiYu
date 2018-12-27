package com.lanmei.peiyu.ui.mine.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.LinearLayout;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.helper.LogisticsHelper;
import com.xson.common.app.BaseActivity;
import com.xson.common.widget.CenterTitleToolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.InjectView;

/**
 * 订单详情
 */
public class OrderDetailsActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @InjectView(R.id.ll_logistics)
    LinearLayout root;//
    private LogisticsHelper helper;

    @Override
    public int getContentViewId() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.order_details);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        helper = new LogisticsHelper(this,root);
        List<String> list = new ArrayList<>();
        Collections.addAll(list,"提交订单","配送中","交易完成");
        helper.setLogisticsPosition(list,1);
    }

}
