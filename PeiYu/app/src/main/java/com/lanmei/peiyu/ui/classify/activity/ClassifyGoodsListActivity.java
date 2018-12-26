package com.lanmei.peiyu.ui.classify.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.ClassifyGoodsListAdapter;
import com.lanmei.peiyu.bean.HomeClassifyBean;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import butterknife.InjectView;
import butterknife.OnClick;

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
        smartSwipeRefreshLayout.setLayoutManager(new GridLayoutManager(this, 2));
        PeiYuApi api = new PeiYuApi("Reservation/index");
        ClassifyGoodsListAdapter adapter = new ClassifyGoodsListAdapter(this);
        smartSwipeRefreshLayout.setAdapter(adapter);
        controller = new SwipeRefreshController<NoPageListBean<HomeClassifyBean>>(this, smartSwipeRefreshLayout, api, adapter) {
        };
        smartSwipeRefreshLayout.setMode(SmartSwipeRefreshLayout.Mode.NO_PAGE);
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.back_iv, R.id.keywordEditText})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                onBackPressed();
                break;
            case R.id.keywordEditText:
                break;
        }
    }
}
