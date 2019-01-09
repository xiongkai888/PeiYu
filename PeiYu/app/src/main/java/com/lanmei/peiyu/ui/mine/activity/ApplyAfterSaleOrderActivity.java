package com.lanmei.peiyu.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.ApplyAfterSaleOrderBean;
import com.lanmei.peiyu.bean.DataEntryBean;
import com.lanmei.peiyu.bean.ProblemTypeBean;
import com.lanmei.peiyu.event.ApplyAfterSaleOrderEvent;
import com.lanmei.peiyu.helper.BGASortableNinePhotoHelper;
import com.lanmei.peiyu.ui.home.activity.DataEntryActivity;
import com.lanmei.peiyu.utils.CommonUtils;
import com.lanmei.peiyu.utils.CompressPhotoUtils;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.JsonUtil;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;

/**
 * 售后申请工单
 */
public class ApplyAfterSaleOrderActivity extends BaseActivity implements BGASortableNinePhotoLayout.Delegate {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @InjectView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout mPhotosSnpl;//拖拽排序九宫格控件
    BGASortableNinePhotoHelper mPhotoHelper;
    @InjectView(R.id.spinner)
    Spinner spinner;
    @InjectView(R.id.phone_et)
    EditText phoneEt;
    @InjectView(R.id.spinner1)
    Spinner spinner1;
    @InjectView(R.id.spinner2)
    Spinner spinner2;
    @InjectView(R.id.address_et)
    EditText addressEt;
    @InjectView(R.id.content_et)
    EditText contentEt;

    private List<DataEntryBean> list;//用户信息列表
    private DataEntryBean bean;//选择的用户信息
    private List<ApplyAfterSaleOrderBean> orderBeanList;
    private ApplyAfterSaleOrderBean orderBean;//选中的电站信息
    private List<ProblemTypeBean> typeBeanList;
    private ProblemTypeBean typeBean;//选中的问题类型
    private CompressPhotoUtils compressPhotoUtils2;//图片上传类（九宫格照片上传）
    private boolean isCompressPhotoUtils;//是否上传九宫格
    private List<String> list2;//上传到阿里云后的九宫格


    @Override
    public int getContentViewId() {
        return R.layout.activity_apply_after_sale_order;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.apply_after_sale_order);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        compressPhotoUtils2 = new CompressPhotoUtils(this);

