package oss;

import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadResult;
import com.alibaba.sdk.android.oss.model.ResumableUploadRequest;
import com.medui.yixiu.ui.teacher.uploadvideo.UploadVideoBean;

import oss.callback.UploadCallback;

/**
 * Created by wk on 2017/11/30.
 */

public abstract class SimUploadCallback implements UploadCallback<ResumableUploadRequest, CompleteMultipartUploadResult> {

    public UploadVideoBean videoUpload = null;

    public SimUploadCallback(UploadVideoBean videoUpload) {
        this.videoUpload = videoUpload;
    }

    public void setData(int position, UploadVideoBean videoUpload) {
        this.videoUpload = videoUpload;
    }

    public void setVideoUpload(UploadVideoBean videoUpload) {
        this.videoUpload = videoUpload;
    }

}
