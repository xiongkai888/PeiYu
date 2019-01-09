package com.lanmei.peiyu.bean;

/**
 * Created by xkai on 2018/10/10.
 * 商品规格
 */

public class GoodsSpecificationsBean {

    /**
     * id : 96
     * addtime : 1538030271
     * uptime : 1538030271
     * gid : 26
     * price : 200.00
     * vipprice : 0.00
     * oemid : null
     * inventory : 100
     * specificationsname : 颜色,尺寸
     * specifications : 红色,M
     */

    private String id;
    private String addtime;
    private String uptime;
    private String gid;
    private String price;
    private String vipprice;
    private String oemid;
    private String inventory;
    private String specificationsname;
    private String specifications;

    private boolean isSelect;
    /**
     * oemid : null
     * business_price : null
     * sale_price : null
     */

    private String business_price;
    private String sale_price;

    public boolean isSelect() {
        return isSelect;
    }
    public void setSelect(boolean select) {
        isSelect = select;
    }

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

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVipprice() {
        return vipprice;
    }

    public void setVipprice(String vipprice) {
        this.vipprice = vipprice;
    }

    public String getOemid() {
        return oemid;
    }

    public void setOemid(String oemid) {
        this.oemid = oemid;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getSpecificationsname() {
        return specificationsname;
    }

    public void setSpecificationsname(String specificationsname) {
        this.specificationsname = specificationsname;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getBusiness_price() {
        return business_price;
    }

    public void setBusiness_price(String business_price) {
        this.business_price = business_price;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }
}
