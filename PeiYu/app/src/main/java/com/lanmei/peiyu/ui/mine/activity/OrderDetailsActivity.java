package com.lanmei.peiyu.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.MineOrderListSubAdapter;
import com.lanmei.peiyu.adapter.PayWayAdapter;
import com.lanmei.peiyu.alipay.AlipayHelper;
import com.lanmei.peiyu.bean.DistributionBean;
import com.lanmei.peiyu.bean.MineOrderListBean;
import com.lanmei.peiyu.bean.PayWayBean;
import com.lanmei.peiyu.bean.WeiXinBean;
import com.lanmei.peiyu.event.OrderOperationEvent;
import com.lanmei.peiyu.event.PaySucceedEvent;
import com.lanmei.peiyu.helper.LogisticsHelper;
import com.lanmei.peiyu.helper.WXPayHelper;
import com.lanmei.peiyu.utils.AKDialog;
import com.lanmei.peiyu.utils.CommonUtils;
import com.lanmei.peiyu.utils.FormatTime;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.bean.DataBean;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.FormatTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 订单详情
 */
public class OrderDetailsActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @InjectView(R.id.ll_logistics)
    LinearLayout root;//
    private LogisticsHelper helper;
    private String id;//订单id
    @InjectView(R.id.scrollView)
    NestedScrollView scrollView;
    @InjectView(R.id.name_tv)
    FormatTextView nameTv;//收货人和电话
    @InjectView(R.id.address_tv)
    FormatTextView addressTv;//收货地址
    @InjectView(R.id.order_no_tv)
    FormatTextView orderNoTv;//订单号
    @InjectView(R.id.order_time_tv)
    FormatTextView orderTimeTv;//下单时间
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;//订单的商品列表
    @InjectView(R.id.total_price_tv)
    TextView totalPriceTv;//总价
    @InjectView(R.id.mode_distribution_tv)
    FormatTextView modeDistributionTv;//配送方式
    @InjectView(R.id.dis_price_tv)
    TextView disPriceTv;//运费价格
    @InjectView(R.id.num_tv)
    TextView numTv;//商品数量
    @InjectView(R.id.order1_tv)
    TextView order1Tv;
    @InjectView(R.id.order2_tv)
    TextView order2Tv;
    @InjectView(R.id.state_tv)
    FormatTextView stateTv;//订单状态
    @InjectView(R.id.pay_way_tv)
    FormatTextView payWayTv;//支付方式
    @InjectView(R.id.recyclerView_pay)
    RecyclerView recyclerViewPay;//支付方式列表

    @Override
    public int getContentViewId() {
        return R.layout.activity_order_details;
    }

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        id = intent.getStringExtra("value");
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        scrollView.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.order_details);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        if (StringUtils.isEmpty(id)) {
            return;
        }
        loadOrderDetails();

        helper = new LogisticsHelper(this,root);
        List<String> list = new ArrayList<>();
        Collections.addAll(list,"提交订单","配送中","交易完成");
        helper.setLogisticsPosition(list,1);
    }

    private MineOrderListBean bean;

    //加载订单详情
    private void loadOrderDetails() {
        PeiYuApi api = new PeiYuApi("app/order_details");
        api.addParams("uid", api.getUserId(this)).addParams("id", id);
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<NoPageListBean<MineOrderListBean>>() {
            @Override
            public void onResponse(NoPageListBean<MineOrderListBean> response) {
                if (isFinishing()) {
                    return;
                }
                List<MineOrderListBean> list = response.data;
                if (StringUtils.isEmpty(list)) {
                    return;
                }
                bean = list.get(0);
                setData();
            }
        });
    }

    private String state;

    private void setData() {
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        loadDistribution();//配送方式
        scrollView.setVisibility(View.VISIBLE);
        id = bean.getId();
        nameTv.setTextValue(bean.getUsername() + "\u3000" + bean.getPhone());
        addressTv.setTextValue(bean.getAddress());
        orderNoTv.setTextValue(bean.getOrder_no());
        FormatTime time = new FormatTime(this);
        time.setTime(bean.getAddtime());
        orderTimeTv.setTextValue(time.formatterTime());

        MineOrderListSubAdapter adapter = new MineOrderListSubAdapter(this);
        adapter.setData(bean.getGoods());
        adapter.setOrderListBean(bean);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        numTv.setText("共计"+bean.getNum() + "件商品");
        disPriceTv.setText(String.format(getString(R.string.price),bean.getDis_price()));
        totalPriceTv.setText(String.format(getString(R.string.price),bean.getTotal_price()));

        String payType = "";
        String pay_status = StringUtils.isSame(bean.getPay_status(), CommonUtils.isOne) ? "已支付" : "未支付";
        if (!StringUtils.isSame(bean.getPay_status(), CommonUtils.isOne)) {
            loadPayment();
        } else {
            recyclerViewPay.setVisibility(View.GONE);
        }
        switch (bean.getPay_type()) {
            case "1":
                payType = "支付宝支付" + "(" + pay_status + ")";
                break;
            case "6":
                payType = "余额支付" + "(" + pay_status + ")";
                break;
            case "7":
                payType = "微信支付" + "(" + pay_status + ")";
                break;
        }
        payWayTv.setTextValue(payType);

        state = bean.getState();// 1|2|3|4|5|6 => 1生成订单|2确认订单|3取消订单|4作废订单|5完成订单|6申请退款
        String stateStr = "";
        switch (state) {//0|1|2|3|9|4|5=>待支付|已支付|待收货|已完成|全部|退款|取消订单
            case "0":
                stateStr = getString(R.string.wait_pay);//待付款
                order1Tv.setVisibility(View.VISIBLE);
                order1Tv.setText(getString(R.string.cancel_order));//取消订单
                order2Tv.setVisibility(View.VISIBLE);
                order2Tv.setText(getString(R.string.go_pay));//去付款
                break;
            case "1":
                stateStr = getString(R.string.wait_send_goods);//待发货
                order1Tv.setVisibility(View.VISIBLE);
                order1Tv.setText(getString(R.string.refund));//退款
                order2Tv.setVisibility(View.GONE);
                break;
            case "2":
                stateStr = getString(R.string.wait_receiving);//待收货
                order1Tv.setVisibility(View.VISIBLE);
                order1Tv.setText(getString(R.string.confirm_receipt));//确认收货
                order2Tv.setVisibility(View.GONE);
                break;
            case "3":
                stateStr = getString(R.string.doned);//已完成
                order1Tv.setVisibility(View.GONE);
                order2Tv.setText(getString(R.string.delete_order));//删除订单
                order2Tv.setVisibility(View.VISIBLE);
                break;
            case "4":
                stateStr = getString(R.string.refund_apply);//退款中
                order1Tv.setVisibility(View.GONE);
                order2Tv.setVisibility(View.GONE);
                break;
            case "5":
                stateStr = getString(R.string.order_cancel);//order_cancel
                order1Tv.setVisibility(View.GONE);
                order2Tv.setText(getString(R.string.delete_order));//删除订单
                order2Tv.setVisibility(View.VISIBLE);
                break;
        }
        stateTv.setTextValue(stateStr);
    }

    @OnClick({R.id.order1_tv, R.id.order2_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.order1_tv://
                switch (state) {//0|1|2|3|9|4|5=>待支付|已支付|待收货|已完成|全部|退款|取消订单
                    case "0"://(待付款)取消订单
                        getAlertDialog("确定取消订单？", "5");
                        break;
                    case "1"://(已支付)退款
                        getAlertDialog("确定退款？", "4");
                        break;
                    case "2"://(待收货)确定收货
                        getAlertDialog("确定收货？", "3");
                        break;
                    case "3":
                        break;
                    case "4":
                        break;
                    case "5":
                        break;
                }
                break;
            case R.id.order2_tv://
                switch (state) {//1下单(待付款)2、3未消费4已完成5取消订单6申请退款7退款完成
                    case "0"://去付款
                        goPay();
                        break;
                    case "1"://
                        break;
                    case "2":
                        break;
                    case "3"://
                        //删除订单
                        deleteOrderDialog();
                        break;
                    case "4"://
                        break;
                    case "5"://
                        //删除订单
                        deleteOrderDialog();
                        break;
                }
                break;
        }
    }

    private void deleteOrderDialog() {
        AKDialog.getAlertDialog(this, "确定删除该订单？", new AKDialog.AlertDialogListener() {
            @Override
            public void yes() {
                deleteOrder();
            }
        });
    }

    //删除订单
    private void deleteOrder() {
        PeiYuApi api = new PeiYuApi("app/delorder");
        api.addParams("uid", api.getUserId(this)).addParams("id", id);
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()) {
                    return;
                }
                UIHelper.ToastMessage(getContext(), response.getInfo());
                EventBus.getDefault().post(new OrderOperationEvent());//刷新订单列表
                finish();
            }
        });
    }

    private void goPay() {
        if (type == 0) {
            UIHelper.ToastMessage(this, getString(R.string.pay_type));
            return;
        }
        PeiYuApi api = new PeiYuApi("app/pay");
        api.addParams("order_id", id).addParams("uid", api.getUserId(this)).addParams("id", id).addParams("pay_type", type);
        HttpClient httpClient = HttpClient.newInstance(this);
        if (type == 1) {//支付宝支付
            httpClient.loadingRequest(api, new BeanRequest.SuccessListener<DataBean<String>>() {
                @Override
                public void onResponse(DataBean<String> response) {
                    if (isFinishing()) {
                        return;
                    }
                    AlipayHelper alipayHelper = new AlipayHelper(getContext());
                    alipayHelper.setPayParam(response.data);
                    alipayHelper.payNow();
                }
            });
        } else if (type == 7) {//微信支付
            httpClient.loadingRequest(api, new BeanRequest.SuccessListener<DataBean<WeiXinBean>>() {
                @Override
                public void onResponse(DataBean<WeiXinBean> response) {
                    if (isFinishing()) {
                        return;
                    }
                    WeiXinBean bean = response.data;
                    WXPayHelper payHelper = new WXPayHelper(getContext());
                    payHelper.setPayParam(bean);
                    payHelper.orderNow();
                }
            });
        } else if (type == 6) {//余额
            httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    if (isFinishing()) {
                        return;
                    }
                    UIHelper.ToastMessage(getContext(), response.getInfo());
                    EventBus.getDefault().post(new PaySucceedEvent());
//                    IntentUtil.startActivity(getContext(), MyGoodsOrderActivity.class);
                    finish();
                }
            });
        }
    }

    private void getAlertDialog(String content, final String state) {
        AKDialog.getAlertDialog(this, content, new AKDialog.AlertDialogListener() {
            @Override
            public void yes() {
                alterState(state);
            }
        });
    }

    //修改订单状态
    private void alterState(String state) {
        PeiYuApi api = new PeiYuApi("app/status_save");
        api.addParams("uid", api.getUserId(this)).addParams("id", id).addParams("status", state);
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()) {
                    return;
                }
                UIHelper.ToastMessage(getContext(), response.getInfo());
                EventBus.getDefault().post(new OrderOperationEvent());//刷新订单列表
                loadOrderDetails();
            }
        });
    }

    //配送列表
    private void loadDistribution() {
        PeiYuApi api = new PeiYuApi("app/distribution_list");
        api.addParams("tablename", "distribution");
        HttpClient.newInstance(this).request(api, new BeanRequest.SuccessListener<NoPageListBean<DistributionBean>>() {
            @Override
            public void onResponse(NoPageListBean<DistributionBean> response) {
                if (isFinishing()) {
                    return;
                }
                List<DistributionBean> list = response.data;
                if (StringUtils.isEmpty(list)) {
                    return;
                }
                for (DistributionBean distributionBean:list){
                    if (StringUtils.isSame(distributionBean.getId(),bean.getDis_type())){
                        modeDistributionTv.setTextValue(distributionBean.getClassname());
                        return;
                    }
                }
            }
        });
    }

    private int type;

    //支付方式
    private void loadPayment() {
        PeiYuApi api = new PeiYuApi("app/payment");
        HttpClient.newInstance(this).request(api, new BeanRequest.SuccessListener<NoPageListBean<PayWayBean>>() {
            @Override
            public void onResponse(NoPageListBean<PayWayBean> response) {
                if (isFinishing()) {
                    return;
                }
                type = 0;
                PayWayAdapter adapter = new PayWayAdapter(getContext());
                adapter.setData(response.data);
                recyclerViewPay.setAdapter(adapter);
                recyclerViewPay.setNestedScrollingEnabled(false);
                recyclerViewPay.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter.setPayWayListener(new PayWayAdapter.PayWayListener() {
                    @Override
                    public void payId(String id) {
                        type = Integer.valueOf(id);
                    }
                });
            }
        });
    }


}
