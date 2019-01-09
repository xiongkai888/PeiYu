package com.lanmei.peiyu.alipay;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lanmei.peiyu.R;
import com.lanmei.peiyu.event.PaySucceedEvent;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * Created by xkai on 2017/7/7.
 * 支付宝支付
 */

public class AlipayHelper {

    private final int SDK_PAY_FLAG = 1;
    private String data;
    private Context mContext;

    public AlipayHelper(Context context) {
        mContext = context;
    }


    public void setPayParam(String data) {
        this.data = data;
    }

    // 支付
    public void payNow() {
        if (StringUtils.isEmpty(data)) {
            Toast.makeText(mContext, mContext.getString(R.string.pay_failure) + ",订单数据为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) mContext);
                Map<String, String> result = alipay.payV2(data, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    //对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    L.d(L.TAG, resultInfo);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(mContext, R.string.pay_succeed, Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new PaySucceedEvent());
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(mContext, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(mContext, mContext.getString(R.string.pay_failure), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    };
}
