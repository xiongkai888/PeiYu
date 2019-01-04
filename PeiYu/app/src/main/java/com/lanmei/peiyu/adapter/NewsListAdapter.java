package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.NewsListBean;
import com.lanmei.peiyu.ui.news.activity.NewsDetailsActivity;
import com.lanmei.peiyu.utils.CommonUtils;
import com.lanmei.peiyu.utils.FormatTime;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 资讯列表
 */
public class NewsListAdapter extends SwipeRefreshAdapter<NewsListBean> {
    private FormatTime time;

    public NewsListAdapter(Context context) {
        super(context);
        time = new FormatTime(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        final NewsListBean bean = getItem(position);
        if (bean == null) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.startActivity(context, NewsDetailsActivity.class, bean.getId());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.title_tv)
        TextView titleTv;
        @InjectView(R.id.name_tv)
        TextView nameTv;
        @InjectView(R.id.time_tv)
        TextView timeTv;
        @InjectView(R.id.pic_iv)
        ImageView picIv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(final NewsListBean bean) {
            titleTv.setText(bean.getTitle());
            nameTv.setText(bean.getName());
            timeTv.setText(time.formatterTime(bean.getAddtime()));
            CommonUtils.loadImage(context,picIv, StringUtils.isEmpty(bean.getFile())?"":bean.getFile().get(0));
        }
    }

}
