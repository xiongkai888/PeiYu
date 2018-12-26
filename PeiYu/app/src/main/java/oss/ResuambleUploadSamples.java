package oss;

import android.content.Context;
import android.os.Environment;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ResumableUploadRequest;
import com.alibaba.sdk.android.oss.model.ResumableUploadResult;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;

import java.io.File;

/**
 * Created by zhouzhuo on 12/3/15.
 */
public class ResuambleUploadSamples extends BaseOss {

    private String uploadFilePath;
    private OSSAsyncTask resumableTask;

    public ResuambleUploadSamples(Context context) {
        super(context);
    }

    // 异步断点上传，不设置记录保存路径，只在本次上传内做断点续传
    public void resumableUpload() {
        // 创建断点上传请求
        ResumableUploadRequest request = new ResumableUploadRequest(testBucket, testObject, uploadFilePath);
        // 设置上传过程回调
        request.setProgressCallback(new OSSProgressCallback<ResumableUploadRequest>() {
            @Override
            public void onProgress(ResumableUploadRequest request, long currentSize, long totalSize) {
                L.d("resumableUpload", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });
        // 异步调用断点上传
        OSSAsyncTask resumableTask = oss.asyncResumableUpload(request, new OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult>() {
            @Override
            public void onSuccess(ResumableUploadRequest request, ResumableUploadResult result) {
                L.d("resumableUpload", "success!");
            }

            @Override
            public void onFailure(ResumableUploadRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    L.e("ErrorCode", serviceException.getErrorCode());
                    L.e("RequestId", serviceException.getRequestId());
                    L.e("HostId", serviceException.getHostId());
                    L.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });

        resumableTask.waitUntilFinished();
    }

    // 异步断点上传，设置记录保存路径，即使任务失败，下次启动仍能继续
    public void resumableUploadWithRecordPathSetting(String uploadFilePath, final SimUploadCallback uploadCallback) {

        if (StringUtils.isEmpty(uploadFilePath)) {
            L.d("resumableUpload", "uploadFilePath: " + uploadFilePath);
            return;
        }

        this.uploadFilePath = uploadFilePath;

        String name = uploadFilePath.substring(uploadFilePath.lastIndexOf("/") + 1);

        testObject = objectDir + "/video/" + name;

        String recordDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/oss_record/";
        L.d("resumableUpload", "recordDirectory: " + recordDirectory + ",testObject:" + testObject);
        File recordDir = new File(recordDirectory);

        // 要保证目录存在，如果不存在则主动创建
        if (!recordDir.exists()) {
            L.d("resumableUpload", "要保证目录存在，如果不存在则主动创建: ");
            recordDir.mkdirs();
        }

        // 创建断点上传请求，参数中给出断点记录文件的保存位置，需是一个文件夹的绝对路径
        ResumableUploadRequest request = new ResumableUploadRequest(testBucket, testObject, uploadFilePath, recordDirectory);
        L.d("resumableUpload", "testObject: " + testObject);
        // 设置上传过程回调
        request.setProgressCallback(new OSSProgressCallback<ResumableUploadRequest>() {
            @Override
            public void onProgress(ResumableUploadRequest request, long currentSize, long totalSize) {
//                L.d("resumableUpload", "currentSize: " + currentSize + " totalSize: " + totalSize);
                if (uploadCallback != null) {
                    uploadCallback.onProgress(request, currentSize, totalSize);
                }
            }
        });


        resumableTask = oss.asyncResumableUpload(request, new OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult>() {
            @Override
            public void onSuccess(ResumableUploadRequest request, ResumableUploadResult result) {
                L.d("resumableUpload", "success!" + "  thread:" + Thread.currentThread().getName());
                if (uploadCallback != null) {
                    uploadCallback.onSuccess(request, result);
                }
            }

            @Override
            public void onFailure(ResumableUploadRequest request, ClientException clientExcepion, ServiceException serviceException) {
                L.d("resumableUpload", "onFailure!" + "  onFailure:");
                if (uploadCallback != null) {
                    uploadCallback.onFailure(request, clientExcepion, serviceException);
                }
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    L.d("clientExcepion:", clientExcepion.getMessage());
                    L.d("clientExcepion:", clientExcepion.getLocalizedMessage());
                    clientExcepion.printStackTrace();

                }
                if (serviceException != null) {
                    // 服务异常
                    L.d("resumableUpload:", serviceException.getErrorCode());
                    L.d("resumableUpload:", serviceException.getRequestId());
                    L.d("resumableUpload:", serviceException.getHostId());
                    L.d("resumableUpload:", serviceException.getRawMessage());
                }
            }
        });
//        resumableTask.waitUntilFinished();
    }

    public void stop() {
        if (resumableTask != null && !resumableTask.isCanceled()) {
            resumableTask.cancel();
        }
    }

    public OSSAsyncTask getResumableTask() {
        return resumableTask;
    }

}
