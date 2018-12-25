package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.HomeClassifyBean;
import com.lanmei.peiyu.ui.classify.activity.ClassifyGoodsListActivity;
import com.lanmei.peiyu.ui.classify.activity.ClassifyListActivity;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.IntentUtil;

import butterknife.ButterKnife;

/**
 * 主页面分类列表
 */
public class ClassifyListAdapter extends SwipeRefreshAdapter<HomeClassifyBean> {


//    private FormatTime time;
    private boolean isMainClassifyList;

    public void setClassifyList(boolean classifyList) {
        isMainClassifyList = classifyList;
    }

    public ClassifyListAdapter(Context context) {
        super(context);
//        time = new FormatTime(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_classify_list, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
//        final MineRecommendBean bean = getItem(position);
//        if (bean == null) {
//            return;
//        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(null);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMainClassifyList){//分类列表
                    IntentUtil.startActivity(context, ClassifyListActivity.class);
                }else {//分类商品列表
                    IntentUtil.startActivity(context, ClassifyGoodsListActivity.class);
                }
            }
        });
    }

    @Override
    public int getCount() {
        return 66;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(final HomeClassifyBean bean) {

        }
    }

}
