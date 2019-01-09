package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.AddressListBean;
import com.lanmei.peiyu.ui.classify.activity.AddAddressActivity;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 选择收货地址
 */
public class AddressListAdapter extends SwipeRefreshAdapter<AddressListBean> {

    public AddressListAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_address, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        final AddressListBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean,position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (l != null) {
                    l.choose(bean);
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.select_iv)
        ImageView selectIv;//设为默认
        @InjectView(R.id.default_tv)
        TextView defaultTv;//设为默认
        @InjectView(R.id.name_tv)
        TextView nameTv;
        @InjectView(R.id.address_tv)
        TextView addressTv;
        @InjectView(R.id.compile_tv)
        TextView compileTv;
        @InjectView(R.id.delete_tv)
        TextView deleteTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

        }

        public void setParameter(final AddressListBean bean, final int position) {
            nameTv.setText(bean.getAccept_name() + "\u3000\u3000" + bean.getMobile());
            addressTv.setText(bean.getAddress());
            boolean d = StringUtils.isSame(CommonUtils.isOne, bean.getDefaultX());
            selectIv.setImageResource(d? R.mipmap.choose_on:R.mipmap.choose_off);
            defaultTv.setText(d?R.string.set_default:R.string.set_default);
            selectIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (l != null && StringUtils.isSame(CommonUtils.isZero,bean.getDefaultX())) {
                        l.setDefault(bean.getId(),position);
                    }
                }
            });
            compileTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean",bean);
                    IntentUtil.startActivity(context, AddAddressActivity.class,bundle);
                }
            });
            deleteTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (l != null) {
                        l.delete(bean.getId(),position);
                    }
                }
            });
        }
    }

    ChooseAddressListener l;

    public interface ChooseAddressListener {
        void choose(AddressListBean bean);
        void setDefault(String id, int position);
        void delete(String id, int position);
    }

    public void setChooseAddressListener(ChooseAddressListener l) {
        this.l = l;
    }

}

