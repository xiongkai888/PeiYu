package com.lanmei.peiyu.ui.news.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.NewsListAdapter;
import com.lanmei.peiyu.bean.NewsListBean;
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
 * 搜索资讯
 */
public class SearchNewsActivity extends BaseActivity implements TextView.OnEditorActionListener {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    NewsListAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<NewsListBean>> controller;
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
        actionbar.setTitle(R.string.search_news);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        api =new PeiYuApi("post/index");

        mAdapter = new NewsListAdapter(this);

        smartSwipeRefreshLayout.initWithLinearLayout();
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<NewsListBean>>(this, smartSwipeRefreshLayout, api, mAdapter) {
        };
        controller.loadFirstPage();
    }



    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String key = CommonUtils.getStringByTextView(v);
            loadSearchNews(key);
            return true;
        }
        return false;
    }

    private void loadSearchNews(String keyword) {
        api.addParams("keyword", keyword);
        controller.loadFirstPage();
    }

}
