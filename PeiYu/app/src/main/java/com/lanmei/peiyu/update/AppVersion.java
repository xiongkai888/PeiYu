package com.lanmei.peiyu.update;

/**
 * Created by xkai on 2017/12/18.
 */

public class AppVersion {


    /**
     * VersionCode : 1
     * VersionName : 1.0
     * VersionLog : 版本说明：1.0
     * AppUrl : /Application/JTERP.apk
     */

    private String VersionCode;
    private String VersionName;
    private String VersionLog;
    private String AppUrl;

    public String getVersionCode() {
        return VersionCode;
    }

    public void setVersionCode(String VersionCode) {
        this.VersionCode = VersionCode;
    }

    public String getVersionName() {
        return VersionName;
    }

    public void setVersionName(String VersionName) {
        this.VersionName = VersionName;
    }

    public String getVersionLog() {
        return VersionLog;
    }

    public void setVersionLog(String VersionLog) {
        this.VersionLog = VersionLog;
    }

    public String getAppUrl() {
        return AppUrl;
    }

    public void setAppUrl(String AppUrl) {
        this.AppUrl = AppUrl;
    }
}
