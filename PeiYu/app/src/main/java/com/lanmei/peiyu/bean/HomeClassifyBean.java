package com.lanmei.peiyu.bean;

import java.io.Serializable;

/**
 * Created by xkai on 2018/12/3.
 */

public class HomeClassifyBean implements Serializable{

    private String name;
    private int picId;

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public int getPicId() {
        return picId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
