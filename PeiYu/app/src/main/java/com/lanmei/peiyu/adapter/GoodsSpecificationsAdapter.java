package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.GoodsSpecificationsBean;
import com.xson.common.adapter.SwipeRefreshAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 商品规格选择
 */
public class GoodsSpecificationsAdapter extends SwipeRefreshAdapter<GoodsSpecificationsBean> {


    public GoodsSpecificationsAdapter(Context context) {
        super(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_goods_specifications, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        final GoodsSpecificationsBean bean = getItem(position);
        if (bean == null) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.isSelect()){
                    return;
                }
                List<GoodsSpecificationsBean> list = getData();
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    GoodsSpecificationsBean examinationTopicBean = list.get(i);
                    if (examinationTopicBean.isSelect()) {
                        examinationTopicBean.setSelect(false);
                    }
                }
                bean.setSelect(!bean.isSelect());
                notifyDataSetChanged();
                if (goodsSpecificationsFiltrateListener != null){
                    goodsSpecificationsFiltrateListener.onFiltrate(bean);
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


        public void setParameter(GoodsSpecificationsBean bean) {
            nameTv.setTextColor(bean.isSelect()?context.getResources().getColor(R.color.colorF5484A):context.getResources().getColor(R.color.color666));
            nameTv.setText(bean.getSpecifications());
            nameTv.setBackgroundResource(bean.isSelect()?R.drawable.goods_specifications_on:R.drawable.goods_specifications_off);
        }
    }

    private GoodsSpecificationsFiltrateListener goodsSpecificationsFiltrateListener;

    public void setTeacherFiltrateListener(GoodsSpecificationsFiltrateListener goodsSpecificationsFiltrateListener){
        this.goodsSpecificationsFiltrateListener = goodsSpecificationsFiltrateListener;
    }

    public interface GoodsSpecificationsFiltrateListener {
        void onFiltrate(GoodsSpecificationsBean bean);
    }

}
