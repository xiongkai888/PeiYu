package oss.callback;

/**
 * Created by wk on 2017/11/29.
 */

public interface UploadCallback<Request,Result> extends ProgressCallback<Request,Result> {

    public void onPerpare(String name, String uploadId);


}
