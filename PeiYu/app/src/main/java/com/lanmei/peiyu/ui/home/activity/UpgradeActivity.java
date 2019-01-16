package com.lanmei.peiyu.ui.home.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.webkit.WebView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.BecomeDistributorBean;
import com.lanmei.peiyu.event.UpgradeEvent;
import com.lanmei.peiyu.webviewpage.WebViewPhotoBrowserUtil;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 会员升级
 */
public class UpgradeActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @InjectView(R.id.webView)
    WebView webView;

    @Override
    public int getContentViewId() {
        return R.layout.activity_upgrade;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);
        String title = getIntent().getStringExtra("value");
        actionbar.setTitle(title);

        PeiYuApi api = new PeiYuApi("station/news");
        api.addParams("title", title);
        HttpClient.newInstance(this).request(api, new BeanRequest.SuccessListener<NoPageListBean<BecomeDistributorBean>>() {
            @Override
            public void onResponse(NoPageListBean<BecomeDistributorBean> response) {
                if (isFinishing()) {
                    return;
                }
                List<BecomeDistributorBean> list = response.data;
                if (!com.xson.common.utils.StringUtils.isEmpty(list)){
                    BecomeDistributorBean bean = list.get(0);
                    WebViewPhotoBrowserUtil.photoBrowser(getContext(), webView, bean.getContent());
                }
            }
        });
    }


    @OnClick({R.id.refuse_tv, R.id.agree_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.agree_tv:
                upgrade(1);
                break;
            case R.id.refuse_tv:
                upgrade(2);
                break;
        }
    }

    /**
     *
     * @param type （1同意，2拒绝）
     */
    private void upgrade(int type) {
        PeiYuApi api = new PeiYuApi("station/upgrade");
        api.addParams("type", type);
        api.addParams("uid", api.getUserId(this));
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()){
                    return;
                }
                UIHelper.ToastMessage(getContext(),response.getInfo());
                EventBus.getDefault().post(new UpgradeEvent());
                finish();
            }
        });
    }
}
