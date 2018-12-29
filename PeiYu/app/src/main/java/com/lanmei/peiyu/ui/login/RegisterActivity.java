package com.lanmei.peiyu.ui.login;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.event.RegisterEvent;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.CodeCountDownTimer;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.DrawClickableEditText;
import com.xson.common.widget.FormatTextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 注册、忘记密码、重设密码
 */
public class RegisterActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @InjectView(R.id.phone_et)
    DrawClickableEditText phoneEt;
    @InjectView(R.id.code_et)
    DrawClickableEditText codeEt;
    @InjectView(R.id.obtainCode_bt)
    Button obtainCodeBt;
    @InjectView(R.id.pwd_et)
    DrawClickableEditText pwdEt;
    @InjectView(R.id.showPwd_iv)
    ImageView showPwdIv;
    @InjectView(R.id.pwd_again_et)
    DrawClickableEditText pwdAgainEt;
    @InjectView(R.id.showPwd_again_iv)
    ImageView showPwdAgainIv;
    @InjectView(R.id.id_et)
    DrawClickableEditText idEt;
    @InjectView(R.id.ll_id)
    LinearLayout llId;
    @InjectView(R.id.register_bt)
    Button button;
    @InjectView(R.id.agree_protocol_tv)
    FormatTextView agreeProtocolTv;

    private String type;
    private CodeCountDownTimer mCountDownTimer;//获取验证码倒计时


    @Override
    public int getContentViewId() {
        return R.layout.activity_register;
    }


    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        //初始化倒计时
        mCountDownTimer = new CodeCountDownTimer(this, 60 * 1000, 1000, obtainCodeBt);

        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_register);
        //toolbar的menu点击事件的监听
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        type = getIntent().getStringExtra("value");

        if (StringUtils.isSame(type, CommonUtils.isOne)) {//1是注册2是找回密码
            llId.setVisibility(View.VISIBLE);
            agreeProtocolTv.setVisibility(View.VISIBLE);
            toolbar.setTitle(R.string.register);
        } else if (StringUtils.isSame(type, CommonUtils.isTwo)) {
            llId.setVisibility(View.GONE);
            agreeProtocolTv.setVisibility(View.GONE);
            toolbar.setTitle("找回密码");
            button.setText(R.string.sure);
        } else if (StringUtils.isSame(type, CommonUtils.isThree)) {
            llId.setVisibility(View.GONE);
            agreeProtocolTv.setVisibility(View.GONE);
            toolbar.setTitle("修改密码");
            button.setText(R.string.sure);
            toolbar.getMenu().clear();
        }
    }

    //注册或找回密码、修改密码
    private void registerOrRetrievePwd(final String phone, String pwd) {
        PeiYuApi api = new PeiYuApi("public/resetPwd");//找回密码、修改密码
        api.addParams("phone",phone);
        api.addParams("password",pwd);
        if (StringUtils.isSame(type,CommonUtils.isOne)){
            api.setPath("public/regist");//注册
            api.addParams("nickname","p_"+phone);
            api.addParams("repassword",pwd);
            api.addParams("tgid",CommonUtils.getStringByEditText(idEt));
        }
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                EventBus.getDefault().post(new RegisterEvent(phone));
                UIHelper.ToastMessage(RegisterActivity.this,response.getInfo());
                finish();
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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        onBackPressed();
        return true;
    }


    private boolean isShowPwd = false;//是否显示密码
    private boolean isShowPwdAgain = false;//是否再次显示密码


    private void showPwd(boolean isShow, DrawClickableEditText editText, ImageView imageView, int type) {
        if (!isShow) {//再次显示密码
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            imageView.setImageResource(R.mipmap.pwd_on);
        } else {//隐藏密码
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            imageView.setImageResource(R.mipmap.pwd_off);
        }
        if (type == 1) {
            isShowPwd = !isShow;
        } else {
            isShowPwdAgain = !isShow;
        }
    }


    @OnClick({R.id.showPwd_iv, R.id.showPwd_again_iv, R.id.register_bt, R.id.obtainCode_bt, R.id.agree_protocol_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.showPwd_iv:
                showPwd(isShowPwd, pwdEt, showPwdIv, 1);
                break;
            case R.id.showPwd_again_iv://注册
                showPwd(isShowPwdAgain, pwdAgainEt, showPwdAgainIv, 2);
                break;
            case R.id.register_bt://注册
                loadRegister();
                break;
            case R.id.obtainCode_bt://获取验证码
                phone = CommonUtils.getStringByEditText(phoneEt);//电话号码
                if (StringUtils.isEmpty(phone)) {
                    UIHelper.ToastMessage(this, R.string.input_phone_number);
                    return;
                }
                if (!StringUtils.isMobile(phone)) {
                    Toast.makeText(this, R.string.not_mobile_format, Toast.LENGTH_SHORT).show();
                    return;
                }
                loadObtainCode(phone);
                break;
            case R.id.agree_protocol_tv://用户协议
                IntentUtil.startActivity(this,ProtocolActivity.class);
                break;
        }
    }


    private String phone;
    String codeStr = "-000k";

    //获取验证码
    private void loadObtainCode(String phone) {
        codeStr = CommonUtils.generateNumberString(6);//随机生成的六位验证码
        HttpClient httpClient = HttpClient.newInstance(this);
        PeiYuApi api = new PeiYuApi("public/send_sms");
        api.addParams("phone", phone);//send
        api.addParams("code", codeStr);//
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

    //注册
    private void loadRegister() {
        phone = CommonUtils.getStringByEditText(phoneEt);//电话号码
        if (StringUtils.isEmpty(phone)) {
            UIHelper.ToastMessage(this, R.string.input_phone_number);
            return;
        }
        if (!StringUtils.isMobile(phone)) {
            Toast.makeText(this, R.string.not_mobile_format, Toast.LENGTH_SHORT).show();
            return;
        }
        String code = CommonUtils.getStringByEditText(codeEt);//
        if (StringUtils.isEmpty(code)) {
            UIHelper.ToastMessage(this, R.string.input_code);
            return;
        }
        String pwd = CommonUtils.getStringByEditText(pwdEt);//
        if (StringUtils.isEmpty(pwd) || pwd.length() < 6) {
            UIHelper.ToastMessage(this, R.string.input_password_count);
            return;
        }
        String pwdAgain = CommonUtils.getStringByEditText(pwdAgainEt);//
        if (StringUtils.isEmpty(pwdAgain)) {
            UIHelper.ToastMessage(this, R.string.input_pwd_again);
            return;
        }
        if (!StringUtils.isSame(pwd, pwdAgain)) {
            UIHelper.ToastMessage(this, R.string.password_inconformity);
            return;
        }
        if (!code.equals(codeStr)){
            UIHelper.ToastMessage(this,R.string.code_error);
            return;
        }

        registerOrRetrievePwd(phone, pwd);
    }

}
