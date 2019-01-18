package com.lanmei.peiyu.ui.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.lanmei.peiyu.R;
import com.lanmei.peiyu.certificate.CameraActivity;
import com.lanmei.peiyu.event.AddressChooseEvent;
import com.lanmei.peiyu.event.DataEntryEvent;
import com.lanmei.peiyu.helper.BGASortableNinePhotoHelper;
import com.lanmei.peiyu.helper.IdentityCardHelper;
import com.lanmei.peiyu.utils.CommonUtils;
import com.lanmei.peiyu.utils.CompressPhotoUtils;
import com.xson.common.api.PeiYuApi;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.ImageUtils;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.JsonUtil;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;

/**
 * 资料录入
 */
public class DataEntryActivity extends BaseActivity implements BGASortableNinePhotoLayout.Delegate {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar toolbar;

    @InjectView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout mPhotosSnpl;//拖拽排序九宫格控件
    BGASortableNinePhotoHelper mPhotoHelper;

    @InjectView(R.id.name_et)
    EditText nameEt;//户主姓名
    @InjectView(R.id.phone_et)
    EditText phoneEt;//联系电话
    @InjectView(R.id.card_et)
    EditText cardEt;//身份证号码
    @InjectView(R.id.card_pic1_iv)
    ImageView cardPic1Iv;//身份证正面
    @InjectView(R.id.card_pic2_iv)
    ImageView cardPic2Iv;//身份证反面
    @InjectView(R.id.address_tv)
    EditText addressTv;//安装地址
    @InjectView(R.id.tjman_tv)
    EditText tjmanTv;//推荐人id
    @InjectView(R.id.acreage_et)
    EditText acreageEt;//屋顶面积
    @InjectView(R.id.wall_tv)
    TextView wallTv;//女儿墙
    @InjectView(R.id.cullis_et)
    EditText cullisEt;//天沟面积
    @InjectView(R.id.content_tv)
    EditText contentTv;//说明文字
    @InjectView(R.id.spinner1)
    Spinner spinner1;//电压等级
    @InjectView(R.id.spinner2)
    Spinner spinner2;//屋顶结构
    @InjectView(R.id.spinner3)
    Spinner spinner3;//瓦的材质
    @InjectView(R.id.spinner4)
    Spinner spinner4;//房屋朝向
    int capacity, structure, material, direction = 1;

    private IdentityCardHelper helper;
    private CompressPhotoUtils compressPhotoUtils1;//图片上传类（身份证）
    private CompressPhotoUtils compressPhotoUtils2;//图片上传类（九宫格照片上传）
    private boolean isCompressPhotoUtils;//是否上传九宫格
    private boolean isIdCard;//是否上传完身份证正反面
    private List<String> list1;//上传到阿里云后的身份证
    private List<String> list2;//上传到阿里云后的九宫格

