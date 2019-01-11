package com.lanmei.peiyu.ui.classify.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.data.volley.Response;
import com.data.volley.error.VolleyError;
import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.ConfirmOrderAdapter;
import com.lanmei.peiyu.adapter.PayWayAdapter;
import com.lanmei.peiyu.alipay.AlipayHelper;
import com.lanmei.peiyu.bean.AddressListBean;
import com.lanmei.peiyu.bean.DistributionBean;
import com.lanmei.peiyu.bean.PayWayBean;
import com.lanmei.peiyu.bean.WeiXinBean;
import com.lanmei.peiyu.event.AddressListEvent;
import com.lanmei.peiyu.event.ChooseAddressEvent;
import com.lanmei.peiyu.event.PaySucceedEvent;
import com.lanmei.peiyu.helper.WXPayHelper;
import com.lanmei.peiyu.ui.mine.activity.MineOrderActivity;
import com.lanmei.peiyu.ui.shopping.shop.ShopCarBean;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.bean.DataBean;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.FormatTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * 确认订单
 */
public class ConfirmOrderActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.name_tv)
    TextView nameTv;
    @InjectView(R.id.address_tv)
    TextView addressTv;
    @InjectView(R.id.distribution_tv)
    TextView distributionTv;
    @InjectView(R.id.coupon_name_tv)
    TextView couponNameTv;//选择的优惠券
    @InjectView(R.id.goods_num_tv)
    TextView goodsNumTv;//商品个数
