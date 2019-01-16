package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.AdBean;
import com.lanmei.peiyu.bean.GoodsDetailsBean;
import com.lanmei.peiyu.bean.HomeClassifyBean;
import com.lanmei.peiyu.bean.NewsListBean;
import com.lanmei.peiyu.ui.news.activity.NewsDetailsActivity;
import com.lanmei.peiyu.utils.CommonUtils;
import com.lanmei.peiyu.utils.FormatTime;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 首页
 */
public class HomeAdapter extends SwipeRefreshAdapter<NewsListBean> {

    public int TYPE_BANNER = 100;
    private BannerViewHolder bannerViewHolder;
    private FormatTime time;

    public HomeAdapter(Context context) {
        super(context);
        time = new FormatTime(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BANNER) { // banner
            if (bannerViewHolder == null){
                bannerViewHolder = new BannerViewHolder(LayoutInflater.from(context).inflate(R.layout.include_head_home, parent, false));
            }
            return bannerViewHolder;
        }
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home, parent, false));
    }


    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_BANNER) {
            return;
        }
        final NewsListBean bean = getItem(position-1);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isLogin(context)){
                    IntentUtil.startActivity(context, NewsDetailsActivity.class,bean.getId());
                }
            }
        });
    }

    @Override
    public int getCount() {
        return super.getCount()+1;
    }

    @Override
    public int getItemViewType2(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        }
        return super.getItemViewType2(position);
    }

    //
    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.title_tv)
        TextView titleTv;
        @InjectView(R.id.name_tv)
        TextView nameTv;
        @InjectView(R.id.time_tv)
        TextView timeTv;
        @InjectView(R.id.pic_iv)
        ImageView picIv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(NewsListBean bean) {
            titleTv.setText(bean.getTitle());
            nameTv.setText(bean.getName());
            timeTv.setText(time.formatterTime(bean.getAddtime()));
            CommonUtils.loadImage(context,picIv, StringUtils.isEmpty(bean.getFile())?"":bean.getFile().get(0));
        }
    }

    //头部
    public class BannerViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.banner)
        ConvenientBanner banner;
        @InjectView(R.id.recyclerView_recommend)
        RecyclerView recyclerViewRecommend;//推荐商品
        @InjectView(R.id.recyclerView_classify)
        RecyclerView recyclerViewClassify;//分类
        @InjectView(R.id.recyclerView_img)
        RecyclerView recyclerViewImg;//推荐图

        public BannerViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setBannerParameter(List<AdBean> adBeanList) {
            banner.setPages(new CBViewHolderCreator() {
                @Override
                public Object createHolder() {
                    return new HomeAdAdapter();
                }
            }, adBeanList);
            banner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
            banner.setPageIndicator(new int[]{R.drawable.shape_item_index_white, R.drawable.shape_item_index_red});
            banner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
            banner.startTurning(3000);
        }
        //推荐商品
        public void setRecommendGoods(List<GoodsDetailsBean> list) {
            HomeRecommendAdapter adapter = new HomeRecommendAdapter(context);
            adapter.setData(list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerViewRecommend.setLayoutManager(layoutManager);
            recyclerViewRecommend.setNestedScrollingEnabled(false);
            recyclerViewRecommend.setAdapter(adapter);
        }
        //首页分类(模拟收益、资料录入等)
        public void setClassifyList(List<HomeClassifyBean> list) {
            HomeClassifyAdapter adapter = new HomeClassifyAdapter(context);
            adapter.setData(list);
            recyclerViewClassify.setLayoutManager(new GridLayoutManager(context, 4));
            recyclerViewClassify.setNestedScrollingEnabled(false);
            recyclerViewClassify.setAdapter(adapter);
        }

        //推荐图
        public void setRecommendImge(List<AdBean> list) {
            RecommendImgeAdapter adapter = new RecommendImgeAdapter(context);
            GridLayoutManager layoutManager = new GridLayoutManager(context, 3);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == 0) {
                        return 3;
                    }
                    return 1;
                }
            });
            adapter.setData(list);
            recyclerViewImg.setLayoutManager(layoutManager);
            recyclerViewImg.setNestedScrollingEnabled(false);
            recyclerViewImg.setAdapter(adapter);
        }

    }


    //轮播图
    public void setBannerParameter(List<AdBean> adBeanList) {
        if (bannerViewHolder != null)
        bannerViewHolder.setBannerParameter(adBeanList);
    }

    //推荐商品
    public void setRecommendGoods(List<GoodsDetailsBean> list) {
        if (bannerViewHolder != null)
        bannerViewHolder.setRecommendGoods(list);
    }
    //首页分类(模拟收益、资料录入等)
    public void setClassifyList(List<HomeClassifyBean> list) {
        if (bannerViewHolder != null)
        bannerViewHolder.setClassifyList(list);
    }
    //首页推荐图
    public void setRecommendImge(List<AdBean> list) {
        if (bannerViewHolder != null)
        bannerViewHolder.setRecommendImge(list);
    }

}
