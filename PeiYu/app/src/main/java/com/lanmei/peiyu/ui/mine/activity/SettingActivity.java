package com.lanmei.peiyu.ui.mine.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.event.LoginQuitEvent;
import com.lanmei.peiyu.ui.login.LoginActivity;
import com.lanmei.peiyu.ui.login.RegisterActivity;
import com.lanmei.peiyu.ui.shopping.shop.DBShopCartHelper;
import com.lanmei.peiyu.update.UpdateAppConfig;
import com.lanmei.peiyu.update.UpdateEvent;
import com.lanmei.peiyu.utils.AKDialog;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.app.BaseActivity;
import com.xson.common.helper.DataCleanManager;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.UIHelper;
import com.xson.common.utils.UserHelper;
import com.xson.common.widget.CenterTitleToolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;
import butterknife.OnClick;


/**
 * 设置
 */

public class SettingActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.cache_count)
    TextView mCleanCacheTv;


    @Override
    public int getContentViewId() {
        return R.layout.activity_setting;
    }


    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.setting);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        EventBus.getDefault().register(this);

        try {
            mCleanCacheTv.setText(DataCleanManager.getCacheSize(getCacheDir()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showClearCache() {
        try {
            DataCleanManager.cleanInternalCache(getApplicationContext());
            DataCleanManager.cleanExternalCache(getApplicationContext());
            mCleanCacheTv.setText(DataCleanManager.getCacheSize(getCacheDir()));
            UIHelper.ToastMessage(this, "清理完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Subscribe
    public void updateEvent(UpdateEvent event) {
        UIHelper.ToastMessage(this, event.getContent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @OnClick({R.id.help_tv,R.id.about_tv, R.id.ll_clean_cache, R.id.alter_pw_tv, R.id.versions_tv, R.id.back_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.help_tv://帮助信息
                IntentUtil.startActivity(this,BecomeDistributorActivity.class,getString(R.string.help_info));
                break;
            case R.id.about_tv://关于我们
                IntentUtil.startActivity(this,BecomeDistributorActivity.class,getString(R.string.about_us));
                break;
            case R.id.ll_clean_cache://清除缓存
                showClearCache();
                break;
            case R.id.alter_pw_tv://修改密码
                IntentUtil.startActivity(this,RegisterActivity.class,CommonUtils.isThree);
                break;
            case R.id.versions_tv://版本更新
                UpdateAppConfig.requestStoragePermission(this);
                break;
            case R.id.back_login://退出登录
                AKDialog.getAlertDialog(this, "确认退出登录？", new AKDialog.AlertDialogListener() {
                    @Override
                    public void yes() {
                        UserHelper.getInstance(SettingActivity.this).cleanLogin();
                        HttpClient.newInstance(SettingActivity.this).clearCache();
                        // show login screen
                        EventBus.getDefault().post(new LoginQuitEvent());//
//                        Toast.makeText(SettingActivity.this, "退出成功", Toast.LENGTH_LONG).show();
                        IntentUtil.startActivity(getContext(), LoginActivity.class);
                        DBShopCartHelper.dbGoodsCartManager = null;//不同用户登录uid重新赋值
                        onBackPressed();
                    }
                });
                break;
        }
    }


}
