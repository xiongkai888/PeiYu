package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.ElectricityTypeBean;
import com.xson.common.adapter.SwipeRefreshAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * voter(下拉筛选)
 */
public class DropDownFiltrateAdapter extends SwipeRefreshAdapter<ElectricityTypeBean.TypeBean> {

    public DropDownFiltrateAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_drop_down_filtrate, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        final ElectricityTypeBean.TypeBean bean = getItem(position);
        if (bean == null) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (l != null) {
                    l.onFiltrate(bean);
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.name_tv)
        TextView nameTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }


        public void setParameter(ElectricityTypeBean.TypeBean bean) {
            nameTv.setText(bean.getProblemname());
        }
    }

    private DropDownFiltrateListener l;

    public void setVoterFiltrateListener(DropDownFiltrateListener l) {
        this.l = l;
    }

    public interface DropDownFiltrateListener {
        void onFiltrate(ElectricityTypeBean.TypeBean bean);
    }

}
