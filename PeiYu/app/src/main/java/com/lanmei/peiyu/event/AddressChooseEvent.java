package com.lanmei.peiyu.event;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;

/**
 * Created by xkai on 2019/1/11.
 * 百度地图地址选择
 */

public class AddressChooseEvent {

    private PoiInfo info;
    private LatLng center;

    public LatLng getCenter() {
        return center;
    }

    public PoiInfo getInfo() {
        return info;
    }

    public AddressChooseEvent(PoiInfo info,LatLng center){
        this.info = info;
        this.center = center;
    }
}
