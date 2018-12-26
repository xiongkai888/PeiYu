package com.xson.common.helper;

import android.content.Context;

public class SharedAccount {

    private static SharedPreferencesTool sp;

    private SharedAccount() {
    }

    private static SharedAccount instance = null;

    public static SharedAccount getInstance(Context context) {
        if (instance == null) {
            instance = new SharedAccount();
        }
        sp = SharedPreferencesTool.getInstance(context, "account");
        return instance;
    }


    public void savePhone(String phone) {
        sp.edit().putString("phone", phone).commit();
    }


    public void setNoFirstLogin(boolean isFirstLogin) {
        sp.edit().putBoolean("isFirstLogin", isFirstLogin).commit();
    }

    public String getPhone() {
        return sp.getString("phone", "");
    }

    public boolean isFirstLogin() {
        return sp.getBoolean("isFirstLogin", false);
    }

    public void clear() {
        sp.clear();
    }


}
