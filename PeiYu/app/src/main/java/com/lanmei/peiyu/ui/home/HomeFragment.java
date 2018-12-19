package com.lanmei.peiyu.ui.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.HomeAdapter;
import com.lanmei.peiyu.adapter.HomeClassifyAdapter;
import com.lanmei.peiyu.adapter.HomeRecommendAdapter;
import com.lanmei.peiyu.bean.HomeClassifyBean;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.app.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by xkai on 2018/7/13.
 * 首页
 */

public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    @InjectView(R.id.banner)
    ConvenientBanner banner;
    @InjectView(R.id.recyclerView_classify)
    RecyclerView recyclerViewClassify;
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
        initHomeClassify();
        initRecyclerViewRecommend();
        CommonUtils.setBanner(banner, CommonUtils.getList(), true);
    }

    private void initHomeClassify() {
        HomeClassifyAdapter adapter = new HomeClassifyAdapter(context);
        adapter.setData(getList());
        recyclerViewClassify.setLayoutManager(new GridLayoutManager(context, 4));
        recyclerViewClassify.setNestedScrollingEnabled(false);
        recyclerViewClassify.setAdapter(adapter);
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

    private List<HomeClassifyBean> getList() {
        List<HomeClassifyBean> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            HomeClassifyBean bean = new HomeClassifyBean();
            switch (i){
                case 0:
                    bean.setName("模拟收益");
                    bean.setPicId(R.mipmap.home_classify_1);
                    break;
                case 1:
                    bean.setName("资料录入");
                    bean.setPicId(R.mipmap.home_classify_2);
                    break;
                case 2:
                    bean.setName("安装申报");
                    bean.setPicId(R.mipmap.home_classify_3);
                    break;
                case 3:
                    bean.setName("售后报修");
                    bean.setPicId(R.mipmap.home_classify_4);
                    break;
            }
            list.add(bean);
        }
        return list;
    }

    @Override
    public void onRefresh() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 500);
    }
}
