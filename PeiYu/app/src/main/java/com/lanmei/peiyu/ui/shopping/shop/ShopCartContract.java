package com.lanmei.peiyu.ui.shopping.shop;


import com.lanmei.peiyu.ui.shopping.core.IPresenter;
import com.lanmei.peiyu.ui.shopping.core.IView;

import java.util.List;

/**
 * Created by xkai on 2018/1/20.
 */

public class ShopCartContract {

    public interface View extends IView {

        void initShopCart(List<ShopCarBean> list);


        /**
         * 显示无数据
         */
        void showEmpty();

        /**
         * 刷新购物车
         */
        void refreshShopCart();

        /**
         * 总计
         * @param sum
         */
        void summation(double sum, boolean selectedAll);

    }

    public interface Presenter extends IPresenter {

        /**
         * 全选
         * @param seleted
         */
        void selectAll(boolean seleted);

        /**
         * 获取购物车数量
         * @return
         */
        int getShopCarCount();

        /**
         * 删除选中的购物车
         */
        void deleteSelectShopCar(List<ShopCarBean> list);
        /**
         * 清空购物车
         */
        void clearShopCart();

        // 获取被选中的购物车
        List<ShopCarBean> getSeletctedCarList();

        // 获取购物车列表
        List<ShopCarBean> getShopCartList();

        //设置商品的数量
        void setGoodsNum(ShopCarBean bean, int position, int num);
        //设置商品的数量
        void setSelect(int position, boolean select);

        void update(ShopCarBean bean, int count);

    }
}
