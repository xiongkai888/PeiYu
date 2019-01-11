package com.lanmei.peiyu.webviewpage;

import android.content.Context;
import android.content.Intent;

import java.io.Serializable;
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
        Intent intent = new Intent();
        intent.putExtra("imageUrls", (Serializable) imageUrls);
        intent.putExtra("curImageUrl", img);
        intent.setClass(context, PhotoBrowserActivity.class);
        context.startActivity(intent);
    }
}
