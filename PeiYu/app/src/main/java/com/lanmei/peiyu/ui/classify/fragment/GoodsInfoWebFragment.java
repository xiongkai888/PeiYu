package com.lanmei.peiyu.ui.classify.fragment;

import android.os.Bundle;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.view.ItemWebView;
import com.lanmei.peiyu.webviewpage.WebViewPhotoBrowserUtil;
import com.xson.common.app.BaseFragment;

import butterknife.InjectView;


/**
 * 图文详情webview的Fragment
 */
public class GoodsInfoWebFragment extends BaseFragment {

    @InjectView(R.id.detail_webView)
    ItemWebView detailWebView;

    
    @Override
    public int getContentViewId() {
        return R.layout.fragment_item_info_web;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle == null){
            return;
        }
        String content = bundle.getString("content");
        WebViewPhotoBrowserUtil.photoBrowser(context, detailWebView, content);
    }

}
