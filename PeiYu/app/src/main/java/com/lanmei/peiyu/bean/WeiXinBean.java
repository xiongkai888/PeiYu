package com.lanmei.peiyu.bean;

/**
 * Created by xkai on 2017/7/6.
 * 微信充值请求返回的数据
 */

public class WeiXinBean {

    /**
     * return_code : SUCCESS
     * return_msg : OK
     * appid : wxf8b150eeb8daca98
     * mch_id : 1501206401
     * nonce_str : ylg5bnccj2np2mkd9gi47udtt13ilt4l
     * sign : 45B38B78EF6645C8A2B7820E3E1AEE4A
     * result_code : SUCCESS
     * prepay_id : wx2018040316283151b16f528b0986662010
     * trade_type : APP
     * timestamp : 1522744111
     */

    private String return_code;
    private String return_msg;
    private String appid;
    private String mch_id;
    private String nonce_str;
    private String sign;
    private String result_code;
    private String prepay_id;
    private String trade_type;
    private String partnerId;

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    private int timestamp;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
