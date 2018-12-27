package com.lanmei.peiyu.ui.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.HomeAdapter;
import com.lanmei.peiyu.adapter.HomeRecommendAdapter;
import com.lanmei.peiyu.ui.home.activity.DataEntryActivity;
import com.lanmei.peiyu.ui.home.activity.SimulationIncomeActivity;
import com.lanmei.peiyu.ui.mine.activity.AfterSaleOrderActivity;
import com.lanmei.peiyu.ui.mine.activity.InstallApplyActivity;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.app.BaseFragment;
import com.xson.common.utils.IntentUtil;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xkai on 2018/7/13.
 * 首页
 */

public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.banner)
    ConvenientBanner banner;
    @InjectView(R.id.recyclerView_recommend)
    RecyclerView recyclerViewRecommend;
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    public int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.color83AE05);
        initHome();
        initRecyclerViewRecommend();
        CommonUtils.setBanner(banner, CommonUtils.getList(), true);
    }


    private void initHome() {
        HomeAdapter adapter = new HomeAdapter(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }


    //推荐商品
    private void initRecyclerViewRecommend() {
        HomeRecommendAdapter adapter = new HomeRecommendAdapter(context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewRecommend.setLayoutManager(layoutManager);
        recyclerViewRecommend.setNestedScrollingEnabled(false);
        recyclerViewRecommend.setAdapter(adapter);
    }


    @Override
    public void onRefresh() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (swipeRefreshLayout != null)
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 500);
    }

    @OnClick({R.id.home_classify1_tv, R.id.home_classify2_tv, R.id.home_classify3_tv, R.id.home_classify4_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_classify1_tv://模拟收入
                IntentUtil.startActivity(context, SimulationIncomeActivity.class);
                break;
            case R.id.home_classify2_tv://资料录入
                IntentUtil.startActivity(context, DataEntryActivity.class);//ApplyInstallActivity
                break;
            case R.id.home_classify3_tv://安装申报
                IntentUtil.startActivity(context, InstallApplyActivity.class);
                break;
            case R.id.home_classify4_tv://售后报修
                IntentUtil.startActivity(context, AfterSaleOrderActivity.class);
                break;
        }
    }
}
