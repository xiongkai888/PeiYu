package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.NotificationBean;
import com.lanmei.peiyu.ui.home.activity.UpgradeActivity;
import com.lanmei.peiyu.utils.CommonUtils;
import com.lanmei.peiyu.utils.FormatTime;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 通知信息（系统通知）
 */
public class NotificationAdapter extends SwipeRefreshAdapter<NotificationBean> {

    FormatTime time;

    public NotificationAdapter(Context context) {
        super(context);
        time = new FormatTime(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        NotificationBean bean = getItem(position);
        if (bean == null){
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.ll_content)
        LinearLayout llContent;
        @InjectView(R.id.title_et)
        TextView titleTv;
        @InjectView(R.id.time_tv)
        TextView timeTv;
        @InjectView(R.id.content_tv)
        TextView contentTv;
        @InjectView(R.id.delete_tv)
        TextView deleteTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }


        public void setParameter(final NotificationBean bean) {
            titleTv.setText(bean.getTitle());
            contentTv.setText(bean.getIntro());
            time.setTime(bean.getAddtime());
            timeTv.setText(time.formatterTime());
            deleteTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.delete(bean.getId(),getAdapterPosition());
                    }
                }
            });
            llContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (StringUtils.isSame(CommonUtils.isOne,bean.getState())&&StringUtils.isSame(CommonUtils.isZero,bean.getType())){//会员升级
                        IntentUtil.startActivity(context, UpgradeActivity.class,bean.getTitle());
                    }
//                    else {//普通的文章
//                        IntentUtil.startActivity(context, BecomeDistributorActivity.class,bean.getTitle());
//                    }
                }
            });
        }
    }

    private DeleteSiXinListener listener;

    public interface DeleteSiXinListener{
        void delete(String id, int position);
    }

    public void setDeleteSiXinListener(DeleteSiXinListener listener){
        this.listener = listener;
    }

}
