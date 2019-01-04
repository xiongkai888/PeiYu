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
import com.lanmei.peiyu.bean.GoodsDetailsBean;
import com.lanmei.peiyu.ui.classify.activity.GoodsDetailsActivity;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.IntentUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 首页推荐商品
 */
public class HomeRecommendAdapter extends SwipeRefreshAdapter<GoodsDetailsBean> {

    public HomeRecommendAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_recommend, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        final GoodsDetailsBean bean = getItem(position);
        if (bean == null) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", bean);
                IntentUtil.startActivity(context, GoodsDetailsActivity.class, bundle);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.image)
        ImageView image;
        @InjectView(R.id.name_tv)
        TextView nameTv;
        @InjectView(R.id.price_tv)
        TextView priceTv;
        @InjectView(R.id.add_goods_iv)
        ImageView addGoodsIv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(GoodsDetailsBean bean) {
//            nameTv.setText(bean.getGoodsname());
            priceTv.setText(String.format(context.getString(R.string.price),bean.getBusiness_price()));
            CommonUtils.loadImage(context,image,bean.getCover());
            addGoodsIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonUtils.developing(context);
                }
            });
        }
    }

}
