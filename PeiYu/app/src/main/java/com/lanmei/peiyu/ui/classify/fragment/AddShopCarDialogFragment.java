package com.lanmei.peiyu.ui.classify.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.GoodsSpecificationsAdapter;
import com.lanmei.peiyu.bean.GoodsDetailsBean;
import com.lanmei.peiyu.bean.GoodsSpecificationsBean;
import com.lanmei.peiyu.ui.classify.activity.ConfirmOrderActivity;
import com.lanmei.peiyu.ui.shopping.shop.DBShopCartHelper;
import com.lanmei.peiyu.ui.shopping.shop.ShopCarBean;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.helper.ImageHelper;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by xson on 2017/12/25.
 * 加入购物车底部弹框
 */

public class AddShopCarDialogFragment extends DialogFragment {

    @InjectView(R.id.items_icon_iv)
    ImageView itemsIconIv;
    @InjectView(R.id.name_tv)
    TextView nameTv;
    @InjectView(R.id.price_tv)
    TextView priceTvTv;
    @InjectView(R.id.num_tv)
    TextView numTv;
    @InjectView(R.id.pay_num_et)
    EditText payNumEt;
    @InjectView(R.id.ll_specifications)
    LinearLayout llSpecifications;//规格
    @InjectView(R.id.specifications_name_tv)
    TextView specificationsNameTv;//规格
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;//规格列表
    private View mRootView;
    private GoodsDetailsBean detailsBean;
    private List<GoodsSpecificationsBean> specificationsBeans;
    private int inventory;//商品库存

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setStyle(STYLE_NO_TITLE, R.style.UI_Dialog);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparency)));

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        WindowManager.LayoutParams params = win.getAttributes();
        params.gravity = Gravity.BOTTOM;
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        win.setAttributes(params);
    }

    public void setParameter() {
        nameTv.setText(detailsBean.getGoodsname());
        ImageHelper.load(getContext(), detailsBean.getCover(), itemsIconIv, null, true, R.mipmap.default_pic, R.mipmap.default_pic);

//        String sale_price = StringUtils.isEmpty(detailsBean.getSale_price())?CommonUtils.isZero:detailsBean.getSale_price();
        String business_price = StringUtils.isEmpty(detailsBean.getBusiness_price())? CommonUtils.isZero:detailsBean.getBusiness_price();

//        priceTvTv.setText(String.format(getString(R.string.price), CommonUtils.isUser(getContext())?sale_price:business_price));
        priceTvTv.setText(String.format(getString(R.string.price), business_price));

        numTv.setText(String.format(getString(R.string.inventory), detailsBean.getInventory()));
        payNumEt.setFocusable(false);

        if (StringUtils.isEmpty(specificationsBeans)) {
            llSpecifications.setVisibility(View.GONE);
            return;
        }
        specificationsNameTv.setText(specificationsBeans.get(0).getSpecificationsname());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        GoodsSpecificationsAdapter teacherFiltrateAdapter = new GoodsSpecificationsAdapter(getContext());
        teacherFiltrateAdapter.setData(specificationsBeans);
        recyclerView.setAdapter(teacherFiltrateAdapter);

        inventory = Integer.valueOf(detailsBean.getInventory());
        if (orderNum > inventory) {
            payNumEt.setText(String.valueOf(inventory));
        }


        teacherFiltrateAdapter.setTeacherFiltrateListener(new GoodsSpecificationsAdapter.GoodsSpecificationsFiltrateListener() {
            @Override
            public void onFiltrate(GoodsSpecificationsBean bean) {
                inventory = Integer.valueOf(bean.getInventory());
                if (orderNum > inventory) {
                    payNumEt.setText(String.valueOf(inventory));
                }
//                String  sale_price = StringUtils.isEmpty(bean.getSale_price())?CommonUtils.isZero:bean.getSale_price();
                String business_price = StringUtils.isEmpty(bean.getBusiness_price())?CommonUtils.isZero:bean.getBusiness_price();

                priceTvTv.setText(String.format(getString(R.string.price), business_price));

                numTv.setText(String.format(getString(R.string.inventory), bean.getInventory()));
                detailsBean.setBusiness_price(business_price);//规格不变同价格不同
                detailsBean.setSpecifications(bean.getSpecifications());
                detailsBean.setGid(bean.getId());
            }
        });
    }

    public void setData(GoodsDetailsBean bean, List<GoodsSpecificationsBean> specificationsBeans) {
        this.detailsBean = bean;
        this.specificationsBeans = specificationsBeans;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.dialog_add_shop_car, null);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        ButterKnife.inject(this, mRootView);
        setParameter();
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    int orderNum = 1;//订购数量,默认1

    @OnClick({R.id.crossIv, R.id.num_subtract_iv, R.id.num_add_iv, R.id.add_shop_car_tv, R.id.pay_now_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.crossIv:
                dismiss();
                break;
            case R.id.num_subtract_iv:
                if (orderNum <= 1) {
                    return;
                }
                orderNum--;
                payNumEt.setText(String.valueOf(orderNum));
                break;
            case R.id.num_add_iv:
                if (orderNum == inventory) {
                    UIHelper.ToastMessage(getContext(), getString(R.string.understock));
                    return;
                }
                orderNum++;
                payNumEt.setText(String.valueOf(orderNum));
                break;
            case R.id.add_shop_car_tv:
                addShopCar();
                break;
            case R.id.pay_now_tv:
                goPayment();
                break;
        }
    }

    //去付款
    private void goPayment() {
        if (!StringUtils.isEmpty(specificationsBeans) && !isChooseGoodsSpecifications()) {
            UIHelper.ToastMessage(getContext(), "请选择商品 " + CommonUtils.getStringByTextView(specificationsNameTv));
            return;
        }
        List<ShopCarBean> list = new ArrayList<>();
        ShopCarBean bean = new ShopCarBean();
        bean.setGid(detailsBean.getGid());
        bean.setGoods_id(detailsBean.getId());
        bean.setGoodsName(detailsBean.getGoodsname());
        bean.setGoodsCount(orderNum);
        bean.setGoodsImg(detailsBean.getCover());
        bean.setSpecifications(detailsBean.getSpecifications());
        bean.setSell_price(Double.parseDouble(detailsBean.getBusiness_price()));
        list.add(bean);

        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable)list);
        IntentUtil.startActivity(getContext(), ConfirmOrderActivity.class, bundle);
        dismiss();
    }

    //加入购物车
    private void addShopCar() {
        if (!StringUtils.isEmpty(specificationsBeans) && !isChooseGoodsSpecifications()) {
            UIHelper.ToastMessage(getContext(), "请选择商品 " + CommonUtils.getStringByTextView(specificationsNameTv));
            return;
        }
        ShopCarBean shopCarBean = new ShopCarBean();
        shopCarBean.setGoodsName(detailsBean.getGoodsname());
        shopCarBean.setSell_price(Double.valueOf(detailsBean.getPrice()));
        shopCarBean.setGoods_id(detailsBean.getId());
        shopCarBean.setGoodsImg(detailsBean.getCover());
        shopCarBean.setGoodsCount(orderNum);
        shopCarBean.setSpecifications(detailsBean.getSpecifications());
        shopCarBean.setGid(detailsBean.getGid());
        DBShopCartHelper.getInstance(getContext().getApplicationContext()).insertGoods(shopCarBean);
        dismiss();
    }

    //是否选择规格
    private boolean isChooseGoodsSpecifications() {
        int size = specificationsBeans.size();
        for (int i = 0; i < size; i++) {
            GoodsSpecificationsBean bean = specificationsBeans.get(i);
            if (bean.isSelect()) {
                return bean.isSelect();
            }
        }
        return false;
    }

}