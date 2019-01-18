package com.lanmei.peiyu.ui.classify.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.ClassifyGoodsListAdapter;
import com.lanmei.peiyu.adapter.SynthesizeFiltrateAdapter;
import com.lanmei.peiyu.bean.GoodsDetailsBean;
import com.lanmei.peiyu.bean.SynthesizeFiltrateBean;
import com.lanmei.peiyu.ui.home.activity.ClassifyActivity;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.SysUtils;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 分类商品列表
 */
public class ClassifyGoodsListActivity extends BaseActivity {

    @InjectView(R.id.ll_by)
    LinearLayout llBy;
    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    @InjectView(R.id.synthesize_tv)
    TextView synthesizeTv;
    @InjectView(R.id.sales_volume_tv)
    TextView salesVolumeTv;
    @InjectView(R.id.price_tv)
    TextView priceTv;
    private SwipeRefreshController<NoPageListBean<GoodsDetailsBean>> controller;
    private int type = -1;//
    private boolean isDown;//价格是不是向下排序
    private  PeiYuApi api;

    @Override
    public int getContentViewId() {
        return R.layout.activity_classify_goods_list;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        smartSwipeRefreshLayout.setLayoutManager(new GridLayoutManager(this, 2));
        api = new PeiYuApi("app/good_list");
        api.addParams("classid", getIntent().getStringExtra("value"));
        ClassifyGoodsListAdapter adapter = new ClassifyGoodsListAdapter(this);
        smartSwipeRefreshLayout.setAdapter(adapter);
        controller = new SwipeRefreshController<NoPageListBean<GoodsDetailsBean>>(this, smartSwipeRefreshLayout, api, adapter) {
        };
        controller.loadFirstPage();
    }

    @OnClick({R.id.back_iv, R.id.keywordEditText, R.id.synthesize_tv, R.id.sales_volume_tv, R.id.price_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                onBackPressed();
                break;
            case R.id.keywordEditText:
                IntentUtil.startActivity(this, SearchGoodsActivity.class);
                break;
            case R.id.synthesize_tv://综合
                popupWindow();
                break;
            case R.id.sales_volume_tv://销量
                if (type == 2) {
                    return;
                }
                type = 2;
                synthesizeTv.setTextColor(getResources().getColor(R.color.black));
                salesVolumeTv.setTextColor(getResources().getColor(R.color.red));
                CommonUtils.setCompoundDrawables(this, priceTv, R.mipmap.o_bothway, R.color.black, 2);
                api.addParams("order",4);
                controller.loadFirstPage();
                break;
            case R.id.price_tv://价格
                type = 3;
                isDown = !isDown;
                CommonUtils.setCompoundDrawables(this, priceTv, isDown?R.mipmap.o_bothway_down:R.mipmap.o_bothway_up, R.color.black, 2);
                synthesizeTv.setTextColor(getResources().getColor(R.color.black));
                salesVolumeTv.setTextColor(getResources().getColor(R.color.black));
                api.addParams("order",isDown?5:6);
                controller.loadFirstPage();
                break;
        }
    }

    private PopupWindow window;

    private void popupWindow() {
        CommonUtils.setCompoundDrawables(getContext(), synthesizeTv, R.mipmap.common_filter_arrow_up, R.color.red, 2);
        if (window != null) {
            window.showAsDropDown(llBy);
            return;
        }
        RecyclerView view = new RecyclerView(this);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setBackgroundColor(getResources().getColor(R.color.white));

        SynthesizeFiltrateAdapter voterFiltrateAdapter = new SynthesizeFiltrateAdapter(this);
        voterFiltrateAdapter.setData(getList());
        view.setAdapter(voterFiltrateAdapter);
        voterFiltrateAdapter.setVoterFiltrateListener(new SynthesizeFiltrateAdapter.SynthesizeFiltrateListener() {
            @Override
            public void onFiltrate(SynthesizeFiltrateBean bean) {
                type = 1;
                synthesizeTv.setText(bean.getName());
                window.dismiss();
                api.addParams("order",bean.getId());
                controller.loadFirstPage();

                salesVolumeTv.setTextColor(getResources().getColor(R.color.black));
                CommonUtils.setCompoundDrawables(getContext(), priceTv, R.mipmap.o_bothway, R.color.black, 2);
            }
        });
//        int width = UIBaseUtils.dp2pxInt(this, 80);
        window = new PopupWindow(view, SysUtils.getScreenWidth(this)/3, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setContentView(view);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setBackgroundDrawable(new BitmapDrawable());
//        int paddingRight = UIBaseUtils.dp2pxInt(this, 0);
//        int xoff = SysUtils.getScreenWidth(this) - width - paddingRight;
        window.showAsDropDown(llBy);
//        L.d(L.TAG,"width:"+width+",paddingRight:"+paddingRight+",xoff:"+xoff);

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                CommonUtils.setCompoundDrawables(getContext(), synthesizeTv, R.mipmap.common_filter_arrow_down, R.color.black, 2);
            }
        });
    }


    private List<SynthesizeFiltrateBean> getList() {
        List<SynthesizeFiltrateBean> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            SynthesizeFiltrateBean bean = new SynthesizeFiltrateBean();
            switch (i) {
                case 0:
                    bean.setName("综合");
                    bean.setSelect(true);
                    break;
                case 1:
                    bean.setName("促销");
                    break;
                case 2:
                    bean.setName("热销");
                    break;
            }
            bean.setId(i+1);
            list.add(bean);
        }
        return list;
    }

}
