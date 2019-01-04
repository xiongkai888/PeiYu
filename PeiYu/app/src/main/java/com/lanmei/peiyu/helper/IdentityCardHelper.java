package com.lanmei.peiyu.helper;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.ImageView;

import com.lanmei.peiyu.BuildConfig;
import com.lanmei.peiyu.R;
import com.lanmei.peiyu.certificate.CameraActivity;
import com.lanmei.peiyu.utils.AKDialog;
import com.xson.common.app.BaseActivity;
import com.xson.common.utils.ImageUtils;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;

import java.io.File;


/**
 * Created by xkai on 2018/5/28.
 * 更改头像帮助类
 */

public class IdentityCardHelper {

    public static final int REQUEST_PERMISSIONS = 10;
    public static final int CHOOSE_FROM_CAMERA = 2;
    public static final int RESULT_FROM_CROP = 3;
    public static final int CHOOSE_FROM_GALLAY = 4;
    private File tempImage;
    private File croppedImage;
    private Context context;
    private boolean isCamera = false;
    private int type;//正面和反面
    private String pic;//选择头像剪切后的路径()

    private String cardPic1;//身份证正面
    private String cardPic2;//身份证反面

    public String getCardPic1() {
        return cardPic1;
    }

    public String getCardPic2() {
        return cardPic2;
    }

    public void setCardPic1(String cardPic1) {
        this.cardPic1 = cardPic1;
    }

    public void setCardPic2(String cardPic2) {
        this.cardPic2 = cardPic2;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
        if (type == CameraActivity.TYPE_IDCARD_FRONT){
            setCardPic1(pic);
        }else if (type == CameraActivity.TYPE_IDCARD_BACK){
            setCardPic2(pic);
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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


    public boolean isCamera() {
        return isCamera;
    }

    public void setCamera(boolean camera) {
        isCamera = camera;
    }

    public IdentityCardHelper(Context context) {
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
            CameraActivity.openCertificateCamera(((BaseActivity) context), type);
        } else {
            startImagePick();
        }
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
        if (requestCode == REQUEST_PERMISSIONS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (isCamera) {
                CameraActivity.openCertificateCamera(((BaseActivity) context), type);
            } else {
                startImagePick();
            }
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
            setPic(croppedImage.getAbsolutePath());
            Bitmap bitmap = ImageUtils.decodeSampledBitmapFromFile(pic, mAvatarIv.getWidth(), mAvatarIv.getHeight());
            mAvatarIv.setImageBitmap(bitmap);
        }
    }

    public void showDialog(int type) {
        this.type = type;
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

}
