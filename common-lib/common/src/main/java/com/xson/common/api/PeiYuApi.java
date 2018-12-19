package com.xson.common.api;

/**
 * Created by xkai on 2018/12/19.
 */

public class PeiYuApi extends AbstractApi {

    private String path;

    public void setPath(String path) {
        this.path = path;
    }

    public PeiYuApi(){
    }

    public PeiYuApi(String path){
        this.path = path;
    }

    @Override
    protected String getPath() {
        return path;
    }
}
