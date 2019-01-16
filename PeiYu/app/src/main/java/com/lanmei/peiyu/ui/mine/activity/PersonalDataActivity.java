package com.lanmei.peiyu.ui.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanmei.peiyu.PeiYuApp;
import com.lanmei.peiyu.R;
import com.lanmei.peiyu.helper.CameraHelper;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.bean.UserBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.ImageHelper;
import com.xson.common.utils.ImageUtils;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.SimpleTextWatcher;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.utils.UserHelper;
import com.xson.common.widget.CenterTitleToolbar;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 个人资料
 */
public class PersonalDataActivity extends BaseActivity {

    @InjectView(R.id.personal_icons_iv)
    ImageView mPersonalIconsIV;//头像
    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.ll_personal_icons)
    LinearLayout mllHeadOnClick;//点击上传头像
    @InjectView(R.id.nick_tv)
    TextView mNickTv;//昵称
    @InjectView(R.id.rid_tv)
    TextView ridTv;//等级
    @InjectView(R.id.qq_tv)
    TextView mQqTv;//qq
    @InjectView(R.id.mail_tv)
    TextView mMailTv;//邮箱
    @InjectView(R.id.phone_tv)
    TextView mPhoneTv;//电话
    @InjectView(R.id.address_tv)
    TextView mAddressTv;//地址
    @InjectView(R.id.signature_et)
    EditText mSignatureEt;//个性签名
    @InjectView(R.id.save_bt)
    Button mSaveButton;//保存
    private CameraHelper cameraHelper;

    @Override
    public int getContentViewId() {
        return R.layout.activity_personal_data;
    }

    private UserBean bean;

    private String name;//姓名
    private String qq;//qq
    private String email;//邮箱
    private String phone;//手机号码
    private String address;//地址
    private String signature;//个性签名
    private String pic;//头像地址

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.personal_data);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        bean = UserHelper.getInstance(this).getUserBean();
        if (bean != null) {
            cameraHelper = new CameraHelper(this);
            cameraHelper.setHeadUrlListener(new CameraHelper.HeadUrlListener() {
                @Override
                public void getUrl(String url) {
                    if (isFinishing()) {
                        return;
                    }
                    loadUpData(url);
                }
            });

            name = bean.getNickname();
            qq = bean.getQq();
            email = bean.getEmail();
            phone = bean.getPhone();
            address = bean.getAddress();
            signature = bean.getSignature();
            pic = bean.getPic();
            cameraHelper.setHeadPathStr(pic);

            mNickTv.setText(name);
            mQqTv.setText(qq);
            mMailTv.setText(email);
            mPhoneTv.setText(phone);
            mAddressTv.setText(address);
            mSignatureEt.setText(signature);
            ridTv.setText(bean.getRidname());
            ImageHelper.load(this, pic, mPersonalIconsIV, null, true, R.mipmap.default_pic, R.mipmap.default_pic);
        }
        mSaveButton.setEnabled(false);
        mSaveButton.setBackgroundResource(R.drawable.button_corners_4_999);
        mSignatureEt.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dataIsChange();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //资料是否有改动过
    private void dataIsChange() {
        String cNickTv = mNickTv.getText().toString().trim();
        String cPhone = mPhoneTv.getText().toString().trim();
        String cQianming = mSignatureEt.getText().toString().trim();
        String cEmail = mMailTv.getText().toString().trim();
        String cQq = mQqTv.getText().toString().trim();
        String cAddress = mAddressTv.getText().toString().trim();
        if (!StringUtils.isSame(cNickTv,name) || !StringUtils.isSame(cQianming,signature) || !StringUtils.isSame(cEmail,email)
                || !StringUtils.isSame(cAddress,address)
                || !StringUtils.isSame(cQq,qq)
                || !StringUtils.isSame(cPhone,phone)
                || !StringUtils.isSame(cameraHelper.getHeadPathStr(), pic)) {
            mSaveButton.setEnabled(true);
            mSaveButton.setBackgroundResource(R.drawable.button_corners_4_radius);
        } else {
            mSaveButton.setEnabled(false);
            mSaveButton.setBackgroundResource(R.drawable.button_corners_4_999);
        }
    }


    private void loadUpData(final String headUrl) {
        final String name = CommonUtils.getStringByTextView(mNickTv);
        final String qq = CommonUtils.getStringByTextView(mQqTv);//
        final String email =  CommonUtils.getStringByTextView(mMailTv);
        final String phone = CommonUtils.getStringByTextView(mPhoneTv);//
        final String address = CommonUtils.getStringByTextView(mAddressTv);//
        final String signature = CommonUtils.getStringByEditText(mSignatureEt);//

        PeiYuApi api = new PeiYuApi("member/update");
        api.addParams("uid",api.getUserId(this));
        if (StringUtils.isEmpty(headUrl)){
            api.addParams("pic",pic);
        }else {
            api.addParams("pic",headUrl);
        }
        api.addParams("nickname",name);
        api.addParams("qq",qq);
        api.addParams("email",email);
        api.addParams("phone",phone);
        api.addParams("address",address);
        api.addParams("signature",signature);
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()) {
                    return;
                }
                UIHelper.ToastMessage(getContext(), response.getInfo());
                if (!StringUtils.isEmpty(headUrl)){
                    CommonUtils.deleteOssObject(pic);//更新头像后删除从oss删除旧的头像
                }
                CommonUtils.loadUserInfo(PeiYuApp.getInstance(),null);
                finish();
            }
        });
    }

    @OnClick({R.id.ll_personal_icons,R.id.ll_nick, R.id.ll_QQ, R.id.ll_mail, R.id.ll_phone, R.id.ll_address, R.id.save_bt})
    public void showOnClick(View view) {
        switch (view.getId()) {//1:我的昵称  2：我的qq  3:我的邮箱  4：我的电话  5：我的联系地址
            case R.id.ll_personal_icons:////上传头像
                cameraHelper.showDialog();
                break;
            case R.id.ll_nick://昵称
                startActivityPersonal(101, CommonUtils.getStringByTextView(mNickTv));
                break;
            case R.id.ll_QQ://qq
                startActivityPersonal(102, CommonUtils.getStringByTextView(mQqTv));
                break;
            case R.id.ll_mail://邮箱
                startActivityPersonal(103, CommonUtils.getStringByTextView(mMailTv));
                break;
            case R.id.ll_phone://电话
                startActivityPersonal(104, CommonUtils.getStringByTextView(mPhoneTv));
                break;
            case R.id.ll_address://地址
                startActivityPersonal(105, CommonUtils.getStringByTextView(mAddressTv));
                break;
            case R.id.save_bt://保存
                ajaxSaveDate();
                break;
        }
    }

    private void startActivityPersonal(int type,String value) {
        IntentUtil.startActivityForResult(this,PersonalCompileActivity.class,value,type);
    }


    private void ajaxSaveDate() {
        if (StringUtils.isSame(cameraHelper.getHeadPathStr(), pic)) {
            loadUpData("");
        } else {
            cameraHelper.execute();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        String compile = "";
        if (data != null) {
            compile = data.getStringExtra("compile");
        }
        String image;
        switch (requestCode) {
            case CameraHelper.CHOOSE_FROM_GALLAY:
                image = ImageUtils.getImageFileFromPickerResult(this, data);
                cameraHelper.startActionCrop(image);
                break;
            case CameraHelper.CHOOSE_FROM_CAMERA:
                //注意小米拍照后data 为null
                image = cameraHelper.getTempImage().getPath();
                cameraHelper.startActionCrop(image);
                break;
            case CameraHelper.RESULT_FROM_CROP:
                cameraHelper.uploadNewPhoto(mPersonalIconsIV);//
                dataIsChange();
                break;
            case 101:
                mNickTv.setText(compile);
                dataIsChange();
                break;
            case 102:
                mQqTv.setText(compile);
                dataIsChange();
                break;
            case 103:
                mMailTv.setText(compile);
                dataIsChange();
                break;
            case 104:
                mPhoneTv.setText(compile);
                dataIsChange();
                break;
            case 105:
                mAddressTv.setText(compile);
                dataIsChange();
                break;
            default:
                break;
        }
    }
}
