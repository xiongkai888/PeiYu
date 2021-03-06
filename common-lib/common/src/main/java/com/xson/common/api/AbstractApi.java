package com.xson.common.api;

import android.content.Context;

import com.xson.common.bean.UserBean;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UserHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Milk <249828165@qq.com>
 */
public abstract class AbstractApi {

    private int p;
    public static String API_URL = "";
    public HashMap<String, Object> paramsHashMap = new HashMap<String, Object>();
    private Method method = Method.POST;

    public void setMethod(Method method) {
        this.method = method;
    }

    public static enum Method {
        GET,
        POST,
    }

    public static enum Enctype {
        TEXT_PLAIN,
        MULTIPART,
    }

    protected abstract String getPath();

    public Method requestMethod() {
        return method;
    }

    public Enctype requestEnctype() {
        return Enctype.TEXT_PLAIN;
    }

    public String getUrl() {
        return API_URL + getPath();
    }

    public void setPage(int page) {
        this.p = page;
    }

    public AbstractApi addParams(String key, Object value) {
        paramsHashMap.put(key, value);
        return this;
    }

    public String getUserId(Context context) {
        UserBean userBean = UserHelper.getInstance(context).getUserBean();
        return StringUtils.isEmpty(userBean) ? "" : userBean.getId()+"";
    }


    public Map<String, Object> getParams() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        for (Map.Entry<String, Object> item : paramsHashMap.entrySet()) {
            params.put(item.getKey(), item.getValue());
        }
        if (p > 0) {
            params.put(L.p, p);
        }
        return params;
    }

}