    @Override
    public int getContentViewId() {
        return R.layout.activity_data_entry;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.data_entry);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);
        initPhotoHelper();
        initSpinnerListener();

        compressPhotoUtils1 = new CompressPhotoUtils(this);
        compressPhotoUtils2 = new CompressPhotoUtils(this);
        helper = new IdentityCardHelper(this);
    }

    private void initSpinnerListener() {
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                capacity = position + 1;
                L.d(L.TAG, "capacity:" + capacity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                structure = position + 1;
                L.d(L.TAG, "structure:" + structure);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                material = position + 1;
                L.d(L.TAG, "material:" + material);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                direction = position + 1;
                L.d(L.TAG, "direction:" + direction);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initPhotoHelper() {
        mPhotoHelper = new BGASortableNinePhotoHelper(this, mPhotosSnpl);
        // 设置拖拽排序控件的代理
        mPhotoHelper.setDelegate(this);
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

    private boolean hasWall;//有无女儿墙

    @OnClick({R.id.card_pic1_iv, R.id.card_pic2_iv, R.id.submit_bt, R.id.wall_tv, R.id.position_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.card_pic1_iv://身份证正面
                helper.showDialog(CameraActivity.TYPE_IDCARD_FRONT);
                break;
            case R.id.card_pic2_iv://身份证反面
                helper.showDialog(CameraActivity.TYPE_IDCARD_BACK);
                break;
            case R.id.wall_tv://女人墙
                hasWall = !hasWall;
                CommonUtils.setCompoundDrawables(this, wallTv, hasWall ? R.mipmap.switch_on : R.mipmap.switch_off, 0, 2);
                break;
            case R.id.submit_bt://提交
                submit();
                break;
            case R.id.position_tv://地图
                center = null;
                IntentUtil.startActivity(this, MapActivity.class);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        helper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_submit:
                submit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void submit() {
        final String name = CommonUtils.getStringByEditText(nameEt);
        if (StringUtils.isEmpty(name)) {
            UIHelper.ToastMessage(this, R.string.input_household_name);
            return;
        }
        final String phone = CommonUtils.getStringByEditText(phoneEt);
        if (StringUtils.isEmpty(phone)) {
            UIHelper.ToastMessage(this, R.string.input_contact_number);
            return;
        }
        final String card = CommonUtils.getStringByEditText(cardEt);//身份证
        try {
            String marked = StringUtils.IDCardValidate(card);//input_installation_address
            if (!StringUtils.isEmpty(marked)) {
                UIHelper.ToastMessage(this, marked);
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (StringUtils.isEmpty(helper.getCardPic1()) || StringUtils.isEmpty(helper.getCardPic2())) {
            UIHelper.ToastMessage(this, "请选择身份证正反面");
            return;
        }

        if (center == null) {
            UIHelper.ToastMessage(this, "请选择安装地址的经纬度");
            return;
        }

        final String address = CommonUtils.getStringByEditText(addressTv);
        if (StringUtils.isEmpty(address)) {
            UIHelper.ToastMessage(this, "请输入选择对应经纬度的安装地址");
            return;
        }

        final String acreage = CommonUtils.getStringByEditText(acreageEt);//屋顶面积
        if (StringUtils.isEmpty(acreage)) {
            UIHelper.ToastMessage(this, R.string.input_roof_area);
            return;
        }
        final String cullis = CommonUtils.getStringByEditText(cullisEt);//天沟面积
        if (StringUtils.isEmpty(cullis)) {
            UIHelper.ToastMessage(this, R.string.input_roof_gutter_area);
            return;
        }

        isCompressPhotoUtils = !StringUtils.isEmpty(mPhotoHelper.getData());
        List<String> list = new ArrayList<>();
        list.add(helper.getCardPic1());
        list.add(helper.getCardPic2());
        compressPhotoUtils1.compressPhoto(CommonUtils.toArray(list), new CompressPhotoUtils.CompressCallBack() {//压缩图片（可多张）
            @Override
            public void success(List<String> list) {
                if (isFinishing()) {
                    return;
                }
                list1 = list;
                isIdCard = true;
                if (!isCompressPhotoUtils) {
                    submitData(name, phone, card, address, acreage, cullis);
                }
            }
        }, CommonUtils.isOne);

        if (isCompressPhotoUtils) {
            compressPhotoUtils2.compressPhoto(CommonUtils.toArray(mPhotoHelper.getData()), new CompressPhotoUtils.CompressCallBack() {//压缩图片（可多张）
                @Override
                public void success(List<String> list) {
                    if (isFinishing()) {
                        return;
                    }
                    list2 = list;
                    isCompressPhotoUtils = false;
                    if (isIdCard) {
                        submitData(name, phone, card, address, acreage, cullis);
                    }
                }
            }, CommonUtils.isTwo);
        }
    }

    private void submitData(String name, String phone, String card, String address, String acreage, String cullis) {
        PeiYuApi api = new PeiYuApi("station/means_add");
        api.addParams("uid", api.getUserId(this));
        api.addParams("s_name", name);//户主名称
        api.addParams("s_capacity", capacity);//电压等级 （1=220V，2=380V）
        api.addParams("s_phone", phone);//户主电话
        api.addParams("s_card", card);//户主身份证号码
        api.addParams("s_pic", JsonUtil.getJSONArrayByList(list1));//身份证图片
        api.addParams("s_address", address);//地址信息
        api.addParams("s_tjman", CommonUtils.getStringByEditText(tjmanTv));//推荐人id
        api.addParams("s_structure", structure);//屋顶结构   （1木质结构，2混凝土结构）
        api.addParams("s_material", material);//瓦材质     （1彩钢瓦，2琉璃瓦，3青瓦，4其他）
        api.addParams("s_direction", direction);//房屋朝向   （1，南2，东3，西4。北）
        api.addParams("s_acreage", acreage);//屋顶面积
        api.addParams("s_cullis", cullis);//天沟面积
        api.addParams("s_wall", hasWall ? 1 : 2);//有无女儿墙（1有2无）
        api.addParams("s_img", JsonUtil.getJSONArrayByList(list2));//图片
        api.addParams("content", CommonUtils.getStringByEditText(contentTv));//说明
        api.addParams("lng", center.longitude);// 经度
        api.addParams("lat", center.latitude);//纬度
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()) {
                    return;
                }
                EventBus.getDefault().post(new DataEntryEvent());
                UIHelper.ToastMessage(getContext(), response.getInfo());
                finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10000 || requestCode == 20000) {//选择图片九宫格
            mPhotoHelper.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        String image;
        switch (requestCode) {
            case IdentityCardHelper.CHOOSE_FROM_GALLAY:
                image = ImageUtils.getImageFileFromPickerResult(this, data);
                helper.startActionCrop(image);
                break;
            case IdentityCardHelper.RESULT_FROM_CROP:
                helper.uploadNewPhoto((helper.getType() == CameraActivity.TYPE_IDCARD_FRONT) ? cardPic1Iv : cardPic2Iv);//
                L.d(L.TAG, helper.getCardPic1() + "," + helper.getCardPic2());
                break;
            case CameraActivity.REQUEST_CODE:
                String path = data.getStringExtra("result");
                if (!TextUtils.isEmpty(path)) {
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    if (bitmap == null) {
                        return;
                    }
                    helper.setPic(path);
                    if (helper.getType() == CameraActivity.TYPE_IDCARD_FRONT) {//正面
                        cardPic1Iv.setImageBitmap(bitmap);
                    } else if (helper.getType() == CameraActivity.TYPE_IDCARD_BACK) {//反面
                        cardPic2Iv.setImageBitmap(bitmap);
                    }

                }
                break;
        }
    }

    private LatLng center;//地图中心位置的坐标

    //选择地址的时候调用
    @Subscribe
    public void addressChooseEvent(AddressChooseEvent event) {
        PoiInfo info = event.getInfo();
        addressTv.setText("");
        center = event.getCenter();
        if (info != null) {
            addressTv.setText(info.getAddress());
        } else {
            UIHelper.ToastMessage(this, "请输入选择对应经纬度地址");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compressPhotoUtils1 != null) {
            compressPhotoUtils1.cancelled();
        }
        if (compressPhotoUtils2 != null) {
            compressPhotoUtils2.cancelled();
        }
    }

}
