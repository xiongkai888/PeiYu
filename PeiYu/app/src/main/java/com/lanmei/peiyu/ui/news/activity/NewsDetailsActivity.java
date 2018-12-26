package com.lanmei.peiyu.ui.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.adapter.NewsDetailsAdapter;
import com.lanmei.peiyu.bean.NewsCommentBean;
import com.lanmei.peiyu.bean.NewsDetailsBean;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.api.AbstractApi;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.DataBean;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.JsonUtil;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import butterknife.InjectView;
import butterknife.OnClick;


/**
 * 资讯详情
 */
public class NewsDetailsActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.compile_comment_et)
    EditText mCompileCommentEt;//写评论

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    NewsDetailsAdapter adapter;
    String id;//资讯id

//    private ShareHelper mShareHelper;


    private SwipeRefreshController<NoPageListBean<NewsCommentBean>> controller;

    @Override
    public int getContentViewId() {
        return R.layout.activity_news_details;
    }

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        id = intent.getStringExtra("value");
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.news_details);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        smartSwipeRefreshLayout.initWithLinearLayout();

        PeiYuApi api = new PeiYuApi("post/reviews");

        adapter = new NewsDetailsAdapter(this);
        smartSwipeRefreshLayout.setAdapter(adapter);
        controller = new SwipeRefreshController<NoPageListBean<NewsCommentBean>>(this, smartSwipeRefreshLayout, api, adapter) {
        };
        smartSwipeRefreshLayout.setMode(SmartSwipeRefreshLayout.Mode.ONLY_PULL_UP);
//        controller.loadFirstPage();
        adapter.notifyDataSetChanged();
        //分享初始化
