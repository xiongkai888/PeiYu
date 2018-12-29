package com.lanmei.peiyu.ui.home;

import android.os.Bundle;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.HomeAdapter;
import com.lanmei.peiyu.bean.AdBean;
import com.lanmei.peiyu.bean.HomeBean;
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
        PeiYuApi api = new PeiYuApi("app/good_list");//热销产品
        api.addParams("hot",1);
        adapter = new HomeAdapter(context);
        smartSwipeRefreshLayout.setAdapter(adapter);
        SwipeRefreshController<NoPageListBean<HomeBean>> controller = new SwipeRefreshController<NoPageListBean<HomeBean>>(context, smartSwipeRefreshLayout, api, adapter) {
        };
        controller.loadFirstPage();
        adapter.notifyDataSetChanged();
        loadAd(1);
    }



    //用户端-商家tab  轮播图
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
                    adapter.setRecommendGoods(response.data);
                }
            }
        });
    }

}
