package com.lanmei.peiyu;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.lanmei.peiyu.adapter.SplashHolderView;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.helper.SharedAccount;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页、启动也
 */
public class SplashActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    ConvenientBanner banner;
    private ImageView mSkipIv;//立即体验
    private ImageView mLaunchIv;//启动图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setContentView(R.layout.activity_splash);
        boolean isFirstLogin = SharedAccount.getInstance(SplashActivity.this).isFirstLogin();
        if (!isFirstLogin) {//第一次进入该应用
            initViewPager();
        } else {
            mLaunchIv = (ImageView) findViewById(R.id.launch_iv);
            mLaunchIv.setVisibility(View.VISIBLE);
            // 如果不是第一次启动app，则正常显示启动屏
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    enterHomeActivity();
                }
            }, 1000);
        }

    }

    private void enterHomeActivity() {
        if (isFinishing()){
            return;
        }
        if (CommonUtils.isLogin(this)) {
            IntentUtil.startActivity(this, MainActivity.class);
        }
        finish();
    }

    private void initViewPager() {
        mSkipIv = (ImageView) findViewById(R.id.skip_tv);

        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.launch);
        list.add(R.mipmap.launch);
        list.add(R.mipmap.launch);
        banner = (ConvenientBanner) findViewById(R.id.banner);
        banner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new SplashHolderView();
            }
        }, list);
        banner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        banner.setPageIndicator(new int[]{R.drawable.shape_item_index_white, R.drawable.shape_item_index_red});
        banner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        banner.setOnPageChangeListener(this);
        banner.stopTurning();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        L.d("BaseAppCompatActivity",position+"");
        if (position == 2){
            mSkipIv.setVisibility(View.VISIBLE);
            mSkipIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enterHomeActivity();
                    SharedAccount.getInstance(SplashActivity.this).setNoFirstLogin(true);
                }
            });
        }else {
            mSkipIv.setVisibility(View.GONE);
            mSkipIv.setOnClickListener(null);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        L.fixInputMethodManagerLeak(this);
        super.onDestroy();
    }
}
