package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.HomeClassifyBean;
import com.lanmei.peiyu.ui.home.activity.DataEntryActivity;
import com.lanmei.peiyu.ui.home.activity.SimulationIncomeActivity;
import com.lanmei.peiyu.ui.mine.activity.AfterSaleOrderActivity;
import com.lanmei.peiyu.ui.mine.activity.InstallApplyActivity;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.IntentUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 首页分类
 */
public class HomeClassifyAdapter extends SwipeRefreshAdapter<HomeClassifyBean> {

    public HomeClassifyAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_classify, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder,final int position) {
        final HomeClassifyBean bean = getItem(position);
        if (bean == null) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0://模拟收入
                        IntentUtil.startActivity(context, SimulationIncomeActivity.class);
                        break;
                    case 1://资料录入
                        if (CommonUtils.isLogin(context)){
                            IntentUtil.startActivity(context, DataEntryActivity.class);//
                        }
                        break;
                    case 2://安装申报
                        if (CommonUtils.isLogin(context)){
                            IntentUtil.startActivity(context, InstallApplyActivity.class);
                        }
                        break;
                    case 3://售后报修
                        if (CommonUtils.isLogin(context)){
                            IntentUtil.startActivity(context, AfterSaleOrderActivity.class);
                        }
                        break;
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.image)
        ImageView image;
        @InjectView(R.id.name_tv)
        TextView nameTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(HomeClassifyBean bean) {
            nameTv.setText(bean.getClassname());
            CommonUtils.loadImage(context,image,bean.getPic());
        }
    }

}
