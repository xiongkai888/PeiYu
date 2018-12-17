package com.xson.common.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.xson.common.utils.L;

import butterknife.ButterKnife;

/**
 * @author Milk <249828165@qq.com>
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * @return contentViewId   布局Id
     */
    public abstract int getContentViewId();

    /**
     * @param savedInstanceState onCreate()中的参数,Bundle类型
     */
    protected abstract void initAllMembersView(Bundle savedInstanceState);

    public void initIntent(Intent intent) {

    }

    @Override
    protected void onDestroy() {
        L.fixInputMethodManagerLeak(this);
        super.onDestroy();
        ButterKnife.reset(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        initIntent(getIntent());
        ButterKnife.inject(this, this);
        initAllMembersView(savedInstanceState);
    }


    public Context getContext() {
        return this;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
