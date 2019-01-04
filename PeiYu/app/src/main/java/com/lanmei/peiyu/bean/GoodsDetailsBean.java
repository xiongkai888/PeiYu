package com.lanmei.peiyu.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xkai on 2018/10/9.
 */

public class GoodsDetailsBean implements Serializable{

    /**
     * id : 6
     * uid : null
     * addtime : 2018-09-29 15:42:23
     * uptime : 0000-00-00 00:00:00
     * goodsname : M手机
     * type : ,6,
     * price : 1000.00
     * up_time : 1538030165
     * down_time : 1493269366
     * inventory : 500
     * is_del : 0
     * order_by : 1
     * cover : http://qkmimages.img-cn-shenzhen.aliyuncs.com/Uploads/imgs/20180927/15380301149743.jpg
     * imgs : ["http://qkmimages.img-cn-shenzhen.aliyuncs.com/Uploads/imgs/20180927/15380301212568.jpg"]
     * state : 1
     * userid : 5
     * content :
     * hot : 1
     * vipprice : 0.00
     * sales : 50
     * promotion : 1
     * spot : 1
     * vgoods : 1
     * comments : 0
     * model : 0
     * specifications : 红色,M
     * specificationsname :
     * attribute :
     * business_price : 500.00
     * sale_price : 500.00
     * barcode : 552111www
     * status : null
     * shoucang : 0
     * classid : 7
     */

    private String id;
    private String uid;
    private String addtime;
    private String uptime;
    private String goodsname;
    private String type;
    private String price;
    private String up_time;
    private String down_time;
    private String inventory;
    private String is_del;
    private String order_by;
    private String cover;
    private String state;
    private String userid;
    private String content;
    private String hot;
    private String vipprice;
    private String sales;
    private String promotion;
    private String spot;
    private String vgoods;
    private String comments;
    private String model;
    private String specifications;
    private String specificationsname;
    private String attribute;
    private String business_price;
    private String sale_price = "";
    private String barcode;
    private String status;
    private String shoucang;
    private String classid;

    public String getGid() {
        return gid;
    }

    private String gid;//规格id

    public void setGid(String gid) {
        this.gid = gid;
    }

    private List<String> imgs;

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

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUp_time() {
        return up_time;
    }

    public void setUp_time(String up_time) {
        this.up_time = up_time;
    }

    public String getDown_time() {
        return down_time;
    }

    public void setDown_time(String down_time) {
        this.down_time = down_time;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public String getOrder_by() {
        return order_by;
    }

    public void setOrder_by(String order_by) {
        this.order_by = order_by;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public String getVipprice() {
        return vipprice;
    }

    public void setVipprice(String vipprice) {
        this.vipprice = vipprice;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getSpot() {
        return spot;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }

    public String getVgoods() {
        return vgoods;
    }

    public void setVgoods(String vgoods) {
        this.vgoods = vgoods;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getSpecificationsname() {
        return specificationsname;
    }

    public void setSpecificationsname(String specificationsname) {
        this.specificationsname = specificationsname;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShoucang() {
        return shoucang;
    }

    public void setShoucang(String shoucang) {
        this.shoucang = shoucang;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    @Override
    public String toString() {
        return "GoodsDetailsBean{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", addtime='" + addtime + '\'' +
                ", uptime='" + uptime + '\'' +
                ", goodsname='" + goodsname + '\'' +
                ", type='" + type + '\'' +
                ", price='" + price + '\'' +
                ", up_time='" + up_time + '\'' +
                ", down_time='" + down_time + '\'' +
                ", inventory='" + inventory + '\'' +
                ", is_del='" + is_del + '\'' +
                ", order_by='" + order_by + '\'' +
                ", cover='" + cover + '\'' +
                ", state='" + state + '\'' +
                ", userid='" + userid + '\'' +
                ", content='" + content + '\'' +
                ", hot='" + hot + '\'' +
                ", vipprice='" + vipprice + '\'' +
                ", sales='" + sales + '\'' +
                ", promotion='" + promotion + '\'' +
                ", spot='" + spot + '\'' +
                ", vgoods='" + vgoods + '\'' +
                ", comments='" + comments + '\'' +
                ", model='" + model + '\'' +
                ", specifications='" + specifications + '\'' +
                ", specificationsname='" + specificationsname + '\'' +
                ", attribute='" + attribute + '\'' +
                ", business_price='" + business_price + '\'' +
                ", sale_price='" + sale_price + '\'' +
                ", barcode='" + barcode + '\'' +
                ", status='" + status + '\'' +
                ", shoucang='" + shoucang + '\'' +
                ", classid='" + classid + '\'' +
                ", gid='" + gid + '\'' +
                ", imgs=" + imgs +
                '}';
    }
}
