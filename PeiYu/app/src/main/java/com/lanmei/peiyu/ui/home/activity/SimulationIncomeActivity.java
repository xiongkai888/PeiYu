package com.lanmei.peiyu.ui.home.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.DropDownFiltrateAdapter;
import com.lanmei.peiyu.bean.ElectricityTypeBean;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.DataBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.DoubleUtil;
import com.xson.common.utils.L;
import com.xson.common.utils.SimpleTextWatcher;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.SysUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 模拟收入
 */
public class SimulationIncomeActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @InjectView(R.id.covered_area_tv)
    EditText coveredAreaTv;
    @InjectView(R.id.price1_et)
    EditText price1Et;
    @InjectView(R.id.price2_et)
    EditText price2Et;
    @InjectView(R.id.price3_et)
    EditText price3Et;
    @InjectView(R.id.other_price_et)
    EditText otherPriceEt;
    @InjectView(R.id.time_tv)
    TextView timeTv;
    @InjectView(R.id.result_tv)
    TextView resultTv;//计算结果
    private DropDownFiltrateAdapter adapter;
    private ElectricityTypeBean electricityTypeBean;
//    private String coveredArea;//建筑面积
    private String electricity1;
    private String electricity2;
    private String electricity3;
    private String electricity4;//发电时间


    @Override
    public int getContentViewId() {
        return R.layout.activity_simulation_income;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {//ElectricityTypeBean
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.simulation_income);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);
        loadElectricity();
        initEditText();
    }

    private void initEditText(){
        price1Et.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                electricity1 = s+"";
            }
        });
        price2Et.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                electricity2 = s+"";
            }
        });
        price3Et.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                electricity3 = s+"";
            }
        });
