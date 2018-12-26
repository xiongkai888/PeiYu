package com.lanmei.peiyu.ui.classify.fragment;

import android.app.Activity;
import android.os.Bundle;

import com.lanmei.peiyu.R;
import com.xson.common.app.BaseFragment;

/**
 * 图文详情里的规格参数的Fragment
 */
public class GoodsConfigFragment extends BaseFragment {

//    @InjectView(R.id.listView)
//    ItemListView listView;

    public Activity activity=null;

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.activity = context;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_config;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

    }

    private void init() {
//        listView.setFocusable(false);
//        List<GoodsConfigBean> data = new ArrayList<>();
//        data.add(new GoodsConfigBean("品牌", "华为"));
//        data.add(new GoodsConfigBean("型号", "华为-值得拥有"));
//        listView.setAdapter(new GoodsConfigAdapter(activity, data));
    }
}
