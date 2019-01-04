package com.lanmei.peiyu.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by xkai on 2018/11/23.
 */

@SuppressLint({"ViewConstructor", "AppCompatCustomView"})
public class AdImageView extends ImageView {

    public AdImageView(Context context) {
        super(context);
    }

    public AdImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AdImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width,width/2);

    }
}
