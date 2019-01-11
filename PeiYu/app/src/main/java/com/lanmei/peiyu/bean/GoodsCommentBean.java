package com.lanmei.peiyu.bean;

import java.util.List;

/**
 * Created by xkai on 2018/1/18.
 * 商品详情评论
 */

public class GoodsCommentBean {
    /**
     * id : 58
     * goods_id : 49
     * order_no : 20181213B0100T1840
     * user_id : 196
     * time : 1544695428
     * comment_time : 1544695428
     * contents : null
     * comment_pic : null
     * point : 0
     * status : 0
     */

    private String id;
    private String goods_id;
    private String order_no;
    private String user_id;
    private String time;
    private String comment_time;
    private String contents;
    private List<String> comment_pic;
    private String point;
    private String status;
    /**
     * username : 托马斯猫
     * user_pic : http://qkmimages.img-cn-shenzhen.aliyuncs.com/181113/5bea445aac15c.jpg
     */

    private String username;
    private String user_pic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public List<String> getComment_pic() {
        return comment_pic;
    }

    public void setComment_pic(List<String> comment_pic) {
        this.comment_pic = comment_pic;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }
}
