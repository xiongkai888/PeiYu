package com.lanmei.peiyu.ui.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.event.SetUserInfoEvent;
import com.lanmei.peiyu.ui.login.RegisterActivity;
import com.lanmei.peiyu.ui.mine.activity.AfterSaleOrderActivity;
import com.lanmei.peiyu.ui.mine.activity.InstallApplyActivity;
import com.lanmei.peiyu.ui.mine.activity.MineOrderActivity;
import com.lanmei.peiyu.ui.mine.activity.MinePowerStationActivity;
import com.lanmei.peiyu.ui.mine.activity.PersonalDataActivity;
import com.lanmei.peiyu.ui.mine.activity.SettingActivity;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.BaseBean;
import com.xson.common.bean.UserBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.ImageHelper;
import com.xson.common.utils.IntentUtil;
import com.xson.common.widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xkai on 2018/7/13.
 * 我的
 */

public class MineFragment extends BaseFragment {

    @InjectView(R.id.pic_iv)
    CircleImageView picIv;
    @InjectView(R.id.name_tv)
    TextView nameTv;
    @InjectView(R.id.m01_tv)
    TextView m01Tv;
    @InjectView(R.id.m02_tv)
    TextView m02Tv;
    @InjectView(R.id.m03_tv)
    TextView m03Tv;
    @InjectView(R.id.m04_tv)
    TextView m04Tv;
    @InjectView(R.id.m05_tv)
    TextView m05Tv;
    @InjectView(R.id.m1_num_tv)
    TextView m1NumTv;
    @InjectView(R.id.m2_num_tv)
    TextView m2NumTv;
    @InjectView(R.id.m3_num_tv)
    TextView m3NumTv;
    @InjectView(R.id.m4_num_tv)
    TextView m4NumTv;
    @InjectView(R.id.m5_num_tv)
    TextView m5NumTv;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        setNum(m3NumTv,6);
        setUserBean(CommonUtils.getUserBean(context));
    }

    private void setUserBean(UserBean userBean) {
        if (userBean == null) {
            nameTv.setText("游客");
            picIv.setImageResource(R.mipmap.default_pic);
            return;
        }
        nameTv.setText(userBean.getNickname());
        ImageHelper.load(context, userBean.getPic(), picIv, null, true, R.mipmap.default_pic, R.mipmap.default_pic);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SetUserInfoEvent event) {
        setUserBean(event.getBean());
    }

    /**
     * 设置订单个数
     * @param textView
     * @param num
     */
    private void setNum(TextView textView,int num){
        if (num == 0){
            textView.setVisibility(View.GONE);
        }else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(num+"");
        }
    }


    @OnClick({R.id.m_message_iv, R.id.pic_iv, R.id.m01_tv, R.id.m02_tv, R.id.m03_tv, R.id.m04_tv, R.id.m05_tv, R.id.m1_rl, R.id.m2_rl, R.id.m3_rl, R.id.m4_rl, R.id.m5_rl, R.id.m6_tv, R.id.m7_tv, R.id.m8_tv, R.id.m9_tv, R.id.m10_tv, R.id.m11_tv, R.id.m12_tv, R.id.m13_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_message_iv://消息
                CommonUtils.developing(context);
                break;
            case R.id.pic_iv://头像
                IntentUtil.startActivity(context, PersonalDataActivity.class);
                break;
            case R.id.m01_tv://余额
                CommonUtils.developing(context);
                break;
            case R.id.m02_tv://我的团队
                CommonUtils.developing(context);
                break;
            case R.id.m03_tv://我的业绩
                CommonUtils.developing(context);
                break;
            case R.id.m04_tv://团队业绩
                CommonUtils.developing(context);
                break;
            case R.id.m05_tv://我的电站
                IntentUtil.startActivity(context, MinePowerStationActivity.class);
                break;
            case R.id.m1_rl://待付款
            case R.id.m2_rl://待发货
            case R.id.m3_rl://待收货
            case R.id.m4_rl://待评价
            case R.id.m5_rl://退款/售后
                IntentUtil.startActivity(context, MineOrderActivity.class);
                break;
            case R.id.m6_tv://安装信息
                IntentUtil.startActivity(context, InstallApplyActivity.class);
                break;
            case R.id.m7_tv://售后工单
                IntentUtil.startActivity(context, AfterSaleOrderActivity.class);
                break;
            case R.id.m8_tv://在线客服
                CommonUtils.developing(context);
                break;
            case R.id.m9_tv://成为经销商
                CommonUtils.developing(context);
                break;
            case R.id.m10_tv://我的收藏
                CommonUtils.developing(context);

                PeiYuApi api = new PeiYuApi("station/station_list");
                api.addParams("uid",api.getUserId(context));
                HttpClient.newInstance(context).request(api, new BeanRequest.SuccessListener<BaseBean>() {
                    @Override
                    public void onResponse(BaseBean response) {

                    }
                });
                break;
            case R.id.m11_tv://我的地址
                CommonUtils.developing(context);
                break;
            case R.id.m12_tv://修改密码
                IntentUtil.startActivity(context,RegisterActivity.class,CommonUtils.isThree);
                break;
            case R.id.m13_tv://设置
                IntentUtil.startActivity(context, SettingActivity.class);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
