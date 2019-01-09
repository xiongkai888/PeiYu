package com.lanmei.peiyu.helper;

import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.lanmei.peiyu.PeiYuApp;
import com.lanmei.peiyu.utils.AssetsUtils;

import java.util.ArrayList;

import cn.qqtheme.framework.entity.Province;

/**
 * 一步消息获取地址 信息
 */
public class AddressAsyncTask extends AsyncTask<String, Integer, ArrayList<Province>> {

    @Override
    protected ArrayList<Province> doInBackground(String... params) {
        ArrayList<Province> data = new ArrayList<>();
        String json = AssetsUtils.getStringFromAssert(PeiYuApp.getInstance(), "city.json");
        data.addAll(JSON.parseArray(json, Province.class));
        return data;
    }

    protected void onPostExecute(ArrayList<Province> result) {
        if (l != null){
            l.setAddressList(result);
        }

    }

    private AddressAsyncTaskListener l;

    public interface AddressAsyncTaskListener{
        void setAddressList(ArrayList<Province> result);
    }

    public void setAddressAsyncTaskListener(AddressAsyncTaskListener l){
        this.l = l;
    }

}