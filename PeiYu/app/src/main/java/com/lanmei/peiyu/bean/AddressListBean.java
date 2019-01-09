package com.lanmei.peiyu.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by xkai on 2018/1/22.
 * 选择收货地址
 */

public class AddressListBean implements Serializable {


    /**
     * id : 23
     * uid : 202
     * accept_name : 大熊
     * zip : null
     * telphone : null
     * country : null
     * province : 440000
     * city : 440100
     * area : 440106
     * address : 广东省广州市天河区图图
     * mobile : 15914369252
     * default : 1
     * default1 : 1
     */

    private String id;
    private String uid;
    private String accept_name;
    private Object zip;
    private Object telphone;
    private Object country;
    private String province;
    private String city;
    private String area;
    private String address;
    private String mobile;
    @JSONField(name = "default")
    private String defaultX;

    public void setDefaultX(String defaultX) {
        this.defaultX = defaultX;
    }

    public String getDefaultX() {
        return defaultX;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccept_name() {
        return accept_name;
    }

    public void setAccept_name(String accept_name) {
        this.accept_name = accept_name;
    }

    public Object getZip() {
        return zip;
    }

    public void setZip(Object zip) {
        this.zip = zip;
    }

    public Object getTelphone() {
        return telphone;
    }

    public void setTelphone(Object telphone) {
        this.telphone = telphone;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
