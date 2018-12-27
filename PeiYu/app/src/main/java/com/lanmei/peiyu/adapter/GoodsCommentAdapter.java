package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.GoodsCommentBean;
import com.lanmei.peiyu.utils.FormatTime;
import com.xson.common.adapter.SwipeRefreshAdapter;

import butterknife.ButterKnife;


/**
 * 商品详情评论
 */
public class GoodsCommentAdapter extends SwipeRefreshAdapter<GoodsCommentBean> {

    FormatTime time;
    boolean isOnly;

    public GoodsCommentAdapter(Context context) {
        super(context);
        time = new FormatTime(context);
    }

    //设置只有一个item
    public void setOnlyItem(boolean isOnly) {
        this.isOnly = isOnly;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_comment_goods, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, final int position) {
//        GoodsCommentBean bean = getItem(position);
//        if (StringUtils.isEmpty(bean)) {
//            return;
//        }
//        ViewHolder viewHolder = (ViewHolder) holder;
//        viewHolder.setParameter(bean);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

//        @InjectView(R.id.head_iv)
//        CircleImageView headIv;
//        @InjectView(R.id.name_tv)
//        TextView nameTv;
////        @InjectView(R.id.ll_star)
////        LinearLayout llStar;
//        @InjectView(R.id.time_tv)
//        TextView timeTv;
//        @InjectView(R.id.ratingbar)
//        RatingBar ratingBar;
//        @InjectView(R.id.content_tv)
//        TextView contentTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(GoodsCommentBean bean) {
//            ImageHelper.load(context, bean.getPic(), headIv, null, true, R.mipmap.default_pic, R.mipmap.default_pic);
//            nameTv.setText(bean.getNickname());
//            time.setTime(bean.getAddtime());
//            timeTv.setText(time.getFormatTime());
//            contentTv.setText(bean.getContent());
//            float point = Float.valueOf(StringUtils.isEmpty(bean.getPoint())?0:Float.valueOf(bean.getPoint()));
//            ratingBar.setRating(point);
//            llStar.removeAllViews();
//            showStar(context, llStar, bean.getPoint());
        }

        public void showStar(Context context, LinearLayout root, String point) {
//            int pointNum = 0;
//            if (!com.xson.common.utils.StringUtils.isEmpty(point)) {
//                pointNum = Integer.valueOf(point);
//            }
//            for (int i = 0; i < 5; i++) {
//                ImageView view = new ImageView(context);
//                if (pointNum < i) {
//                    view.setImageResource(R.mipmap.icon_collect_on);
//                } else {
//                    view.setImageResource(R.mipmap.icon_collect_off);
//                }
//                root.addView(view);
//            }
        }
    }

    @Override
    public int getCount() {
        if (isOnly && super.getCount() > 1) {
            return 1;
        }
        return 2;
    }
}
