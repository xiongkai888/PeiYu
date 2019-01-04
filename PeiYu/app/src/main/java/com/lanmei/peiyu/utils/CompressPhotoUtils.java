package com.lanmei.peiyu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.ProgressHUD;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import oss.ManageOssUpload;

/**
 * Created by xkai on 2017/6/1.
 * 批量压缩图片并上传
 */

public class CompressPhotoUtils {

    //    private ProgressDialog progressDialog;
    private String type;
    private Context context;
    private CompressCallBack callBack;
    private CompressTask compressTask;
    private UpdateImageViewTask updateImageViewTask;
    private String uploadingText = "照片上传中...";
    private ProgressHUD mProgressHUD;

    public void setUploadingText(String uploadingText) {
        this.uploadingText = uploadingText;
    }

    public CompressPhotoUtils(Context context) {
        this.context = context;
    }

    public void compressPhoto(String[] urls, CompressCallBack callBack, String type) {
        compressPhoto(callBack, type);
        compressTask.execute(urls);
    }

    public void compressPhoto(String path, CompressCallBack callBack, String type) {
        compressPhoto(callBack, type);
        compressTask.execute(path);
    }

    public void compressPhoto(CompressCallBack callBack, String type) {
        this.type = type;
        this.callBack = callBack;
        compressTask = new CompressTask();
        mProgressHUD = ProgressHUD.show(context, uploadingText, true, false, null);
    }

    public class CompressTask extends AsyncTask<String, Integer, List<String>> {

        private int num = 0;
        private List<String> fileList = new ArrayList<>();
        /**
         * 运行在UI线程中，在调用doInBackground()之前执行
         */
        @Override
        protected void onPreExecute() {
//            progressDialog = ProgressDialog.show(context, null, "处理中...");
        }

        /**
         * 后台运行的方法，可以运行非UI线程，可以执行耗时的方法
         */
        @Override
        protected List<String> doInBackground(String... params) {
            for (String s : params) {
                setList(s, num);
                num += 1;
            }
            return fileList;
        }

        public void setList(String nativePath, int num) {
            Bitmap bitmap = getBitmap(nativePath);
            String path = saveBitmap(bitmap, num);
            L.d("AyncListObjects", "压缩后的图片地址：" + path);
            fileList.add(path);
        }

        /**
         * 运行在ui线程中，在doInBackground()执行完毕后执行
         */
        @Override
        protected void onPostExecute(List<String> result) {
            if (!StringUtils.isEmpty(result)) {
                L.d("CompressPhotoUtils", "图片压缩成功");
                updateImageViewTask = new UpdateImageViewTask();
                updateImageViewTask.execute(CommonUtils.toArray(result));
            } else {
                mProgressHUD.cancel();
            }
        }

        /**
         * 在publishProgress()被调用以后执行，publishProgress()用于更新进度
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
        }
    }

    /**
     * 从sd卡获取压缩图片bitmap
     */
    public Bitmap getBitmap(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 1280f;
        float ww = 720f;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        newOpts.inSampleSize = be;// 设置缩放比例
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return bitmap;
    }

    /**
     * 保存bitmap到内存卡
     */
    public String saveBitmap(Bitmap bmp, int num) {
        File file = new File("mnt/sdcard/peiyu" + type + "/");
        String path = null;
        if (!file.exists())
            file.mkdirs();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String picName = formatter.format(new Date());
            path = file.getPath() + "/" + picName + "-" + num + ".jpg";
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    public interface CompressCallBack {
        void success(List<String> list);
    }


    /**
     * Created by xkai on 2018/5/30.
     */

    public class UpdateImageViewTask extends AsyncTask<String, Integer, List<String>> {


        /**
         * 运行在UI线程中，在调用doInBackground()之前执行
         */
        @Override
        protected void onPreExecute() {
        }

        /**
         * 后台运行的方法，可以运行非UI线程，可以执行耗时的方法
         */
        @Override
        protected List<String> doInBackground(String... params) {
            List<String> successPath = new ArrayList<>();
            ManageOssUpload manageOssUpload = new ManageOssUpload(context);//图片上传类
            for (String picPath : params) {
                String urlPic = manageOssUpload.uploadFile_img(picPath, type);
                if (StringUtils.isEmpty(urlPic)) {
                    //写上传失败逻辑
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    msg.obj = picPath;
                    mHandler.sendMessage(msg);
                } else {
                    successPath.add(urlPic);
                    L.d("CompressPhotoUtils", "上传成功返回的图片地址:" + urlPic);
                }
            }
            return successPath;
        }

        /**
         * 运行在ui线程中，在doInBackground()执行完毕后执行
         */
        @Override
        protected void onPostExecute(List<String> list) {
            //            mProgressDialog.cancel();
            mProgressHUD.cancel();
            if (StringUtils.isEmpty(list)) {
                return;
            }
            if (callBack != null) {
                callBack.success(list);
            }
            mProgressHUD = null;
        }

        /**
         * 在publishProgress()被调用以后执行，publishProgress()用于更新进度
         */
        @Override
        protected void onProgressUpdate(Integer... values) {

        }

    }

    public void cancelled() {
        if (compressTask != null && compressTask.getStatus() == AsyncTask.Status.RUNNING) {
            compressTask.cancel(true);
        }
        if (updateImageViewTask != null && updateImageViewTask.getStatus() == AsyncTask.Status.RUNNING) {
            updateImageViewTask.cancel(true);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1://上传某张图片失败
                    UIHelper.ToastMessage(context, "上传文件失败：" + msg.obj);
                    break;
            }
        }
    };

}