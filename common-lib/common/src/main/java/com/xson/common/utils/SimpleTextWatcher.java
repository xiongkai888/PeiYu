package com.xson.common.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import static java.lang.Float.parseFloat;

/**
 * Created by Administrator on 2017/11/17.
 */

public abstract class SimpleTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public static int StringToInt(CharSequence s) {
        Integer tem = 0;
        if (TextUtils.isEmpty(s))
            return 0;
        try {

            tem = Integer.parseInt(s.toString());
        } catch (NumberFormatException err) {
            err.printStackTrace();
        } finally {
            return tem;
        }

    }

    public static float StringToFloat(CharSequence s) {
        float tem = 0;
        if (TextUtils.isEmpty(s))
            return 0;
        try {

            tem = parseFloat(s.toString());
        } catch (NumberFormatException err) {
            err.printStackTrace();
        } finally {
            return tem;
        }

    }
    public static double StringToDouble(CharSequence s) {
        double tem = 0;
        if (TextUtils.isEmpty(s))
            return 0;
        try {

            tem = Double.parseDouble(s.toString());
        } catch (NumberFormatException err) {
            err.printStackTrace();
        } finally {
            return tem;
        }

    }

}
