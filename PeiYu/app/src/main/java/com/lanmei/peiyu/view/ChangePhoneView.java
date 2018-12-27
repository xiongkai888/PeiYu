package com.lanmei.peiyu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.api.AbstractApi;
import com.xson.common.api.PeiYuApi;
import com.xson.common.bean.BaseBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.CodeCountDownTimer;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;

/**
 * Created by xkai on 2017/8/8.
 * 更换手机号码(自定义view)
 */

public class ChangePhoneView extends LinearLayout{

    private Context context;
    private Button mNextStepBt;//下一步
    private EditText mPhoneEt;//手机号码
    private TextView mObtainCodeTv;//获取验证码
    private EditText mCodeEt;//输入验证码
    private TextView mTitleTv;//弹框内容
    private String mPhone;
    private CodeCountDownTimer mCountDownTimer;//获取验证码倒计时
    private boolean isChangePhone;

    public ChangePhoneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mNextStepBt = (Button) findViewById(R.id.bt_next_step);
        mPhoneEt = (EditText) findViewById(R.id.phone_et);
        mPhoneEt.setEnabled(false);
        mObtainCodeTv = (TextView) findViewById(R.id.obtain_code_tv);
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mCodeEt = (EditText) findViewById(R.id.code_et);
        mNextStepBt.setOnClickListener(new OnClickListener() {//下一步
            @Override
            public void onClick(View v) {
                mPhone = mPhoneEt.getText().toString().trim();
                if (StringUtils.isEmpty(mPhone)) {
                    UIHelper.ToastMessage(context, context.getString(R.string.input_phone_number));
                    return;
                }
                if (!StringUtils.isMobile(mPhone)) {
                    UIHelper.ToastMessage(context, context.getString(R.string.not_mobile_format));
                    return;
                }
                String code = mCodeEt.getText().toString().trim();
                if (StringUtils.isEmpty(code)) {
                    UIHelper.ToastMessage(context, context.getString(R.string.input_code));
                    return;
                }
                if (!code.equals(codeStr)) {
                    UIHelper.ToastMessage(context, context.getString(R.string.code_error));
                    return;
                }
                if ("changePhone".equals(type)){//更换手机
                    mNextStepBt.setText("确认并更换");
                    mPhoneEt.setText("");
                    mCodeEt.setText("");
                    mPhoneEt.setHint("请输入需要更新的手机号");
                    mPhoneEt.setEnabled(true);
                    mCountDownTimer.cancel();
                    mCountDownTimer.onFinish();
                    if (isChangePhone){
                        ajaxChangePhone();
                    }
                    isChangePhone = true;
                }else {//解绑银行卡
                    loadUnBoundCar();
                }

            }
        });
        mObtainCodeTv.setOnClickListener(new OnClickListener() {//获取验证码
            @Override
            public void onClick(View v) {
                ajaxObtainCode();
            }
        });
        //初始化倒计时
        initCountDownTimer();
    }

    private void loadUnBoundCar() {
        HttpClient httpClient = HttpClient.newInstance(context);
        PeiYuApi api = new PeiYuApi("member/bank_card");
        api.addParams("uid",api.getUserId(getContext()));
        api.addParams("del",1);//表示删除
        api.addParams("id",type);
        api.setMethod(AbstractApi.Method.GET);
        httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (mChangePhoneListener != null){
                    mChangePhoneListener.unBound();
                }
            }
        });
    }

    public void setTitle(String title){
        mTitleTv.setText(title);
    }

    private void ajaxChangePhone() {
        HttpClient httpClient = HttpClient.newInstance(context);
        PeiYuApi api = new PeiYuApi("member/update");
        api.addParams("uid",api.getUserId(context));
        api.addParams("phone",mPhone);
        httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                UIHelper.ToastMessage(context,"更新号码成功");
                if (mChangePhoneListener != null){
                    mChangePhoneListener.succeed(mPhone);
                }
            }
        });
    }

    String codeStr = "好神奇的验证码";
    //获取验证码
    private void ajaxObtainCode() {
        codeStr = CommonUtils.generateNumberString(6);//随机生成的六位验证码
        HttpClient httpClient = HttpClient.newInstance(context);
        PeiYuApi api = new PeiYuApi("public/send_sms");
        api.addParams("phone",mPhone);
        api.addParams("code",codeStr);
        httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (mCountDownTimer == null){
                    return;
                }
                mCountDownTimer.start();
                UIHelper.ToastMessage(context, response.getInfo());
            }
        });
    }

    private void initCountDownTimer() {
        mCountDownTimer = new CodeCountDownTimer(context,60 * 1000, 1000,mObtainCodeTv);
    }

    public void setPhone(String phone) {
        mPhone = phone;
        mPhoneEt.setText(phone);
    }

    String type;//changePhone为更换手机号，否则为解绑银行卡

    public void setType(String type) {
        this.type = type;
        if (!"changePhone".equals(type)){
            mNextStepBt.setText("确定");
        }
    }

//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        if (mCountDownTimer != null) {
//            mCountDownTimer.cancel();
//        }
//    }


    ChangePhoneListener mChangePhoneListener;

    public void setChangePhoneListener(ChangePhoneListener l){
        mChangePhoneListener = l;
    }

    public interface ChangePhoneListener{
        void succeed(String newPhone);//更新号码成功监听
        void unBound();//解绑银行卡
    }

}
