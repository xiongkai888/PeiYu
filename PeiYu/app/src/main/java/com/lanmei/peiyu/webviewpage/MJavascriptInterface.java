package com.lanmei.peiyu.webviewpage;

import android.content.Context;

import com.lanmei.peiyu.utils.CommonUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/2/10.
 */

public class MJavascriptInterface {
    private Context context;
    private List<String> imageUrls;

    public MJavascriptInterface(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @android.webkit.JavascriptInterface
    public void openImage(String img) {
        CommonUtils.showPhotoBrowserActivity(context,imageUrls,img);
//        for (int i = 0; i < imageUrls.length; i++) {
//            Log.e("imageSrcList"+i,"图片地址："+imageUrls[i].toString()+",img = "+img);
//        }
    }
}
