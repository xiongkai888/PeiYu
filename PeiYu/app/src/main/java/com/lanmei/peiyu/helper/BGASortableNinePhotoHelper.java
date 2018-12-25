package com.lanmei.peiyu.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

/**
 * Created by xkai on 2017/11/14.
 * 拖拽排序九宫格控件帮助类
 */

public class BGASortableNinePhotoHelper {

    public final int REQUEST_CODE_PERMISSION_PHOTO_PICKER = 1;
    public final int REQUEST_CODE_CHOOSE_PHOTO = 1;
    public final int REQUEST_CODE_PHOTO_PREVIEW = 2;
    BGASortableNinePhotoLayout mPhotosSnpl;
    Activity context;

    public BGASortableNinePhotoHelper(Activity context, BGASortableNinePhotoLayout photosSnpl) {

        this.context = context;
        mPhotosSnpl = photosSnpl;
        mPhotosSnpl.setMaxItemCount(9);//默认设为9宫格
        mPhotosSnpl.setPlusEnable(true);//九图控件的加号按钮
        mPhotosSnpl.setSortable(true);//开启拖拽排序功能
    }

    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(context, perms)) {//点击加号时调用
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "peiyu");
            context.startActivityForResult(BGAPhotoPickerActivity.newIntent(context, takePhotoDir, mPhotosSnpl.getMaxItemCount() - mPhotosSnpl.getItemCount(), null, false), REQUEST_CODE_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(context, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
        }
    }

    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        choicePhotoWrapper();
    }

    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotosSnpl.removeItem(position);
    }

    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        context.startActivityForResult(BGAPhotoPickerPreviewActivity.newIntent(context, mPhotosSnpl.getMaxItemCount(), models, models, position, false), REQUEST_CODE_PHOTO_PREVIEW);
    }

    public void setDelegate(BGASortableNinePhotoLayout.Delegate delegate) {
        mPhotosSnpl.setDelegate(delegate);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_PHOTO) {//选择图片点击确定时
            mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW) {//预览时
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        }
    }

    public void setData(ArrayList<String> photos){
        mPhotosSnpl.setData(photos);
    }

    /**
     * 设置为maxItemCount空格
     *
     * @param maxItemCount
     */
    public void setMaxItemCount(int maxItemCount) {
        mPhotosSnpl.setMaxItemCount(maxItemCount);//设为9宫格
    }

    /**
     * 是否设置九图控件的加号按钮
     *
     * @param isPlusEnable
     */
    public void setPlusEnable(boolean isPlusEnable) {
        mPhotosSnpl.setPlusEnable(isPlusEnable);//九图控件的加号按钮
    }

    /**
     * 是否开启拖拽排序功能
     *
     * @param isSortable
     */
    public void setSortable(boolean isSortable) {
        mPhotosSnpl.setSortable(isSortable);//开启拖拽排序功能
    }

    public List<String> getData(){
        return mPhotosSnpl.getData();
    }

    public int getItemCount(){
        return mPhotosSnpl.getItemCount();
    }

}
