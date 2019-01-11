package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.AdBean;
import com.lanmei.peiyu.bean.EquipmentBean;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.widget.FormatTextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 我的电站-我的设备
 */
public class MinePowerStationEquipmentAdapter extends SwipeRefreshAdapter<EquipmentBean> {


    public int TYPE_BANNER = 100;
    private BannerViewHolder bannerViewHolder;

    public MinePowerStationEquipmentAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BANNER) { // banner
            if (bannerViewHolder == null) {
                bannerViewHolder = new BannerViewHolder(LayoutInflater.from(context).inflate(R.layout.head_mine_power_station, parent, false));
            }
            return bannerViewHolder;
        }
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mine_power_station_equipment, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_BANNER) {
            return;
        }
        final EquipmentBean bean = getItem(position - 1);
        if (bean == null) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    @Override
    public int getItemViewType2(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        }
        return super.getItemViewType2(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.name_tv)
        TextView nameTv;
        @InjectView(R.id.setval_tv)
        TextView setvalTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(EquipmentBean bean) {
            nameTv.setText(bean.getClassname());
            setvalTv.setText(String.format(context.getString(R.string.power), bean.getSetval()));
        }
    }


    //头部
    public class BannerViewHolder extends RecyclerView.ViewHolder {


        @InjectView(R.id.today_generating_capacity_tv)
        TextView todayGeneratingCapacityTv;
        @InjectView(R.id.power_tv)
        FormatTextView powerTv;
        @InjectView(R.id.total_generate_electricity)
        FormatTextView totalGenerateElectricity;
        @InjectView(R.id.today_earnings_tv)
        FormatTextView todayEarningsTv;
        @InjectView(R.id.accumulated_earnings_tv)
        FormatTextView accumulatedEarningsTv;

        public BannerViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }


        public void setPowerInformation(List<AdBean> list) {

        }

    }

    //设置 今日发电量 电站功率、累计发电、今日收益、累计收益 等信息
    public void setPowerInformation(List<AdBean> list) {
        if (bannerViewHolder != null)
            bannerViewHolder.setPowerInformation(list);
    }

}
