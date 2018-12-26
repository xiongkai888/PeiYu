package oss;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.common.utils.OssUserInfo;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.DeleteObjectRequest;
import com.alibaba.sdk.android.oss.model.DeleteObjectResult;
import com.alibaba.sdk.android.oss.model.ListObjectsRequest;
import com.alibaba.sdk.android.oss.model.ListObjectsResult;
import com.lanmei.peiyu.utils.CommonUtils;
import com.xson.common.utils.L;

/**
 * Created by Administrator on 2017/11/28.
 */

public class BaseOss {
    private OSSCredentialProvider mCredentialProvider;
    protected OSS oss;
    protected String userId;
    protected String objectDir;
    protected String testBucket;
    protected String testObject;
    protected Context context;

    public BaseOss(Context context) {
        this.context = context;
        userId = CommonUtils.getUserId(context);
        testBucket= OssUserInfo.testBucket;
        objectDir="android/"+userId;
        testObject=objectDir;
        initOss();
    }


    public void initOss(){

        if (mCredentialProvider == null || oss == null) {
            /*sts认证*/
//            mCredentialProvider = new OSSAuthCredentialsProvider(OssConfig.STSSERVER);
            mCredentialProvider = new OSSPlainTextAKSKCredentialProvider(OssUserInfo.accessKeyId, OssUserInfo.accessKeySecret);
            ClientConfiguration conf = new ClientConfiguration();
            conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
            conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
            conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
            conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
//            conf.setHttpDnsEnable(false);//默认是true 开启，需要关闭可以设置false

            oss = new OSSClient(context, OssUserInfo.endpoint, mCredentialProvider, conf);

//            initSamples();
        }
    }

    public void setOss(OSS oss) {
        this.oss = oss;
    }

    public void getBucketFiles(){
        ListObjectsRequest listObjects = new ListObjectsRequest(OssUserInfo.testBucket);

        // 设定前缀
        listObjects.setPrefix("android/"+userId);

        // 设置成功、失败回调，发送异步罗列请求
        OSSAsyncTask task = oss.asyncListObjects(listObjects, new OSSCompletedCallback<ListObjectsRequest, ListObjectsResult>() {
            @Override
            public void onSuccess(ListObjectsRequest request, ListObjectsResult result) {
                Log.d("AyncListObjects", "android:Success!");
                for (int i = 0; i < result.getObjectSummaries().size(); i++) {
                    L.d("AyncListObjects", "android: " + result.getObjectSummaries().get(i).getKey());
                }
            }

            @Override
            public void onFailure(ListObjectsRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    L.d("ErrorCode", serviceException.getErrorCode());
                    L.d("RequestId", serviceException.getRequestId());
                    L.d("HostId", serviceException.getHostId());
                    L.d("RawMessage", serviceException.getRawMessage());
                }
            }
        });
        task.waitUntilFinished();
    }


    // 检查文件是否存在
    public boolean checkIsObjectExist(String name) {
        try {
            if (oss.doesObjectExist(testBucket, name)) {
                L.d("doesObjectExist", "object exist.");
                return true;
            } else {
                L.d("doesObjectExist", "object does not exist.");
                return false;
            }
        } catch (ClientException e) {
            // 本地异常如网络异常等
            e.printStackTrace();
        } catch (ServiceException e) {
            // 服务异常
            L.d("ErrorCode", e.getErrorCode());
            L.d("RequestId", e.getRequestId());
            L.d("HostId", e.getHostId());
            L.d("RawMessage", e.getRawMessage());
        }
        return false;
    }

    public void deleteOssFile(String fileOss){
        // Creates the file deletion request
        DeleteObjectRequest delete = new DeleteObjectRequest(testBucket, fileOss);
        // Deletes the file asynchronously.
        OSSAsyncTask deleteTask = oss.asyncDeleteObject(delete, new OSSCompletedCallback<DeleteObjectRequest, DeleteObjectResult>() {
            @Override
            public void onSuccess(DeleteObjectRequest request, DeleteObjectResult result) {
                L.d("asyncCopyAndDelObject", "success!");
                getBucketFiles();
            }

            @Override
            public void onFailure(DeleteObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // request exception
                if (clientExcepion != null) {
                    // client side exception,  such as network exception
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // service side exception
                    L.d("ErrorCode", serviceException.getErrorCode());
                    L.d("RequestId", serviceException.getRequestId());
                    L.d("HostId", serviceException.getHostId());
                    L.d("RawMessage", serviceException.getRawMessage());
                }
            }

        });
        deleteTask.waitUntilFinished();
    }

}
