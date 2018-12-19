package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.lanmei.peiyu.R;
import com.xson.common.helper.ImageHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 图片轮播适配器
 */
public class BannerHolderView implements Holder<String> {

    @InjectView(R.id.banner_img)
    ImageView bannerImg;

    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner_img, null);
        ButterKnife.inject(this,view);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        ImageHelper.load(context,data,bannerImg,null,true,R.mipmap.default_pic,R.mipmap.default_pic);
//        Glide.with(context).load(data).error(R.mipmap.default_pic).into(bannerImg);
    }
}
