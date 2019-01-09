package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.TeamBean;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.helper.ImageHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 我的团队
 */
public class TeamListAdapter extends SwipeRefreshAdapter<TeamBean> {

//    private FormatTime time;

    public TeamListAdapter(Context context) {
        super(context);
//        time = new FormatTime(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_team, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        final TeamBean bean = getItem(position);
        if (bean == null) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("bean", bean);
//                IntentUtil.startActivity(context, MemberInformationActivity.class,bundle);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.pic_iv)
        ImageView picIv;
        @InjectView(R.id.nickname_tv)
        TextView nicknameTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(TeamBean bean) {
            ImageHelper.load(context, bean.getPic(), picIv, null, true, R.mipmap.default_pic, R.mipmap.default_pic);
            nicknameTv.setText(bean.getNickname());
//            regTimeTv.setText("注册时间："+time.formatterTime(bean.getReg_time()));
        }
    }
}
