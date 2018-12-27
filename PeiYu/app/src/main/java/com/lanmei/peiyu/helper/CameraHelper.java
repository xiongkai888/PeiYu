package com.lanmei.peiyu.helper;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.ImageView;

import com.lanmei.peiyu.BuildConfig;
import com.lanmei.peiyu.R;
import com.lanmei.peiyu.utils.AKDialog;
import com.xson.common.app.BaseActivity;
import com.xson.common.utils.ImageUtils;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.ProgressHUD;

import java.io.File;

import oss.ManageOssUpload;


/**
 * Created by xkai on 2018/5/28.
 * 更改头像帮助类
 */

public class CameraHelper {

    public static final int REQUEST_PERMISSIONS = 1;
    public static final int CHOOSE_FROM_CAMERA = 2;
    public static final int RESULT_FROM_CROP = 3;
    public static final int CHOOSE_FROM_GALLAY = 4;
    private File tempImage;
    private File croppedImage;
    private Context context;
    private boolean isCamera = false;
    private String headPathStr;//选择头像剪切后的路径()
    private   String mHeadUrl;//上传阿里云返回的头像地址

    public void setHeadPathStr(String headPathStr) {
        this.headPathStr = headPathStr;
    }

    public File getTempImage() {
        return tempImage;
    }

    public File getCroppedImage() {
        return croppedImage;
    }

    public void setTempImage(File tempImage) {
        this.tempImage = tempImage;
    }

    public void setCroppedImage(File croppedImage) {
        this.croppedImage = croppedImage;
    }

    public String getHeadPathStr() {
        return headPathStr;
    }

    public boolean isCamera() {
        return isCamera;
    }

    public void setCamera(boolean camera) {
        isCamera = camera;
    }

    public CameraHelper(Context context) {
        this.context = context;
    }

    public void applyWritePermission() {

        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (isCamera) {
            permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        }
        if (Build.VERSION.SDK_INT >= 23) {
            int check = ContextCompat.checkSelfPermission(context, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (check == PackageManager.PERMISSION_GRANTED) {
                //调用相机
                start();
            } else {
                ((BaseActivity) context).requestPermissions(permissions, REQUEST_PERMISSIONS);
            }
        } else {
            start();
        }
    }

    private void start(){
        if (isCamera) {
            startActionCamera();
        } else {
            startImagePick();
        }
    }

    /**
     * 相机拍照
     */
    public void startActionCamera() {
        tempImage = ImageUtils.getTempFile(context, "head");
        if (tempImage == null) {
            UIHelper.ToastMessage(context, R.string.cannot_create_temp_file);
            return;
        }

        Intent intent = ImageUtils.getImageCaptureIntent(getUriForFile(tempImage));
        ((BaseActivity) context).startActivityForResult(intent, CHOOSE_FROM_CAMERA);
    }


    //解决Android 7.0之后的Uri安全问题
    public Uri getUriForFile(File file) {
        if (file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID+".fileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }


    /**
     * 相册
     */
    public void startImagePick() {
        Intent intent = ImageUtils.getImagePickerIntent();
        ((BaseActivity) context).startActivityForResult(intent, CHOOSE_FROM_GALLAY);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (isCamera) {
                startActionCamera();
            } else {
                startImagePick();
            }
        } else {
            // 没有获取 到权限，从新请求，或者关闭app
//            UIHelper.ToastMessage(this,"需要存储权限");

        }
    }

    public void startActionCrop(String image) {
        if (TextUtils.isEmpty(image)) {
            UIHelper.ToastMessage(context, R.string.image_not_exists);
            return;
        }
        File imageFile = new File(image);
        if (!imageFile.exists()) {
            UIHelper.ToastMessage(context, R.string.image_not_exists);
            return;
        }
        croppedImage = ImageUtils.getTempFile(context, "head");
        if (croppedImage == null) {
            UIHelper.ToastMessage(context, R.string.cannot_create_temp_file);
            return;
        }

        Intent intent = ImageUtils.getImageCropIntent(getUriForFile(imageFile), Uri.fromFile(croppedImage));
        ((BaseActivity) context).startActivityForResult(intent, RESULT_FROM_CROP);
    }

    public void uploadNewPhoto(ImageView mAvatarIv) {
        if (!StringUtils.isEmpty(croppedImage) && croppedImage.exists()) {
            try {
                ImageUtils.compressImage(croppedImage.getPath(), 300, 300, Bitmap.CompressFormat.JPEG);
            } catch (Exception e) {
                L.e(e);
            }
        }
        if (croppedImage != null) {
            headPathStr = croppedImage.getAbsolutePath();
            Bitmap bitmap = ImageUtils.decodeSampledBitmapFromFile(headPathStr, mAvatarIv.getWidth(), mAvatarIv.getHeight());
            mAvatarIv.setImageBitmap(bitmap);
        }
    }

    public class UpdateHeadViewTask extends AsyncTask<String, Integer, String> {


        private ProgressHUD mProgressHUD;

        public UpdateHeadViewTask() {
            mProgressHUD = ProgressHUD.show(context, "", true, false, null);
        }

        /**
         * 运行在UI线程中，在调用doInBackground()之前执行
         */
        @Override
        protected void onPreExecute() {
            //            Toast.makeText(PublishDynamicActivity.this, "开始执行", Toast.LENGTH_SHORT).show();
        }

        /**
         * 后台运行的方法，可以运行非UI线程，可以执行耗时的方法
         */
        @Override
        protected String doInBackground(String... params) {
            ManageOssUpload manageOssUpload = new ManageOssUpload(context);//图片上传类
            String urlPic = manageOssUpload.uploadFile_img(params[0], "1");
            if (StringUtils.isEmpty(urlPic)) {
                //写上传失败逻辑
                Message msg = mHandler.obtainMessage();
                msg.what = 1;
                mHandler.sendMessage(msg);
                mProgressHUD.cancel();
            }
            return urlPic;
        }

        /**
         * 运行在ui线程中，在doInBackground()执行完毕后执行
         */
        @Override
        protected void onPostExecute(String headUrl) {
            mHeadUrl = headUrl;
            mProgressHUD.cancel();
            if (l != null) {
                l.getUrl(mHeadUrl);
            }
//            loadUpDate();
        }

        /**
         * 在publishProgress()被调用以后执行，publishProgress()用于更新进度
         */
        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1://上传某张图片失败
                    UIHelper.ToastMessage(context, "上传图片失败");
                    break;
            }
        }
    };


    public void showDialog() {
        AKDialog.showBottomListDialog(context, ((BaseActivity) context), new AKDialog.AlbumDialogListener() {
            @Override
            public void photograph() {
                isCamera = true;
                applyWritePermission();

            }

            @Override
            public void photoAlbum() {
                isCamera = false;
                applyWritePermission();
            }
        });
    }

    public void execute() {
        new UpdateHeadViewTask().execute(getHeadPathStr());
    }

    HeadUrlListener l;

    public void setHeadUrlListener(HeadUrlListener l) {
        this.l = l;
    }

    public interface HeadUrlListener {
        void getUrl(String url);
    }

}