//        loadBiDetails();//加载资讯详情
        mCompileCommentEt.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    adapter.setNewsDetails(JsonUtil.jsonToBean("{\n" +
                            "        \"id\": \"290\",\n" +
                            "        \"uid\": \"\",\n" +
                            "        \"cid\": \"11\",\n" +
                            "        \"type\": \"2\",\n" +
                            "        \"title\": \"赵丽颖《你和我的倾城时光》，成就最美的你！\",\n" +
                            "        \"content\": \"<p style=\\\"text-align:justify;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:微软雅黑;color:#333333;font-size:14px;font-weight:400;\\\" align=\\\"center\\\">\\r\\n\\t<img style=\\\"width:504px;height:447px;\\\" alt=\\\"\\\" src=\\\"http://qkmimages.img-cn-shenzhen.aliyuncs.com/181205/05145747_28460.png\\\" width=\\\"504\\\" height=\\\"641\\\" /> \\r\\n<\\/p>\\r\\n<p style=\\\"text-align:justify;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:微软雅黑;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<span style=\\\"font-family:Optima-Regular, PingFangTC-light;\\\">曾经的\\u201c她\\u201d只是一个平凡的女孩，家境普通，际遇普通。独自进入演艺圈打拼，从最寻常的龙套做起，用10年的漫漫时光，一点一点磨练自己。<\\/span> \\r\\n<\\/p>\\r\\n<p style=\\\"text-align:justify;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:微软雅黑;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<span style=\\\"font-family:Optima-Regular, PingFangTC-light;\\\">十年来，她是端庄大方、体贴入微的晴儿；是刻苦努力、坚韧善良的陆贞；是性格天真、敢爱敢恨的花千骨；是勇敢果断、侠骨柔肠的楚乔。<\\/span> \\r\\n<\\/p>\\r\\n<p style=\\\"text-align:justify;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:微软雅黑;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<span style=\\\"font-family:Microsoft YaHei;\\\">她，是演艺圈的拼命三娘、收视女王；<\\/span> \\r\\n<\\/p>\\r\\n<p style=\\\"text-align:justify;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:微软雅黑;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<span style=\\\"font-family:Optima-Regular, PingFangTC-light;\\\">她，是元气少女、正能量甜心，也是\\u201c吐槽补刀小能手\\u201d；<\\/span> \\r\\n<\\/p>\\r\\n<p style=\\\"text-align:justify;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:微软雅黑;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<span style=\\\"font-family:Microsoft YaHei;\\\">她，不依附、不屈服、不畏缩；<\\/span> \\r\\n<\\/p>\\r\\n<p style=\\\"text-align:justify;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:微软雅黑;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<span style=\\\"font-family:Optima-Regular, PingFangTC-light;\\\"><span style=\\\"font-family:Optima-Regular, PingFangTC-light;\\\">她，最能完美诠释出什么是真正由内而外的美丽<\\/span> \\r\\n<\\/p>\\r\\n<p style=\\\"text-align:justify;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:微软雅黑;color:#333333;font-size:14px;font-weight:400;\\\" align=\\\"center\\\">\\r\\n\\t<span style=\\\"font-family:Optima-Regular, PingFangTC-light;\\\"><img alt=\\\"\\\" src=\\\"http://qkmimages.img-cn-shenzhen.aliyuncs.com/181205/05145933_88776.png\\\" width=\\\"469\\\" height=\\\"271\\\" /><\\/span> \\r\\n<\\/p>\\r\\n<\\/span> \\r\\n<p style=\\\"text-align:justify;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:微软雅黑;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<span style=\\\"font-family:Optima-Regular, PingFangTC-light;\\\">她，就是赵丽颖。<\\/span> \\r\\n<\\/p>\\r\\n<hr />\\r\\n<p>\\r\\n\\t<span style=\\\"background-color:#ffffff;font-style:normal;font-family:Optima-Regular, PingFangTC-light;color:#333333;font-size:14px;font-weight:400;\\\">入行十一年，赵丽颖的星途是天道酬勤，是厚积薄发，流言和误解她鲜少解释，一番\\u201c官宣\\u201d就引发了微博的大奔溃，可见大家对她的热爱。<\\/span> \\r\\n<\\/p>\\r\\n<p style=\\\"text-align:justify;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:微软雅黑;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<img alt=\\\"\\\" src=\\\"http://qkmimages.img-cn-shenzhen.aliyuncs.com/181205/05150233_49548.png\\\" /> \\r\\n<\\/p>\\r\\n<p style=\\\"text-align:justify;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:微软雅黑;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<span style=\\\"font-family:Optima-Regular, PingFangTC-light;\\\"><\\/span><span style=\\\"background-color:#ffffff;font-style:normal;font-family:Optima-Regular, PingFangTC-light;color:#333333;font-size:14px;font-weight:400;\\\">天美纪亦是如此，品牌发展至今，产品始终坚持品质如一，以达到甚至优于国家食药局72项严格检测的标准，深受广大女性青睐。<\\/span> \\r\\n<\\/p>\\r\\n<p style=\\\"text-align:justify;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:微软雅黑;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<img alt=\\\"\\\" src=\\\"http://qkmimages.img-cn-shenzhen.aliyuncs.com/181205/05150304_44827.png\\\" width=\\\"734\\\" height=\\\"831\\\" /><span style=\\\"background-color:#ffffff;font-style:normal;font-family:Optima-Regular, PingFangTC-light;color:#333333;font-size:14px;font-weight:400;\\\"><\\/span>&nbsp;\\r\\n<\\/p>\\r\\n<p style=\\\"text-align:justify;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:微软雅黑;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<span style=\\\"font-family:Optima-Regular, PingFangTC-light;\\\">前七年，赵丽颖一直是配角。没关系，她一直坚持自己、锻炼自己，让自己更坚强。造就了现在拥有实力去探索更广阔的的天空。<\\/span> \\r\\n<\\/p>\\r\\n<p style=\\\"text-align:justify;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:微软雅黑;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<span style=\\\"font-family:Optima-Regular, PingFangTC-light;\\\"><span style=\\\"font-family:Microsoft YaHei;\\\">而天美纪，坚持天然研制纯粹，不断提高萃取技术，护肤品连孕妇都可以安全使用，只为带去每一份用心给你美的变化<\\/span><span style=\\\"font-family:Microsoft YaHei;\\\">。<\\/span><\\/span> \\r\\n<\\/p>\\r\\n<p style=\\\"text-align:justify;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:微软雅黑;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<span style=\\\"font-family:Optima-Regular, PingFangTC-light;\\\"> \\r\\n\\t<hr />\\r\\n\\t<span style=\\\"font-family:Optima-Regular, PingFangTC-light;\\\">北方有佳人，绝世而独立。千山人海，为你倾城，无数次的坚持不变，必将恒久而动人。<\\/span> <span style=\\\"font-family:Optima-Regular, PingFangTC-light;\\\"><br />\\r\\n<\\/span>\\r\\n<\\/p>\\r\\n<p style=\\\"text-align:justify;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:微软雅黑;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<span style=\\\"font-family:Optima-Regular, PingFangTC-light;\\\">天美纪将所有美好祝福倾予她，上天会奖赏努力坚持的姑娘，愿她如翱翔羽翼，如骄傲冠羽，如轻盈鸢鸟，坚定、纯粹、天然！<\\/span> <span style=\\\"font-family:Optima-Regular, PingFangTC-light;\\\"><br />\\r\\n<\\/span>\\r\\n<\\/p>\\r\\n<p style=\\\"text-align:justify;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:-apple-system-font, BlinkMacSystemFont, 'Helvetica Neue', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei UI', 'Microsoft YaHei', Arial, sans-serif;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<span style=\\\"font-family:Optima-Regular, PingFangTC-light;\\\">天美纪携手智能直播电视登陆浙江卫视、东方卫视热播大剧《你和我的倾城时光》，11月12日敬请关注！<\\/span> \\r\\n<\\/p>\\r\\n<p style=\\\"text-align:justify;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:-apple-system-font, BlinkMacSystemFont, 'Helvetica Neue', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei UI', 'Microsoft YaHei', Arial, sans-serif;color:#333333;font-size:14px;font-weight:400;\\\" align=\\\"center\\\">\\r\\n\\t<img alt=\\\"\\\" src=\\\"http://qkmimages.img-cn-shenzhen.aliyuncs.com/181205/05150501_74015.png\\\" />\\r\\n<\\/p>\\r\\n<p style=\\\"text-align:justify;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:-apple-system-font, BlinkMacSystemFont, 'Helvetica Neue', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei UI', 'Microsoft YaHei', Arial, sans-serif;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t&nbsp;\\r\\n<\\/p>\\r\\n<p style=\\\"text-align:justify;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:-apple-system-font, BlinkMacSystemFont, 'Helvetica Neue', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei UI', 'Microsoft YaHei', Arial, sans-serif;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<\\/span> \\r\\n\\t<hr />\\r\\n<\\/p>\\r\\n<p style=\\\"text-align:center;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:-apple-system-font, BlinkMacSystemFont, 'Helvetica Neue', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei UI', 'Microsoft YaHei', Arial, sans-serif;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<span style=\\\"font-family:Optima-Regular, PingFangTC-light;color:#ff2941;\\\">倾城时光，倾心于你\\r\\n<\\/p>\\r\\n<\\/span> \\r\\n<p style=\\\"text-align:center;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:-apple-system-font, BlinkMacSystemFont, 'Helvetica Neue', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei UI', 'Microsoft YaHei', Arial, sans-serif;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<span style=\\\"font-family:Optima-Regular, PingFangTC-light;color:#ff2941;\\\">天美纪<\\/span> \\r\\n<\\/p>\\r\\n<p style=\\\"text-align:center;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:-apple-system-font, BlinkMacSystemFont, 'Helvetica Neue', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei UI', 'Microsoft YaHei', Arial, sans-serif;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<span style=\\\"font-family:Optima-Regular, PingFangTC-light;color:#ff2941;\\\">赵丽颖<\\/span> \\r\\n<\\/p>\\r\\n<p style=\\\"text-align:center;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:-apple-system-font, BlinkMacSystemFont, 'Helvetica Neue', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei UI', 'Microsoft YaHei', Arial, sans-serif;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<span style=\\\"font-family:Optima-Regular, PingFangTC-light;color:#ff2941;\\\">联合展现<\\/span> \\r\\n<\\/p>\\r\\n<p style=\\\"text-align:center;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:-apple-system-font, BlinkMacSystemFont, 'Helvetica Neue', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei UI', 'Microsoft YaHei', Arial, sans-serif;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<span style=\\\"font-family:Optima-Regular, PingFangTC-light;color:#ff2941;\\\">时光倾城，给你如初<\\/span> \\r\\n<\\/p>\\r\\n<p style=\\\"text-align:center;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:-apple-system-font, BlinkMacSystemFont, 'Helvetica Neue', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei UI', 'Microsoft YaHei', Arial, sans-serif;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<span style=\\\"font-family:Optima-Regular, PingFangTC-light;color:#ff2941;\\\"><\\/span>&nbsp;\\r\\n<\\/p>\\r\\n<p style=\\\"text-align:center;background-color:#ffffff;font-style:normal;text-indent:0px;font-family:-apple-system-font, BlinkMacSystemFont, 'Helvetica Neue', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei UI', 'Microsoft YaHei', Arial, sans-serif;color:#333333;font-size:14px;font-weight:400;\\\">\\r\\n\\t<img alt=\\\"\\\" src=\\\"http://qkmimages.img-cn-shenzhen.aliyuncs.com/181205/05150534_30285.jpg\\\" /><span style=\\\"font-family:Optima-Regular, PingFangTC-light;color:#ff2941;\\\"><\\/span> \\r\\n<\\/p>\",\n" +
                            "        \"file\": [],\n" +
                            "        \"view\": \"0\",\n" +
                            "        \"favour\": \"0\",\n" +
                            "        \"reviews\": \"0\",\n" +
                            "        \"like\": \"0\",\n" +
                            "        \"unlike\": \"0\",\n" +
                            "        \"addtime\": \"1543994842\",\n" +
                            "        \"status\": \"1\",\n" +
                            "        \"del\": \"0\",\n" +
                            "        \"device_id\": \"0\",\n" +
                            "        \"aid\": \"0\",\n" +
                            "        \"top\": \"0\",\n" +
                            "        \"essential\": \"0\",\n" +
                            "        \"hot\": \"0\",\n" +
                            "        \"recommend\": \"1\",\n" +
                            "        \"area\": \"\",\n" +
                            "        \"price\": \"0.00\",\n" +
                            "        \"sub_title\": \"\",\n" +
                            "        \"site\": \"\",\n" +
                            "        \"label\": \"\",\n" +
                            "        \"tel\": \"\",\n" +
                            "        \"video\": \"\",\n" +
                            "        \"author\": \"\",\n" +
                            "        \"stime\": \"0\",\n" +
                            "        \"etime\": \"0\",\n" +
                            "        \"pic_size\": \"\",\n" +
                            "        \"cname\": \"热门资讯\",\n" +
                            "        \"lr\": \"0\",\n" +
                            "        \"favoured\": \"0\",\n" +
                            "        \"course_apply\": \"0\",\n" +
                            "        \"customize_apply\": \"0\",\n" +
                            "        \"activity_apply\": \"0\",\n" +
                            "        \"pic\": \"\",\n" +
                            "        \"nickname\": \"\",\n" +
                            "        \"area_format\": \"\"\n" +
                            "    }",NewsDetailsBean.class));
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        },1000);

    }


    private void loadBiDetails() {
        HttpClient httpClient = HttpClient.newInstance(this);
        PeiYuApi api = new PeiYuApi("post/details");
        api.setMethod(AbstractApi.Method.GET);
        api.addParams("id",id);
        api.addParams("uid",api.getUserId(this));
        httpClient.loadingRequest(api, new BeanRequest.SuccessListener<DataBean<NewsDetailsBean>>() {
            @Override
            public void onResponse(DataBean<NewsDetailsBean> response) {
                if (isFinishing()) {
                    return;
                }
                NewsDetailsBean bean = response.data;
                if (bean == null){
                    UIHelper.ToastMessage(getContext(),"无法获取资讯详情");
                    return;
                }
                adapter.setNewsDetails(bean);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                CommonUtils.developing(this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.send_info_tv})
    public void showInfo(View view) {//发送消息
        if (!CommonUtils.isLogin(this)) {
            return;
        }
        switch (view.getId()) {
            case R.id.send_info_tv:
                ajaxSend(mCompileCommentEt.getText().toString());
                break;
        }

    }


    /**
     * @param content 评论内容
     */
    private void ajaxSend(String content) {
        CommonUtils.developing(this);
//        if (StringUtils.isEmpty(content)){
//            UIHelper.ToastMessage(this,getString(R.string.input_comment));
//            return;
//        }
//        HttpClient httpClient = HttpClient.newInstance(this);
//        PeiYuApi api = new PeiYuApi("post/do_reviews");
//        api.addParams("content",content);
//        api.addParams("id",id);
//        api.addParams("uid",api.getUserId(this));
//        httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
//            @Override
//            public void onResponse(BaseBean response) {
//                if (isFinishing()) {
//                    return;
//                }
//                UIHelper.ToastMessage(NewsDetailsActivity.this, "评论成功");
//                if (controller != null){
//                    controller.loadFirstPage();
//                    loadBiDetails();//加载资讯详情
//                }
//                mCompileCommentEt.setText("");
//            }
//        });
    }

//    /**
//     * 结果返回
//     */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        mShareHelper.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mShareHelper.onDestroy();
//    }
}
