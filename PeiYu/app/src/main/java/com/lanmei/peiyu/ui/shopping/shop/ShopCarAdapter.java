package com.lanmei.peiyu.ui.shopping.shop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.xson.common.adapter.SwipeRefreshAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 购物车
 */
public class ShopCarAdapter extends SwipeRefreshAdapter<ShopCarBean> {

    ShopCartContract.Presenter mPresenter;

    public ShopCarAdapter(Context context, ShopCartContract.Presenter presenter) {
        super(context);
        mPresenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_shop_car, parent, false));
    }

    @Override
    public void onBindViewHolder2(final RecyclerView.ViewHolder holder, int position) {
        ShopCarBean bean = getItem(position);
        if (bean == null) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean,position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.select_iv)
        ImageView selectIv;
        @InjectView(R.id.head_iv)
        ImageView headIv;
        @InjectView(R.id.name_tv)
        TextView nameTv;
        @InjectView(R.id.price_tv)
        TextView priceTv;
        @InjectView(R.id.specifications_tv)
        TextView specificationsTv;//规格
        @InjectView(R.id.num_subtract_iv)
        ImageView numSubtractIv;
        @InjectView(R.id.pay_num_et)
        EditText payNumEt;
        @InjectView(R.id.num_add_iv)
        ImageView numAddIv;
        int goodsCount;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(final ShopCarBean bean,final int position) {
            goodsCount = bean.getGoodsCount();
//            ImageHelper.load(context,bean.getGoodsImg(),headIv,null,true,R.mipmap.default_pic,R.mipmap.default_pic);
//            headIv.setImageResource(R.mipmap.temp2);
            priceTv.setText(String.format(context.getString(R.string.price),String.valueOf(bean.getSell_price())));
            nameTv.setText(bean.getGoodsName());
            payNumEt.setText(String.valueOf(goodsCount));
            payNumEt.setFocusable(false);
            specificationsTv.setText(bean.getSpecifications());
            numAddIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goodsCount ++;
                    mPresenter.setGoodsNum(bean,position, goodsCount);
                }
            });
            numSubtractIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (goodsCount == 1){
                        return;
                    }
                    goodsCount --;
                    mPresenter.setGoodsNum(bean,position, goodsCount);
                }
            });
            final boolean isSelect = bean.isSelect();
            if (isSelect){
                selectIv.setImageResource(R.mipmap.choose_on);
            }else {
                selectIv.setImageResource(R.mipmap.choose_off);
            }
            selectIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.setSelect(position, !isSelect);
                }
            });
        }

    }
}
