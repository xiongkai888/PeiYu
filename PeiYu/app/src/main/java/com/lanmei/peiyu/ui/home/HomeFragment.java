package com.lanmei.peiyu.ui.home;

import android.os.Bundle;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.HomeAdapter;
import com.lanmei.peiyu.bean.AdBean;
import com.lanmei.peiyu.bean.GoodsDetailsBean;
import com.lanmei.peiyu.bean.HomeClassifyBean;
import com.lanmei.peiyu.bean.NewsListBean;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.StringUtils;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import butterknife.InjectView;

/**
 * Created by xkai on 2018/7/13.
 * 首页
 */

public class HomeFragment extends BaseFragment {

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    private HomeAdapter adapter;

    public int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        smartSwipeRefreshLayout.initWithLinearLayout();
        PeiYuApi api = new PeiYuApi("post/index");
        api.addParams("cid",5);
        adapter = new HomeAdapter(context);
        smartSwipeRefreshLayout.setAdapter(adapter);
        SwipeRefreshController<NoPageListBean<NewsListBean>> controller = new SwipeRefreshController<NoPageListBean<NewsListBean>>(context, smartSwipeRefreshLayout, api, adapter) {
            @Override
            public void onRefreshResponse(NoPageListBean<NewsListBean> response) {
                super.onRefreshResponse(response);
                loadAd(1);//轮播图
                loadAd(2);//推荐图
                loadClassList();   //首页分类(模拟收益、资料录入等)
            }
        };
        controller.loadFirstPage();
        adapter.notifyDataSetChanged();
        loadRecommendGoods();  //推荐商品
    }


    /**
     *
     * @param type type  图片类型 （1是头部轮播，2是推荐图）
     */
    private void loadAd(final int type) {
        PeiYuApi api = new PeiYuApi("app/index_img");
        api.addParams("type",type);//1是头部轮播，2是推荐图(即热门活动)
        HttpClient.newInstance(context).request(api, new BeanRequest.SuccessListener<NoPageListBean<AdBean>>() {
            @Override
            public void onResponse(NoPageListBean<AdBean> response) {
                if (smartSwipeRefreshLayout == null) {
                    return;
                }
                if (StringUtils.isEmpty(response.data)) {
                    return;
                }
                if (type == 1){
                    adapter.setBannerParameter(response.data);
                }else {
                    adapter.setRecommendImge(response.data);
                }
            }
        });
    }
    //首页分类(模拟收益、资料录入等)
    private void loadClassList() {
        PeiYuApi api = new PeiYuApi("station/class_list");
        HttpClient.newInstance(context).request(api, new BeanRequest.SuccessListener<NoPageListBean<HomeClassifyBean>>() {
            @Override
            public void onResponse(NoPageListBean<HomeClassifyBean> response) {
                if (smartSwipeRefreshLayout == null) {
                    return;
                }
                adapter.setClassifyList(response.data);
            }
        });
    }
    //推荐商品
    private void loadRecommendGoods() {
        PeiYuApi api = new PeiYuApi("app/good_list");//热销产品
        api.addParams("hot",1);
        HttpClient.newInstance(context).request(api, new BeanRequest.SuccessListener<NoPageListBean<GoodsDetailsBean>>() {
            @Override
            public void onResponse(NoPageListBean<GoodsDetailsBean> response) {
                if (smartSwipeRefreshLayout == null) {
                    return;
                }
                adapter.setRecommendGoods(response.data);
            }
        });
    }

}
