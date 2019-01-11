package com.lanmei.peiyu.ui.mine.fragment;

import android.os.Bundle;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.TopUpAdapter;
import com.lanmei.peiyu.bean.RechargeResultBean;
import com.xson.common.api.AbstractApi;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import butterknife.InjectView;


/**
 * Created by Administrator on 2017/4/27.
 * 消费明细
 */

public class ConsumeDetailsFragment extends BaseFragment {


    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_single_listview;
    }

    public static ConsumeDetailsFragment newInstance() {
        Bundle args = new Bundle();
        ConsumeDetailsFragment fragment = new ConsumeDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initSwipeRefreshLayout();
    }
    private void initSwipeRefreshLayout() {
        smartSwipeRefreshLayout.initWithLinearLayout();

        PeiYuApi api = new PeiYuApi("member/money_log");
//        api.add("token",api.getToken(context));
        api.addParams("uid",api.getUserId(context));
        api.setMethod(AbstractApi.Method.GET);
        TopUpAdapter mAdapter = new TopUpAdapter(context);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        SwipeRefreshController<NoPageListBean<RechargeResultBean>> controller = new SwipeRefreshController<NoPageListBean<RechargeResultBean>>(context, smartSwipeRefreshLayout, api, mAdapter) {
        };
        controller.loadFirstPage();
    }
}
