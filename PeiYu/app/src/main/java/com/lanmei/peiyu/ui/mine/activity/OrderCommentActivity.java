package com.lanmei.peiyu.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.MineOrderListBean;
import com.lanmei.peiyu.event.OrderOperationEvent;
import com.lanmei.peiyu.helper.BGASortableNinePhotoHelper;
import com.lanmei.peiyu.utils.CommonUtils;
import com.lanmei.peiyu.utils.CompressPhotoUtils;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.JsonUtil;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;

/**
 * 商品评论
 */
public class OrderCommentActivity extends BaseActivity implements BGASortableNinePhotoLayout.Delegate {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.content_et)
    EditText contentEt;
    @InjectView(R.id.ratingbar)
    RatingBar ratingBar;
    MineOrderListBean.GoodsBean bean;//我的订单item信息
    int point = 1;

    @InjectView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout mPhotosSnpl;//拖拽排序九宫格控件
    BGASortableNinePhotoHelper mPhotoHelper;

    private CompressPhotoUtils compressPhotoUtils;

    @Override
    public int getContentViewId() {
        return R.layout.activity_order_comment;
    }

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        Bundle bundle = intent.getBundleExtra("bundle");
        if (!StringUtils.isEmpty(bundle)) {
            bean = (MineOrderListBean.GoodsBean) bundle.getSerializable("bean");
        }
    }

    private void initPhotoHelper() {
        mPhotoHelper = new BGASortableNinePhotoHelper(this, mPhotosSnpl);
        // 设置拖拽排序控件的代理
        mPhotoHelper.setDelegate(this);
    }


    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("商品评论");
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        initPhotoHelper();

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                point = (int) rating;
            }
        });
    }


    @OnClick(R.id.submit_bt)
    public void onViewClicked() {
        final String content = CommonUtils.getStringByEditText(contentEt);
        if (StringUtils.isEmpty(content)) {
            UIHelper.ToastMessage(this, R.string.input_comment);
            return;
        }

        if (mPhotosSnpl.getItemCount() == 0) {
            loadSubmitComment(content,null);
            return ;
        }else {
            compressPhotoUtils = new CompressPhotoUtils(this);
            compressPhotoUtils.compressPhoto(CommonUtils.toArray(mPhotoHelper.getData()), new CompressPhotoUtils.CompressCallBack() {//压缩图片（可多张）
                @Override
                public void success(List<String> list) {
                    if (isFinishing()) {
                        return;
                    }
                    for (int i= 0;i<list.size();i++){
                        L.d(L.TAG,""+list.get(i));
                    }
                    loadSubmitComment(content,list);
                }
            }, "4");
        }
    }

    private void loadSubmitComment(String content,List<String> list) {
        PeiYuApi api = new PeiYuApi("app/comment_save");
        api.addParams("userid", api.getUserId(this));
        api.addParams("order_no", bean.getOrder_no());
        api.addParams("goodsid", bean.getGoodsid());
        api.addParams("content", content);
        api.addParams("point", point);
        if (!StringUtils.isEmpty(list)){
            api.addParams("comment_pic", JsonUtil.getJSONArrayByList(list));
        }
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()) {
                    return;
                }
                UIHelper.ToastMessage(getContext(),response.getInfo());
                EventBus.getDefault().post(new OrderOperationEvent());
                finish();
            }
        });
    }


    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        mPhotoHelper.onClickAddNinePhotoItem(sortableNinePhotoLayout, view, position, models);
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotoHelper.onClickDeleteNinePhotoItem(sortableNinePhotoLayout, view, position, model, models);
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotoHelper.onClickNinePhotoItem(sortableNinePhotoLayout, view, position, model, models);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPhotoHelper.onActivityResult(requestCode, resultCode, data);
    }

}
