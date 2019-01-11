package com.lanmei.peiyu.ui.mine.fragment;

import android.os.Bundle;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.MineOrderListAdapter;
import com.lanmei.peiyu.bean.MineOrderListBean;
import com.lanmei.peiyu.event.OrderOperationEvent;
import com.lanmei.peiyu.event.PaySucceedEvent;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.BaseBean;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;


/**
 * Created by Administrator on 2017/4/27.
 * 订单列表
 */

public class MineOrderListFragment extends BaseFragment {

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    private SwipeRefreshController<NoPageListBean<MineOrderListBean>> controller;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_single_listview_no;
    }


    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initSwipeRefreshLayout();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void initSwipeRefreshLayout() {
        smartSwipeRefreshLayout.initWithLinearLayout();
        PeiYuApi api = new PeiYuApi("app/order_list");
        api.addParams("uid", api.getUserId(context));
        api.addParams("state", getArguments().getInt("state"));//9全部0待支付2待收货3已完成
        MineOrderListAdapter adapter = new MineOrderListAdapter(context);
        adapter.setOrderAlterListener(new MineOrderListAdapter.OrderAlterListener() {
            @Override
            public void affirm(String status, String oid) {
                statusAffirm(status, oid);
            }

            @Override
            public void deleteOrder(String oid) {
                loadDeleteOrder(oid);
            }
        });
        smartSwipeRefreshLayout.setAdapter(adapter);
        controller = new SwipeRefreshController<NoPageListBean<MineOrderListBean>>(context, smartSwipeRefreshLayout, api, adapter) {
        };
        controller.loadFirstPage();
    }

    //删除订单
    private void loadDeleteOrder(String oid) {
        PeiYuApi api = new PeiYuApi("app/delorder");
        api.addParams("id",oid);
        api.addParams("uid",api.getUserId(context));
        api.addParams("is_del",1);
        HttpClient.newInstance(context).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (controller != null){
                    UIHelper.ToastMessage(context,response.getInfo());
                    EventBus.getDefault().post(new OrderOperationEvent());
                }
            }
        });
    }

    //修改订单状态
    private void statusAffirm(String status, String oid) {
        PeiYuApi api = new PeiYuApi("app/status_save");
        api.addParams("id",oid);
        api.addParams("uid",api.getUserId(context));
        api.addParams("status",status);
        HttpClient.newInstance(context).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (controller != null){
//                    controller.loadFirstPage();
                    UIHelper.ToastMessage(context,response.getInfo());
                    EventBus.getDefault().post(new OrderOperationEvent());
                }
            }
        });
    }

    //订单操作后调用
    @Subscribe
    public void orderOperationEvent(OrderOperationEvent event) {
        controller.loadFirstPage();
    }
    //订单详情支付完成时调用
    @Subscribe
    public void paySucceedEvent(PaySucceedEvent event) {
        controller.loadFirstPage();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
