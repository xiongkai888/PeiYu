package com.lanmei.peiyu.ui.classify.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanmei.peiyu.MainActivity;
import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.GoodsDetailsPagerAdapter;
import com.lanmei.peiyu.bean.GoodsDetailsBean;
import com.lanmei.peiyu.event.PaySucceedEvent;
import com.lanmei.peiyu.ui.shopping.shop.DBShopCartHelper;
import com.lanmei.peiyu.ui.shopping.shop.ShowShopCountEvent;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.DataBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UserHelper;
import com.xson.common.widget.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 商品详情（商品、详情、评论）
 */
public class GoodsDetailsActivity extends BaseActivity {

    @InjectView(R.id.tabLayout)
    TabLayout tabLayout;
    @InjectView(R.id.viewPager)
    NoScrollViewPager viewPager;
    @InjectView(R.id.collect_iv)
    ImageView collectIv;
    @InjectView(R.id.collect_tv)
    TextView collectTv;
    @InjectView(R.id.title_tv)
    TextView titleTv;
    @InjectView(R.id.ll_details_bottom)
    View llDetailsBottom;
    @InjectView(R.id.num_tv)
    TextView shopNumTv;//购物车数量
    GoodsDetailsBean bean;//商品详情信息

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        bean = (GoodsDetailsBean)intent.getBundleExtra("bundle").getSerializable("bean");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_goods_details;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
//        loadGoodsDetails();
        if (!StringUtils.isEmpty(bean)) {
            init(bean);
        }
    }

    private void loadGoodsDetails() {
        PeiYuApi api = new PeiYuApi("app/goodsdetail");
        api.addParams("id",bean.getId());
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<DataBean<GoodsDetailsBean>>() {
            @Override
            public void onResponse(DataBean<GoodsDetailsBean> response) {
                if (isFinishing()) {
                    return;
                }
                bean = response.data;
                if (!StringUtils.isEmpty(bean)) {
                    init(bean);
                }
            }
        });
    }


    public void operaTitleBar(boolean scroAble, boolean titleVisiable) {
        viewPager.setNoScroll(scroAble);
        titleTv.setText(titleVisiable ? getString(R.string.graphic_details) : "");
        tabLayout.setVisibility(titleVisiable ? View.GONE : View.VISIBLE);
    }

//    int favorite;//是否收藏了该商品

    private void init(GoodsDetailsBean bean) {
        llDetailsBottom.setVisibility(View.VISIBLE);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new GoodsDetailsPagerAdapter(getSupportFragmentManager(), bean));
        tabLayout.setupWithViewPager(viewPager);

        if (UserHelper.getInstance(this).hasLogin()){
            showShopCount();
        }

    }





    @OnClick({R.id.ll_collect, R.id.ll_shop, R.id.add_shop_car_tv, R.id.pay_now_tv,R.id.back_iv})
    public void onViewClicked(View view) {
//        if (!CommonUtils.isLogin(this)) {
//            return;
//        }
//        if (StringUtils.isEmpty(bean)) {
//            return;
//        }
        switch (view.getId()) {
            case R.id.ll_collect://收藏
                CommonUtils.developing(this);
                break;
            case R.id.ll_shop://购物车
                MainActivity.showShopping(this);
                break;
            case R.id.add_shop_car_tv://加入购物车
            case R.id.pay_now_tv://立即购买
                CommonUtils.developing(this);
                break;
            case R.id.back_iv://
                finish();
                break;
        }
    }


    //支付成功调用
    @Subscribe
    public void paySucceedEvent(PaySucceedEvent event) {
        finish();
    }

    //(加入购物车、删除购物车)显示购物车数量事件
    @Subscribe
    public void showShopCarCountEvent(ShowShopCountEvent event) {
        showShopCount();
    }

    //显示购物车数量
    private void showShopCount() {
        int count = DBShopCartHelper.getInstance(getApplicationContext()).getShopCarListCount();
        shopNumTv.setText(String.valueOf(count));
        shopNumTv.setVisibility(count == 0?View.GONE:View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
//        mShareHelper.onDestroy();
    }


}
