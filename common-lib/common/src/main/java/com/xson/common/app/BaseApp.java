package com.xson.common.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.xson.common.api.AbstractApi;
import com.xson.common.utils.L;
import com.xson.common.utils.SysUtils;

public abstract class BaseApp extends Application implements Thread.UncaughtExceptionHandler {


    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @SuppressLint("NewApi")
    public void init() {
        installMonitor();

        // 捕捉未知异常(崩溃报告)
//        Thread.setDefaultUncaughtExceptionHandler(this);

        AbstractApi.API_URL = (String) SysUtils.getBuildConfigValue(this, "API_URL");

        // 友盟捕获异常
//        MobclickAgent.setCatchUncaughtExceptions(true);
        // 打开统计SDK调试模式（上线时记得关闭）
//        MobclickAgent.setDebugMode( true );
        //禁止默认的页面统计功能，这样将不会再自动统计Activity页面。（包含Activity、Fragment或View的应用）
//        MobclickAgent.openActivityDurationTrack(false);
    }

    @Override
    public void uncaughtException(Thread thread, final Throwable ex) {
        L.e("fangApp", "错误：" + ex.getMessage(), ex);

        Intent dialogIntent = new Intent(this, CrashReportDialogActivity.class);
        dialogIntent.putExtra(CrashReportDialogActivity.KEY_MSG, ex.getMessage());
        dialogIntent.putExtra(CrashReportDialogActivity.KEY_TRACE, Log.getStackTraceString(ex));
        dialogIntent.putExtra(CrashReportDialogActivity.KEY_MEMORY_ERROR, ex instanceof OutOfMemoryError);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    protected abstract void installMonitor();

    public abstract void watch(Object object);
}
