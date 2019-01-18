package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.RechargeResultBean;
import com.lanmei.peiyu.utils.FormatTime;
import com.xson.common.adapter.SwipeRefreshAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 充值记录
 */
public class TopUpAdapter extends SwipeRefreshAdapter<RechargeResultBean> {

    private FormatTime time;

    public TopUpAdapter(Context context) {
        super(context);
        time = new FormatTime(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_top_up, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {

        RechargeResultBean bean = getItem(position);
        if (bean == null) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.rechargeTypeTv.setText(bean.getRecode_info());
        viewHolder.rechargePriceTv.setText(String.format(context.getString(R.string.yuan), bean.getMoney()));
        viewHolder.rechargeTimeTv.setText(time.formatterTime(bean.getAddtime()));
        viewHolder.balanceTv.setText(String.format(context.getString(R.string.balance), bean.getBalance()));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.recharge_type_tv)
        TextView rechargeTypeTv;
        @InjectView(R.id.recharge_price_tv)
        TextView rechargePriceTv;
        @InjectView(R.id.balance_tv)
        TextView balanceTv;
        @InjectView(R.id.recharge_time_tv)
        TextView rechargeTimeTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
