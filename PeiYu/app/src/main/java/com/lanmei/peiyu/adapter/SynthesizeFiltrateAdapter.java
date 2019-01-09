package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.SynthesizeFiltrateBean;
import com.xson.common.adapter.SwipeRefreshAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 商品列表综合分类(下拉筛选)
 */
public class SynthesizeFiltrateAdapter extends SwipeRefreshAdapter<SynthesizeFiltrateBean> {

    public SynthesizeFiltrateAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_synthesize_filtrate, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        final SynthesizeFiltrateBean bean = getItem(position);
        if (bean == null) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bean.isSelect()) {
                    List<SynthesizeFiltrateBean> list = getData();
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        SynthesizeFiltrateBean filtrateBean = list.get(i);
                        if (filtrateBean.isSelect()) {
                            filtrateBean.setSelect(false);
                        }
                    }
                    bean.setSelect(!bean.isSelect());
                    notifyDataSetChanged();
                }
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


        public void setParameter(SynthesizeFiltrateBean bean) {
            nameTv.setTextColor(bean.isSelect() ? context.getResources().getColor(R.color.red) : context.getResources().getColor(R.color.black));
            nameTv.setText(bean.getName());
        }
    }

    private SynthesizeFiltrateListener l;

    public void setVoterFiltrateListener(SynthesizeFiltrateListener l) {
        this.l = l;
    }

    public interface SynthesizeFiltrateListener {
        void onFiltrate(SynthesizeFiltrateBean bean);
    }

}
