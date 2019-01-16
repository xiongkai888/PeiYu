package com.lanmei.peiyu.bean;

/**
 * Created by xkai on 2019/1/15.
 * 通知信息（系统通知）
 */

public class NotificationBean {

    /**
     * id : 85
     * addtime : 1547517761
     * title : 专业项目
     * intro : dsfsafdfasdf
     * state : 0
     * type : 0
     */

    private String id;
    private String addtime;
    private String title;
    private String intro;
    private String state;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
