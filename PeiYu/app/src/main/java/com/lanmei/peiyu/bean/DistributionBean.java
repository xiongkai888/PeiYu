package com.lanmei.peiyu.bean;

/**
 * Created by xkai on 2018/10/16.
 * 配送方式
 */

public class DistributionBean {

    /**
     * id : 1
     * classname : 快递
     * setval : 1
     */

    private String id;
    private String classname;
    private String setval;
    /**
     * free_shipping : 500.00
     * free : 15.00
     */

    private String free_shipping;
    private String free;

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

    public String getSetval() {
        return setval;
    }

    public void setSetval(String setval) {
        this.setval = setval;
    }

    public String getFree_shipping() {
        return free_shipping;
    }

    public void setFree_shipping(String free_shipping) {
        this.free_shipping = free_shipping;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }
}
