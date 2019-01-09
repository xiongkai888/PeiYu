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
import com.lanmei.peiyu.bean.MineOrderListBean;
import com.lanmei.peiyu.ui.mine.activity.OrderCommentActivity;
import com.lanmei.peiyu.ui.mine.activity.OrderDetailsActivity;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.helper.ImageHelper;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 商品订单列表子项
 */
public class MineOrderListSubAdapter extends SwipeRefreshAdapter<MineOrderListBean.GoodsBean> {


    private MineOrderListBean orderListBean;

    public void setOrderListBean(MineOrderListBean orderListBean) {
        this.orderListBean = orderListBean;
    }

    public MineOrderListSubAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mine_order_list_sub, parent, false));
    }

    @Override
    public void onBindViewHolder2(final RecyclerView.ViewHolder holder, int position) {
        MineOrderListBean.GoodsBean bean = getItem(position);
        if (bean == null) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.startActivity(context, OrderDetailsActivity.class);
            }
        });
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
        @InjectView(R.id.comment_tv)//晒单评价按钮
                TextView commentTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(final MineOrderListBean.GoodsBean bean) {
            bean.setOrder_no(orderListBean.getOrder_no());
            ImageHelper.load(context, bean.getCover(), itemsIconIv, null, true, R.mipmap.default_pic, R.mipmap.default_pic);
            titleTv.setText(bean.getGoodsname());
            priceNumTv.setText(String.format(context.getString(R.string.goods_price_and_num), bean.getPrice(), bean.getNum()));
            specificationsNameTv.setText(com.xson.common.utils.StringUtils.isEmpty(bean.getSpecifications()) ? "" : bean.getSpecifications());
            if (StringUtils.isSame(CommonUtils.isZero, bean.getStatus())) {//去评价(0未评价1已评价)
                commentTv.setVisibility(View.VISIBLE);
                commentTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("bean",bean);
                        IntentUtil.startActivity(context, OrderCommentActivity.class,bundle);
                    }
                });
            } else {
                commentTv.setVisibility(View.GONE);
            }
        }

    }
}
