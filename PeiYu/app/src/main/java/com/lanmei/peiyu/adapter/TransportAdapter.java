package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.DistributionBean;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 货运方式
 */
public class TransportAdapter extends SwipeRefreshAdapter<DistributionBean> {

    public TransportAdapter(Context context) {
        super(context);
    }

    int index = 0;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_transport, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, final int position) {
        DistributionBean bean = getItem(position);
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

        public void setParameter(final DistributionBean bean, int position) {
            nameTv.setText(bean.getClassname());
            if (bean.isChoose()) {
                payIv.setBackground(context.getResources().getDrawable(R.mipmap.choose_on));
                index = position;
            } else {
                payIv.setBackground(context.getResources().getDrawable(R.mipmap.choose_off));
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.transport(bean);
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

    public TransportListener listener;

    public interface TransportListener{
        void transport(DistributionBean bean);
    }

    public void setTransportListener(TransportListener listener){
        this.listener = listener;
    }

}

