package com.lanmei.peiyu.webviewpage;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xkai on 2017/6/2.
 * webview 图片预览工具
 */

public class WebViewPhotoBrowserUtil {

    /**
     * @param webView
     * @param content 内容
     */
    public static void photoBrowser(Context context, WebView webView, String content) {
        if (com.xson.common.utils.StringUtils.isEmpty(content)) {
            return;
        }
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        List<String> imageUrls  = StringUtils.returnImageUrlsFromHtml(content);
        loadHtml(content, webView);
        if (!com.xson.common.utils.StringUtils.isEmpty(imageUrls)) {
//            WebSettings webSettings = webView.getSettings();
//                        webSettings.setJavaScriptEnabled(true);
//                        webSettings.setDefaultFixedFontSize(13);
//                        webSettings.setDefaultTextEncodingName("UTF-8");
//                        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//                        webSettings.setSupportZoom(true);
//                        webSettings.setSaveFormData(false);
            webView.addJavascriptInterface(new MJavascriptInterface(context, imageUrls), "imagelistener");
            webView.setWebViewClient(new MyWebViewClient());
        }
    }

    public static void loadHtml(String content, WebView webView) {
        String html = "<div style=\"width:100%\">" + content + "</div>";
        html = delHTMLTag(html);
        webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
    }


    public static String delHTMLTag(String htmlStr) {

        htmlStr = "<div style=\\\"word-wrap:break-word;word-break:break-all;\\\">" + htmlStr + "</div><script>var pic = document.getElementsByTagName(\\\"img\\\");for (var i = 0; i < pic.length; i++) {pic[i].style.maxWidth = \\\"100%%\\\";pic[i].style.maxHeight = \\\"100%%\\\";};</script>";

        String img = "<img[^>]+>";
        Pattern imgp = Pattern.compile(img);
        Matcher html = imgp.matcher(htmlStr);
        while (html.find()) {
            Matcher m = Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(
                    html.group());
            while (m.find()) {
                String ig = "<img style=\"box-sizing: border-box; width: 100%; height: auto !important;\" src=\""
                        + m.group(1) + "\" />";
                htmlStr = htmlStr.replace(html.group(), ig);
            }
        }
        return htmlStr; // 返回文本字符串
    }

    public static void loadUrl(WebView webview,String url) {
        WebSettings webSettings = webview.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        //加载需要显示的网页
        webview.loadUrl(url);
        //设置Web视图
        webview.setWebViewClient(new WebViewClient());
    }

}
