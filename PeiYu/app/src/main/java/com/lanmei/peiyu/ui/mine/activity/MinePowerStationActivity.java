package com.lanmei.peiyu.ui.mine.activity;

import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.MinePowerStationEquipmentAdapter;
import com.lanmei.peiyu.adapter.SelectPlantAdapter;
import com.lanmei.peiyu.bean.ApplyAfterSaleOrderBean;
import com.lanmei.peiyu.bean.EquipmentBean;
import com.lanmei.peiyu.utils.AKDialog;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.SysUtils;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 我的电站
 */
public class MinePowerStationActivity extends BaseActivity {

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    @InjectView(R.id.title_tv)
    TextView titleTv;
    @InjectView(R.id.line_tv)
    TextView lineTv;
    private SwipeRefreshController<NoPageListBean<EquipmentBean>> controller;
    private PeiYuApi api;
    private MinePowerStationEquipmentAdapter adapter;
    private List<ApplyAfterSaleOrderBean> orderBeanList;

    @Override
    public int getContentViewId() {
        return R.layout.activity_mine_power_station;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        smartSwipeRefreshLayout.initWithLinearLayout();
        api = new PeiYuApi("station/device");
        api.addParams("uid", api.getUserId(this));
        adapter = new MinePowerStationEquipmentAdapter(this);
        smartSwipeRefreshLayout.setAdapter(adapter);
        controller = new SwipeRefreshController<NoPageListBean<EquipmentBean>>(this, smartSwipeRefreshLayout, api, adapter) {
        };
        loadPowerStationList();
    }

    /**
     * 电站名称列表
     */
    private void loadPowerStationList() {
        PeiYuApi peiYuApi = new PeiYuApi("station/station");
        peiYuApi.addParams("uid", api.getUserId(this));
        HttpClient.newInstance(this).loadingRequest(peiYuApi, new BeanRequest.SuccessListener<NoPageListBean<ApplyAfterSaleOrderBean>>() {
            @Override
            public void onResponse(NoPageListBean<ApplyAfterSaleOrderBean> response) {
                if (isFinishing()) {
                    return;
                }
                orderBeanList = response.data;
                if (StringUtils.isEmpty(orderBeanList)) {
                    AKDialog.getMessageDialog(getContext(), getString(R.string.no_power_station_information), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
                    return;
                }
                setPlantInfo(orderBeanList.get(0));
                adapter.notifyDataSetChanged();
            }
        });
    }

    //请求我的电站列表、电站功率、累计发电等等
    private void setPlantInfo(ApplyAfterSaleOrderBean bean){
        loadInfo();
        titleTv.setText(bean.getS_name());
        api.addParams("sid", bean.getS_id());
        controller.loadFirstPage();
    }
    //加载获取 今日发电量 电站功率、累计发电、今日收益、累计收益 等信息
    private void loadInfo(){

    }

    private PopupWindow window;

    private void popupWindow() {
        if (window != null) {
            window.showAsDropDown(lineTv);
            return;
        }
        RecyclerView view = new RecyclerView(this);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setBackgroundColor(getResources().getColor(R.color.white));

        SelectPlantAdapter filtrateAdapter = new SelectPlantAdapter(this);
        filtrateAdapter.setData(orderBeanList);
        view.setAdapter(filtrateAdapter);
        filtrateAdapter.setPlantFiltrateListener(new SelectPlantAdapter.PlantFiltrateListener() {
            @Override
            public void onFiltrate(ApplyAfterSaleOrderBean bean) {
                setPlantInfo(bean);
                window.dismiss();
            }
        });
//        int width = UIBaseUtils.dp2pxInt(this, 80);
        window = new PopupWindow(view, SysUtils.getScreenWidth(this), ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setContentView(view);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setBackgroundDrawable(new BitmapDrawable());
//        int paddingRight = UIBaseUtils.dp2pxInt(this, 0);
//        int xoff = SysUtils.getScreenWidth(this) - width - paddingRight;
        window.showAsDropDown(lineTv);
//        L.d(L.TAG,"width:"+width+",paddingRight:"+paddingRight+",xoff:"+xoff);
    }

    @OnClick({R.id.back_iv,R.id.title_tv})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.title_tv:
                popupWindow();
                break;
        }
    }
}
