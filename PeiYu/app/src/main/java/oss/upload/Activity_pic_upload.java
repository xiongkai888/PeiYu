package oss.upload;

import android.os.Bundle;

import com.xson.common.app.BaseActivity;

public class Activity_pic_upload extends BaseActivity {

//
//    @InjectView(R.id.toolbar)
//    CenterTitleToolbar toolbar;
//    @InjectView(R.id.list)
//    LRecyclerView mRecyclerView;
//
//    String TAG="Activity_pic_upload";
//
//    public static final String KEY_id="KEY_id";
//    public static final String KEY_type="KEY_type";
//    public static final int REQUEST_CODE_head=2;
//    public static final int REQUEST_CODE_goods_pic=3;
//    public static final int REQUEST_CODE_goods_logo=4;
//    public static final int REQUEST_CODE_shop=5;
//
//
//    public int type;
//
//    private ItemTouchHelper mItemTouchHelper;
//
//    private int goodsId;
//
//    private ArrayList<String> dataList;
//    public static final String addImg=""+ R.drawable.ease_type_select_btn;
//
//
//
//    private ManageOssUpload manageOssUpload;
//    private PhotoSelectActivityResult<Activity_pic_upload> photoSelectActivityResult;
//
//    @Override
//    public int getContentViewId() {
//        return R.layout.activity_single_listview;
//    }
//
//    @Override
//    protected void initAllMembersView(Bundle savedInstanceState) {
//        init();
//        initBar(toolbar,"上传",0);
//        initUi();
//    }
//
//    @Override
//    public int setMenu() {
//        return R.menu.menu_upload;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.action_upload:
//                uploadImg();
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // TODO: add setContentView(...) invocation
//        ButterKnife.bind(this);
//
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        initIntent(intent);
//    }
//
//    private void init(){
//        initIntent(getIntent());
//
//        dataList = new ArrayList<>();
//        manageOssUpload=new ManageOssUpload(this);
//        photoSelectActivityResult=new PhotoSelectActivityResult<>(this,this);
//    }
//    private void initIntent(Intent intent){
//        if (intent!=null){
//            goodsId=intent.getIntExtra(KEY_id,0);
//            type=intent.getIntExtra(KEY_type,0);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        photoSelectActivityResult.photoSelectActivityResult(uploadImgFile,requestCode, resultCode, data);
//    }
//
//
//
//    private Handler mHandler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case 1:
//                    referGoodsPic((ArrayList<String>) msg.obj);
//                    break;
//                case 2:
//                    StaticMethod.showInfo(Activity_pic_upload.this,"图片上传失败："+msg.obj);
//                    mDataAdapter.remove(msg.arg1);
//                    break;
//
//                case 3://头像oss上传成功
//                    referUserPic(msg.obj.toString());
//                    break;
//                case 4:
//                    StaticMethod.showInfo(Activity_pic_upload.this,"头像上传失败："+msg.obj);
//                    mDataAdapter.remove(msg.arg1);
//                    break;
//            }
//        }
//    };
//
//    public void initUi() {
//
//        mRecyclerView = (LRecyclerView) findViewById(R.id.list);
//
//        //setLayoutManager must before setAdapter
////        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
//
//
//        dataList.add(addImg);
//
//        mItemTouchHelper = new ItemTouchHelper(mCallback);
//        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
//
//        mDataAdapter = new Adapter_Img_Del(this);
//        mDataAdapter.setOnPicListener(this);
//        mDataAdapter.setDataList(dataList);
//
//        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mDataAdapter);
//
//        mRecyclerView.setAdapter(mLRecyclerViewAdapter);
//
//
//        DividerDecoration divider = new DividerDecoration.Builder(this)
//                .setHeight(R.dimen.divider)
////                .setPadding(R.dimen.default_divider_padding)
//                .setColorResource(R.color.divider)
//                .build();
//        mRecyclerView.addItemDecoration(divider);
//        mRecyclerView.setHasFixedSize(true);
//
//
////        mLRecyclerViewAdapter.addHeaderView(new SampleHeader(this));
////
////        //add a FooterView
////        mLRecyclerViewAdapter.addFooterView(new SampleFooter(this));
//
//        //禁用下拉刷新功能
////        mRecyclerView.setPullRefreshEnabled(false);
//
//        //禁用自动加载更多功能
//        mRecyclerView.setLoadMoreEnabled(false);
//
//        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                mDataAdapter.clear();
//                mLRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
//                requestInfo();
//
//            }
//        });
//
//
//        switch (type){
//            case REQUEST_CODE_shop:
//            case REQUEST_CODE_goods_logo:
//            case REQUEST_CODE_goods_pic:
//                mRecyclerView.refresh();
//                break;
//
//        }
//    }
//
//    public void requestInfo(){
//
//        switch (type){
//            case REQUEST_CODE_shop:
//                requestUserInfo();
//                break;
//            case REQUEST_CODE_goods_logo:
//            case REQUEST_CODE_goods_pic:
//                requestGoodsInfo();
//                break;
//
//            case REQUEST_CODE_head:
//                dataList.add(addImg);
//                mDataAdapter.addAll(dataList);
//                mDataAdapter.notifyDataSetChanged();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mRecyclerView.refreshComplete(RequestUrl.PAGE_SIZE);
//                    }
//                }, 1000);
//
//                break;
//
//        }
//
//    }
//
//
//
//
//    @Override
//    public void uploadImgResult(String urlImg) {
//
//        mDataAdapter.addFirst("file://"+urlImg);
//
//    }
//
//    @Override
//    public void backFinish() {
//        setResult(type,null);
//        super.backFinish();
//    }
//
//    private ItemTouchHelper.Callback mCallback = new ItemTouchHelper.Callback() {
//
//        @Override
//        public boolean isLongPressDragEnabled() {
//            return true;
//        }
//
//        @Override
//        public boolean isItemViewSwipeEnabled() {
//            return true;
//        }
//
//        @Override
//        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//            int dragFlags = 0, swipeFlags = 0;
//            if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
//                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
//            } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
//                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//                //设置侧滑方向为从左到右和从右到左都可以
//                swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
//            }
//            return makeMovementFlags(dragFlags, swipeFlags);
//        }
//
//        @Override
//        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
//            if (source.getItemViewType() != target.getItemViewType()) {
//                return false;
//            } else {
//                mDataAdapter.onItemDragMoving(source, target,mLRecyclerViewAdapter.getHeaderViewsCount());
//                return true;//返回true表示执行拖动
//            }
//
//        }
//
//        @Override
//        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//            int position = mLRecyclerViewAdapter.getAdapterPosition(true,viewHolder.getAdapterPosition());
//            L.d("","onSwiped position " + position);
//            mDataAdapter.getDataList().remove(position);
//            mDataAdapter.notifyItemRemoved(position);
//        }
//
//        @Override
//        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//            if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
//                //滑动时改变Item的透明度
//                final float alpha = 1 - Math.abs(dX) / (float)viewHolder.itemView.getWidth();
//                viewHolder.itemView.setAlpha(alpha);
//                viewHolder.itemView.setTranslationX(dX);
//            }
//        }
//    };
//
//
//    @Override
//    public void onItemPicClick(int position, String pic) {
//
//    }
//
//    /**删除*/
//    @Override
//    public void onItemPicDel(int position, String pic) {
//        L.d(TAG,pic+"");
//        if (pic.startsWith("file")){
//            mDataAdapter.remove(position);
//
//        }else if (pic.startsWith("http")){
//            delPicOss(pic,position);
//        }
//
//    }
//
//    @Override
//    public void onPicAdd() {
//        switch (type){
//            case REQUEST_CODE_shop:
//            case REQUEST_CODE_goods_pic:
//                break;
//            case REQUEST_CODE_goods_logo:
//                mDataAdapter.clear();
//                mDataAdapter.addFirst(addImg);
//                break;
//            case REQUEST_CODE_head:
//                mDataAdapter.clear();
//                mDataAdapter.addFirst(addImg);
//                break;
//        }
//        loadImg();
//    }
//
//
//    private void loadImg(){
//        new SelectPicPopupWindow(this, new SelectPicPopupWindow.PicPopupListener() {
//            @Override
//            public void takePhone(Button v) {
//                selectTakePhone();
//            }
//
//            @Override
//            public void pickPhone(Button v) {
//                selectPickPhone();
//            }
//        }).showPopupWindow(mRecyclerView);
//    }
//
//    File uploadImgFile;
//    /**相册*/
//    private void selectPickPhone(){
//        String uploadImgName = getImgName();
//        File dir;
//        try {
//            dir= SDCardUtils.getDirFile(Enum_Dir.DIR_img);
//        } catch (CustomExce customExce) {
//            customExce.printStackTrace();
//            StaticMethod.showInfo(this,"没有存储权限");
//            return;
//        }
//        uploadImgFile= new File(dir, uploadImgName);
//        Intent intent = new Intent(Intent.ACTION_PICK, null);
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                PhotoSelectActivityResult.IMAGE_UNSPECIFIED);
//        this.startActivityForResult(intent, PhotoSelectActivityResult.PHOTO_PICK);
//    }
//
//    /**拍照*/
//    private void selectTakePhone(){
//        String uploadImgName = getImgName();
//        File dir;
//        try {
//            dir= SDCardUtils.getDirFile(Enum_Dir.DIR_img);
//        } catch (CustomExce customExce) {
//            customExce.printStackTrace();
//            StaticMethod.showInfo(this,"没有存储权限");
//            return;
//        }
//        uploadImgFile = new File(dir, uploadImgName);
//        /** getUriForFile(Context context, String authority, File file):此处的authority需要和manifest里面保持一致。
//         *   photoURI打印结果：content://cn.lovexiaoai.myapp.fileprovider/camera_photos/Pictures/Screenshots/testImg.png 。
//         *   这里的camera_photos:对应filepaths.xml中的name
//         */
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Uri photoURI = FileProvider.getUriForFile(this, this.getPackageName()+".fileprovider", uploadImgFile);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
//
//        } else {
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(uploadImgFile));
//        }
//
//        startActivityForResult(intent, PhotoSelectActivityResult.PHOTO_GRAPH);
//    }
//
//    private String getImgName(){
//        return System.currentTimeMillis()+".png";
//    }
//
//    /**上传*/
//    public void uploadImg(){
//        showProgressDf();
//        switch (type){
//            case REQUEST_CODE_shop:
//            case REQUEST_CODE_goods_logo:
//            case REQUEST_CODE_goods_pic:
//                uploadServicePic();
//                break;
//            case REQUEST_CODE_head:
//                uploadUserPic();
//                break;
//            default:
//                closeProgressDf();
//        }
//
//
//
//
//
//
//    }
//
//    /**上传图片*/
//    private void uploadServicePic(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                List<String> imgsList = mDataAdapter.getDataList();
//                ArrayList<String> imgsListResult =new ArrayList<String>();
//                for (int i=0;i<imgsList.size();i++){
//                    String picPath=imgsList.get(i);
//                    if (picPath.startsWith("file")){
//                        String urlPic= manageOssUpload.uploadFile_img(getPicPath(picPath));
//                        if (urlPic==null){
//                            Message msg=mHandler.obtainMessage();
//                            msg.what=2;
//                            msg.arg1=i;
//                            msg.obj=picPath;
//                            mHandler.sendMessage(msg);
//                        }else {
//                            imgsListResult.add(urlPic);
//                        }
//
//                    }else if (picPath.startsWith("http")){
//                        imgsListResult.add(picPath);
//
//                    }
//                }
//                L.d(TAG,"阿里云上传完毕：");
//
//                Message msg=mHandler.obtainMessage();
//                msg.what=1;
//                msg.obj=imgsListResult;
//                mHandler.sendMessage(msg);
//            }
//        }).start();
//    }
//
//    /**上传头像*/
//    private void uploadUserPic(){
//        if (mDataAdapter.getItemCount()<2){
//            StaticMethod.showInfo(this,"请添加图片");
//            closeProgressDf();
//            return;
//        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String path= mDataAdapter.getDataList().get(0);
//                String urlPic= manageOssUpload.uploadFile_img(getPicPath(path));
//                if (urlPic==null){
//                    Message msg=mHandler.obtainMessage();
//                    msg.what=4;
//                    msg.obj=urlPic;
//                    mHandler.sendMessage(msg);
//                }else {
//                    Message msg=mHandler.obtainMessage();
//                    msg.what=3;
//                    msg.obj=urlPic;
//                    mHandler.sendMessage(msg);
//                }
//
//            }
//        }).start();
//
//
//    }
//
//    private String getPicPath(String sdPath){
//        return sdPath.substring(7);
//
//    }
//
//    /**删除oss图片*/
//    private void delPicOss(String picUrl, final int position){
//
//        OSSCompletedCallback<DeleteObjectRequest, DeleteObjectResult> ossDelCallback = new OSSCompletedCallback<DeleteObjectRequest, DeleteObjectResult>() {
//            @Override
//            public void onSuccess(DeleteObjectRequest request, DeleteObjectResult result) {
//                Log.d("异步删除", "success!：" + result.getUrl());
//                mDataAdapter.remove(position);
//                uploadImg();
//
//            }
//
//            @Override
//            public void onFailure(DeleteObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
//                StaticMethod.showInfo(Activity_pic_upload.this,"图片删除失败");
//
//                // 请求异常
//                if (clientExcepion != null) {
//                    // 本地异常如网络异常等
//                    clientExcepion.printStackTrace();
//                }
//                if (serviceException != null) {
//                    // 服务异常
//                    Log.e("ErrorCode", serviceException.getErrorCode());
//                    Log.e("RequestId", serviceException.getRequestId());
//                    Log.e("HostId", serviceException.getHostId());
//                    Log.e("RawMessage", serviceException.getRawMessage());
//                }
//            }
//
//        };
//
//        manageOssUpload.delOssPic(picUrl,ossDelCallback);
//    }
//
//
//
//    /**提交商品图片*/
//    public void referGoodsPic(final ArrayList<String> picList){
//        if (goodsId==0){
//            closeProgressDf();
//            Intent intent=new Intent();
//            intent.putStringArrayListExtra("data",picList);
//            Activity_pic_upload.this.setResult(type,intent);
//            Activity_pic_upload.this.finish();
//            return;
//        }
//        RequestUrl requestParams=new RequestUrl(RequestPath.PATH_goods_edit);
//        requestParams.addParam("uid", MyApplication.getUid());
//        requestParams.addParam("id", goodsId);
//        if (type==REQUEST_CODE_goods_logo){
//            if (picList.size()>0){
//                if (picList.get(0).startsWith("http")){
//                    requestParams.addParam("img", picList.get(0));
//                }
//            }
//
//        }else if (type==REQUEST_CODE_goods_pic){
//            for (int i=0;i<picList.size();i++){
//                if (picList.get(i).startsWith("http"))
//                    requestParams.addParam("pic[" +i+"]", picList.get(i));
//            }
//        }else if (type==REQUEST_CODE_shop){
//            requestParams=new RequestUrl(RequestPath.PATH_shop_update);
//            requestParams.addParam("uid", MyApplication.getUid());
//            requestParams.addParam("mid", MyApplication.getUid());
//            requestParams.addParam("id", shopId);
//            requestParams.addParam("shop", MyApplication.getUserType());
//            String pic="";
//            for (int i=0;i<picList.size();i++){
//                if (picList.get(i).startsWith("http"))
//                    pic+=picList.get(i)+",";
//            }
//            requestParams.addParam("pic", pic);
//        }
//
//
//        new DataFromServer().request(requestParams, new DataServer.RequestResultListener<Bean<String>>() {
//            @Override
//            public void successful(Bean<String> bean) {
//                closeProgressDf();
//                StaticMethod.showInfo(Activity_pic_upload.this,"图片提交："+bean.getInfo());
//                Intent intent=new Intent();
//                intent.putStringArrayListExtra("data",picList);
//
//                Activity_pic_upload.this.setResult(type,intent);
//                Activity_pic_upload.this.finish();
//            }
//
//            @Override
//            public void fauil(int code, String msg) {
//                super.fauil(code, msg);
//                StaticMethod.showInfo(Activity_pic_upload.this,"图片提交失败");
//                closeProgressDf();
//            }
//        });
//
//    }
//
//    /**提交个人头像*/
//    public void referUserPic(String picUrl){
//        if (picUrl==null||picUrl.isEmpty()||!picUrl.startsWith("http")){
//            StaticMethod.showInfo(this,"请选择图片");
//            return;
//        }
//        RequestUrl requestParams=new RequestUrl(RequestPath.ACTION_Member_update);
//        requestParams.addParam("uid", MyApplication.getUid());
//        requestParams.addParam("pic",picUrl);
//
//        new DataFromServer().request(requestParams, new DataServer.RequestResultListener<Bean<String>>() {
//            @Override
//            public void successful(Bean<String> bean) {
//                closeProgressDf();
//                L.d(TAG,"提交个人头像"+bean.getInfo()+":"+bean.getInfo());
//                StaticMethod.showInfo(Activity_pic_upload.this,""+bean.getInfo());
//                backFinish();
//            }
//
//            @Override
//            public void fauil(int code, String msg) {
//                super.fauil(code, msg);
//                StaticMethod.showInfo(Activity_pic_upload.this,msg);
//                closeProgressDf();
//            }
//        });
//
//    }
//
//
//    /**商品资料*/
//    public void requestGoodsInfo(){
//        if (goodsId==0){
//            dataList.add(addImg);
//            mDataAdapter.addAll(dataList);
//            mDataAdapter.notifyDataSetChanged();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mRecyclerView.refreshComplete(RequestUrl.PAGE_SIZE);
//                }
//            }, 1000);
//
//            return;
//        }
//        RequestUrl api = new RequestUrl(RequestPath.PATH_goods_detail);
//        api.addParam("uid", MyApplication.getUid());
//        api.addParam("id",goodsId);
//        new DataFromServer().request(api, new DataServer.RequestResultListener<Bean<String>>() {
//            @Override
//            public void successful(Bean<String> bean) {
//                L.d("", bean.getData() + "");
//                dataList.clear();
//                JSONObject json = null;
//                try {
//                    json = new JSONObject(bean.getData());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                if (type==REQUEST_CODE_goods_logo){
//                    try {
//                        dataList.add(json.getString("img"));
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }else if (type==REQUEST_CODE_goods_pic){
//                    JSONArray pics = null;
//                    try {
//                        pics = json.getJSONArray("imgs");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    if (pics!=null){
//                        for (int i=0;i<pics.length();i++){
//                            try {
//                                dataList.add(pics.getString(i));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//                dataList.add(addImg);
//                mDataAdapter.addAll(dataList);
//                mDataAdapter.notifyDataSetChanged();
//                mRecyclerView.refreshComplete(RequestUrl.PAGE_SIZE);
//            }
//
//            @Override
//            public void fauil(int code, String msg) {
//                super.fauil(code, msg);
//                mRecyclerView.refreshComplete(RequestUrl.PAGE_SIZE);
//            }
//        });
//
//
//    }
//
//    /**商店资料*/
//    int shopId;
//    public void requestUserInfo(){
//        RequestUrl api = new RequestUrl(RequestPath.PATH_shop_update);
//        api.addParam("uid", MyApplication.getUid());
//        api.addParam("mid",MyApplication.getUid());
//        api.addParam("shop",MyApplication.getUserType());
//
//
//        new DataFromServer().request(api, new DataServer.RequestResultListener<Bean<String>>() {
//            @Override
//            public void successful(Bean<String> bean) {
//                L.d("", bean.getData() + "");
//                dataList.clear();
//                JSONObject json = null;
//                try {
//                    json = new JSONObject(bean.getData());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    shopId=json.getInt("id");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                if (type==REQUEST_CODE_shop){//商家图
//
//                    String[] pics=new String[]{};
//                    try {
//                        pics = json.getString("pic").split(",");
//                        for (int i=0;i<pics.length;i++){
//                            dataList.add(pics[i]);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//
//                }
//                dataList.add(addImg);
//                mDataAdapter.addAll(dataList);
//                mDataAdapter.notifyDataSetChanged();
//                mRecyclerView.refreshComplete(RequestUrl.PAGE_SIZE);
//            }
//
//            @Override
//            public void fauil(int code, String msg) {
//                super.fauil(code, msg);
//                mRecyclerView.refreshComplete(RequestUrl.PAGE_SIZE);
//            }
//        });
//
//
//    }

    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

    }
}
