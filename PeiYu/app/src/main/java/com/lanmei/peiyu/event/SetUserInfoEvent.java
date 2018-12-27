package com.lanmei.peiyu.event;

import com.xson.common.bean.UserBean;

/**
 * Created by xkai on 2018/1/2.
 * 设置用户信息事件，登录、修改用户信息后设置用户信息(或者退出用户的时候)
 */

public class SetUserInfoEvent {

    private UserBean bean;

    public UserBean getBean() {
        return bean;
    }

    public SetUserInfoEvent(UserBean bean){
        this.bean = bean;
    }
}
