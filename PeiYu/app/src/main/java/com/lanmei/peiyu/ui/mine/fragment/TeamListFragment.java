package com.lanmei.peiyu.ui.mine.fragment;

import android.os.Bundle;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.TeamListAdapter;
import com.lanmei.peiyu.bean.TeamBean;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import butterknife.InjectView;


/**
 * Created by xkai on 2017/4/27.
 * 我的团队
 */

public class TeamListFragment extends BaseFragment {

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_single_listview;
    }


    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initSwipeRefreshLayout();
    }
    private void initSwipeRefreshLayout() {
        smartSwipeRefreshLayout.initWithLinearLayout();
        Bundle bundle = getArguments();
        PeiYuApi api = new PeiYuApi("app/my_team");
        api.addParams("agent",bundle.getInt("agent")).addParams("uid",api.getUserId(context));
        TeamListAdapter adapter = new TeamListAdapter(context);
        smartSwipeRefreshLayout.setAdapter(adapter);
        SwipeRefreshController<NoPageListBean<TeamBean>> controller = new SwipeRefreshController<NoPageListBean<TeamBean>>(getContext(), smartSwipeRefreshLayout, api, adapter) {
        };
        controller.loadFirstPage();
    }
}
