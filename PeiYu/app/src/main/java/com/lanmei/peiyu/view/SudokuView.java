package com.lanmei.peiyu.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.ItemImageAdapter;
import com.xson.common.helper.ImageHelper;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIBaseUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xkai on 2018/1/3.
 */

public class SudokuView extends RelativeLayout {

    @InjectView(R.id.only_imageView)
    DoubleScaleImageView oneImageView;
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ItemImageAdapter adapter;
    public boolean isComment;//是不是评论

    public void setComment(boolean comment) {
        isComment = comment;
    }

    public SudokuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        adapter = new ItemImageAdapter(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
        int padding = UIBaseUtils.dp2pxInt(getContext(), 4);
        recyclerView.addItemDecoration(new SpaceItemDecoration(padding));
    }

    public void setListData(final List<String> list) {

        if (StringUtils.isEmpty(list)) {
            recyclerView.setVisibility(View.GONE);
            oneImageView.setVisibility(View.GONE);
        } else if (list.size() > 1 || (isComment && list.size() == 1)) {
            recyclerView.setVisibility(View.VISIBLE);
            oneImageView.setVisibility(View.GONE);
            adapter.setData(list);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            recyclerView.setAdapter(adapter);
            recyclerView.setNestedScrollingEnabled(false);
            adapter.setOnClickListener(new ItemImageAdapter.SingleTapConfirmedListener() {
                @Override
                public void onClick(int position) {
                    if (l != null) {
                        l.onClick(position);
                    }
                }

                @Override
                public void onDoubleTap(int position) {
                    if (l != null) {
                        l.onDoubleTap(position);
                    }
                }
            });
        } else {
            recyclerView.setVisibility(View.GONE);
            oneImageView.setVisibility(View.VISIBLE);
            ImageHelper.load(getContext(), list.get(0), oneImageView, null, true, R.mipmap.default_pic, R.mipmap.default_pic);
            oneImageView.setDoubleClickListener(new DoubleScaleImageView.DoubleClickListener() {
                @Override
                public void onSingleTapConfirmed() {
                    if (l != null) {
                        l.onClick(0);
                    }
                }

                @Override
                public void onDoubleTap() {
                    if (l != null) {
                        l.onDoubleTap(0);
                    }
                }
            });
        }
    }

    SudokuViewClickListener l;

    public interface SudokuViewClickListener {
        void onClick(int position);

        void onDoubleTap(int position);
    }

    public void setOnSingleClickListener(SudokuViewClickListener l) {
        this.l = l;
    }

    public static class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.top = space / 2;
            outRect.bottom = space / 2;
            outRect.left = space / 2;
            outRect.right = space / 2;
        }
    }

}
