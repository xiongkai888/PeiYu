package com.lanmei.peiyu.ui.classify.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.AddressListAdapter;
import com.lanmei.peiyu.bean.AddressListBean;
import com.lanmei.peiyu.event.AddAddressEvent;
import com.lanmei.peiyu.event.AddressListEvent;
import com.lanmei.peiyu.event.AlterAddressEvent;
import com.lanmei.peiyu.event.ChooseAddressEvent;
import com.lanmei.peiyu.utils.AKDialog;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;


/**
 * 地址列表（选择收货地址）
 */
public class AddressListActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    AddressListAdapter adapter;
    private SwipeRefreshController<NoPageListBean<AddressListBean>> controller;
    private List<AddressListBean> list;

    @Override
    public int getContentViewId() {
        return R.layout.activity_single_listview;
    }

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        Bundle bundle = intent.getBundleExtra("bundle");
        if (bundle != null){
            list = (List<AddressListBean>)bundle.getSerializable("list");
        }
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("选择收货地址");
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        EventBus.getDefault().register(this);

        smartSwipeRefreshLayout.initWithLinearLayout();
        smartSwipeRefreshLayout.setMode(SmartSwipeRefreshLayout.Mode.NO_PAGE);
        adapter = new AddressListAdapter(this);
        smartSwipeRefreshLayout.setAdapter(adapter);

        PeiYuApi api = new PeiYuApi("app/address");
        api.addParams("uid",api.getUserId(this));
//        api.add("uid",46);
        api.addParams("operation",4);
        controller = new SwipeRefreshController<NoPageListBean<AddressListBean>>(getContext(), smartSwipeRefreshLayout, api, adapter) {
            @Override
            public boolean onSuccessResponse(NoPageListBean<AddressListBean> response) {
                list = response.data;
                EventBus.getDefault().post(new AddressListEvent(response.data));
                return super.onSuccessResponse(response);
            }
        };

        if (StringUtils.isEmpty(list)){
            controller.loadFirstPage();
        }else {
            adapter.setData(list);
            adapter.notifyDataSetChanged();
        }
        adapter.setChooseAddressListener(new AddressListAdapter.ChooseAddressListener() {
            @Override
            public void choose(AddressListBean bean) {
                EventBus.getDefault().post(new ChooseAddressEvent(bean));
                finish();
            }

            @Override
            public void setDefault(String id, int position) {
                setAddressDefault(id, position);
            }

            @Override
            public void delete(final String id,final int position) {
                AKDialog.getAlertDialog(getContext(), getString(R.string.delete_this_address), new AKDialog.AlertDialogListener() {
                    @Override
                    public void yes() {
                        deleteAddress(id,position);
                    }
                });
            }
        });
    }

    private void deleteAddress(String id,final int position) {
        PeiYuApi api = new PeiYuApi("app/address");
        api.addParams("uid",api.getUserId(this));
        api.addParams("operation",3);
        api.addParams("id",id);
        HttpClient.newInstance(getContext()).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()){
                    return;
                }
                adapter.getData().remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyDataSetChanged();
                EventBus.getDefault().post(new AlterAddressEvent());//
            }
        });
    }

    //设为默认
    private void setAddressDefault(String id, final int position) {
        PeiYuApi api = new PeiYuApi("app/address");
        api.addParams("uid",api.getUserId(this));
        api.addParams("operation",2);
        api.addParams("id",id);
        api.addParams("default",1);
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()){
                    return;
                }
                List<AddressListBean> list = adapter.getData();
                int size = list.size();
                for (int i=0;i<size;i++){
                    list.get(i).setDefaultX("0");
                }
                list.get(position).setDefaultX("1");
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //添加地址的时候
    @Subscribe
    public void addAddressEvent(AddAddressEvent event) {
       controller.loadFirstPage();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add://添加
                IntentUtil.startActivity(this, AddAddressActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
