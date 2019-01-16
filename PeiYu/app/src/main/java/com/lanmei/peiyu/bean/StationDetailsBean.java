package com.lanmei.peiyu.bean;

import com.lanmei.peiyu.utils.CommonUtils;

/**
 * Created by xkai on 2019/1/16.
 */

public class StationDetailsBean {

    /**
     * id : 1
     * addtime : 1547109915
     * uptime : 1547109915
     * capacity : 1000
     * sid : 12
     * source : 1
     * name : 今日发电量
     * pic : http://qkmimages.img-cn-shenzhen.aliyuncs.com/180119/5a61bf33cd068.jpg
     * power : 5000
     * pname : 电站功率
     * ppic : http://qkmimages.img-cn-shenzhen.aliyuncs.com/180119/5a61bf33cd068.jpg
     * power_total : 50000
     * ptname : 累计发电量
     * ptpic : null
     * lucre : 5000
     * lname : 今日收益
     * lpic : http://qkmimages.img-cn-shenzhen.aliyuncs.com/180119/5a61bf33cd068.jpg
     * lucre_total : 5000
     * ltname : 累计收益
     * ltpic : http://qkmimages.img-cn-shenzhen.aliyuncs.com/180119/5a61bf33cd068.jpg
     */

    private String id;
    private String addtime;
    private String uptime;
    private String capacity = CommonUtils.isZero;
    private String sid;
    private String source;
    private String name;
    private String pic;
    private String power = CommonUtils.isZero;
    private String pname;
    private String ppic;
    private String power_total = CommonUtils.isZero;
    private String ptname;
    private String ptpic;
    private String lucre = CommonUtils.isZero;
    private String lname;
    private String lpic;
    private String lucre_total = CommonUtils.isZero;
    private String ltname;
    private String ltpic;

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

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPpic() {
        return ppic;
    }

    public void setPpic(String ppic) {
        this.ppic = ppic;
    }

    public String getPower_total() {
        return power_total;
    }

    public void setPower_total(String power_total) {
        this.power_total = power_total;
    }

    public String getPtname() {
        return ptname;
    }

    public void setPtname(String ptname) {
        this.ptname = ptname;
    }

    public String getPtpic() {
        return ptpic;
    }

    public void setPtpic(String ptpic) {
        this.ptpic = ptpic;
    }

    public String getLucre() {
        return lucre;
    }

    public void setLucre(String lucre) {
        this.lucre = lucre;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getLpic() {
        return lpic;
    }

    public void setLpic(String lpic) {
        this.lpic = lpic;
    }

    public String getLucre_total() {
        return lucre_total;
    }

    public void setLucre_total(String lucre_total) {
        this.lucre_total = lucre_total;
    }

    public String getLtname() {
        return ltname;
    }

    public void setLtname(String ltname) {
        this.ltname = ltname;
    }

    public String getLtpic() {
        return ltpic;
    }

    public void setLtpic(String ltpic) {
        this.ltpic = ltpic;
    }
}
