package com.lanmei.peiyu.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lanmei.peiyu.MainActivity;
import com.lanmei.peiyu.PeiYuApp;
import com.lanmei.peiyu.R;
import com.lanmei.peiyu.event.LoginQuitEvent;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.bean.UserBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.SharedAccount;
import com.xson.common.utils.CodeCountDownTimer;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.utils.UserHelper;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.DrawClickableEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 绑定手机
 */
public class BindingPhoneActivity extends BaseActivity{

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    private UserBean bean;
    @InjectView(R.id.obtainCode_bt)
    Button mObtainCodeBt;
    @InjectView(R.id.et_mobile)
    DrawClickableEditText mEdMolile;//手机号
    @InjectView(R.id.et_code)
    DrawClickableEditText mEdCode;//验证码
    @InjectView(R.id.tgid_et)
    DrawClickableEditText tgidEt;//推荐人ID/手机号

    private CodeCountDownTimer mCountDownTimer;//获取验证码倒计时

    @Override
    public int getContentViewId() {
        return R.layout.activity_binding_phone;
    }

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        Bundle bundle = intent.getBundleExtra("bundle");
        if (bundle != null) {
            bean = (UserBean) bundle.getSerializable("bean");
        }
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        //初始化倒计时
        mCountDownTimer = new CodeCountDownTimer(this, 60 * 1000, 1000, mObtainCodeBt);

        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("绑定手机");
        actionbar.setHomeAsUpIndicator(R.mipmap.back);
        UIHelper.ToastMessage(this,"先绑定手机号");
    }


    private String mMobile;

    String codeStr = "-000k";

    private void ajaxObtainCode(String mobile) {
        this.mMobile = mobile;//获取验证码的手机号
        codeStr = CommonUtils.generateNumberString(6);//随机生成的六位验证码
        L.d("codeStr", codeStr);
        HttpClient httpClient = HttpClient.newInstance(this);

        PeiYuApi api = new PeiYuApi("public/send_sms");
        api.addParams("phone", mobile);
        api.addParams("code", codeStr);

        httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()) {
                    return;
                }
                mCountDownTimer.start();
                UIHelper.ToastMessage(getContext(), response.getInfo());
            }
        });
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }


    @OnClick({R.id.obtainCode_bt, R.id.sure_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.obtainCode_bt:
                mMobile = CommonUtils.getStringByEditText(mEdMolile);//电话号码
                if (StringUtils.isEmpty(mMobile)) {
                    UIHelper.ToastMessage(this, R.string.input_phone_number);
                    return;
                }
                if (!StringUtils.isMobile(mMobile)) {
                    Toast.makeText(this, R.string.not_mobile_format, Toast.LENGTH_SHORT).show();
                    return;
                }
                PeiYuApi api = new PeiYuApi("Public/user_search");
                api.addParams("phone",mMobile);
                HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<NoPageListBean<UserBean>>() {
                    @Override
                    public void onResponse(NoPageListBean<UserBean> response) {
                        if (isFinishing()){
                            return;
                        }
                        if (!StringUtils.isEmpty(response.data)){
                            UIHelper.ToastMessage(getContext(),"该手机号已经被其他用户绑定");
                            return;
                        }
                        ajaxObtainCode(mMobile);
                    }
                });

                break;
            case R.id.sure_bt:
                if (StringUtils.isEmpty(mMobile)) {
                    UIHelper.ToastMessage(this, R.string.input_phone_number);
                    return;
                }
                if (!StringUtils.isMobile(mMobile)) {
                    Toast.makeText(this, R.string.not_mobile_format, Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = CommonUtils.getStringByEditText(mEdCode);//
                if (StringUtils.isEmpty(code)) {
                    UIHelper.ToastMessage(this, R.string.input_code);
                    return;
                }
                if (!code.equals(codeStr)) {
                    UIHelper.ToastMessage(this, R.string.code_error);
                    return;
                }
                loadBindingPhone();
                break;
        }
    }

    private void loadBindingPhone() {
        PeiYuApi api = new PeiYuApi("member/update");
        api.addParams("uid",bean.getId());
        api.addParams("phone",mMobile);
        api.addParams("tgid",CommonUtils.getStringByEditText(tgidEt));
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()){
                    return;
                }
                EventBus.getDefault().post(new LoginQuitEvent());//
                UIHelper.ToastMessage(getContext(),response.getInfo());
                UserHelper.getInstance(getContext()).saveBean(bean);
                CommonUtils.loadUserInfo(PeiYuApp.getInstance(),null);
                SharedAccount.getInstance(getContext()).savePhone(mMobile);
                MainActivity.showHome(getContext());
                finish();
            }
        });
    }



}
