package com.lanmei.peiyu.bean;

/**
 * Created by xkai on 2019/1/7.
 * 问题类型
 */

public class ProblemTypeBean {

    /**
     * id : 1
     * problemname : 机器坏了
     * addtime : null
     * uptime : null
     */

    private String id;
    private String problemname;
    private String addtime;
    private String uptime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProblemname() {
        return problemname;
    }

    public void setProblemname(String problemname) {
        this.problemname = problemname;
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
}
