package com.lanmei.peiyu.ui.home.activity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.PoiResultAdapter;
import com.lanmei.peiyu.event.AddressChooseEvent;
import com.lanmei.peiyu.utils.BaiduLocation;
import com.xson.common.app.BaseActivity;
import com.xson.common.utils.L;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class MapActivity extends BaseActivity implements OnGetPoiSearchResultListener, OnGetSuggestionResultListener, OnGetGeoCoderResultListener {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @InjectView(R.id.mapview)
    MapView mMapview;
    @InjectView(R.id.searchkey)
    AutoCompleteTextView keyWorldsView;
    @InjectView(R.id.img_search)
    ImageView mImgSearch;//搜索
    @InjectView(R.id.img_location_center)
    ImageView mImgLocationCenter;//地图中心位置
    @InjectView(R.id.img_location)
    ImageView mImgLocation;//当前位置
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerView;
    @InjectView(R.id.lng_tv)
    TextView lngTv;//显示经纬度


    private String TAG = "MapActivityName";

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
        initUI();
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

    private BaiduMap mBaiduMap;
    private ArrayAdapter<String> sugAdapter = null;
    private PoiResultAdapter resultListAdapter;

    private GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
    private PoiSearch mPoiSearch = null;
    private SuggestionSearch mSuggestionSearch = null;
    private List<String> suggest;
    private List<SuggestionResult.SuggestionInfo> suggestResult;

    private String keyCity;
    private int loadIndex = 0;
    private int radius = 100;
    private LatLng center;

    private boolean isReasonGesture = false;//是否手动滑动地图
    private boolean isSearch = true;
    private int curSelect = -1;


    public void initUI() {
        resultListAdapter = new PoiResultAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultListAdapter.setOnChangeListener(new PoiResultAdapter.OnChangeListener() {
            @Override
            public void onChangeListener(int position, PoiInfo item) {
                isSearch = false;
                keyWorldsView.setText(item.name + "");
                curSelect = position;
                moveLocation(item.location);
                isSearch = true;
            }
        });
        recyclerView.setAdapter(resultListAdapter);

        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);

        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);

        // 初始化建议搜索模块，注册建议搜索事件监听
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);

        mBaiduMap = mMapview.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
                L.d(TAG, "onMapStatusChangeStart:" + mapStatus.toString());
                isReasonGesture = false;
            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {
                isReasonGesture = i == BaiduMap.OnMapStatusChangeListener.REASON_GESTURE;
                L.d(TAG, "onMapStatusChangeStart:" + i + ":" + mapStatus.toString());
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                reverseGeoCode(mapStatus.target);
            }
        });
        locationCur();
        mImgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationCur();
            }
        });


        sugAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line);
        keyWorldsView.setAdapter(sugAdapter);
        keyWorldsView.setThreshold(1);
        keyWorldsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SuggestionResult.SuggestionInfo item = suggestResult.get(position);
                center = item.pt;
                lngTv.setText(String.format(getString(R.string.longitude_and_latitude),center.longitude+"",center.latitude+""));
//                StaticMethod.hideSoft(ActivityAddrMap.this,keyWorldsView);
                L.d(TAG, "key:" + item.key + "---district:" + item.district + ":address:" + item.address + ":tag:" + item.tag);
                nearbySearch(item.key);
            }
        });
        mImgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nearbySearch(keyWorldsView.getText().toString());
            }
        });

        /**
         * 当输入关键字变化时，动态更新建议列表
         */
        keyWorldsView.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                isSearch = true;
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                if (cs.length() <= 0 || isReasonGesture || !isSearch) {
                    return;
                }

                /**
                 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                 */
                isSearch = true;
                mSuggestionSearch
                        .requestSuggestion((new SuggestionSearchOption())
                                .keyword(cs.toString()).city(keyCity + ""));
            }
        });
