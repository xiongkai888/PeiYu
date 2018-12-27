package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.HomeClassifyBean;
import com.lanmei.peiyu.ui.mine.activity.OrderDetailsActivity;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.IntentUtil;

import butterknife.ButterKnife;

/**
 * 订单列表
 */
public class MineOrderListAdapter extends SwipeRefreshAdapter<HomeClassifyBean> {


//    private FormatTime time;

    public MineOrderListAdapter(Context context) {
        super(context);
//        time = new FormatTime(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mine_order_list, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
//        final MineRecommendBean bean = getItem(position);
//        if (bean == null) {
//            return;
//        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(null);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.startActivity(context, OrderDetailsActivity.class);
            }
        });
    }

    @Override
    public int getCount() {
        return 6;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(final HomeClassifyBean bean) {

        }
    }

}
