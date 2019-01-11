package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.GoodsCommentBean;
import com.lanmei.peiyu.utils.CommonUtils;
import com.lanmei.peiyu.utils.FormatTime;
import com.lanmei.peiyu.view.SudokuView;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.helper.ImageHelper;
import com.xson.common.utils.StringUtils;
import com.xson.common.widget.CircleImageView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 商品详情评论
 */
public class GoodsCommentAdapter extends SwipeRefreshAdapter<GoodsCommentBean> {

    private FormatTime time;
    private boolean isOnly;
    private int num;

    public GoodsCommentAdapter(Context context) {
        super(context);
        time = new FormatTime(context);
    }

    /**
     * 最多只设置num个item
     * @param isOnly
     * @param num
     */
    public void setOnlyItem(boolean isOnly,int num) {
        this.isOnly = isOnly;
        this.num = num;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_comment_goods, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, final int position) {
        GoodsCommentBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.head_iv)
        CircleImageView headIv;
        @InjectView(R.id.name_tv)
        TextView nameTv;
        @InjectView(R.id.time_tv)
        TextView timeTv;
        @InjectView(R.id.ratingbar)
        RatingBar ratingBar;
        @InjectView(R.id.content_tv)
        TextView contentTv;
        @InjectView(R.id.sudokuView)
        SudokuView sudokuView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(final GoodsCommentBean bean) {
            ImageHelper.load(context, bean.getUser_pic(), headIv, null, true, R.mipmap.default_pic, R.mipmap.default_pic);
            nameTv.setText(bean.getUsername());
            time.setTime(bean.getComment_time());
            timeTv.setText(time.formatterTime());
            contentTv.setText(bean.getContents());
            float point = StringUtils.isEmpty(bean.getPoint()) ? 0 : Float.valueOf(bean.getPoint());
            ratingBar.setRating(point);
            final List<String> list = bean.getComment_pic();
//            L.d(L.TAG,StringUtils.isEmpty(list)+"");
            sudokuView.setVisibility(View.VISIBLE);
            sudokuView.setComment(true);
            sudokuView.setListData(list);
            sudokuView.setOnSingleClickListener(new SudokuView.SudokuViewClickListener() {
                @Override
                public void onClick(int positionSub) {
                    CommonUtils.showPhotoBrowserActivity(context, bean.getComment_pic(), positionSub);
                }

                @Override
                public void onDoubleTap(int position) {

                }
            });
        }
    }

    @Override
    public int getCount() {
        if (isOnly && super.getCount() > num) {
            return num;
        }
        return super.getCount();
    }
}
