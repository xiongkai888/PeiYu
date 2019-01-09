package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.ui.shopping.shop.ShopCarBean;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.helper.ImageHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 确认订单商品列表
 */
public class ConfirmOrderAdapter extends SwipeRefreshAdapter<ShopCarBean> {


    public ConfirmOrderAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_confirm_order, parent, false));
    }

    @Override
    public void onBindViewHolder2(final RecyclerView.ViewHolder holder, int position) {
        ShopCarBean bean = getItem(position);
        if (bean == null) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.items_icon_iv)
        ImageView itemsIconIv;
        @InjectView(R.id.title_tv)
        TextView titleTv;
        @InjectView(R.id.specifications_name_tv)
        TextView specificationsNameTv;
        @InjectView(R.id.price_num_tv)
        TextView priceNumTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(ShopCarBean bean) {
            ImageHelper.load(context, bean.getGoodsImg(), itemsIconIv, null, true, R.mipmap.default_pic, R.mipmap.default_pic);
            titleTv.setText(bean.getGoodsName());
//            priceNumTv.setText(String.format(context.getString(R.string.goods_price_and_num), String.valueOf(bean.getSell_price()), String.valueOf(bean.getGoodsCount())));
            priceNumTv.setText(String.format(context.getString(R.string.goods_price_and_num), bean.getSell_price()+"", String.valueOf(bean.getGoodsCount())));
            specificationsNameTv.setText(com.xson.common.utils.StringUtils.isEmpty(bean.getSpecifications())?"":bean.getSpecifications());
        }

    }
}
