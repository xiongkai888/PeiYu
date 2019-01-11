package com.lanmei.peiyu.ui.mine.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.BecomeDistributorBean;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.widget.CenterTitleToolbar;

import java.util.List;

import butterknife.InjectView;


/**
 * 成为经销商
 */

public class BecomeDistributorActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @InjectView(R.id.content_tv)
    TextView contentTv;
    private ActionBar actionbar;


    @Override
    public int getContentViewId() {
        return R.layout.activity_become_distributor;
    }


    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.become_distributor);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        PeiYuApi api = new PeiYuApi("station/news");
//                api.addParams("uid",api.getUserId(context));
        HttpClient.newInstance(this).request(api, new BeanRequest.SuccessListener<NoPageListBean<BecomeDistributorBean>>() {
            @Override
            public void onResponse(NoPageListBean<BecomeDistributorBean> response) {
                if (isFinishing()) {
                    return;
                }
                List<BecomeDistributorBean> list = response.data;
                if (!com.xson.common.utils.StringUtils.isEmpty(list)){
                    BecomeDistributorBean bean = list.get(0);
                    actionbar.setTitle(bean.getTitle());
                    contentTv.setText(bean.getContent());
                }
            }
        });
    }

}
