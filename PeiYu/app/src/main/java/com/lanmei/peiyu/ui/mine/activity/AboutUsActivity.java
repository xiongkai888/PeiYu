package com.lanmei.peiyu.ui.mine.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.lanmei.peiyu.R;
import com.xson.common.app.BaseActivity;
import com.xson.common.widget.CenterTitleToolbar;

import butterknife.InjectView;


/**
 * 关于我们
 */

public class AboutUsActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;


    @Override
    public int getContentViewId() {
        return R.layout.activity_about_us;
    }


    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.about_us);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

    }
}
