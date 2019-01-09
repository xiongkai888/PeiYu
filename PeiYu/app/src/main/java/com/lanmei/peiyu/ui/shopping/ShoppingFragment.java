package com.lanmei.peiyu.ui.shopping;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.lanmei.peiyu.MainActivity;
import com.lanmei.peiyu.R;
import com.lanmei.peiyu.event.PaySucceedEvent;
import com.lanmei.peiyu.event.RefreshShopCartEvent;
import com.lanmei.peiyu.ui.shopping.core.IPresenter;
import com.lanmei.peiyu.ui.shopping.shop.ShopCarAdapter;
import com.lanmei.peiyu.ui.shopping.shop.ShopCarBean;
import com.lanmei.peiyu.ui.shopping.shop.ShopCarPresenter;
import com.lanmei.peiyu.ui.shopping.shop.ShopCartContract;
import com.lanmei.peiyu.utils.AKDialog;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.app.BaseFragment;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.FormatTextView;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xkai on 2018/12/19.
 * 购物车
 */

public class ShoppingFragment extends BaseFragment implements ShopCartContract.View, Toolbar.OnMenuItemClickListener {

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    // 全选
    @InjectView(R.id.all_select_checkbox)
    CheckBox mAllSelectCheckbox;
    @InjectView(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @InjectView(R.id.total_money_tv)
    FormatTextView mTotalMoneyTV;
    @InjectView(R.id.balance_bt)
    Button balanceBt;
    @InjectView(R.id.ll_bottom)
    LinearLayout llBottom;
    private ShopCarAdapter adapter;
    private ShopCartContract.Presenter mPresenter;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_shopping;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        toolbar.setTitle(R.string.shopping_cart);
        EventBus.getDefault().register(this);

        mPresenter = new ShopCarPresenter(context,this);

        smartSwipeRefreshLayout.initWithLinearLayout();
        View view = LayoutInflater.from(context).inflate(R.layout.empty_shop_car, smartSwipeRefreshLayout, false);
        smartSwipeRefreshLayout.setEmptyLayout(view);
        smartSwipeRefreshLayout.setMode(SmartSwipeRefreshLayout.Mode.NO_PAGE);
//        mEmptyView.addView(view);
        //去逛逛
        view.findViewById(R.id.go_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.showClassify(context);
            }
        });
        adapter = new ShopCarAdapter(context, mPresenter);
        smartSwipeRefreshLayout.setAdapter(adapter);
        mAllSelectCheckbox.setOnCheckedChangeListener(mAllSelectedChangeListener);
        mPresenter.start();
    }



    private  int style = 1;//1去付款，2删除

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit) {
            toolbar.getMenu().clear();
            toolbar.inflateMenu(R.menu.menu_done);
            balanceBt.setText(R.string.delete);
            style = 2;
        } else if (id == R.id.action_done) {
            toolbar.getMenu().clear();
            toolbar.inflateMenu(R.menu.menu_edit);
            balanceBt.setText(R.string.submit);
            style = 1;
        }
        return true;
    }

    //支付成功事件
    @Subscribe
    public void paySucceedEvent(PaySucceedEvent event) {
        deleteGoods(delecteList);
    }

    @Override
    public void setPresenter(IPresenter presenter) {

    }

    @Override
    public void initShopCart(List<ShopCarBean> list) {
        adapter.setData(list);
        adapter.notifyDataSetChanged();
//        UIHelper.ToastMessage(context,list.size()+"");
        llBottom.setVisibility(View.VISIBLE);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_edit);

        //toolbar的menu点击事件的监听
        toolbar.setOnMenuItemClickListener(this);
        L.d("ShopCarActivity", "initShopCart");
        style = 1;
//        balanceBt.setText(String.format(getString(R.string.balance),CommonUtils.getUserBean(context).getMoney()));
        balanceBt.setText(R.string.submit);
    }

    @Override
    public void showEmpty() {
        toolbar.getMenu().clear();
        llBottom.setVisibility(View.GONE);
        L.d("ShopCarActivity", "showEmpty");//
    }

    @Override
    public void refreshShopCart() {
        if (adapter != null) {
            L.d("ShopCarActivity", "refreshShopCart");//
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void summation(double sum, boolean selectedAll) {
        mTotalMoneyTV.setTextValue(String.format("%.2f", sum));

        mAllSelectCheckbox.setOnCheckedChangeListener(null);
        mAllSelectCheckbox.setChecked(selectedAll);
        mAllSelectCheckbox.setOnCheckedChangeListener(mAllSelectedChangeListener);

        L.d("ShopCarActivity", "summation");//
        refreshShopCart();
    }

    List<ShopCarBean> delecteList;//支付成功后要删除的数据
    @OnClick(R.id.balance_bt)
    public void onClick() {
        delecteList = mPresenter.getSeletctedCarList();
        if (style == 1) {//1去付款，2删除
            CommonUtils.developing(context);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("list", (Serializable)delecteList);
//            IntentUtil.startActivity(getContext(), ConfirmOrderActivity.class, bundle);
        } else {
            if (StringUtils.isEmpty(delecteList)) {
                UIHelper.ToastMessage(context, "请选择要删除的商品");
                return;
            }
            AKDialog.getAlertDialog(context, "确认要删除选择的商品？", new AKDialog.AlertDialogListener() {
                @Override
                public void yes() {
                    deleteGoods(delecteList);
                }
            });
        }
    }

    private void deleteGoods(List<ShopCarBean> list) {
        mPresenter.deleteSelectShopCar(list);
//        EventBus.getDefault().post(new ShowShopCountEvent());//商品详情显示购物车数量(通知购物车个数更新)
    }

    private CompoundButton.OnCheckedChangeListener mAllSelectedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            mPresenter.selectAll(b);
        }
    };

    //添加商品进入购物车时调用
    @Subscribe
    public void refreshShopCartEvent(RefreshShopCartEvent event){
        mPresenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
