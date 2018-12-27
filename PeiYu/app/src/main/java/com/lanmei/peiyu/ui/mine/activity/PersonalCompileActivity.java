package com.lanmei.peiyu.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.utils.AKDialog;
import com.xson.common.app.BaseActivity;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 我的昵称、我的QQ、我的邮箱、我的电话、我的联系地址
 */
public class PersonalCompileActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.compile_et)
    EditText mCompileEt;

    @InjectView(R.id.ll_input_name)
    LinearLayout mLLinputName;
    @InjectView(R.id.phone_iv)
    ImageView mImageView;//电话图标
    @InjectView(R.id.save_bt)
    Button mButton;//保存或者更换手机号

    @Override
    public int getContentViewId() {
        return R.layout.activity_personal_compile;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        initPersonal(actionbar);
    }

    int typeInt = 0;
    String value = "";

    private void initPersonal(ActionBar actionbar) {
        Intent intent = getIntent();
        typeInt = intent.getIntExtra("type", 0);
        value = intent.getStringExtra("value");
        switch (typeInt) {//101:我的昵称  102：我的qq  103:我的邮箱  104：我的电话  105：我的联系地址
            case 101:
                actionbar.setTitle("我的昵称");
                mCompileEt.setHint("请输入我的昵称");
                mCompileEt.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case 102:
                actionbar.setTitle("我的QQ");
                mCompileEt.setHint("请输入QQ号");
                mCompileEt.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 103:
                actionbar.setTitle("我的邮箱");
                mCompileEt.setHint("请输入邮箱");
                mCompileEt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case 104:
                actionbar.setTitle("我的电话");
                mLLinputName.setVisibility(View.GONE);
                mImageView.setVisibility(View.VISIBLE);
                mButton.setText("更换手机号");
//                actionbar.setTitle("我的电话");
//                mCompileEt.setHint(R.string.input_phone_number);
//                mCompileEt.setInputType(InputType.TYPE_CLASS_PHONE);
//                mCompileEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                break;
            case 105:
                actionbar.setTitle("我的联系地址");
                mCompileEt.setHint("请输入联系地址");
                mCompileEt.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
        }
        mCompileEt.setText(value);
    }

    AlertDialog alertDialog;

    @OnClick(R.id.save_bt)
    public void showSave() {
        if (typeInt == 104) {
            if (alertDialog != null && !alertDialog.isShowing()) {
                alertDialog.show();
                return;
            }
            if (StringUtils.isEmpty(value)) {
                UIHelper.ToastMessage(this, "您还没有绑定手机号，请先绑定");
                return;
            }
            alertDialog = AKDialog.getChangePhoneDialog(this, "验证码将以短息方式发送至您的手机，点击获取验证码按钮后请在60s内输入验证码！", value, "changePhone", new AKDialog.ChangePhoneListener() {
                @Override
                public void succeed(String newPhone) {
                    alertDialog.cancel();
                    setCompileIntent(newPhone);
                }

                @Override
                public void unBound() {

                }
            });
            alertDialog.show();
            return;
        }
        String compile = mCompileEt.getText().toString().trim();
        switch (typeInt) {//101:我的昵称  102：我的qq  103:我的邮箱  104：我的电话  105：我的联系地址
            case 101:
                if (StringUtils.isEmpty(compile)) {
                    UIHelper.ToastMessage(this, "请输入我的昵称");
                    return;
                }
                break;
            case 102:
                if (StringUtils.isEmpty(compile)) {
                    UIHelper.ToastMessage(this, "请输入QQ号");
                    return;
                }
                break;
            case 103:
                if (StringUtils.isEmpty(compile)) {
                    UIHelper.ToastMessage(this, "请输入邮箱");
                    return;
                }
                if (!StringUtils.isEmail(compile)) {
                    UIHelper.ToastMessage(this, "邮箱格式不正确");
                    return;
                }
                break;
            case 104:
                if (StringUtils.isEmpty(compile)) {
                    UIHelper.ToastMessage(this, R.string.input_phone_number);
                    return;
                }
                if (!StringUtils.isMobile(compile)) {
                    UIHelper.ToastMessage(this, R.string.not_mobile_format);
                    return;
                }
                break;
            case 105:
                if (StringUtils.isEmpty(compile)) {
                    UIHelper.ToastMessage(this, "请输入联系地址");
                    return;
                }
                break;
        }
        setCompileIntent(compile);
    }

    private void setCompileIntent(String compile) {
        Intent intent = new Intent();
        intent.putExtra("compile", compile);
        setResult(RESULT_OK, intent);
        finish();
    }
}
