package com.lanmei.peiyu.ui.mine.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lanmei.peiyu.PeiYuApp;
import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.WithdrawCardListBean;
import com.lanmei.peiyu.event.SetUserInfoEvent;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.BaseBean;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.bean.UserBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.SimpleTextWatcher;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/27.
 * 提现
 */

public class WithdrawFragment extends BaseFragment {

    @InjectView(R.id.card_tv)
    TextView mCardNameTv;
    @InjectView(R.id.balance_tv)
    TextView mBalanceTv;//余额
    @InjectView(R.id.money_et)
    EditText moneyEt;//
    private WithdrawCardListBean bean;
    private double withdrawMoney = 100;//

    @Override
    public int getContentViewId() {
        return R.layout.fragment_withdraw;
    }

    public static WithdrawFragment newInstance() {
        Bundle args = new Bundle();
        WithdrawFragment fragment = new WithdrawFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }
        CommonUtils.loadUserInfo(PeiYuApp.getInstance(), null);
        ajaxWithdraw();
        moneyEt.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isEmpty("" + s)) {
                    withdrawMoney = 0;
                } else {
                    withdrawMoney = Double.valueOf(s + "");
                }
            }
        });
    }

    @Subscribe
    public void onEventMainThread(SetUserInfoEvent event) {
        UserBean bean = event.getBean();
        if (mBalanceTv != null && bean != null) {
            mBalanceTv.setText(bean.getMoney());
        }
    }

    private void ajaxWithdraw() {
        HttpClient httpClient = HttpClient.newInstance(context);
        PeiYuApi api = new PeiYuApi("member/bank_card");
        api.addParams("uid", api.getUserId(context));
        httpClient.request(api, new BeanRequest.SuccessListener<NoPageListBean<WithdrawCardListBean>>() {
            @Override
            public void onResponse(NoPageListBean<WithdrawCardListBean> response) {
                if (mCardNameTv == null) {
                    return;
                }
                List<WithdrawCardListBean> list = response.data;
                if (list != null && list.size() > 0) {
                    bean = list.get(0);
                    if (bean != null) {
                        mCardNameTv.setText(bean.getBanks_name());
                    }
                } else {
                    alertDialog();
                }
            }
        });
    }
//
//    @Subscribe
//    public void cardEvent(CardEvent event) {
//        switch (event.getType()) {
//            case 1:
//                bean = event.getBean();
//                mCardNameTv.setText(bean.getBanks_name());
//                L.d(L.TAG,"name:"+bean.getBanks_name());
//                break;
//            case 2:
//                ajaxWithdraw();
//                break;
//        }
//    }

    private void alertDialog() {
//        AKDialog.getAlertDialog(context, getString(R.string.no_bound_card), new AKDialog.AlertDialogListener() {
//            @Override
//            public void yes() {
//                IntentUtil.startActivity(context, BoundKaActivity.class);
//            }
//        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }


    @OnClick({R.id.ll_choose_ka, R.id.save_bt, R.id.add_tv, R.id.subtract_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_choose_ka:
                CommonUtils.developing(context);
//                IntentUtil.startActivity(context, ChooseKaActivity.class);
                break;
            case R.id.save_bt:
                CommonUtils.developing(context);
//                if (StringUtils.isEmpty(bean)) {
//                    UIHelper.ToastMessage(context, getString(R.string.choose_bank_card));
//                    break;
//                }
//                String money = CommonUtils.getStringByEditText(moneyEt);
//                if (StringUtils.isEmpty(money)) {
//                    UIHelper.ToastMessage(context, "请输入提现金额");
//                    break;
//                }
//                if (withdrawMoney < 100) {
//                    UIHelper.ToastMessage(context, "提现金额需100元及以上");
//                    break;
//                }
//
//                AKDialog.getAlertDialog(context, "确定申请提现？", new AKDialog.AlertDialogListener() {
//                    @Override
//                    public void yes() {
//                        withdraw();
//                    }
//                });
                break;
            case R.id.add_tv://加钱
                moneyEt.setText((withdrawMoney + 100) + "");
                break;
            case R.id.subtract_tv://减钱
                if (withdrawMoney <= 100) {
                    UIHelper.ToastMessage(context, "提现金额需100元及以上");
                    moneyEt.setText("100");
                    break;
                }
                moneyEt.setText((withdrawMoney - 100) + "");
                break;
        }
    }

    private void withdraw() {
        PeiYuApi api = new PeiYuApi("member/withdraw");
        api.addParams("uid", api.getUserId(context));
        api.addParams("banks_id", bean.getId());
        api.addParams("money", withdrawMoney);
        HttpClient.newInstance(context).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (mBalanceTv == null) {
                    return;
                }
                UIHelper.ToastMessage(context, response.getInfo());
                CommonUtils.loadUserInfo(PeiYuApp.getInstance(), null);
            }
        });
    }
}
