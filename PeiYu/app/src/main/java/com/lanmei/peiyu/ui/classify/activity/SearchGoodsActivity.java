package com.lanmei.peiyu.ui.classify.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.ClassifyGoodsListAdapter;
import com.lanmei.peiyu.bean.GoodsDetailsBean;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.DrawClickableEditText;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import butterknife.InjectView;

/**
 * 搜索商品
 */
public class SearchGoodsActivity extends BaseActivity implements TextView.OnEditorActionListener {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    ClassifyGoodsListAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<GoodsDetailsBean>> controller;
    private PeiYuApi api;
    @InjectView(R.id.keywordEditText)
    DrawClickableEditText mKeywordEditText;

    @Override
    public int getContentViewId() {
        return R.layout.activity_single_listview_search_no;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        mKeywordEditText.setFocusableInTouchMode(true);
        mKeywordEditText.setOnEditorActionListener(this);

        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.search_goods);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        api =new PeiYuApi("app/good_list");

        mAdapter = new ClassifyGoodsListAdapter(this);

        smartSwipeRefreshLayout.setLayoutManager(new GridLayoutManager(this, 2));
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<GoodsDetailsBean>>(this, smartSwipeRefreshLayout, api, mAdapter) {
        };
        controller.loadFirstPage();
    }



    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String key = CommonUtils.getStringByTextView(v);
            loadSearchGoods(key);
            return true;
        }
        return false;
    }

    private void loadSearchGoods(String keyword) {
        api.addParams("goodsname", keyword);
        controller.loadFirstPage();
    }

}
