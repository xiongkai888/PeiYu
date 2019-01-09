package com.lanmei.peiyu.helper;

import android.content.Context;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.WeiXinBean;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xson.common.utils.UIHelper;


/**
 * Created by xkai on 2017/7/7.
 * 微信支付帮助类(充值用)
 */

public class WXPayHelper {
    private Context mContext;
    private IWXAPI api;
    private WeiXinBean mPayParam;
    public WXPayHelper(Context context) {
        mContext = context;
    }

    public void setPayParam(WeiXinBean payParam) {
        mPayParam = payParam;
        initWX();
    }

    private void initWX() {
        if (mPayParam == null) {
            UIHelper.ToastMessage(mContext,mContext.getString(R.string.pay_failure));
            return;
        }
        api = WXAPIFactory.createWXAPI(mContext, mPayParam.getAppid());
        api.registerApp(mPayParam.getAppid());
    }

    // 微信下单充值
    public void orderNow() {
        if (mPayParam == null) {
            UIHelper.ToastMessage(mContext,mContext.getString(R.string.pay_failure));
            return;
        }
        PayReq req = new PayReq();
        req.appId = mPayParam.getAppid();
        req.partnerId = mPayParam.getPartnerId();
        req.prepayId = mPayParam.getPrepay_id();
        req.nonceStr = mPayParam.getNonce_str();
        req.timeStamp = mPayParam.getTimestamp()+"";
        req.packageValue = "Sign=WXPay";
        req.extData = "app data";
        req.sign = mPayParam.getSign();
        api.sendReq(req);
    }
}
