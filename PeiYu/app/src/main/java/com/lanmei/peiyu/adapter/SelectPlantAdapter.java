package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.ApplyAfterSaleOrderBean;
import com.xson.common.adapter.SwipeRefreshAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 选择电站(下拉筛选)
 */
public class SelectPlantAdapter extends SwipeRefreshAdapter<ApplyAfterSaleOrderBean> {

    public SelectPlantAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_synthesize_filtrate, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        final ApplyAfterSaleOrderBean bean = getItem(position);
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


        public void setParameter(ApplyAfterSaleOrderBean bean) {
//            nameTv.setTextColor(bean.isSelect() ? context.getResources().getColor(R.color.red) : context.getResources().getColor(R.color.black));
            nameTv.setText(bean.getS_name());
        }
    }

    private PlantFiltrateListener l;

    public void setPlantFiltrateListener(PlantFiltrateListener l) {
        this.l = l;
    }

    public interface PlantFiltrateListener {
        void onFiltrate(ApplyAfterSaleOrderBean bean);
    }

}
