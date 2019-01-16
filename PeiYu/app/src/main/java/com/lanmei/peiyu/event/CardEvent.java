package com.lanmei.peiyu.event;


import com.lanmei.peiyu.bean.WithdrawCardListBean;

/**
 * Created by xkai on 2018/10/18.
 *
 */

public class CardEvent {

    private String name;
    private int type;//1卡名
    private WithdrawCardListBean bean;

    public void setBean(WithdrawCardListBean bean) {
        this.bean = bean;
    }

    public WithdrawCardListBean getBean() {
        return bean;
    }

    public CardEvent(int type){
        this.type = type;
    }

    public void setName(String cardName) {
        this.name = cardName;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