        initPhotoHelper();
        loadProblemList();//问题列表
        loadPowerStationList();//电站列表
        loadDataList();//资料列表
    }

    //资料列表
    private void loadDataList() {
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
                    initDataListSpinner(null);
                    return;
                }
                List<String> stringList = new ArrayList<>();
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    String sName = list.get(i).getS_name();
                    if (!StringUtils.isEmpty(sName)){
                        stringList.add(sName);
                    }
                }
                initDataListSpinner(stringList);
            }
        });
    }


    /**
     * 电站名称列表
     */
    private void loadPowerStationList() {
        PeiYuApi api = new PeiYuApi("station/station");
        api.addParams("uid", api.getUserId(this));
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<NoPageListBean<ApplyAfterSaleOrderBean>>() {
            @Override
            public void onResponse(NoPageListBean<ApplyAfterSaleOrderBean> response) {
                if (isFinishing()) {
                    return;
                }
                orderBeanList = response.data;
                if (StringUtils.isEmpty(orderBeanList)) {
                    UIHelper.ToastMessage(getContext(), "暂无电站信息");
                    return;
                }
                List<String> stringList = new ArrayList<>();
                int size = orderBeanList.size();
                for (int i = 0; i < size; i++) {
                    String sName = orderBeanList.get(i).getS_name();
                    if (!StringUtils.isEmpty(sName)){
                        stringList.add(sName);
                    }
                }
                initPowerStationListSpinner(stringList);
            }
        });
    }
    private void loadProblemList() {
        PeiYuApi api = new PeiYuApi("station/problem_list");
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<NoPageListBean<ProblemTypeBean>>() {
            @Override
            public void onResponse(NoPageListBean<ProblemTypeBean> response) {
                if (isFinishing()) {
                    return;
                }
                typeBeanList = response.data;
                if (StringUtils.isEmpty(typeBeanList)) {
                    UIHelper.ToastMessage(getContext(), getString(R.string.no_problem));
                    return;
                }
                List<String> stringList = new ArrayList<>();
                int size = typeBeanList.size();
                for (int i = 0; i < size; i++) {
                    String sProblemName = typeBeanList.get(i).getProblemname();
                    if (!StringUtils.isEmpty(sProblemName)){
                        stringList.add(sProblemName);
                    }
                }
                initProblemTypeListSpinner(stringList);
            }
        });
    }


    private void initPowerStationListSpinner(List<String> s_list) {
        if (StringUtils.isEmpty(orderBeanList)) {
            s_list = new ArrayList<>();
            s_list.add("暂无电站信息");
            return;
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, s_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                name = (String) parent.getItemAtPosition(position);
                if (StringUtils.isEmpty(orderBeanList)) {
                    return;
                }
                orderBean = orderBeanList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && StringUtils.isEmpty(orderBeanList)) {
//                    loadDataList();
                    UIHelper.ToastMessage(getContext(), "暂无电站信息");
                    L.d("ApplyInstallActivity", "setOnTouchListener");
                }
                return false;
            }
        });
    }

    private void initProblemTypeListSpinner(List<String> s_list) {
        if (StringUtils.isEmpty(typeBeanList)) {
            s_list = new ArrayList<>();
            s_list.add(getString(R.string.no_problem));
            return;
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, s_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                name = (String) parent.getItemAtPosition(position);
                if (StringUtils.isEmpty(typeBeanList)) {
                    return;
                }
                typeBean = typeBeanList.get(position);
//                UIHelper.ToastMessage(getContext(),typeBean.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && StringUtils.isEmpty(orderBeanList)) {
//                    loadDataList();
                    UIHelper.ToastMessage(getContext(),getString(R.string.no_problem));
                    L.d("ApplyInstallActivity", "setOnTouchListener");
                }
                return false;
            }
        });
    }

    private void initDataListSpinner(List<String> s_list) {
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
                phoneEt.setText(bean.getS_phone());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && StringUtils.isEmpty(list)) {
                    loadDataList();
                    L.d("ApplyInstallActivity", "setOnTouchListener");
                }
                return false;
            }
        });
    }

    private void initPhotoHelper() {
        mPhotoHelper = new BGASortableNinePhotoHelper(this, mPhotosSnpl);
        // 设置拖拽排序控件的代理
        mPhotoHelper.setDelegate(this);
    }


    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        mPhotoHelper.onClickAddNinePhotoItem(sortableNinePhotoLayout, view, position, models);
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotoHelper.onClickDeleteNinePhotoItem(sortableNinePhotoLayout, view, position, model, models);
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotoHelper.onClickNinePhotoItem(sortableNinePhotoLayout, view, position, model, models);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPhotoHelper.onActivityResult(requestCode, resultCode, data);
    }


    @OnClick(R.id.submit_bt)
    public void onViewClicked() {
        submit();
    }

    private void submit() {
        if (StringUtils.isEmpty(bean)) {
            UIHelper.ToastMessage(this, getString(R.string.choose_user_info));
            return;
        }
        final String phone = CommonUtils.getStringByEditText(phoneEt);
        if (StringUtils.isEmpty(phone)) {
            UIHelper.ToastMessage(this, R.string.input_contact_number);
            return;
        }
        final String address = CommonUtils.getStringByEditText(addressEt);
        if (StringUtils.isEmpty(address)) {
            UIHelper.ToastMessage(this, R.string.input_installation_address);
            return;
        }
        if (StringUtils.isEmpty(orderBean)) {
            UIHelper.ToastMessage(this, getString(R.string.choose_power_info));
            return;
        }
        isCompressPhotoUtils = !StringUtils.isEmpty(mPhotoHelper.getData());
        if (isCompressPhotoUtils) {
            compressPhotoUtils2.compressPhoto(CommonUtils.toArray(mPhotoHelper.getData()), new CompressPhotoUtils.CompressCallBack() {//压缩图片（可多张）
                @Override
                public void success(List<String> list) {
                    if (isFinishing()) {
                        return;
                    }
                    list2 = list;
                    submitAfterOrder(phone, address);
                }
            }, CommonUtils.isTwo);
        } else {
            list2 = null;
            submitAfterOrder(phone, address);
        }
    }

    private void submitAfterOrder(String phone, String address) {
        PeiYuApi api = new PeiYuApi("station/saled_add");
        api.addParams("uid", api.getUserId(this));
        api.addParams("address", address);
        api.addParams("sid", orderBean.getS_id());
        api.addParams("sname", orderBean.getS_name());
        api.addParams("linkname", bean.getS_name());
        api.addParams("phone", phone);
        api.addParams("content", CommonUtils.getStringByEditText(contentEt));
//        api.addParams("means_id", bean.getId());
        api.addParams("state", 1);
        api.addParams("problem_id", typeBean.getId());
        if (!StringUtils.isEmpty(list2)) {
            api.addParams("pic", JsonUtil.getJSONArrayByList(list2));//图片
        }
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()) {
                    return;
                }
                UIHelper.ToastMessage(getContext(), response.getInfo());
                EventBus.getDefault().post(new ApplyAfterSaleOrderEvent());
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compressPhotoUtils2 != null) {
            compressPhotoUtils2.cancelled();
        }
    }

}
