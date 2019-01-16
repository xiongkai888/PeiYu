package com.lanmei.peiyu;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.sdk.android.oss.common.OSSLog;
import com.lanmei.peiyu.update.UpdateAppConfig;
import com.lanmei.peiyu.utils.Constant;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.xson.common.app.BaseApp;
import com.xson.common.utils.L;

/**
 * Created by xkai on 2018/4/13.
 */

public class PeiYuApp extends BaseApp {

    private static PeiYuApp instance;

    @Override
    protected void installMonitor() {
        instance = this;
        L.debug = OSSLog.enableLog = true;//true,false
        UpdateAppConfig.initUpdateApp(this);//app版本更新
        //友盟初始化设置
        initUM();
    }

    public void initUM() {

        PlatformConfig.setWeixin(Constant.WEIXIN_APP_ID, Constant.WEIXIN_APP_SECRET);

        UMConfigure.setLogEnabled(L.debug);//如果查看初始化过程中的LOG，一定要在调用初始化方法前将LOG开关打开。

        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
    }

    @Override
    public void watch(Object object) {

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public static PeiYuApp getInstance() {
        return instance;
    }

}
