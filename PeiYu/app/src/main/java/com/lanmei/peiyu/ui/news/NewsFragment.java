package com.lanmei.peiyu.ui.news;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.NewsTabAdapter;
import com.lanmei.peiyu.bean.NewsTabBean;
import com.lanmei.peiyu.ui.news.activity.SearchNewsActivity;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xkai on 2018/7/13.
 * 资讯
 */

public class NewsFragment extends BaseFragment {

    @InjectView(R.id.viewPager)
    ViewPager mViewPager;
    @InjectView(R.id.tabLayout)
    TabLayout mTabLayout;

    public int getContentViewId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        PeiYuApi api = new PeiYuApi("post/category");
        HttpClient.newInstance(context).request(api, new BeanRequest.SuccessListener<NoPageListBean<NewsTabBean>>() {
            @Override
            public void onResponse(NoPageListBean<NewsTabBean> response) {
                if (mTabLayout == null) {
                    return;
                }
                initTabLayout(response.data);
            }
        });
    }

    private void initTabLayout(List<NewsTabBean> list) {
        if (StringUtils.isEmpty(list)) {
            return;
        }
        NewsTabAdapter tabAdapter = new NewsTabAdapter(getChildFragmentManager());
        tabAdapter.setList(list);
        mViewPager.setAdapter(tabAdapter);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @OnClick({R.id.classify_tv, R.id.search_tv, R.id.menu_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.classify_tv:
                break;
            case R.id.search_tv://搜索资讯
                IntentUtil.startActivity(context, SearchNewsActivity.class);
                break;
            case R.id.menu_tv:
                break;
        }
    }
}
