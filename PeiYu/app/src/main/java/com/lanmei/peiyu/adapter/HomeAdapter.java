package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.HomeClassifyBean;
import com.lanmei.peiyu.ui.news.activity.NewsDetailsActivity;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.IntentUtil;

import butterknife.ButterKnife;


/**
 * 首页
 */
public class HomeAdapter extends SwipeRefreshAdapter<HomeClassifyBean> {

    public HomeAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home, parent, false));
    }


    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {

        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(null);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.startActivity(context, NewsDetailsActivity.class);
            }
        });
    }

    @Override
    public int getCount() {
        return 6;
    }

    //
    public class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(HomeClassifyBean bean) {

        }
    }

}
