package com.lanmei.peiyu.ui.classify;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.ClassifyListAdapter;
import com.lanmei.peiyu.adapter.ClassifyTabAdapter;
import com.lanmei.peiyu.bean.ClassifyTabBean;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.StringUtils;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by xkai on 2018/7/13.
 * 分类
 */

public class ClassifyFragment extends BaseFragment {


    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;//代替垂直tabLayout
    private ClassifyListAdapter adapter;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_classify;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        //分类列表
        adapter = new ClassifyListAdapter(context);
        smartSwipeRefreshLayout.setLayoutManager(new GridLayoutManager(context,3));
        smartSwipeRefreshLayout.setAdapter(adapter);
        smartSwipeRefreshLayout.setMode(SmartSwipeRefreshLayout.Mode.NO_PAGE);
        adapter.notifyDataSetChanged();

        PeiYuApi api = new PeiYuApi("station/goods_class");
        HttpClient.newInstance(context).request(api, new BeanRequest.SuccessListener<NoPageListBean<ClassifyTabBean>>() {
            @Override
            public void onResponse(NoPageListBean<ClassifyTabBean> response) {
                if (recyclerView == null){
                    return;
                }
                setRecyclerView(response.data);
            }
        });
    }


    //分类Tab
    private void setRecyclerView(List<ClassifyTabBean> list){
        if (StringUtils.isEmpty(list)){return;}
        ClassifyTabAdapter voterTabAdapter = new ClassifyTabAdapter(context);
        list.get(0).setChoose(true);
        adapter.setData(list.get(0).get_child());
        adapter.notifyDataSetChanged();
        voterTabAdapter.setData(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(voterTabAdapter);
        voterTabAdapter.setListener(new ClassifyTabAdapter.OnTabClickListener() {
            @Override
            public void OnClickListener(List<ClassifyTabBean.ChildBean> list) {
                adapter.setData(list);
                adapter.notifyDataSetChanged();
            }
        });
    }

}
