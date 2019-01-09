package com.lanmei.peiyu.ui.classify.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.AddressListBean;
import com.lanmei.peiyu.event.AddAddressEvent;
import com.lanmei.peiyu.event.AlterAddressEvent;
import com.lanmei.peiyu.helper.AddressAsyncTask;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.bean.UserBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;

/**
 * 添加收货地址
 */
public class AddAddressActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.name_et)
    EditText nameEt;
    @InjectView(R.id.phone_et)
    EditText phoneEt;
    @InjectView(R.id.area_tv)
    TextView areaTv;
    @InjectView(R.id.detail_address_et)
    EditText detailAddressEt;
    @InjectView(R.id.is_default_checkbox)
    CheckBox isDefaultCheckbox;
    private String isDefault = CommonUtils.isZero;
    private AddressListBean bean;//地址信息
    private boolean isAdd;
    private AddressAsyncTask addressAsyncTask;
    private AddressPicker addressPicker;

    @Override
    public int getContentViewId() {
        return R.layout.activity_add_address;
    }

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        Bundle bundle = intent.getBundleExtra("bundle");
        if (!StringUtils.isEmpty(bundle)) {
            bean = (AddressListBean) bundle.getSerializable("bean");
        }
        isAdd = (bean == null);
    }

    private void initAddressPicker(ArrayList<Province> data) {
        String provinceS = "";
        String cityS = "";
        String countyS = "";
        if (!isAdd) {
            for (Province province : data) {
                if (StringUtils.isSame(province.getAreaId(), provinceId)) {
                    provinceS = province.getAreaName();
                    List<City> cityList = province.getCities();
                    for (City city : cityList) {
                        if (StringUtils.isSame(city.getAreaId(), cityId)) {
                            cityS = city.getAreaName();
                            List<County> countyList = city.getCounties();
                            for (County county : countyList) {
                                if (StringUtils.isSame(county.getAreaId(), areaId)) {
                                    countyS = county.getAreaName();
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    break;
                }
            }
            areaTv.setText(provinceS + "  " + cityS + "  " + countyS);

        }
        addressPicker = new AddressPicker(this, data);
        if (!StringUtils.isEmpty(provinceS) && !StringUtils.isEmpty(cityS) && !StringUtils.isEmpty(countyS)) {
            addressPicker.setSelectedItem(provinceS, cityS, countyS);
            if (!isAdd) {
                String address = bean.getAddress();
                if (!StringUtils.isEmpty(address)) {
                    address = address.replace(provinceS + cityS + countyS, "");
                    detailAddressEt.setText(address);
                }
            }
        } else {
            addressPicker.setSelectedItem(getString(R.string.province), getString(R.string.city), getString(R.string.county));
        }
        addressPicker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
            @Override
            public void onAddressPicked(Province province, City city, County county) {
                areaTv.setText(province.getAreaName() + "  " + city.getAreaName() + "  " + county.getAreaName());
                provinceId = province.getAreaId();
                cityId = city.getAreaId();
                areaId = county.getAreaId();
//                L.d("AddressPicker", province.getAreaId() + "," + city.getAreaId() + "," + county.getAreaId());
            }
        });
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        if (isAdd) {
            actionbar.setTitle("添加收货地址");
        } else {
            actionbar.setTitle(R.string.compile_address);
            isDefaultCheckbox.setChecked(StringUtils.isSame(CommonUtils.isOne, bean.getDefaultX()));
            isDefault = bean.getDefaultX();
            provinceId = bean.getProvince();
            cityId = bean.getCity();
            areaId = bean.getArea();

        }
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        isDefaultCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isDefault = isChecked ? CommonUtils.isOne : CommonUtils.isZero;
            }
        });
        UserBean bean = CommonUtils.getUserBean(this);
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        nameEt.setText(bean.getNickname());
        phoneEt.setText(bean.getPhone());


        addressAsyncTask = new AddressAsyncTask();//异步获取省市区列表
        addressAsyncTask.setAddressAsyncTaskListener(new AddressAsyncTask.AddressAsyncTaskListener() {
            @Override
            public void setAddressList(ArrayList<Province> result) {
                initAddressPicker(result);
            }
        });
        addressAsyncTask.execute();
    }

    String provinceId;//省ID
    String cityId;//市ID
    String areaId;//


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            loadAddAddress();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadAddAddress() {
        String name = CommonUtils.getStringByEditText(nameEt);
        if (StringUtils.isEmpty(name)) {
            UIHelper.ToastMessage(this, R.string.input_reception_name);
            return;
        }
        String phone = CommonUtils.getStringByEditText(phoneEt);//
        if (StringUtils.isEmpty(phone)) {
            UIHelper.ToastMessage(this, R.string.input_phone_number);
            return;
        }
        if (!StringUtils.isMobile(phone)) {
            UIHelper.ToastMessage(this, R.string.not_mobile_format);
            return;
        }
        String area = CommonUtils.getStringByTextView(areaTv);//
        if (StringUtils.isEmpty(area)) {
            UIHelper.ToastMessage(this, getString(R.string.choose_area));
            return;
        }
        String detailAddress = CommonUtils.getStringByEditText(detailAddressEt);//
        if (StringUtils.isEmpty(detailAddress)) {
            UIHelper.ToastMessage(this, R.string.input_details_address);
            return;
        }
        httpAddress(name, phone, detailAddress);
    }

    private void httpAddress(String name, String phone, String detailAddress) {
        PeiYuApi api = new PeiYuApi("app/address");
        api.addParams("uid", api.getUserId(this));
        api.addParams("operation", isAdd ? 1 : 2);//1|2|3|4=>添加|修改|删除|列表
        api.addParams("id", isAdd ? "" : bean.getId());

        api.addParams("accept_name", name);
        api.addParams("mobile", phone);
        api.addParams("address", detailAddress);
        api.addParams("province", provinceId);
        api.addParams("city", cityId);
        api.addParams("area", areaId);
        api.addParams("default", isDefault);
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()) {
                    return;
                }
                EventBus.getDefault().post(new AddAddressEvent());
                if (!isAdd) {
                    EventBus.getDefault().post(new AlterAddressEvent());//通知确认订单的地址，地址有变化
                }
                finish();
            }
        });
    }


    @OnClick(R.id.ll_area)
    public void onViewClicked() {//所在区域
        if (addressPicker != null) {
            addressPicker.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (addressAsyncTask != null && addressAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            addressAsyncTask.cancel(true);
            addressAsyncTask = null;
        }
        addressPicker = null;
    }
}
