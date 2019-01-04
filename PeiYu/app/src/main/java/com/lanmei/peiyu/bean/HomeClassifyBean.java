package com.lanmei.peiyu.bean;

import java.io.Serializable;

/**
 * Created by xkai on 2018/12/3.
 * 首页分类
 */

public class HomeClassifyBean implements Serializable{


    /**
     * id : 97
     * classname : 模拟收益
     * state : 1
     * pic : http://qkmimages.img-cn-shenzhen.aliyuncs.com/Uploads/imgs/20181228/1545983376654.png
     * setval : null
     */

    private String id;
    private String classname;
    private String state;
    private String pic;
    private String setval;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSetval() {
        return setval;
    }

    public void setSetval(String setval) {
        this.setval = setval;
    }
}
