package com.lanmei.peiyu.ui.classify.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.GoodsDetailsBean;
import com.lanmei.peiyu.ui.classify.activity.GoodsDetailsActivity;
import com.lanmei.peiyu.utils.CommonUtils;
import com.lanmei.peiyu.view.SlideDetailsLayout;
import com.xson.common.app.BaseFragment;
import com.xson.common.utils.StringUtils;
import com.xson.common.widget.FormatTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 商品
 */
public class GoodsInfoFragment extends BaseFragment implements SlideDetailsLayout.OnSlideDetailsListener{


    @InjectView(R.id.banner)
    ConvenientBanner banner;
    @InjectView(R.id.goods_detail)
    TextView goodsDetail;
    @InjectView(R.id.goods_config)
    TextView goodsConfig;
    @InjectView(R.id.after_sale)
    TextView afterSale;
    @InjectView(R.id.tab_cursor)
    View tabCursor;
    @InjectView(R.id.frameLayout_content)
    FrameLayout frameLayoutContent;
    @InjectView(R.id.slideDetailsLayout)
    SlideDetailsLayout slideDetailsLayout;
    @InjectView(R.id.fab_up)
    FloatingActionButton fabUp;
    @InjectView(R.id.scrollView)
    NestedScrollView scrollView;
    @InjectView(R.id.name_tv)
    TextView nameTv;
    @InjectView(R.id.price_tv)
    TextView priceTv;
    @InjectView(R.id.comment_num_tv)
    FormatTextView commentNumTv;

    private GoodsConfigFragment goodsConfigFragment;
    private GoodsInfoWebFragment goodsInfoWebFragment;
    private GoodsConfigFragment goodsConfigFragment1;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<TextView> tabTextList = new ArrayList<>();
    private Fragment currFragment;
    private int currIndex = 0;
    public GoodsDetailsActivity activity;
    private float fromX;
    GoodsDetailsBean bean;//商品信息bean

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (GoodsDetailsActivity) context;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_goods_info;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        slideDetailsLayout.setOnSlideDetailsListener(this);
        init();
    }

    private void init() {
        Bundle bundle = getArguments();
        if (!StringUtils.isEmpty(bundle)) {
            bean = (GoodsDetailsBean) bundle.getSerializable("bean");
        }
        if (bean == null) {
            return;
        }
        setParameter();
        initView();
        initTabView();
        CommonUtils.setBanner(banner,bean.getImgs(),true);//商品轮播图
    }

    private void setParameter() {
        nameTv.setText(bean.getGoodsname());
        priceTv.setText(String.format(context.getString(R.string.price), bean.getSale_price()));
    }

    private void initTabView() {
        tabTextList.add(goodsDetail);
        tabTextList.add(goodsConfig);
        tabTextList.add(afterSale);
    }


    private void initView() {
        fabUp.hide();
        goodsInfoWebFragment = new GoodsInfoWebFragment();//商品详情
        goodsConfigFragment = new GoodsConfigFragment();//规格参数
        goodsConfigFragment1 = new GoodsConfigFragment();//售后保障
        Bundle bundle = new Bundle();
        bundle.putString("content", bean.getContent());
        goodsInfoWebFragment.setArguments(bundle);

        fragmentList.add(goodsConfigFragment);
        fragmentList.add(goodsInfoWebFragment);
        fragmentList.add(goodsConfigFragment1);
        currFragment = goodsInfoWebFragment;
        //默认显示商品详情tab
        getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_content, currFragment).commitAllowingStateLoss();
    }



    private void scrollCursor() {
        TranslateAnimation anim = new TranslateAnimation(fromX, currIndex * tabCursor.getWidth(), 0, 0);
        anim.setFillAfter(true);
        anim.setDuration(50);
        fromX = currIndex * tabCursor.getWidth();
        tabCursor.startAnimation(anim);

        for (int i = 0; i < tabTextList.size(); i++) {
            tabTextList.get(i).setTextColor(i == currIndex ? getResources().getColor(R.color.colorPrimaryDark) : getResources().getColor(R.color.black));
        }
    }

    private void switchFragment(Fragment fromFragment, Fragment toFragment) {
        if (currFragment != toFragment) {
            if (!toFragment.isAdded()) {
                getFragmentManager().beginTransaction().hide(fromFragment).add(R.id.frameLayout_content, toFragment).commitAllowingStateLoss();
            } else {
                getFragmentManager().beginTransaction().hide(fromFragment).show(toFragment).commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.goods_detail, R.id.goods_config,R.id.after_sale,R.id.fab_up,R.id.pull_up_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.goods_detail: //商品详情tab
                currIndex = 0;
                scrollCursor();
                switchFragment(currFragment, goodsInfoWebFragment);
                currFragment = goodsInfoWebFragment;
                break;
            case R.id.goods_config://商品规格tab
                currIndex = 1;
                scrollCursor();
                switchFragment(currFragment, goodsConfigFragment);
                currFragment = goodsConfigFragment;
                break;
            case R.id.after_sale://售后
                currIndex = 2;
                scrollCursor();
                switchFragment(currFragment, goodsConfigFragment1);
                currFragment = goodsConfigFragment1;
                break;
            case R.id.fab_up://滚到顶部
                scrollView.smoothScrollTo(0, 0);
                slideDetailsLayout.smoothClose(true);
                break;
            case R.id.pull_up_view://上拉查看图文详情
                slideDetailsLayout.smoothOpen(true);
                break;
        }
    }

    //只设置显示一个评论item
    @Subscribe(sticky = true)
    public void showOnlyComment(Object event) {
//        GoodsCommentAdapter adapter = new GoodsCommentAdapter(context);
//        adapter.setOnlyItem(true);
//        adapter.setData(event.getList());
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStatusChanged(SlideDetailsLayout.Status status) {
        //当前为图文详情页
        if (status == SlideDetailsLayout.Status.OPEN) {
            fabUp.show();
            activity.operaTitleBar(true, true);
        } else {
            //当前为商品详情页
            fabUp.hide();
            activity.operaTitleBar(false, false);
        }
    }
}
