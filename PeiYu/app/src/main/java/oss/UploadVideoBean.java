package oss;

/**
 * @author xkai
 *  上传视频
 */
public class UploadVideoBean {

    private String title;//上传视频标题
    private String pic;//上传视频封面
    private String path;//上传视频本地路径
    private String status;//状态 正在上传、等待上传、暂停、完成
    private boolean isEdit;//是否选中
    private int progress;//进度
    private String cid;//分类

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCid() {
        return cid;
    }

    public String getTitle() {
        return title;
    }

    public String getPic() {
        return pic;
    }

    public String getStatus() {
        return status;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public int getProgress() {
        return progress;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

}