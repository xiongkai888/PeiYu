package com.lanmei.peiyu.bean;

/**
 * Created by xkai on 2017/7/7.
 * 充值记录
 */

public class RechargeResultBean {

    /**
     * id : 300
     * uid : 202
     * money : 0.00
     * balance : 0.00
     * sn : 361
     * recode_type : 其他
     * recode_info : 订单支付
     * isread : 0
     * readtime : 0
     * addtime : 2018-02-01 16:09:43
     * updatetime : 1517472583
     * nickname : 大熊
     * pic : http://stdrimages.oss-cn-shenzhen.aliyuncs.com/lanmei/kang/img/head1167190746.jpg.tmp
     * recode_info_date : {"sn":"361","info":"订单支付"}
     */

    private String id;
    private String uid;
    private String money;
    private String balance;
    private String sn;
    private String recode_type;
    private String recode_info;
    private String isread;
    private String readtime;
    private String addtime;
    private String updatetime;
    private String nickname;
    private String pic;
    private RecodeInfoDateBean recode_info_date;

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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getRecode_type() {
        return recode_type;
    }

    public void setRecode_type(String recode_type) {
        this.recode_type = recode_type;
    }

    public String getRecode_info() {
        return recode_info;
    }

    public void setRecode_info(String recode_info) {
        this.recode_info = recode_info;
    }

    public String getIsread() {
        return isread;
    }

    public void setIsread(String isread) {
        this.isread = isread;
    }

    public String getReadtime() {
        return readtime;
    }

    public void setReadtime(String readtime) {
        this.readtime = readtime;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
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

    public RecodeInfoDateBean getRecode_info_date() {
        return recode_info_date;
    }

    public void setRecode_info_date(RecodeInfoDateBean recode_info_date) {
        this.recode_info_date = recode_info_date;
    }

    public static class RecodeInfoDateBean {
        /**
         * sn : 361
         * info : 订单支付
         */

        private String sn;
        private String info;

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
