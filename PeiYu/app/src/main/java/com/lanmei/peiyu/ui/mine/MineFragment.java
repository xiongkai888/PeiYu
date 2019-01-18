package com.lanmei.peiyu.ui.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.NumBean;
import com.lanmei.peiyu.event.SetUserInfoEvent;
import com.lanmei.peiyu.ui.classify.activity.AddressListActivity;
import com.lanmei.peiyu.ui.home.activity.MessageCenterActivity;
import com.lanmei.peiyu.ui.login.RegisterActivity;
import com.lanmei.peiyu.ui.mine.activity.AfterSaleOrderActivity;
import com.lanmei.peiyu.ui.mine.activity.BecomeDistributorActivity;
import com.lanmei.peiyu.ui.mine.activity.ClubActivity;
import com.lanmei.peiyu.ui.mine.activity.InstallApplyActivity;
import com.lanmei.peiyu.ui.mine.activity.MineOrderActivity;
import com.lanmei.peiyu.ui.mine.activity.MinePowerStationActivity;
import com.lanmei.peiyu.ui.mine.activity.MyCollectActivity;
import com.lanmei.peiyu.ui.mine.activity.MyTeamActivity;
import com.lanmei.peiyu.ui.mine.activity.PersonalDataActivity;
import com.lanmei.peiyu.ui.mine.activity.SettingActivity;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.DataBean;
import com.xson.common.bean.UserBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UserHelper;
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
    CircleImageView picIv;//头像
    @InjectView(R.id.name_tv)
    TextView nameTv;//用户名
    @InjectView(R.id.m01_tv)
    TextView m01Tv;//我的余额
    @InjectView(R.id.m02_tv)
    TextView m02Tv;//我的团队
    @InjectView(R.id.m03_tv)
    TextView m03Tv;//我的业绩
    @InjectView(R.id.m04_tv)
    TextView m04Tv;//团队业绩
    @InjectView(R.id.m05_tv)
    TextView m05Tv;//我的电站
    @InjectView(R.id.m1_num_tv)
    TextView m1NumTv;//待付款数量
    @InjectView(R.id.m2_num_tv)
    TextView m2NumTv;//待发货数量
    @InjectView(R.id.m3_num_tv)
    TextView m3NumTv;//待收货数量
    @InjectView(R.id.m4_num_tv)
    TextView m4NumTv;//待评价数量
    @InjectView(R.id.m5_num_tv)
    TextView m5NumTv;//退款/售后 数量

    @Override
    public int getContentViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        setUserBean(CommonUtils.getUserBean(context));
    }

    private void setUserBean(UserBean userBean) {
        if (userBean == null) {
            nameTv.setText("游客");
            String o = CommonUtils.isZero;
            setString(o,o,o,o,o);
            picIv.setImageResource(R.mipmap.default_pic);
            return;
        }
        nameTv.setText(userBean.getNickname() + (StringUtils.isEmpty(userBean.getRidname()) ? "" : (" (" + userBean.getRidname() + ")")));
        CommonUtils.loadImage(context,picIv,userBean.getPic());
    }

    //获取用户信息后调用
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SetUserInfoEvent event) {
        setUserBean(event.getBean());
    }

    /**
     * 设置订单个数
     *
     * @param textView
     * @param num
     */
    private void setNum(TextView textView, int num) {
        if (num == 0) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(String.valueOf(num));
        }
    }


    @OnClick({R.id.name_tv, R.id.m_message_iv, R.id.pic_iv, R.id.m01_tv, R.id.m02_tv, R.id.m03_tv, R.id.m04_tv, R.id.m05_tv, R.id.m1_rl, R.id.m2_rl, R.id.m3_rl, R.id.m4_rl, R.id.m5_rl, R.id.m6_tv, R.id.m7_tv, R.id.m8_tv, R.id.m9_tv, R.id.m10_tv, R.id.m11_tv, R.id.m12_tv, R.id.m13_tv})
    public void onViewClicked(View view) {
        if (!CommonUtils.isLogin(context)) {
            return;
        }
        switch (view.getId()) {
            case R.id.m_message_iv://消息
                IntentUtil.startActivity(context, MessageCenterActivity.class);
                break;
            case R.id.name_tv://
            case R.id.pic_iv://头像
                IntentUtil.startActivity(context, PersonalDataActivity.class);
                break;
            case R.id.m01_tv://余额
                IntentUtil.startActivity(context, ClubActivity.class);
                break;
            case R.id.m02_tv://我的团队
                IntentUtil.startActivity(context, MyTeamActivity.class);
                break;
            case R.id.m03_tv://我的业绩
//                PeiYuApi api = new PeiYuApi("station/station_list");
//                api.addParams("uid",api.getUserId(context));
//                HttpClient.newInstance(context).request(api, new BeanRequest.SuccessListener<BaseBean>() {
//                    @Override
//                    public void onResponse(BaseBean response) {
//
//                    }
//                });
                break;
            case R.id.m04_tv://团队业绩
                break;
            case R.id.m05_tv://我的电站
                IntentUtil.startActivity(context, MinePowerStationActivity.class);
                break;
            case R.id.m1_rl://待付款
                IntentUtil.startActivity(context, 1, MineOrderActivity.class);
                break;
            case R.id.m2_rl://待发货
                IntentUtil.startActivity(context, 0, MineOrderActivity.class);
                break;
            case R.id.m3_rl://待收货
                IntentUtil.startActivity(context, 2, MineOrderActivity.class);
                break;
            case R.id.m4_rl://待评价
                IntentUtil.startActivity(context, 0, MineOrderActivity.class);
                break;
            case R.id.m5_rl://退款/售后
                IntentUtil.startActivity(context, 0, MineOrderActivity.class);
                break;
            case R.id.m6_tv://安装信息
                IntentUtil.startActivity(context, InstallApplyActivity.class);
                break;
            case R.id.m7_tv://售后工单
                IntentUtil.startActivity(context, AfterSaleOrderActivity.class);
                break;
            case R.id.m8_tv://在线客服
                IntentUtil.startActivity(context, BecomeDistributorActivity.class, context.getString(R.string.online_service));
                break;
            case R.id.m9_tv://成为经销商
                IntentUtil.startActivity(context, BecomeDistributorActivity.class, context.getString(R.string.become_distributor));
                break;
            case R.id.m10_tv://我的收藏
                IntentUtil.startActivity(context, MyCollectActivity.class);
                break;
            case R.id.m11_tv://我的地址
                Bundle bundle = new Bundle();
                bundle.putString("title", context.getString(R.string.my_address));
                IntentUtil.startActivity(context, AddressListActivity.class, bundle);
                break;
            case R.id.m12_tv://修改密码
                IntentUtil.startActivity(context, RegisterActivity.class, CommonUtils.isThree);
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

    @Override
    public void onResume() {
        super.onResume();
        if (!UserHelper.getInstance(context).hasLogin()) {
            return;
        }
        PeiYuApi api = new PeiYuApi("app/allcount");
        api.addParams("uid", api.getUserId(context));
        HttpClient.newInstance(context).request(api, new BeanRequest.SuccessListener<DataBean<NumBean>>() {
            @Override
            public void onResponse(DataBean<NumBean> response) {
                if (nameTv == null) {
                    return;
                }
                NumBean bean = response.data;
                if (bean == null) {
                    return;
                }
                setNum(m1NumTv, bean.getObligation());
                setNum(m2NumTv, bean.getPayed());
                setNum(m3NumTv, bean.getReceiver());
                setNum(m4NumTv, bean.getAssess());
                setNum(m5NumTv, bean.getDrawback());
                setString(bean.getMoney(),bean.getMy_team() + "",bean.getMy_achievement() + "",bean.getTeam_achievement() + "",bean.getMy_station() + "");
            }
        });
    }


    private void setString(String s1,String s2,String s3,String s4,String s5){
        m01Tv.setText(String.format(context.getString(R.string.my_balance), StringUtils.isEmpty(s1)?CommonUtils.isZero:s1));
        m02Tv.setText(String.format(context.getString(R.string.my_team_sub), s2));
        m03Tv.setText(String.format(context.getString(R.string.my_performance), s3));
        m04Tv.setText(String.format(context.getString(R.string.team_performance), s4));
        m05Tv.setText(String.format(context.getString(R.string.my_power_station), s5));
    }

}
