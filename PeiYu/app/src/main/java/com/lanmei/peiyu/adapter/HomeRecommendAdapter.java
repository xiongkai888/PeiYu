package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.HomeRecommendBean;
import com.xson.common.adapter.SwipeRefreshAdapter;

import butterknife.ButterKnife;


/**
 * 首页推荐商品
 */
public class HomeRecommendAdapter extends SwipeRefreshAdapter<HomeRecommendBean> {

    public HomeRecommendAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_recommend, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
//        final MineRecommendBean bean = getItem(position);
//        if (bean == null) {
//            return;
//        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(null);
    }

    @Override
    public int getCount() {
        return 16;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

//        @InjectView(R.id.price_sub_tv)
//        TextView priceSubTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(HomeRecommendBean bean) {
//            SpannableString spannableString = new SpannableString(priceSubTv.getText());
//            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
//            spannableString.setSpan(strikethroughSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//            priceSubTv.setText(spannableString);
        }
    }

}
