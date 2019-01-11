package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.view.DoubleScaleImageView;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.helper.ImageHelper;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 *
 */
public class ItemImageAdapter extends SwipeRefreshAdapter<String> {

    public ItemImageAdapter(Context context) {
        super(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_imageview, parent, false));
    }


    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, final int position) {
        final String bean = getItem(position);
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder)holder;
        ImageHelper.load(context, bean, viewHolder.image, null, true, R.mipmap.default_pic, R.mipmap.default_pic);
        viewHolder.image.setDoubleClickListener(new DoubleScaleImageView.DoubleClickListener() {
            @Override
            public void onSingleTapConfirmed() {
                if (l != null){
                    l.onClick(position);
                }
            }

            @Override
            public void onDoubleTap() {
                if (l != null){
                    l.onDoubleTap(position);
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @InjectView(R.id.iv_item_image)
        DoubleScaleImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    SingleTapConfirmedListener l;//单击点击

    public interface SingleTapConfirmedListener{
        void onClick(int position);
        void onDoubleTap(int position);
    }

    public void setOnClickListener(SingleTapConfirmedListener l){
        this.l = l;
    }

}
