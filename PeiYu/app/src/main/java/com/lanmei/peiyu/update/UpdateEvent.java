package com.lanmei.peiyu.update;

/**
 * Created by xkai on 2018/5/31.
 * 版本更新事件
 */

public class UpdateEvent {

    private String content;

    public UpdateEvent(String content){
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
