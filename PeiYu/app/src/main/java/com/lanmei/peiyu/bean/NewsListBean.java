package com.lanmei.peiyu.bean;

import java.util.List;

/**
 * Created by xkai on 2019/1/3.
 * 资讯列表
 */

public class NewsListBean {

    /**
     * id : 290
     * title : 赵丽颖《你和我的倾城时光》，成就最美的你！
     * file : ["http://qkmimages.img-cn-shenzhen.aliyuncs.com/181207/5c09ed6208945.jpg","http://qkmimages.img-cn-shenzhen.aliyuncs.com/181207/5c09ed7057971.jpg","http://qkmimages.img-cn-shenzhen.aliyuncs.com/181207/5c09ed8d14c13.jpg"]
     * addtime : 1543994842
     * reviews : 0
     * name : 最新资讯
     */

    private String id;
    private String title;
    private String addtime;
    private String reviews;
    private String name;
    private List<String> file;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFile() {
        return file;
    }

    public void setFile(List<String> file) {
        this.file = file;
    }
}