//        refreshArea();
    }


    @Override
    protected void onDestroy() {
        mPoiSearch.destroy();
        mSuggestionSearch.destroy();
        mMapview.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mMapview.onPause();
        super.onPause();
    }


    @Override
    protected void onResume() {
        mMapview.onResume();
        super.onResume();
    }


    /**
     * 发起搜索
     */
    public void reverseGeoCode(LatLng code) {
        int version = 0;
        center = code;
        lngTv.setText(String.format(getString(R.string.longitude_and_latitude),center.longitude+"",center.latitude+""));
        // 反Geo搜索
        version = 1;
        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                .location(code).newVersion(version));
    }

    //获取当前位置
    private void locationCur() {

        new BaiduLocation(this, new BaiduLocation.WHbdLocationListener() {
            @Override
            public void bdLocationListener(LocationClient mLocationClient, BDLocation location) {
                if (location != null) {

                    keyCity = location.getCity();
                    // 构造定位数据
                    MyLocationData locData = new MyLocationData.Builder()
                            .accuracy(location.getRadius())//获取默认误差半径
                            .accuracy(10)//自定义误差半径
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                            .direction(location.getDirection())
                            .latitude(location.getLatitude())
                            .longitude(location.getLongitude()).build();

                    // 设置定位数据
//                    mBaiduMap.setMyLocationData(locData);
                    MyLocationConfiguration.LocationMode mCurrentMode = null;
                    mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;//默认为 LocationMode.NORMAL 普通态
//                    mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;   //定位跟随态
//                    mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;  //定位罗盘态
                    // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
                    BitmapDescriptor mCurrentMarker = null;
                    mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.location);
                    MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
                    mBaiduMap.setMyLocationConfiguration(config);

                    String key = location.getAddress().street;
                    center = new LatLng(location.getLatitude(), location.getLongitude());
                    lngTv.setText(String.format(getString(R.string.longitude_and_latitude),center.longitude+"",center.latitude+""));
                    if (!TextUtils.isEmpty(key)) {
                        isSearch = false;
                        keyWorldsView.setText(key);
                        isSearch = true;
                        nearbySearch(key);
                    }
                    moveLocation(center);
                    // 当不需要定位图层时关闭定位图层
                    mBaiduMap.setMyLocationEnabled(false);
                    mLocationClient.stop();
                }
            }
        });


    }

    private void moveLocation(LatLng ll) {
//        LatLng ll = new LatLng(location.getLatitude(),
//                location.getLongitude());
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(14.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    /**
     * 响应周边搜索按钮点击事件
     */
    private void nearbySearch(String key) {
        if (TextUtils.isEmpty(key))
            return;
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption()
                .keyword(key)
                .sortType(PoiSortType.distance_from_near_to_far)
                .location(center)
                .radius(radius).pageNum(loadIndex);
        mPoiSearch.searchNearby(nearbySearchOption);
    }

    public void goToNextPage(View v) {
        loadIndex++;
        nearbySearch(keyWorldsView.getText().toString());
    }

    /**
     * 获取POI搜索结果，包括searchInCity，searchNearby，searchInBound返回的搜索结果
     *
     * @param poiResult
     */
    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            resultListAdapter.clear();
            resultListAdapter.notifyDataSetChanged();
            return;
        }
        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
//            mBaiduMap.clear();
            resultListAdapter.setData(poiResult.getAllPoi());
            resultListAdapter.notifyDataSetChanged();
            return;
        }
//        if (poiResult.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
//            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
//            String strInfo = "在";
//            for (CityInfo cityInfo : poiResult.getSuggestCityList()) {
//                strInfo += cityInfo.city;
//                strInfo += ",";
//            }
//            strInfo += "找到结果";
//            UIHelper.ToastMessage(this, strInfo);
//        }
    }


    /**
     * 获取POI详情搜索结果，得到searchPoiDetail返回的搜索结果
     *
     * @param poiDetailResult
     */
    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
            UIHelper.ToastMessage(this, "抱歉，未找到结果3");
        } else {
//            UIHelper.ToastMessage(this, poiDetailResult.getName() + ": " + poiDetailResult.getAddress());
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
        if (poiDetailSearchResult.error != SearchResult.ERRORNO.NO_ERROR) {
            UIHelper.ToastMessage(this, "抱歉，未找到结果5");
        }
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirm, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_confirm:
                if (center != null) {
                    PoiInfo data = null;
                    if (curSelect >= 0 && resultListAdapter != null && resultListAdapter.getCount() > curSelect) {
                        data = resultListAdapter.getData().get(curSelect);
                        center = data.getLocation();
                    }
                    EventBus.getDefault().post(new AddressChooseEvent(data, center));
                    finish();
                } else {
                    UIHelper.ToastMessage(this, "请选择位置");
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 获取在线建议搜索结果，得到requestSuggestion返回的搜索结果
     *
     * @param suggestionResult
     */
    @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {
        if (suggestionResult == null || suggestionResult.getAllSuggestions() == null) {
            suggestResult = null;
            return;
        }
        suggest = new ArrayList<>();
        suggestResult = suggestionResult.getAllSuggestions();
        for (SuggestionResult.SuggestionInfo info : suggestionResult.getAllSuggestions()) {
            if (info.key != null) {
                suggest.add(info.key);
            }
        }
        sugAdapter = new ArrayAdapter<>(this, R.layout.item_text, suggest);
        keyWorldsView.setAdapter(sugAdapter);
        sugAdapter.notifyDataSetChanged();
//        UIHelper.ToastMessage(this, "onGetSuggestionResult");
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            UIHelper.ToastMessage(this, "抱歉，未找到结果1");
            return;
        }
//        mBaiduMap.clear();
//        mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
//                .icon(BitmapDescriptorFactory
//                        .fromResource(R.drawable.icon_marka)));
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
                .getLocation()));
//        String strInfo = String.format("纬度：%f 经度：%f",
//                result.getLocation().latitude, result.getLocation().longitude);
//        UIHelper.ToastMessage(this, strInfo);

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//            UIHelper.ToastMessage(this, "抱歉，未找到结果2");
            return;
        }
//        mBaiduMap.clear();
//        mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
//                .icon(BitmapDescriptorFactory
//                        .fromResource(R.drawable.icon_marka)));
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
                .getLocation()));

        if (isReasonGesture) {
            keyWorldsView.setText(result.getAddress());
            nearbySearch(result.getAddress());
        }
//        UIHelper.ToastMessage(this, result.getAddress());
    }

}
