package com.lanmei.peiyu.ui.home.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.AnnouncementListBean;
import com.lanmei.peiyu.bean.NotificationBean;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;
import com.xson.common.widget.CenterTitleToolbar;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 消息中心
 */
public class MessageCenterActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @InjectView(R.id.gonggao_tv)
    TextView gonggaoTv;
    @InjectView(R.id.sixin_tv)
    TextView sixinTv;

    @Override
    public int getContentViewId() {
        return R.layout.activity_message_center;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.message_center);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);
        loadGongGao();
        loadSiXin();
    }

    @OnClick({R.id.ll_announcement, R.id.ll_notice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_announcement://公告信息
                IntentUtil.startActivity(this, AnnouncementActivity.class);
                break;
            case R.id.ll_notice://私信
                IntentUtil.startActivity(this, NotificationActivity.class);//
                break;
        }
    }

    private void loadGongGao(){
        PeiYuApi api = new PeiYuApi("station/news");
        api.addParams("type",1);
        HttpClient.newInstance(this).request(api, new BeanRequest.SuccessListener<NoPageListBean<AnnouncementListBean>>() {
            @Override
            public void onResponse(NoPageListBean<AnnouncementListBean> response) {
                if (isFinishing()){
                    return;
                }
                List<AnnouncementListBean> list = response.data;
                if (!StringUtils.isEmpty(list)){
                    AnnouncementListBean bean = list.get(0);
                    gonggaoTv.setText(bean.getIntro());
                }
            }
        });
    }
    private void loadSiXin(){
        PeiYuApi api = new PeiYuApi("station/sixin");
        api.addParams("uid",api.getUserId(this));
        HttpClient.newInstance(this).request(api, new BeanRequest.SuccessListener<NoPageListBean<NotificationBean>>() {
            @Override
            public void onResponse(NoPageListBean<NotificationBean> response) {
                if (isFinishing()){
                    return;
                }
                List<NotificationBean> list = response.data;
                if (!StringUtils.isEmpty(list)){
                    NotificationBean bean = list.get(0);
                    sixinTv.setText(bean.getIntro());
                }
            }
        });
    }

}
