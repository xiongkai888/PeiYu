package com.lanmei.peiyu.ui.classify;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.ClassifyListAdapter;
import com.lanmei.peiyu.adapter.ClassifyTabAdapter;
import com.lanmei.peiyu.bean.ClassifyTabBean;
import com.xson.common.app.BaseFragment;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import java.util.ArrayList;
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
        ClassifyTabAdapter voterTabAdapter = new ClassifyTabAdapter(context);
        voterTabAdapter.setData(getVoterTabList());
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(voterTabAdapter);

        adapter = new ClassifyListAdapter(context);
        smartSwipeRefreshLayout.setLayoutManager(new GridLayoutManager(context,3));
        smartSwipeRefreshLayout.setAdapter(adapter);
        smartSwipeRefreshLayout.setMode(SmartSwipeRefreshLayout.Mode.NO_PAGE);
        adapter.notifyDataSetChanged();
    }



    private List<ClassifyTabBean> getVoterTabList() {
        List<ClassifyTabBean> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ClassifyTabBean bean = new ClassifyTabBean();
            switch (i){
                case 0:
                    bean.setName("为您推荐");//"Snack","Frunt","Drink","Others"
                    bean.setChoose(true);
                    break;
                case 1:
                    bean.setName("风电");
                    break;
                case 2:
                    bean.setName("太阳能");
                    break;
                case 3:
                    bean.setName("生物质发电");
                    break;
                case 4:
                    bean.setName("节能");
                    break;
            }
            list.add(bean);
        }
        return list;
    }

}
