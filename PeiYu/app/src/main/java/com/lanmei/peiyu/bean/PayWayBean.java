package com.lanmei.peiyu.bean;

/**
 * Created by xkai on 2018/10/11.
 * 支付方式信息
 */

public class PayWayBean {


    private boolean isChoose;


    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public boolean isChoose() {
        return isChoose;
    }
    /**
     * id : 7
     * c_name : 微信支付
     */

    private String id;
    private String c_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }
}
