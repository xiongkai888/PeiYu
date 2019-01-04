package com.lanmei.peiyu.ui.mine.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.DataEntryBean;
import com.lanmei.peiyu.event.ApplyInstallSucceedEvent;
import com.lanmei.peiyu.event.DataEntryEvent;
import com.lanmei.peiyu.ui.home.activity.DataEntryActivity;
import com.lanmei.peiyu.utils.CommonUtils;
import com.lanmei.peiyu.utils.FormatTime;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DateTimePicker;

/**
 * 申请安装
 */
public class ApplyInstallActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar toolbar;
    //    @InjectView(R.id.name_et)
//    EditText nameEt;
    @InjectView(R.id.phone_et)
    EditText phoneEt;
    @InjectView(R.id.time_tv)
    TextView timeTv;
    @InjectView(R.id.address_et)
    EditText addressEt;
    @InjectView(R.id.content_et)
    EditText contentEt;

    @InjectView(R.id.spinner)
    Spinner spinner;//用户信息

    private DateTimePicker picker;
    private FormatTime time;
    private long timeLong;
    private List<DataEntryBean> list;
    private DataEntryBean bean;//选择的用户信息


    @Override
    public int getContentViewId() {
        return R.layout.activity_apply_install;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.apply_install);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);
        initDatePicker();

        loadData();
    }

    //录入资料成功后调用
    @Subscribe
    public void dataEntryEvent(DataEntryEvent event) {
        loadData();
    }

    private void loadData() {
        PeiYuApi api = new PeiYuApi("station/station_list");
        api.addParams("uid", api.getUserId(this));
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<NoPageListBean<DataEntryBean>>() {
            @Override
            public void onResponse(NoPageListBean<DataEntryBean> response) {
                if (isFinishing()) {
                    return;
                }
                list = response.data;
                if (StringUtils.isEmpty(list)) {
                    UIHelper.ToastMessage(getContext(), "先录入资料");
                    IntentUtil.startActivity(getContext(), DataEntryActivity.class);
                    initSpinner(null);
                    return;
                }
                List<String> stringList = new ArrayList<>();
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    stringList.add(list.get(i).getS_name());
                }
                initSpinner(stringList);
            }
        });
    }

    private void initSpinner(List<String> s_list) {
        if (StringUtils.isEmpty(list)) {
            s_list = new ArrayList<>();
            s_list.add("先录入资料");
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, s_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                name = (String) parent.getItemAtPosition(position);
                if (StringUtils.isEmpty(list)) {
                    return;
                }
                bean = list.get(position);
                addressEt.setText(bean.getS_address());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && StringUtils.isEmpty(list)){
                    loadData();
                    L.d("ApplyInstallActivity", "setOnTouchListener");
                }
                return false;
            }
        });
    }

    private void initDatePicker() {
        picker = new DateTimePicker(this, DateTimePicker.HOUR_24);
        time = new FormatTime(this);
        time.setTime(System.currentTimeMillis() / 1000 + 60);
        int year = time.getYear();
        int month = time.getMonth();
        int day = time.getDay();
        int hour = time.getHour();
        int minute = time.getMinute();
        picker.setDateRangeStart(year, month, day);
        picker.setDateRangeEnd(year + 10, month, day);
        picker.setSelectedItem(year, month, day, hour, minute);
//        picker.setLabel("年","月","日",":","");
        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                try {
                    String timeStr = year + "-" + month + "-" + day + " " + hour + ":" + minute;
                    timeTv.setText(timeStr);
                    timeLong = time.dateToStampLong(timeStr);
                    L.d(L.TAG, "时间：" + time.formatterTime(timeLong + "") + ", " + timeLong);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @OnClick({R.id.time_tv, R.id.submit_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.time_tv:
                if (picker != null) {
                    picker.show();
                }
                break;
            case R.id.submit_bt:
                submit();
                break;
        }
    }

    private void submit() {
        if (StringUtils.isEmpty(bean)) {
            UIHelper.ToastMessage(this, "请选择用户基本信息");
            return;
        }
        String phone = CommonUtils.getStringByEditText(phoneEt);
        if (StringUtils.isEmpty(phone)) {
            UIHelper.ToastMessage(this, "输入联系电话");
            return;
        }
        if (timeLong == 0) {
            UIHelper.ToastMessage(this, "请选择安装时间");
            return;
        }
        String address = CommonUtils.getStringByEditText(addressEt);
        if (StringUtils.isEmpty(address)) {
            UIHelper.ToastMessage(this, "请输入安装地址");
            return;
        }
        PeiYuApi api = new PeiYuApi("station/install_add");
        api.addParams("uid", api.getUserId(this));
        api.addParams("means_id", bean.getId());
        api.addParams("address", address);
        api.addParams("linkname", bean.getS_name());
        api.addParams("phone", phone);
        api.addParams("content", CommonUtils.getStringByEditText(contentEt));
        api.addParams("installtime", timeLong);
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()) {
                    return;
                }
                UIHelper.ToastMessage(getContext(), response.getInfo());
                EventBus.getDefault().post(new ApplyInstallSucceedEvent());//通知列表刷新
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
