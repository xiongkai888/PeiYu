package com.lanmei.peiyu.update;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.xson.common.utils.L;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.callback.DefaultDownloadCB;
import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.creator.DefaultNeedDownloadCreator;
import org.lzh.framework.updatepluginlib.creator.DefaultNeedUpdateCreator;
import org.lzh.framework.updatepluginlib.model.CheckEntity;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.model.UpdateParser;
import org.lzh.framework.updatepluginlib.strategy.UpdateStrategy;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;


/**
 * Created by Administrator on 2017/10/19.
 */

public class UpdateAppConfig {


    public static void initUpdateApp(final Context context) {
        String url = "http://47.107.116.247/api/index/siteinfo";
        Map<String, String> params = new HashMap<>();
        params.put("type", "android");
//        params.put("key","yxg");
        UpdateConfig.getConfig()
                // 必填：数据更新接口,url与checkEntity两种方式任选一种填写
                //                .url(url)
                .checkEntity(new CheckEntity().setMethod("GET").setUrl(url).setParams(params))
                // 必填：用于从数据更新接口获取的数据response中。解析出Update实例。以便框架内部处理
                .jsonParser(new UpdateParser() {
                    @Override
                    public Update parse(String response) throws JSONException {
                        /* 此处根据上面url或者checkEntity设置的检查更新接口的返回数据response解析出
                         * 一个update对象返回即可。更新启动时框架内部即可根据update对象的数据进行处理
                         */
                        L.d("AppUpdateConfig", "response = " + response);
                        JSONObject object = new JSONObject(response);
                        if (object.getInt("status") == 1) {
                            object = object.getJSONObject("data");
                            object = object.getJSONObject("android_update");
                        } else {
                            return null;
                        }
                        UpdateBean update = new UpdateBean();
                        // 此apk包的下载地址
                        L.d("AppUpdateConfig", "url = " + object.optString("url"));
                        update.setUpdateUrl(object.optString("url"));
                        // 此apk包的版本号
                        update.setVersionCode(Integer.parseInt(object.optString("version")));
                        // 此apk包的版本名称
                        update.setVersionName(object.optString("versionName"));
                        // 此apk包的更新内容
                        update.setUpdateContent(object.optString("description"));
                        // 此apk包是否为强制更新
                        update.setForced(object.optBoolean("share_qr"));
                        // 是否显示忽略此次版本更新按钮
                        update.setIgnore(false);
                        return update;

                    }
                })
                // TODO: 2016/5/11 除了以上两个参数为必填。以下的参数均为非必填项。
                // 自定义检查出更新后显示的Dialog，
                .updateDialogCreator(new DefaultNeedUpdateCreator())
                // 自定义下载时的进度条Dialog
                .downloadDialogCreator(new DefaultNeedDownloadCreator())
                // 自定义更新策略，默认WIFI下自动下载更新
                .strategy(new UpdateStrategy() {
                    @Override
                    public boolean isShowUpdateDialog(Update update) {
                        L.d("AppUpdateConfig", "isShowUpdateDialog,UpdateUrl = " + update.getUpdateUrl());
                        // 是否在检查到有新版本更新时展示Dialog。
                        return true;
                    }

                    @Override
                    public boolean isAutoInstall() {
                        L.d("AppUpdateConfig", "isAutoInstall");
                        // 是否自动更新.当为自动更新时。代表下载成功后不通知用户。直接调起安装。
                        return false;
                    }

                    @Override
                    public boolean isShowDownloadDialog() {
                        L.d("AppUpdateConfig", "isShowDownloadDialog");
                        // 在APK下载时。是否显示下载进度的Dialog
                        return true;
                    }
                })
                // 检查更新接口是否有新版本更新的回调。
                .checkCB(new UpdateCheckCB() {
                    @Override
                    public void onCheckStart() {
//                        UIHelper.ToastMessage(context, "开始检查");
                        EventBus.getDefault().post(new UpdateEvent("开始检查"));
                        L.d("AppUpdateConfig", "开始检查");
                    }

                    @Override
                    public void hasUpdate(Update update) {
                        L.d("updateConfig", "hasUpdate");
                    }

                    @Override
                    public void noUpdate() {
//                        UIHelper.ToastMessage(context, "当前已是最新版本");
                        EventBus.getDefault().post(new UpdateEvent("当前已是最新版本"));
                        L.d("AppUpdateConfig", "当前已是最新版本");
                    }

                    @Override
                    public void onCheckError(Throwable t) {
                        L.d("AppUpdateConfig", "onCheckError:" + t.getMessage());
                        EventBus.getDefault().post(new UpdateEvent("检查出错"));
                    }

                    @Override
                    public void onUserCancel() {
                        L.d("updateConfig", "onUserCancel");
                    }

                    @Override
                    public void onCheckIgnore(Update update) {
                        L.d("updateConfig", "更新驳回");
                    }
                })
                // apk下载的回调
                .downloadCB(new DefaultDownloadCB());
    }

    /**
     * 请求文件读写权限。
     */
    public static void requestStoragePermission(Activity activity) {
        new RxPermissions(activity)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            //app更新
                            UpdateBuilder.create()
                                    .downloadDialogCreator(new NotificationDownloadCreator())
//                                    .installDialogCreator(new DefaultNeedInstallCreator())
//                                    .strategy(new AllDialogShowStrategy())
                                    .check();
                        }
                    }
                });
    }

}


