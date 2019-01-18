package com.lanmei.peiyu.ui.login;

import android.content.Intent;
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
import com.lanmei.peiyu.event.LoginQuitEvent;
import com.lanmei.peiyu.event.RegisterEvent;
import com.lanmei.peiyu.event.SetUserInfoEvent;
import com.lanmei.peiyu.utils.CommonUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.DataBean;
import com.xson.common.bean.UserBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.SharedAccount;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.utils.UserHelper;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.DrawClickableEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

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
    private UMShareAPI mShareAPI;//友盟第三方登录api

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
        mShareAPI = UMShareAPI.get(this);
    }

    private void getUserInfo(SHARE_MEDIA platform) {

        mShareAPI.getPlatformInfo(this, platform, new UMAuthListener() {

            @Override
            public void onStart(SHARE_MEDIA share_media) {
//                L.d("impower", "onStart");
            }

            @Override
            public void onError(SHARE_MEDIA arg0, int arg1, Throwable arg2) {
            }

            @Override
            public void onComplete(SHARE_MEDIA arg0, int arg1, Map<String, String> data) {
                // 拿到具体数据
                otherTypeLogin("1", data.get("openid"), data.get("name"),
                        data.get("iconurl"));
            }

            @Override
            public void onCancel(SHARE_MEDIA arg0, int arg1) {

            }
        });
    }

    //第三方登录授权监听
    private UMAuthListener umAuthListener = new UMAuthListener() {

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            getUserInfo(platform);
//            UIHelper.ToastMessage(getContext(), getString(R.string.impower_succeed));
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            UIHelper.ToastMessage(getContext(), getString(R.string.impower_failed));
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            UIHelper.ToastMessage(getContext(), getString(R.string.impower_cancel));
        }
    };


    protected void otherTypeLogin(String loginType, String openid, String userName, final String userImgUrl) {
        PeiYuApi api = new PeiYuApi("public/login");
        api.addParams("open_type",loginType);
        api.addParams("open_id",openid);
        api.addParams("nickname",userName);
        api.addParams("pic",userImgUrl);
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<DataBean<UserBean>>() {
            @Override
            public void onResponse(DataBean<UserBean> response) {
                if (isFinishing()){
                    return;
                }
                goTo(response.data);
            }
        });
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

    @OnClick({R.id.showPwd_iv, R.id.forgotPwd_tv, R.id.login_bt,R.id.weixin_iv})
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
            case R.id.weixin_iv://维信诺登录
                doOauthVerify(SHARE_MEDIA.WEIXIN, CommonUtils.isOne);
                break;
        }
    }


    private void doOauthVerify(SHARE_MEDIA platform, String loginType) {
        mShareAPI.deleteOauth(this, platform, umAuthListener);
    }
    private String phone;

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
                goTo(response.data);
            }
        });
    }

    private void goTo(UserBean bean){
        if (StringUtils.isEmpty(bean)){
            return;
        }
        if (StringUtils.isEmpty(bean.getPhone())){//要是手机号为空就绑定手机
            Bundle bundle = new Bundle();
            bundle.putSerializable("bean",bean);
            IntentUtil.startActivity(getContext(),BindingPhoneActivity.class,bundle);
        }else {
            SharedAccount.getInstance(getContext()).savePhone(phone);
            UserHelper.getInstance(getContext()).saveBean(bean);
            MainActivity.showHome(getContext());
            EventBus.getDefault().post(new SetUserInfoEvent(bean));
            finish();
        }
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
        mShareAPI.get(this).release();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.get(this).onActivityResult(requestCode, resultCode, data);//完成回调
    }


    //绑定手机后调用
    @Subscribe
    public void loginQuitEvent(LoginQuitEvent event) {
        finish();
    }

}