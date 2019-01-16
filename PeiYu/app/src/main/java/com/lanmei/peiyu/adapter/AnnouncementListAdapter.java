package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.AnnouncementListBean;
import com.lanmei.peiyu.ui.mine.activity.BecomeDistributorActivity;
import com.lanmei.peiyu.utils.FormatTime;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.IntentUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公告信息
 */
public class AnnouncementListAdapter extends SwipeRefreshAdapter<AnnouncementListBean> {

    private FormatTime formatTime;

    public AnnouncementListAdapter(Context context) {
        super(context);
        formatTime = new FormatTime(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_announcement_list, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        final AnnouncementListBean bean = getItem(position);
        if (bean == null) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.startActivity(context, BecomeDistributorActivity.class,bean.getTitle());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.intro_tv)
        TextView introTv;
        @InjectView(R.id.time_tv)
        TextView timeTv;
        @InjectView(R.id.title_tv)
        TextView titleTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(final AnnouncementListBean bean) {
            introTv.setText(bean.getIntro());
            titleTv.setText(bean.getTitle());
            timeTv.setText(formatTime.formatterTime(bean.getAddtime()));
        }
    }

}
