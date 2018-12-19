package com.lanmei.peiyu.ui.shopping.shop;

import android.content.Context;

import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xkai on 2018/1/20.
 * 数据处理类
 */

public class ShopCartDataHelper {

    private List<ShopCarBean> mShopCarList;
    DBShopCartHelper dbShopCartHelper;

    public ShopCartDataHelper(Context context) {
        dbShopCartHelper = DBShopCartHelper.getInstance(context);
    }

    public void setShopCarList(List<ShopCarBean> shopCarList) {
        mShopCarList = shopCarList;
    }

    public void selectAll(boolean selected) {
        if (isNoList()) {
            return;
        }

        int size = mShopCarList.size();
        for (int i = 0; i < size; i++) {
            ShopCarBean bean = getShopCarBean(i);
            bean.setSelect(selected);
        }
        L.d("ShopCarActivity", "selected = " + selected);

    }

    private boolean isNoList() {
        return StringUtils.isEmpty(mShopCarList);
    }

    public void setGoodsNum(int position, int num) {
        ShopCarBean bean = getShopCarBean(position);
        if (!StringUtils.isEmpty(bean)) {
            bean.setGoodsCount(num);
        }
    }

    public ShopCarBean getShopCarBean(int position) {
        if (isNoList()) {
            return null;
        }

        return mShopCarList.get(position);
    }

    public void setSelect(int position, boolean select) {
        if (isNoList()) {
            return;
        }
        getShopCarBean(position).setSelect(select);
    }

    public List<ShopCarBean> getShopCartList() {
        return dbShopCartHelper.getShopCarList();
    }

    //获取选择商品的价格
    public double getSelectedTotalPrice() {
        double price = 0;
        if (isNoList()) {
            return price;
        }
        int size = mShopCarList.size();
        for (int i = 0; i < size; i++) {
            ShopCarBean bean = getShopCarBean(i);
            if (bean.isSelect()) {
                price += (bean.getSell_price() * bean.getGoodsCount());
            }
        }
        return price;
    }

    //是否全选
    public boolean isAllSelected() {
        if (isNoList()) {
            return false;
        }
        int size = mShopCarList.size();
        for (int i = 0; i < size; i++) {
            ShopCarBean bean = getShopCarBean(i);
            if (!bean.isSelect()) {
                return false;
            }
        }
        return true;
    }

    public List<ShopCarBean> getSeletctedCarList() {
        if (isNoList()) {
            return null;
        }
        List<ShopCarBean> list = new ArrayList<>();
        int size = mShopCarList.size();
        for (int i = 0; i < size; i++) {
            ShopCarBean bean = mShopCarList.get(i);
            if (bean.isSelect()) {
                list.add(bean);
            }
        }
        return list;
    }

    //删除选中的购物车
    public void deleteSelectShopCar(List<ShopCarBean> list) {
        dbShopCartHelper.delete(list);
    }

    public void update(ShopCarBean bean,int count){
        //先屏蔽掉
//        dbShopCartHelper.update(bean,count);
    }

}
