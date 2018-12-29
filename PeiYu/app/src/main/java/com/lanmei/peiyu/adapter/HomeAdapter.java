package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.AdBean;
import com.lanmei.peiyu.bean.HomeBean;
import com.lanmei.peiyu.bean.HomeClassifyBean;
import com.lanmei.peiyu.ui.home.activity.DataEntryActivity;
import com.lanmei.peiyu.ui.home.activity.SimulationIncomeActivity;
import com.lanmei.peiyu.ui.mine.activity.AfterSaleOrderActivity;
import com.lanmei.peiyu.ui.mine.activity.InstallApplyActivity;
import com.lanmei.peiyu.ui.news.activity.NewsDetailsActivity;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.IntentUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * 首页
 */
public class HomeAdapter extends SwipeRefreshAdapter<HomeBean> {

    public int TYPE_BANNER = 100;
    private BannerViewHolder bannerViewHolder;

    public HomeAdapter(Context context) {
        super(context);
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
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(null);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.startActivity(context, NewsDetailsActivity.class);
            }
        });
    }

    @Override
    public int getCount() {
        return 7;
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


        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(HomeClassifyBean bean) {

        }
    }

    //头部
    public class BannerViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.banner)
        ConvenientBanner banner;
        @InjectView(R.id.recyclerView_recommend)
        RecyclerView recyclerViewRecommend;//推荐商品

        public BannerViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        @OnClick({R.id.home_classify1_tv, R.id.home_classify2_tv, R.id.home_classify3_tv, R.id.home_classify4_tv})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.home_classify1_tv://模拟收入
                    IntentUtil.startActivity(context, SimulationIncomeActivity.class);
                    break;
                case R.id.home_classify2_tv://资料录入
                    IntentUtil.startActivity(context, DataEntryActivity.class);//
                    break;
                case R.id.home_classify3_tv://安装申报
                    IntentUtil.startActivity(context, InstallApplyActivity.class);
                    break;
                case R.id.home_classify4_tv://售后报修
                    IntentUtil.startActivity(context, AfterSaleOrderActivity.class);
                    break;
            }
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
        public void setRecommendGoods(List<AdBean> adBeanList) {
            HomeRecommendAdapter adapter = new HomeRecommendAdapter(context);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerViewRecommend.setLayoutManager(layoutManager);
            recyclerViewRecommend.setNestedScrollingEnabled(false);
            recyclerViewRecommend.setAdapter(adapter);
        }
    }


    //轮播图
    public void setBannerParameter(List<AdBean> adBeanList) {
        bannerViewHolder.setBannerParameter(adBeanList);
    }

    //推荐商品
    public void setRecommendGoods(List<AdBean> list) {
        bannerViewHolder.setRecommendGoods(list);
    }

}
