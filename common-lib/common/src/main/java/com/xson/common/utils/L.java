package com.xson.common.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

/**
 * @author Milk <249828165@qq.com>
 */
public class L {

    public final static int limit = 20;//列表条数限制

    public static boolean debug = true;
    public final static String TAG = "BeanRequest";
    public final static String p = "p";

    public static int v(String tag, String msg) {
        if (!debug || StringUtils.isEmpty(msg))
            return 0;
        return Log.v(tag, msg);
    }

    public static int v(String tag, String msg, Throwable tr) {
        if (!debug || StringUtils.isEmpty(msg))
            return 0;
        return Log.v(tag, msg, tr);
    }

    public static int d(String tag, String msg) {
        if (!debug || StringUtils.isEmpty(msg))
            return 0;
        if (msg.length() > 4000) {
            int size = msg.length();
            for (int i = 0; i < size; i += 4000) {
                if (i + 4000 < size) {
                    Log.d(tag, msg.substring(i, i + 4000));
                } else {
                    Log.d(tag, msg.substring(i, msg.length()));
                }
            }
        } else {
            Log.d(tag, msg);
        }
        return 0;
    }

    public static int d(String tag, String msg, Throwable tr) {
        if (!debug || StringUtils.isEmpty(msg))
            return 0;
        return Log.d(tag, msg, tr);
    }

    public static int d(String format, Object... args) {
        return d("TK_LOG_D", String.format(format, args));
    }

    public static int d(Throwable t) {
        return d("TK_LOG_D", t.getMessage(), t);
    }

    public static int i(String tag, String msg) {
        if (!debug || StringUtils.isEmpty(msg))
            return 0;
        return Log.i(tag, msg);
    }

    public static int i(String tag, String msg, Throwable tr) {
        if (!debug || StringUtils.isEmpty(msg))
            return 0;
        return Log.i(tag, msg, tr);
    }

    public static int w(String tag, String msg) {
        return Log.w(tag, msg);
    }

    public static int w(String tag, String msg, Throwable tr) {
        return Log.w(tag, msg, tr);
    }

    public static int w(String tag, Throwable tr) {
        return Log.w(tag, tr);
    }

    public static int e(String tag, String msg) {
        return Log.e(tag, msg);
    }

    public static int e(String tag, String msg, Throwable tr) {
        return Log.e(tag, msg, tr);
    }

    public static int e(String format, Object... args) {
        return e("TK_LOG_E", String.format(format, args));
    }

    public static int e(Throwable t) {
        return Log.e("TK_LOG_E", t.getMessage(), t);
    }

    //防止inputMethodManager引用this发生
    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager inputMethodManager = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) {
            return;
        }

        String [] viewArray = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field filed;
        Object filedObject;

        for (String view:viewArray) {
            try{
                filed = inputMethodManager.getClass().getDeclaredField(view);
                if (!filed.isAccessible()) {
                    filed.setAccessible(true);
                }
                filedObject = filed.get(inputMethodManager);
                if (filedObject != null && filedObject instanceof View) {
                    View fileView = (View) filedObject;
                    if (fileView.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        filed.set(inputMethodManager, null); // 置空，破坏掉path to gc节点
                    } else {
                        break;// 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                    }
                }
            }catch(Throwable t){
                t.printStackTrace();
            }
        }
    }

}
