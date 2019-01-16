package com.lanmei.peiyu.update;

import com.xson.common.utils.StringUtils;

import org.lzh.framework.updatepluginlib.model.Update;

/**
 * Created by xkai on 2018/6/11.
 */

public class UpdateBean extends Update {
    @Override
    public void setVersionName(String versionName) {
        if (StringUtils.isEmpty(versionName)){
            versionName = "";
        }
        super.setVersionName(versionName);
    }
}
