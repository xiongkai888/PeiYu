package com.lanmei.peiyu.bean;

import java.io.Serializable;

/**
 * Created by xkai on 2018/2/2.
 * 用户信息
 */

public class UserInfoBean implements Serializable{

    /**
     * id : 202
     * rid :
     * nickname : 大熊
     * pic : http://stdrimages.oss-cn-shenzhen.aliyuncs.com/lanmei/kang/img1/head-915467964.jpg
     * realname :
     * email : dfghh@qq.com
     * phone : 15914369252
     * custom :
     * signature : 我们不一样！
     * money : 9995672.99
     * files_img :
     * ratio : 100
     */

    private String id;
    private String rid;
    private String nickname;
    private String pic;
    private String realname;
    private String email;
    private String phone;
    private String custom;
    private String signature;
    private String money;
    private String files_img;
    private String ridname;//等级

    public void setRidname(String ridname) {
        this.ridname = ridname;
    }

    public String getRidname() {
        return ridname;
    }

    private double ratio;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getFiles_img() {
        return files_img;
    }

    public void setFiles_img(String files_img) {
        this.files_img = files_img;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}
