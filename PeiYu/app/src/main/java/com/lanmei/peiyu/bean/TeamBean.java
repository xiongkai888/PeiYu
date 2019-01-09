package com.lanmei.peiyu.bean;

import java.io.Serializable;

/**
 * Created by xkai on 2018/10/26.
 */

public class TeamBean implements Serializable{


    /**
     * nickname : qwer
     * id : 3
     * phone : 13502641232
     * pic : http://qkmimages.img-cn-shenzhen.aliyuncs.com/180119/5a61bf79bb762.jpg
     * reg_time : 1970-01-01 08:00
     * address": null
     * user_type : 会员
     */

    private String nickname;
    private String id;
    private String phone;
    private String pic;
    private String reg_time;
    private String address;
    private String user_type;

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