//    @InjectView(R.id.ll_coupon)
//    LinearLayout llCoupon;//是否隐藏优惠券
    @InjectView(R.id.goods_price_tv)
    TextView goodsPriceTv;//总的商品价格（扣除运费前）
    @InjectView(R.id.price_tv)
    FormatTextView priceTv;//总的商品价格（扣除运费后）
    @InjectView(R.id.recyclerViewShop)
    RecyclerView recyclerViewShop;//商品列表
    private List<ShopCarBean> list;//提交的商品列表
    private List<DistributionBean> distributionBeanList;//配送列表
    private DistributionBean distributionBean;//选择的配送方式
    private OptionPicker picker;//
    private AddressListBean addressBean;//地址信息
    private List<AddressListBean> addressListBeans;
    private int type = -10;
    private int goodsNum;
    private double price;//


    @Override
    public int getContentViewId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        Bundle bundle = intent.getBundleExtra("bundle");
        if (bundle != null) {
            list = (List<ShopCarBean>) bundle.getSerializable("list");
        }
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.confirm_order);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        checkAddress();

        if (StringUtils.isEmpty(list)) {
            UIHelper.ToastMessage(this, "获取商品异常");
            finish();
            return;
        }

        price = getPrice();

        String s = String.format("%.2f", price);

        priceTv.setTextValue(s);
        goodsPriceTv.setText(String.format(getString(R.string.price), s));
        goodsNumTv.setText(goodsNum + "");
        ConfirmOrderAdapter adapter = new ConfirmOrderAdapter(this);
        adapter.setData(list);
        recyclerViewShop.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewShop.setNestedScrollingEnabled(false);
        recyclerViewShop.setAdapter(adapter);

        loadDistribution();//配送方式
        loadPayment();//支付方式
        loadAddressList();
    }

    private double getPrice() {
        goodsNum = 0;
        double price = 0;
        for (ShopCarBean bean : list) {
            price += bean.getSell_price() * bean.getGoodsCount();
            goodsNum += bean.getGoodsCount();
        }
        return price;
    }

    private void initPicker() {
        picker = new OptionPicker(this, toStringList());
        picker.setOffset(2);
        picker.setSelectedIndex(1);
        picker.setTextSize(16);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                distributionBean = distributionBeanList.get(index);
                distributionTv.setText(item+"\u3000"+String.format(getString(R.string.price),distributionBean.getFree()));

                priceTv.setTextValue(String.format("%.2f", (price +Double.valueOf(distributionBean.getFree()))));
            }
        });
    }


    private List<String> toStringList() {
        List<String> list = new ArrayList<>();
        for (DistributionBean bean : distributionBeanList) {
            list.add(bean.getClassname());
        }
        return list;
    }

    //配送列表
    private void loadDistribution() {
        PeiYuApi api = new PeiYuApi("app/distribution_list");
        api.addParams("tablename", "distribution");
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<NoPageListBean<DistributionBean>>() {
            @Override
            public void onResponse(NoPageListBean<DistributionBean> response) {
                if (isFinishing()) {
                    return;
                }
                distributionBeanList = response.data;
                if (StringUtils.isEmpty(distributionBeanList)) {
                    return;
                }
                initPicker();
            }
        });
    }

    //支付方式
    private void loadPayment() {
        PeiYuApi api = new PeiYuApi("app/payment");
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<NoPageListBean<PayWayBean>>() {
            @Override
            public void onResponse(NoPageListBean<PayWayBean> response) {
                if (isFinishing()) {
                    return;
                }
                PayWayAdapter adapter = new PayWayAdapter(getContext());
                adapter.setData(response.data);
                recyclerView.setAdapter(adapter);
                adapter.setPayWayListener(new PayWayAdapter.PayWayListener() {
                    @Override
                    public void payId(String id) {
                        type = Integer.valueOf(id);
                    }
                });
            }
        });
    }


    @OnClick({R.id.ll_address, R.id.submit_order_tv, R.id.ll_distribution, R.id.ll_coupon})
    public void onViewClicked(View view) {
        if (StringUtils.isEmpty(list)) {
            return;
        }
        switch (view.getId()) {
            case R.id.ll_address:
                Bundle bundle = new Bundle();
                bundle.putSerializable("list",(Serializable) addressListBeans);
                bundle.putString("title","选择收货地址");
                IntentUtil.startActivity(this, AddressListActivity.class,bundle);
                break;
            case R.id.submit_order_tv://提交订单
                submitOrder();
                break;
            case R.id.ll_distribution://配送方式
                if (picker != null) {
                    picker.show();
                }
                break;
            case R.id.ll_coupon://优惠券
                break;
        }
    }

    private void submitOrder() {
        if (StringUtils.isEmpty(addressBean)) {
            UIHelper.ToastMessage(this, R.string.choose_address);
            return;
        }
        if (StringUtils.isEmpty(distributionBean)) {
            UIHelper.ToastMessage(this, "请选择配送方式");
            return;
        }
        if (type == -10) {
            UIHelper.ToastMessage(this, getString(R.string.pay_type));
            return;
        }
        StringBuilder goodsidBuilder = new StringBuilder();
        StringBuilder goodsnameBuilder = new StringBuilder();
        StringBuilder numBuilder = new StringBuilder();
        StringBuilder gidBuilder = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ShopCarBean bean = list.get(i);
            goodsidBuilder.append(bean.getGoods_id()).append(((size - 1) != i) ? "," : "");
            goodsnameBuilder.append(bean.getGoodsName()).append(((size - 1) != i) ? "," : "");
            numBuilder.append(String.valueOf(bean.getGoodsCount())).append(((size - 1) != i) ? "," : "");
            gidBuilder.append(!StringUtils.isEmpty(bean.getGid()) ? bean.getGid() : CommonUtils.isZero).append(((size - 1) != i) ? "," : "");
        }
        PeiYuApi api = new PeiYuApi("app/createorder");
        api.addParams("pay_type", type).addParams("uid", api.getUserId(this)).addParams("goodsid", goodsidBuilder.toString())
                .addParams("goodsname", goodsnameBuilder.toString()).addParams("num", numBuilder.toString()).addParams("username", addressBean.getAccept_name())
                .addParams("phone", addressBean.getMobile()).addParams("address", addressBean.getAddress())
                .addParams("dis_type", distributionBean.getId()).addParams("dis_name",distributionBean.getClassname()).addParams("dis_price",distributionBean.getFree())
                .addParams("gid", gidBuilder.toString());
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<DataBean<Integer>>() {
            @Override
            public void onResponse(DataBean<Integer> response) {
                if (isFinishing()) {
                    return;
                }
                loadPayMent(response.data);
            }
        });

    }


    private void loadPayMent(int order_id) {
        PeiYuApi api = new PeiYuApi("app/pay");
        api.addParams("order_id", order_id).addParams("uid", api.getUserId(this)).addParams("id", order_id);
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
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (isFinishing()) {
                        return;
                    }
                    UIHelper.ToastMessage(getContext(), error.getMessage());
                }
            });
        }
    }

    private void loadAddressList() {
        PeiYuApi api = new PeiYuApi("app/address");
        api.addParams("uid", api.getUserId(this));
//        api.add("uid",46);
        api.addParams("operation", 4);
        HttpClient.newInstance(this).request(api, new BeanRequest.SuccessListener<NoPageListBean<AddressListBean>>() {
            @Override
            public void onResponse(NoPageListBean<AddressListBean> response) {
                if (isFinishing()) {
                    return;
                }
                addressListBeans = response.data;
                if (!StringUtils.isEmpty(addressListBeans)) {
                    for (AddressListBean bean : addressListBeans) {
                        if (!StringUtils.isEmpty(bean) && StringUtils.isSame(CommonUtils.isOne, bean.getDefaultX())) {//获取默认地址
                            chooseAddress(bean);
                            return;
                        }
                    }
                }
                checkAddress();
            }
        });
    }

    private void chooseAddress(AddressListBean bean) {
        addressBean = bean;
        nameTv.setVisibility(View.VISIBLE);
        nameTv.setText(addressBean.getAccept_name() + "\u3000\u3000" + addressBean.getMobile());
        addressTv.setText(bean.getAddress());
    }

    //点击选择地址的时候调用
    @Subscribe
    public void chooseAddressEvent(ChooseAddressEvent event) {
        chooseAddress(event.getBean());
    }

    //
    @Subscribe
    public void paySucceedEvent(PaySucceedEvent event) {
        IntentUtil.startActivity(getContext(), MineOrderActivity.class);
        finish();
    }

    //选择地址列表获取地址列表的时候调用
    @Subscribe
    public void addressListEvent(AddressListEvent event) {
        addressListBeans = event.getList();
        if (StringUtils.isEmpty(addressListBeans)) {
            checkAddress();
        } else {
            if (!StringUtils.isEmpty(addressBean)) {
                for (AddressListBean bean : addressListBeans) {
                    if (StringUtils.isSame(bean.getId(), addressBean.getId())) {
                        chooseAddress(bean);
                        return;
                    }
                }
                checkAddress();
            }
        }
    }

    private void checkAddress() {
        addressBean = null;
        nameTv.setVisibility(View.GONE);
        addressTv.setText(R.string.choose_address);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
