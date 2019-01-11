package com.lanmei.peiyu.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xkai on 2018/10/24.
 * 商品订单列表
 */

public class MineOrderListBean implements Serializable{


    /**
     * id : 977
     * addtime : 1547024506
     * uptime : 1547024506
     * pay_no : 20190109B0100T2974
     * state : 0
     * order_no : 20190109B0100T6104
     * gname : 
     * num : 1
     * total_price : 98.00
     * addressid : 0
     * address : 广东省云浮市云城区美图秀哈哈
     * uid : 202
     * phone : 15914369252
     * username : 大熊
     * pay_type : 1
     * is_del : 0
     * endtime : 1547024506
     * pay_status : 0
     * courier : 
     * dis_type : 2
     * sellerid : 0
     * attribute : null
     * pay_time : null
     * yj_status : 0
     * c_type : 0
     * station_num : 0
     * total_station_num : 0
     * dis_name : 顺丰快递
     * dis_price : 10.00
     * money : null
     * lname : null
     * goods : [{"goodsid":"47","price":98,"goodsname":"天美纪寡肽修护面膜","num":"1","danwei":"","cover":"http://qkmimages.img-cn-shenzhen.aliyuncs.com/Uploads/imgs/20181116/15423527616887.jpg","gid":"0","status":null,"specifications":"","specificationsname":""}]
     */

    private String id;
    private String addtime;
    private String uptime;
    private String pay_no;
    private String state;
    private String order_no;
    private String gname;
    private int num;
    private String total_price;
    private String addressid;
    private String address;
    private String uid;
    private String phone;
    private String username;
    private String pay_type;
    private String is_del;
    private String endtime;
    private String pay_status;
    private String courier;
    private String dis_type;
    private String sellerid;
    private String attribute;
    private String pay_time;
    private String yj_status;
    private String c_type;
    private String station_num;
    private String total_station_num;
    private String dis_name;
    private String dis_price;
    private String money;
    private String lname;
    private List<GoodsBean> goods;

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

    public String getPay_no() {
        return pay_no;
    }

    public void setPay_no(String pay_no) {
        this.pay_no = pay_no;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public String getDis_type() {
        return dis_type;
    }

    public void setDis_type(String dis_type) {
        this.dis_type = dis_type;
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getYj_status() {
        return yj_status;
    }

    public void setYj_status(String yj_status) {
        this.yj_status = yj_status;
    }

    public String getC_type() {
        return c_type;
    }

    public void setC_type(String c_type) {
        this.c_type = c_type;
    }

    public String getStation_num() {
        return station_num;
    }

    public void setStation_num(String station_num) {
        this.station_num = station_num;
    }

    public String getTotal_station_num() {
        return total_station_num;
    }

    public void setTotal_station_num(String total_station_num) {
        this.total_station_num = total_station_num;
    }

    public String getDis_name() {
        return dis_name;
    }

    public void setDis_name(String dis_name) {
        this.dis_name = dis_name;
    }

    public String getDis_price() {
        return dis_price;
    }

    public void setDis_price(String dis_price) {
        this.dis_price = dis_price;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class GoodsBean implements Serializable{
        /**
         * goodsid : 47
         * price : 98
         * goodsname : 天美纪寡肽修护面膜
         * num : 1
         * danwei : 
         * cover : http://qkmimages.img-cn-shenzhen.aliyuncs.com/Uploads/imgs/20181116/15423527616887.jpg
         * gid : 0
         * status : null
         * specifications : 
         * specificationsname : 
         */

        private String goodsid;
        private double price;
        private String goodsname;
        private String num;
        private String danwei;
        private String cover;
        private String gid;
        private String status;
        private String specifications;
        private String specificationsname;
        private String order_no;

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getOrder_no() {
            return order_no;
        }

        public String getGoodsid() {
            return goodsid;
        }

        public void setGoodsid(String goodsid) {
            this.goodsid = goodsid;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getGoodsname() {
            return goodsname;
        }

        public void setGoodsname(String goodsname) {
            this.goodsname = goodsname;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getDanwei() {
            return danwei;
        }

        public void setDanwei(String danwei) {
            this.danwei = danwei;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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
    }
}
