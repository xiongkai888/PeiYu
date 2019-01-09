package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.AfterSaleOrderBean;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 售后工单
 */
public class AfterSaleOrderListAdapter extends SwipeRefreshAdapter<AfterSaleOrderBean> {


//    private FormatTime time;

    public AfterSaleOrderListAdapter(Context context) {
        super(context);
//        time = new FormatTime(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_after_sale_order_list, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        final AfterSaleOrderBean bean = getItem(position);
        if (bean == null) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.title_tv)
        TextView titleTv;
        @InjectView(R.id.state_tv)
        TextView stateTv;
        @InjectView(R.id.link_name_tv)
        TextView linkNameTv;
        @InjectView(R.id.phone_tv)
        TextView phoneTv;
        @InjectView(R.id.address_tv)
        TextView addressTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(final AfterSaleOrderBean bean) {
            titleTv.setText(String.format(context.getString(R.string.s_name),bean.getSname()));
            String state = "";
            if (StringUtils.isEmpty(bean.getState())){
                bean.setState(CommonUtils.isZero);
            }
            switch (bean.getState()){
                case "1":
                    state = context.getString(R.string.untreated);//未处理
                    break;
                case "2":
                    state = context.getString(R.string.being_processed);//处理中
                    break;
                case "3":
                    state = context.getString(R.string.doned);//已完成
                    break;
            }
            stateTv.setText(state);
//            L.d(L.TAG,state);
            linkNameTv.setText(String.format(context.getString(R.string.link_name),bean.getLinkname()));
            phoneTv.setText(String.format(context.getString(R.string.phone),bean.getPhone()));
            addressTv.setText(String.format(context.getString(R.string.address),bean.getAddress()));
        }
    }
}
