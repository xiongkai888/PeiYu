package com.lanmei.peiyu.ui.classify.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.ClassifyGoodsListAdapter;
import com.lanmei.peiyu.bean.HomeClassifyBean;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import butterknife.InjectView;

/**
 * 分类商品列表
 */
public class ClassifyGoodsListActivity extends BaseActivity {

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    private SwipeRefreshController<NoPageListBean<HomeClassifyBean>> controller;

    @Override
    public int getContentViewId() {
        return R.layout.activity_classify_goods_list;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        smartSwipeRefreshLayout.setLayoutManager(new GridLayoutManager(this,2));
        PeiYuApi api = new PeiYuApi("Reservation/index");
        ClassifyGoodsListAdapter adapter = new ClassifyGoodsListAdapter(this);
        smartSwipeRefreshLayout.setAdapter(adapter);
        controller = new SwipeRefreshController<NoPageListBean<HomeClassifyBean>>(this, smartSwipeRefreshLayout, api, adapter) {
        };
        smartSwipeRefreshLayout.setMode(SmartSwipeRefreshLayout.Mode.NO_PAGE);
        adapter.notifyDataSetChanged();
    }


}
