package com.lanmei.peiyu.utils;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.common.utils.OssUserInfo;
import com.alibaba.sdk.android.oss.model.DeleteObjectRequest;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.data.volley.Response;
import com.data.volley.error.VolleyError;
import com.lanmei.peiyu.PeiYuApp;
import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.BannerHolderView;
import com.lanmei.peiyu.event.SetUserInfoEvent;
import com.lanmei.peiyu.ui.login.LoginActivity;
import com.lanmei.peiyu.webviewpage.PhotoBrowserActivity;
import com.xson.common.api.AbstractApi;
import com.xson.common.api.PeiYuApi;
import com.xson.common.bean.DataBean;
import com.xson.common.bean.UserBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.ImageHelper;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.utils.UserHelper;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import oss.ManageOssUpload;


public class CommonUtils {

    public final static String isZero = "0";
    public final static String isOne = "1";
    public final static String isTwo = "2";
    public final static String isThree = "3";
    public final static String uid = "uid";
    //支付宝回调
    public static final String ALIPAY_NOTIFY_URL = AbstractApi.API_URL + "payment/callback/_id/1";

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


    public static boolean isLogin(Context context) {
        if (!UserHelper.getInstance(context).hasLogin()) {
            IntentUtil.startActivity(context, LoginActivity.class);
            return false;
        }
        return true;
    }

    /**
     * 设置字体的背景和颜色，文字。
     *
     * @param context
     * @param type     0或1
     * @param textView
     * @param strId
     * @param strIdEd
     */
    public static void setTextViewType(Context context, String type, TextView textView, int strId, int strIdEd) {
        if (StringUtils.isSame(CommonUtils.isZero,type)) {
            textView.setText(strId);
            textView.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            textView.setBackgroundResource(R.drawable.send);
        } else {
            textView.setText(strIdEd);
            textView.setTextColor(context.getResources().getColor(R.color.color999));
            textView.setBackgroundResource(R.drawable.send_on);
        }
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
     * 加载图片
     * @param context
     * @param image
     * @param url
     */
    public static void loadImage(Context context, ImageView image,String url) {
        ImageHelper.load(context,url,image,null,true,R.mipmap.default_pic,R.mipmap.default_pic);
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



    public static UserBean getUserBean(Context context) {
        return UserHelper.getInstance(context).getUserBean();
    }

    public static String getMoney(Context context) {
        UserBean bean = getUserBean(context);
        return StringUtils.isEmpty(bean) ? "0" : bean.getMoney();
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

    /**
     * 浏览图片
     *
     * @param context
     * @param arry     图片地址数组
     * @param position 点击的第position+1张图片
     */
    public static void showPhotoBrowserActivity(Context context, List<String> arry, int position) {
        Intent intent = new Intent();
        intent.putExtra("imageUrls", (Serializable) arry);
        intent.putExtra("curImageUrl", arry.get(position));
        intent.setClass(context, PhotoBrowserActivity.class);
        context.startActivity(intent);
    }

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

    /**
     * 返回一个定长的随机字符串(只包、数字)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateNumberString(int length) {
        StringBuilder sb = new StringBuilder();
        String numberChar = "0123456789";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
        }
        return sb.toString();
    }


    //获取用户信息
    public static void loadUserInfo(final Context context, final UserInfoListener l) {
        PeiYuApi api = new PeiYuApi("member/member");
        api.addParams("uid", api.getUserId(context));
        api.setMethod(AbstractApi.Method.GET);
        HttpClient.newInstance(context).request(api, new BeanRequest.SuccessListener<DataBean<UserBean>>() {
            @Override
            public void onResponse(DataBean<UserBean> response) {
                if (context == null) {
                    return;
                }
                UserBean bean = response.data;
                if (bean != null) {
                    if (l != null) {
                        l.userInfo(bean);
                    }
                    UserHelper.getInstance(context).saveBean(bean);
                    EventBus.getDefault().post(new SetUserInfoEvent(bean));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (l != null) {
                    l.error(error.getMessage());
                }
            }
        });
    }

    public interface UserInfoListener {
        void userInfo(UserBean bean);
        void error(String error);
    }


    /**
     * 删除OSS文件
     *
     * @param url
     */
    public static void deleteOssObject(String url) {
        String objectKey = getObjectKey(url);
        if (StringUtils.isEmpty(objectKey)) {
            return;
        }
        ManageOssUpload manageOssUpload = new ManageOssUpload(PeiYuApp.getInstance());
        manageOssUpload.deleteObject(new DeleteObjectRequest(OssUserInfo.testBucket, objectKey));
        manageOssUpload.logAyncListObjects();
    }

    /**
     * 删除OSS文件(批量)
     *
     * @param paths
     */
    public static void deleteOssObjectList(final List<String> paths) {
        if (StringUtils.isEmpty(paths)) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                ManageOssUpload manageOssUpload = new ManageOssUpload(PeiYuApp.getInstance());
                manageOssUpload.logAyncListObjects();
                int size = paths.size();
                for (int i = 0; i < size; i++) {
                    String objectKey = getObjectKey(paths.get(i));
                    if (!StringUtils.isEmpty(objectKey)) {
                        manageOssUpload.deleteObject(new DeleteObjectRequest(OssUserInfo.testBucket, objectKey));
                    }
                }
                manageOssUpload.logAyncListObjects();
            }
        }).start();
    }

    public static String getObjectKey(String url) {
        if (StringUtils.isEmpty(url) || !url.contains(OssUserInfo.endpoint)) {
            return "";
        }
        return url.substring(url.indexOf("/", 35) + 1, url.length());
    }


}
