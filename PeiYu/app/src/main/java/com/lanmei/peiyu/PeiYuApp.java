package com.lanmei.peiyu;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.sdk.android.oss.common.OSSLog;
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
