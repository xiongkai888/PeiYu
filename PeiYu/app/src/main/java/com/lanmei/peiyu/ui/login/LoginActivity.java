package com.lanmei.peiyu.ui.login;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lanmei.peiyu.MainActivity;
import com.lanmei.peiyu.R;
import com.lanmei.peiyu.event.RegisterEvent;
import com.lanmei.peiyu.event.SetUserInfoEvent;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.DataBean;
import com.xson.common.bean.UserBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.SharedAccount;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UserHelper;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.DrawClickableEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @InjectView(R.id.phone_et)
    DrawClickableEditText phoneEt;
    @InjectView(R.id.pwd_et)
    DrawClickableEditText pwdEt;
    @InjectView(R.id.showPwd_iv)
    ImageView showPwdIv;

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }


    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(R.string.login);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);
        String mobile = SharedAccount.getInstance(this).getPhone();
        phoneEt.setText(mobile);
        EventBus.getDefault().register(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_register:
                IntentUtil.startActivity(this, RegisterActivity.class, CommonUtils.isOne);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.showPwd_iv, R.id.forgotPwd_tv, R.id.login_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.showPwd_iv://显示密码
                showPwd();
                break;
            case R.id.forgotPwd_tv://忘记密码
                IntentUtil.startActivity(this, RegisterActivity.class, CommonUtils.isTwo);
                break;
            case R.id.login_bt://登录
                login();
                break;
        }
    }

    String phone;

    private void login() {
        phone = CommonUtils.getStringByEditText(phoneEt);
        if (StringUtils.isEmpty(phone)) {
            Toast.makeText(this, R.string.input_phone_number, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!StringUtils.isMobile(phone)) {
            Toast.makeText(this, R.string.not_mobile_format, Toast.LENGTH_SHORT).show();
            return;
        }
        String pwd = CommonUtils.getStringByEditText(pwdEt);
        if (StringUtils.isEmpty(pwd) || pwd.length() < 6) {
            Toast.makeText(this, R.string.input_password_count, Toast.LENGTH_SHORT).show();
            return;
        }
        PeiYuApi api = new PeiYuApi("public/login");
        api.addParams("phone", phone);
        api.addParams("password", pwd);
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<DataBean<UserBean>>() {
            @Override
            public void onResponse(DataBean<UserBean> response) {
                if (isFinishing()) {
                    return;
                }
                UserBean bean = response.data;
                SharedAccount.getInstance(getContext()).savePhone(phone);
                UserHelper.getInstance(getContext()).saveBean(bean);
                MainActivity.showHome(getContext());
                EventBus.getDefault().post(new SetUserInfoEvent(bean));
                finish();
            }
        });
    }


    private boolean isShowPwd = false;//是否显示密码

    private void showPwd() {
        if (!isShowPwd) {//显示密码
            pwdEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showPwdIv.setImageResource(R.mipmap.pwd_on);
        } else {//隐藏密码
            pwdEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showPwdIv.setImageResource(R.mipmap.pwd_off);
        }
        isShowPwd = !isShowPwd;
    }

    //注册后调用
    @Subscribe
    public void respondRegisterEvent(RegisterEvent event) {
        phoneEt.setText(event.getPhone());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}