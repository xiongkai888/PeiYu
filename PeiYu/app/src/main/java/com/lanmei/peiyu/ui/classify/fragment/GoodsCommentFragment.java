package com.lanmei.peiyu.ui.classify.fragment;

import android.os.Bundle;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.GoodsCommentAdapter;
import com.lanmei.peiyu.bean.GoodsCommentBean;
import com.lanmei.peiyu.bean.GoodsDetailsBean;
import com.lanmei.peiyu.event.OnlyCommentEvent;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.StringUtils;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import butterknife.InjectView;


/**
 * 评价
 */
public class GoodsCommentFragment extends BaseFragment {

    GoodsDetailsBean bean;//商品信息bean
    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_single_listview_no;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (!StringUtils.isEmpty(bundle)) {
            bean = (GoodsDetailsBean) bundle.getSerializable("bean");
        }
        if (bean == null){
            return;
        }
        smartSwipeRefreshLayout.initWithLinearLayout();

        PeiYuApi api = new PeiYuApi("app/comment");
//        api.addParams("uid",api.getUserId(context));
        api.addParams("goodsid",bean.getId());
        GoodsCommentAdapter adapter = new GoodsCommentAdapter(context);
        smartSwipeRefreshLayout.setAdapter(adapter);
        SwipeRefreshController<NoPageListBean<GoodsCommentBean>> controller = new SwipeRefreshController<NoPageListBean<GoodsCommentBean>>(context, smartSwipeRefreshLayout, api, adapter) {
            @Override
            public void onRefreshResponse(NoPageListBean<GoodsCommentBean> response) {
                super.onRefreshResponse(response);
                if (smartSwipeRefreshLayout != null)
                EventBus.getDefault().post(new OnlyCommentEvent(response.data));
            }
        };
        controller.loadFirstPage();
    }

}
