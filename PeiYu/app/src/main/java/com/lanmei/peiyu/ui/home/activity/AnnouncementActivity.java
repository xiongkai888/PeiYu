package com.lanmei.peiyu.ui.home.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.AnnouncementListAdapter;
import com.lanmei.peiyu.bean.AnnouncementListBean;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import butterknife.InjectView;


/**
 * 公告信息
 */
public class AnnouncementActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;

    @Override
    public int getContentViewId() {
        return R.layout.activity_single_listview_no;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.announcement_message);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        smartSwipeRefreshLayout.initWithLinearLayout();
        PeiYuApi api = new PeiYuApi("station/news");
        api.addParams("type",1);
        AnnouncementListAdapter adapter = new AnnouncementListAdapter(this);
        smartSwipeRefreshLayout.setAdapter(adapter);
        SwipeRefreshController<NoPageListBean<AnnouncementListBean>> controller = new SwipeRefreshController<NoPageListBean<AnnouncementListBean>>(this, smartSwipeRefreshLayout, api, adapter) {
        };
        controller.loadFirstPage();
    }

}