//        coveredAreaTv.addTextChangedListener(new SimpleTextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                coveredArea = s+"";
//            }
//        });
    }

    //电价
    private void loadElectricity() {
        PeiYuApi api = new PeiYuApi("station/electricity_price");
        HttpClient.newInstance(this).request(api, new BeanRequest.SuccessListener<DataBean<ElectricityTypeBean>>() {
            @Override
            public void onResponse(DataBean<ElectricityTypeBean> response) {
                if (isFinishing()) {
                    return;
                }
                electricityTypeBean = response.data;
            }
        });
    }

    @OnClick({R.id.price1_et, R.id.price2_et, R.id.price3_et, R.id.time_tv, R.id.submit_bt, R.id.icon_down1, R.id.icon_down2, R.id.icon_down3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.price1_et://电费价格
                if (electricityTypeBean == null || price1Et.isFocusable()) {
                    return;
                }
                popupWindow(1, price1Et, electricityTypeBean.getType1());
                break;
            case R.id.price2_et://国家补贴电费价格
                if (electricityTypeBean == null || price2Et.isFocusable()) {
                    return;
                }
                popupWindow(2, price2Et, electricityTypeBean.getType2());
                break;
            case R.id.price3_et://省市级电费补贴
                if (electricityTypeBean == null || price3Et.isFocusable()) {
                    return;
                }
                popupWindow(3, price3Et, electricityTypeBean.getType3());
                break;
            case R.id.time_tv://
                if (electricityTypeBean == null) {
                    return;
                }
                popupWindow(4, timeTv, electricityTypeBean.getType4());
                break;
            case R.id.submit_bt://
                submit();
                break;
            case R.id.icon_down1://
                if (electricityTypeBean == null) {
                    return;
                }
                popupWindow(1, price1Et, electricityTypeBean.getType1());
                break;
            case R.id.icon_down2://
                if (electricityTypeBean == null) {
                    return;
                }
                popupWindow(2, price2Et, electricityTypeBean.getType2());
                break;
            case R.id.icon_down3://
                if (electricityTypeBean == null) {
                    return;
                }
                popupWindow(3, price3Et, electricityTypeBean.getType3());
                break;
        }
    }

    private void submit() {//
        String coveredArea = CommonUtils.getStringByEditText(coveredAreaTv);
        if (StringUtils.isEmpty(coveredArea)) {
            UIHelper.ToastMessage(this, R.string.input_covered_area);
            return;
        }
        //电费价格
        if (StringUtils.isEmpty(electricity1)) {
            UIHelper.ToastMessage(this, "请选择或输入电费价格");
            return;
        }

        //国家补贴电费价格
        if (StringUtils.isEmpty(electricity2)) {
//            UIHelper.ToastMessage(this, "请选择或输入国家补贴电费价格");
//            return;
            electricity2 = CommonUtils.isZero;
        }

        //省市级补贴电费价格
        if (StringUtils.isEmpty(electricity3)) {
//            UIHelper.ToastMessage(this, "请选择或输入省市级补贴电费价格");
//            return;
            electricity3 = CommonUtils.isZero;
        }
        //发电时间
        if (StringUtils.isEmpty(electricity4)) {
            UIHelper.ToastMessage(this, "请选择发电时间");
            return;
        }

        calculate(coveredArea);
    }

    private void calculate(String coveredArea){
        double v1 = Double.valueOf(electricity1);
        double v2 = Double.valueOf(electricity2);
        double v3 = Double.valueOf(electricity3);
        double v4 = Double.valueOf(electricity4);

        double rong = DoubleUtil.divide(Double.valueOf(coveredArea), 7.0, 2);//容量
        double n = DoubleUtil.round(rong * v4,2);//年发电量
        double cheng = DoubleUtil.round(DoubleUtil.mul(rong, 5.5),2);//投资成本
        String other = CommonUtils.getStringByEditText(otherPriceEt);
        if (StringUtils.isEmpty(other)) {
            other = CommonUtils.isZero;
        }
        L.d(L.TAG,"v1 = "+v1+",v2 = "+v2+",v3 = "+v3+",other = "+other+",n = "+n+",v4 = "+v4);
        double shou = DoubleUtil.round(((v1 + v2 + v3 + Double.valueOf(other)) * n) * 20,2);//20年收益

        double li = shou - (cheng*10000);

        String result = "容量：" + DoubleUtil.formatDoubleNumber(rong) + "kw\n年发电量：" + DoubleUtil.formatDoubleNumber(n) + "kwh\n投资成本：" + DoubleUtil.formatDoubleNumber(cheng) + "万元\n20年收益：" + DoubleUtil.formatDoubleNumber(shou) + "元\n利润：" + DoubleUtil.formatDoubleNumber(li) + "元";

        resultTv.setText(result);
    }

    private void popupWindow(final int type, final View v, List<ElectricityTypeBean.TypeBean> list) {
        RecyclerView view = new RecyclerView(this);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setBackgroundColor(getResources().getColor(R.color.white));
        adapter = new DropDownFiltrateAdapter(this);
        adapter.setData(list);
        view.setAdapter(adapter);
        final PopupWindow window = new PopupWindow(view, SysUtils.getScreenWidth(this), ViewGroup.LayoutParams.WRAP_CONTENT);
        adapter.setVoterFiltrateListener(new DropDownFiltrateAdapter.DropDownFiltrateListener() {
            @Override
            public void onFiltrate(ElectricityTypeBean.TypeBean bean) {
                window.dismiss();
                if (StringUtils.isSame(bean.getProblemname(), "其他")) {
                    if (type == 1) {
                        electricity1 = "";
                        price1Et.setText("");
                        price1Et.setHint(R.string.input_electricity_price);
                        focusable(price1Et, true);
                    } else if (type == 2) {
                        electricity2 = "";
                        price2Et.setText("");
                        price2Et.setHint(R.string.input_electricity_price);
                        focusable(price2Et, true);
                    } else if (type == 3) {
                        electricity3 = "";
                        price3Et.setText("");
                        price3Et.setHint(R.string.input_electricity_price);
                        focusable(price3Et, true);
                    }
                } else {
                    String name = bean.getProblemname();
                    if (StringUtils.isSame(bean.getProblemname(),"无")){
                        name = CommonUtils.isZero;
                    }
                    if (type == 1) {
                        price1Et.setText(name);
                        electricity1 = bean.getSetval();
                        focusable(price1Et, false);
                    } else if (type == 2) {
                        price2Et.setText(name);
                        electricity2 = bean.getSetval();
                        focusable(price2Et, false);
                    } else if (type == 3) {
                        price3Et.setText(name);
                        electricity3 = bean.getSetval();
                        focusable(price3Et, false);
                    } else {
                        electricity4 = bean.getSetval();
                        timeTv.setText(bean.getProblemname());
                    }
                }
            }
        });
//        int width = UIBaseUtils.dp2pxInt(this, 80);
        window.setContentView(view);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setBackgroundDrawable(new BitmapDrawable());
//        int paddingRight = UIBaseUtils.dp2pxInt(this, 0);
//        int xoff = SysUtils.getScreenWidth(this) - width - paddingRight;
        window.showAsDropDown(v);
    }

    private void focusable(EditText editText, boolean focusable) {
        editText.setFocusable(focusable);
        editText.setCursorVisible(focusable);
        editText.setFocusableInTouchMode(focusable);
        if (focusable) {
            editText.requestFocus();//设置可编辑
        }
    }

}
