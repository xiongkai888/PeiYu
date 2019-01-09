package com.lanmei.peiyu.ui.mine.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.NewsListAdapter;
import com.lanmei.peiyu.bean.NewsListBean;
import com.lanmei.peiyu.event.CollectNewsEvent;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;


/**
 * 我的收藏
 */
public class MyCollectActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
//    @InjectView(R.id.viewPager)
//    ViewPager mViewPager;
//    @InjectView(R.id.tabLayout)
//    TabLayout mTabLayout;

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    NewsListAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<NewsListBean>> controller;

    @Override
    public int getContentViewId() {
//        return R.layout.activity_my_collect;
        return R.layout.activity_single_listview;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("资讯收藏");
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

//        mViewPager.setAdapter(new MyCollectAdapter(getSupportFragmentManager()));
//        mViewPager.setOffscreenPageLimit(2);
//        mTabLayout.setupWithViewPager(mViewPager);

        smartSwipeRefreshLayout.initWithLinearLayout();
        smartSwipeRefreshLayout.getRecyclerView().addItemDecoration(new DividerItemDecoration(this));
        PeiYuApi api = new PeiYuApi("post/favour");
        api.addParams("uid",api.getUserId(this));
        mAdapter = new NewsListAdapter(this);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<NewsListBean>>(this, smartSwipeRefreshLayout, api, mAdapter) {
        };
        controller.loadFirstPage();
    }

    //资讯收藏事件
    @Subscribe
    public void newsCollectEvent(CollectNewsEvent event) {
        if (controller != null) {
            controller.loadFirstPage();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
