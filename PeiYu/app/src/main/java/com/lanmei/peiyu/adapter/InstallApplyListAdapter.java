package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.InstallApplyBean;
import com.lanmei.peiyu.utils.CommonUtils;
import com.lanmei.peiyu.utils.FormatTime;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 安装申请
 */
public class InstallApplyListAdapter extends SwipeRefreshAdapter<InstallApplyBean> {

    private FormatTime time;

    public InstallApplyListAdapter(Context context) {
        super(context);
        time = new FormatTime(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_install_apply_list, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        final InstallApplyBean bean = getItem(position);
        if (bean == null) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                IntentUtil.startActivity(context, OrderDetailsActivity.class);
//            }
//        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.title_tv)
        TextView titleTv;
        @InjectView(R.id.state_tv)
        TextView stateTv;
        @InjectView(R.id.link_name_tv)
        TextView linkNameTv;
        @InjectView(R.id.phone_tv)
        TextView phoneTv;
        @InjectView(R.id.address_tv)
        TextView addressTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(final InstallApplyBean bean) {
            titleTv.setText(String.format(context.getString(R.string.s_name),bean.getSname()));
            String state = "";
            if (StringUtils.isEmpty(bean.getState())){
                bean.setState(CommonUtils.isZero);
            }
            switch (bean.getState()){
                case "1":
                    state = context.getString(R.string.apply_being_processed);
                    break;
                case "2":
                    state = context.getString(R.string.shipped);
                    break;
                case "3":
                    state = context.getString(R.string.being_installed);
                    break;
                case "4":
                    state = context.getString(R.string.completion_acceptance);
                    break;
            }
            stateTv.setText(state);
            linkNameTv.setText(String.format(context.getString(R.string.link_name),bean.getLinkname()));
            phoneTv.setText(String.format(context.getString(R.string.phone),bean.getPhone()));
            addressTv.setText(String.format(context.getString(R.string.address),bean.getAddress()));
        }
    }

}
