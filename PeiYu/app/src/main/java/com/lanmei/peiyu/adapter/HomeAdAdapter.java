package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.AdBean;
import com.xson.common.helper.ImageHelper;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 首页图片轮播适配器(有地址就跳转)
 */
public class HomeAdAdapter implements Holder<AdBean> {

    @InjectView(R.id.banner_img)
    ImageView bannerImg;

    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner_img, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void UpdateUI(final Context context, final int position, final AdBean data) {
        if (data == null) {
            return;
        }
        ImageHelper.load(context, data.getPic(), bannerImg, null, true, R.mipmap.default_pic, R.mipmap.default_pic);
        bannerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = data.getLink();
                if (StringUtils.isEmpty(link)) {
                    return;
                }
//                if (link.startsWith("http")) {
//                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
//                } else if (link.startsWith("c")) {
//                    String[] strings = link.split("_");
//                    if (!StringUtils.isEmpty(strings) && strings.length == 2) {
//                        Bundle bundle = new Bundle();
//                        MerchantTabClassifyBean bean = new MerchantTabClassifyBean();
//                        bean.setId(strings[1]);
//                        bean.setClassname("商品列表");
//                        bundle.putSerializable("bean", bean);
//                        IntentUtil.startActivity(context, GoodsListActivity.class, bundle);
//                    }
//                } else if (link.startsWith("g")) {
//                    String[] strings = link.split("_");
//                    if (!StringUtils.isEmpty(strings) && strings.length == 2) {
//                        IntentUtil.startActivity(context, GoodsDetailsActivity.class,strings[1]);
//                    }
//                } else if (link.startsWith("p")) {
//                    String[] strings = link.split("_");
//                    if (!StringUtils.isEmpty(strings) && strings.length == 2) {
//                        IntentUtil.startActivity(context, NewsDetailsActivity.class,strings[1]);
//                    }
//                }
            }
        });
    }
}
