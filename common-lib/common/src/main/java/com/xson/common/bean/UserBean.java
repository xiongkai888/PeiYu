package com.xson.common.bean;

import java.util.List;

/**
 * Created by xkai on 2018/8/30.
 * 用户信息
 *
 */

public class UserBean {

    /**
     * id : 820515
     * nickname : 小四轮
     * realname : null
     * phone : 15914369252
     * user_type : 0
     * pic : http://gzyxg.oss-cn-shenzhen.aliyuncs.com/lanmei/yixiuge/img1/head4219045509521850070.jpg
     * email : 173422222@qq.com
     * area : 广东省广州市天河区
     * sex : 1
     * address : 天河客运站
     * education : null
     * school : null
     * unit : null
     * weixin : null
     * student_type : 2016级一班
     * student_nature : 院本部（包括分院）
     * department : ["普外科","神经外科","胸心外科","泌尿外科","整形外科","骨科","儿外科"]
     * position : null
     * learned : 普外科,神经外科,胸心外科,泌尿外科
     * nolearned : 整形外科,骨科,儿外科
     */

    private String id;
    private String nickname;
    private String realname;
    private String phone;
    private String user_type;
    private String pic;
    private String email;
    private String area;
    private String sex;
    private String address;
    private String education;
    private String school;
    private String unit;
    private String weixin;
    private String student_type;
    private String student_nature;
    private String position;
    private String learned;
    private String nolearned;
    private String political;//政治面貌

    private List<String> department;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getStudent_type() {
        return student_type;
    }

    public void setStudent_type(String student_type) {
        this.student_type = student_type;
    }

    public String getStudent_nature() {
        return student_nature;
    }

    public void setStudent_nature(String student_nature) {
        this.student_nature = student_nature;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLearned() {
        return learned;
    }

    public void setLearned(String learned) {
        this.learned = learned;
    }

    public String getNolearned() {
        return nolearned;
    }

    public void setNolearned(String nolearned) {
        this.nolearned = nolearned;
    }

    public List<String> getDepartment() {
        return department;
    }

    public void setDepartment(List<String> department) {
        this.department = department;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    public String getPolitical() {
        return political;
    }
}
