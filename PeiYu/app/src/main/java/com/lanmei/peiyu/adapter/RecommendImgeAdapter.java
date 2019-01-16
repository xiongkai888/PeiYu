package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.AdBean;
import com.lanmei.peiyu.bean.GoodsDetailsBean;
import com.lanmei.peiyu.ui.classify.activity.GoodsDetailsActivity;
import com.lanmei.peiyu.ui.home.activity.ClassifyActivity;
import com.lanmei.peiyu.ui.news.activity.NewsDetailsActivity;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.DataBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 首页推荐图
 */
public class RecommendImgeAdapter extends SwipeRefreshAdapter<AdBean> {

    public RecommendImgeAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recommend_imge, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        final AdBean bean = getItem(position);
        if (bean == null) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        CommonUtils.loadImage(context, viewHolder.picIv, bean.getPic());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = bean.getLink();
                if (StringUtils.isEmpty(link)) {
                    return;
                }
                if (link.startsWith("http")) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                } else if (link.startsWith("c")) {
                    String[] strings = link.split("_");
                    if (!StringUtils.isEmpty(strings) && strings.length == 2) {
                        IntentUtil.startActivity(context, ClassifyActivity.class);
                    }
                } else if (link.startsWith("g")) {
                    String[] strings = link.split("_");
                    if (!StringUtils.isEmpty(strings) && strings.length == 2) {
                        loadGoodsDetails(context, strings[1]);
                    }
                } else if (link.startsWith("p")) {
                    String[] strings = link.split("_");
                    if (!StringUtils.isEmpty(strings) && strings.length == 2) {
                        IntentUtil.startActivity(context, NewsDetailsActivity.class, strings[1]);
                    }
                }
            }
        });
    }

    //商品详情
    private void loadGoodsDetails(final Context context, String id) {
        PeiYuApi api = new PeiYuApi("app/goodsdetail");
        api.addParams("id", id);
        HttpClient.newInstance(context).loadingRequest(api, new BeanRequest.SuccessListener<DataBean<GoodsDetailsBean>>() {
            @Override
            public void onResponse(DataBean<GoodsDetailsBean> response) {
                if (((BaseActivity) context).isFinishing()) {
                    return;
                }
                GoodsDetailsBean bean = response.data;
                if (!StringUtils.isEmpty(bean)) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean", bean);
                    IntentUtil.startActivity(context, GoodsDetailsActivity.class, bundle);
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.pic_iv)
        ImageView picIv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
