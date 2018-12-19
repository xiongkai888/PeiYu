package com.lanmei.peiyu.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.BannerHolderView;
import com.xson.common.bean.UserBean;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.utils.UserHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CommonUtils {

    public final static String isZero = "0";
    public final static String isOne = "1";
    public final static String isTwo = "2";
    public final static String isThree = "3";
    public final static String uid = "uid";

    public static int quantity = 3;


    /**
     * 获取TextView 字符串
     *
     * @param textView
     * @return
     */
    public static String getStringByTextView(TextView textView) {
        return textView.getText().toString().trim();
    }

    /**
     * 获取EditText 字符串
     *
     * @param editText
     * @return
     */
    public static String getStringByEditText(EditText editText) {
        return editText.getText().toString().trim();
    }

//    public static boolean isLogin(Context context) {
//        if (!UserHelper.getInstance(context).hasLogin()) {
//            IntentUtil.startActivity(context, LoginActivity.class);
//            return false;
//        }
//        return true;
//    }

    public static void developing(Context context) {
        UIHelper.ToastMessage(context, R.string.developing);
    }


    /**
     * @param list
     * @return
     */
    public static String[] toArray(List<String> list) {
        return list.toArray(new String[StringUtils.isEmpty(list) ? 0 : list.size()]);
    }


    public static String getUserId(Context context) {
        UserBean bean = getUserBean(context);
        if (StringUtils.isEmpty(bean)) {
            return "22";
        }
        return bean.getId();
    }

    //获取 用户类型
    public static String getUserType(Context context) {
        UserBean bean = getUserBean(context);
        if (StringUtils.isEmpty(bean)) {
            return "";
        }
        return bean.getUser_type();
    }

    public static UserBean getUserBean(Context context) {
        return UserHelper.getInstance(context).getUserBean();
    }

    public static String getRealName(Context context) {
        UserBean bean = getUserBean(context);
        return StringUtils.isEmpty(bean) ? "" : bean.getRealname();
    }


    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        if (StringUtils.isEmpty(cardId) || cardId.startsWith(isZero)) {
            return false;
        }
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0 || !nonCheckCodeCardId.matches("\\d+")) {
            return 'N';//如果传的不是数据返回N
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

//    /**
//     * 浏览图片
//     *
//     * @param context
//     * @param arry     图片地址数组
//     * @param imageUrl 点击的图片地址
//     */
//    public static void showPhotoBrowserActivity(Context context, List<String> arry, String imageUrl) {
//        Intent intent = new Intent();
//        intent.putExtra("imageUrls", (Serializable) arry);
//        intent.putExtra("curImageUrl", imageUrl);
//        intent.setClass(context, PhotoBrowserActivity.class);
//        context.startActivity(intent);
//    }

    public static String getFileName(String url) {
        if (StringUtils.isEmpty(url)) {
            return "";
        }
        return url.substring(url.lastIndexOf("/") + 1, url.length());

    }


    /**
     * 设置TabLayout tab 左右边距
     *
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    public static void setTabLayoutIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }


    /**
     * @param context
     * @param textView
     * @param drawableId 本地图片资源
     * @param colorId    textView的颜色id
     * @param position   图片在文字的位置  0左、1上、2右、3下
     */
    public static void setCompoundDrawables(Context context, TextView textView, int drawableId, int colorId, int position) {
        if (colorId > 0) {
            textView.setTextColor(context.getResources().getColor(colorId));
        }
        if (drawableId > 0) {
            Drawable img = context.getResources().getDrawable(drawableId);
            // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
            img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
            textView.setCompoundDrawables(position == 0 ? img : null, position == 1 ? img : null, position == 2 ? img : null, position == 3 ? img : null); //设置右图标
        } else {//清除文字周围的图片
            textView.setCompoundDrawables(null, null, null, null);
        }
    }

    // a integer to xx:xx:xx
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = isZero + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    public static void setBanner(ConvenientBanner banner, List<String> list, boolean isTurning) {
        //初始化商品图片轮播
        banner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new BannerHolderView();
            }
        }, list);
        banner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        banner.setPageIndicator(new int[]{R.drawable.shape_item_index_white, R.drawable.shape_item_index_red});
        banner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        if (list.size() == 1 || !isTurning) {
            return;
        }
        banner.startTurning(3000);
    }

    public static List<String> getList() {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "http://upload-images.jianshu.io/upload_images/3054428-0a653cc081a2a76e.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/678/h/260"
                , "http://upload-images.jianshu.io/upload_images/5862228-94c2cc04e8e0272f.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/678/h/260"
                , "http://p3.so.qhmsg.com/bdr/200_200_/t01c9b562b657abcb68.jpg");
        return list;
    }

    /**
     * @param s1       要改变颜色的文字
     * @param s2       要改变颜色的文字为空时的默认文字
     * @param s3       全部文字
     * @param textView
     * @param color     要改变颜色ID
     */
    public static void setSpannableString(String s1, String s2, String s3, TextView textView, String color) {
        if (StringUtils.isEmpty(s1)) {
            s1 = s2;
        }
        SpannableString spannableString = new SpannableString(s3);
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.2f);
        spannableString.setSpan(sizeSpan, 0, s1.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);//字体大小
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor(color));
//        L.d(L.TAG,"color:"+colorSpan.getForegroundColor());
        spannableString.setSpan(colorSpan, 0, s1.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);//字体颜色
        textView.setText(spannableString);
    }

}
