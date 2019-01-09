package com.lanmei.peiyu.ui.home.activity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.lanmei.peiyu.R;
import com.lanmei.peiyu.utils.BaiduLocation;
import com.xson.common.app.BaseActivity;
import com.xson.common.utils.L;
import com.xson.common.widget.CenterTitleToolbar;

import butterknife.InjectView;

public class MapActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @InjectView(R.id.mapView)
    MapView mapView;
    private BaiduMap mBaiduMap;
    private BitmapDescriptor bitmap;

    @Override
    public int getContentViewId() {
        return R.layout.activity_map;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("选择地址");
        actionbar.setHomeAsUpIndicator(R.mipmap.back);
        initPermission();

        mBaiduMap = mapView.getMap();
        //设置是否显示比例尺控件
        mapView.showScaleControl(false);
        //设置是否显示缩放控件
        mapView.showZoomControls(false);
        // 删除百度地图LoGo
        mapView.removeViewAt(1);


        // 显示个人位置图标
//        MyLocationData.Builder builder = new MyLocationData.Builder();
//        builder.latitude(location.getLatitude());
//        builder.longitude(location.getLongitude());
//        MyLocationData data = builder.build();
//        baiduMap.setMyLocationData(data);

        // 设置marker图标
        bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.icon_coordinate);

        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //获取经纬度
                double latitude = latLng.latitude;
                double longitude = latLng.longitude;
                L.d(L.TAG,"latitude=" + latitude + ",longitude=" + longitude);
                //先清除图层
                mBaiduMap.clear();
                // 定义Maker坐标点
                LatLng point = new LatLng(latitude, longitude);
                // 构建MarkerOption，用于在地图上添加Marker
                MarkerOptions options = new MarkerOptions().position(point)
                        .icon(bitmap);
                // 在地图上添加Marker，并显示
                mBaiduMap.addOverlay(options);
                //实例化一个地理编码查询对象
                GeoCoder geoCoder = GeoCoder.newInstance();
                //设置反地理编码位置坐标
                ReverseGeoCodeOption op = new ReverseGeoCodeOption();
                op.location(latLng);
                //发起反地理编码请求(经纬度->地址信息)
                geoCoder.reverseGeoCode(op);
                geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

                    @Override
                    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
                        //获取点击的坐标地址
                        L.d(L.TAG,"address=" + arg0.getAddress());
                    }

                    @Override
                    public void onGetGeoCodeResult(GeoCodeResult arg0) {
                    }
                });
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });

        new BaiduLocation(this, new BaiduLocation.WHbdLocationListener() {
            @Override
            public void bdLocationListener(LocationClient locationClient, BDLocation location) {
                if (isFinishing()) {
                    return;
                }
                MyLocationConfiguration configuration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,true,bitmap);
//                mBaiduMap.setMyLocationConfiguration(configuration);
                mBaiduMap.setMyLocationEnabled(true); // 设置可改变地图位置
                // 构造定位数据
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(0).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                // 设置定位数据
                mBaiduMap.setMyLocationData(locData);
            }
        });

    }

    private boolean initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if (mapView != null)
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        if (mapView != null)
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        if (mapView != null)
        mapView.onPause();
    }
}
