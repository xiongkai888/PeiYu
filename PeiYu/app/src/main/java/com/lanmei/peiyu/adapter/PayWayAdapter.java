package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.PayWayBean;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 支付方式
 */
public class PayWayAdapter extends SwipeRefreshAdapter<PayWayBean> {

    public PayWayAdapter(Context context) {
        super(context);
    }

    int index = 0;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pay_ment, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, final int position) {
        PayWayBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean, position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.name_tv)
        TextView nameTv;
        @InjectView(R.id.pay_iv)
        ImageView payIv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(final PayWayBean bean, int position) {
            nameTv.setText(bean.getC_name());
            if (bean.isChoose()) {
                payIv.setBackground(context.getResources().getDrawable(R.mipmap.choose_on));
            } else {
                payIv.setBackground(context.getResources().getDrawable(R.mipmap.choose_off));
            }
            if (bean.isChoose()){
                index = position;
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.payId(bean.getId());
                    }
                    if (bean.isChoose()){
                        return;
                    }
                    getItem(index).setChoose(false);
                    bean.setChoose(true);
                    notifyDataSetChanged();
                }
            });
        }
    }

    public PayWayListener listener;

    public interface PayWayListener{
        void payId(String id);
    }

    public void setPayWayListener(PayWayListener listener){
        this.listener = listener;
    }

}

