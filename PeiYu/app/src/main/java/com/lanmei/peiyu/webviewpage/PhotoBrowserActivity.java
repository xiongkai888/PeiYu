package com.lanmei.peiyu.webviewpage;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lanmei.peiyu.R;
import com.xson.common.utils.L;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;


/**
 * webview 的图片点击预览
 */

public class PhotoBrowserActivity extends Activity implements View.OnClickListener {
    private ImageView crossIv;
    private ViewPager mPager;
    private ImageView centerIv;
    private TextView photoOrderTv;
    private boolean isHide;//是否隐藏显示的图片数量 false为不隐藏，true为隐藏
    private TextView saveTv;
    private String curImageUrl = "";
    private List<String> imageUrls = new ArrayList<>();

    private int curPosition = -1;
    private int[] initialedPositions = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setContentView(R.layout.activity_photo_browser);
        Intent intent = getIntent();
        imageUrls = (List<String>)intent.getSerializableExtra("imageUrls");
        curImageUrl = intent.getStringExtra("curImageUrl");
        isHide = intent.getBooleanExtra("hide",false);
        initialedPositions = new int[imageUrls.size()];
        initInitialedPositions();

        photoOrderTv = (TextView) findViewById(R.id.photoOrderTv);
        if (isHide){
            photoOrderTv.setVisibility(View.GONE);
        }
        saveTv = (TextView) findViewById(R.id.saveTv);
        saveTv.setOnClickListener(this);
        centerIv = (ImageView) findViewById(R.id.centerIv);
        crossIv = (ImageView) findViewById(R.id.crossIv);
        crossIv.setOnClickListener(this);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        mPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageUrls.size();
            }


            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                if (!com.xson.common.utils.StringUtils.isEmpty(imageUrls.get(position))) {
                    final com.bm.library.PhotoView view = new com.bm.library.PhotoView(PhotoBrowserActivity.this);
                    view.enable();
                    view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    Glide.with(PhotoBrowserActivity.this).load(imageUrls.get(position)).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).fitCenter().crossFade().listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            if (position == curPosition) {
                                hideLoadingAnimation();
                            }
                            showErrorLoading();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            occupyOnePosition(position);
                            if (position == curPosition) {
                                hideLoadingAnimation();
                            }
                            return false;
                        }
                    }).into(view);

                    container.addView(view);
                    return view;
                }
                return null;
            }


            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                releaseOnePosition(position);
                container.removeView((View) object);
            }

        });

        curPosition = returnClickedPosition() == -1 ? 0 : returnClickedPosition();
        mPager.setCurrentItem(curPosition);
        mPager.setTag(curPosition);
        if (initialedPositions[curPosition] != curPosition) {//如果当前页面未加载完毕，则显示加载动画，反之相反；
            showLoadingAnimation();
        }
        photoOrderTv.setText((curPosition + 1) + "/" + imageUrls.size());//设置页面的编号

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (initialedPositions[position] != position) {//如果当前页面未加载完毕，则显示加载动画，反之相反；
                    showLoadingAnimation();
                } else {
                    hideLoadingAnimation();
                }
                curPosition = position;
                photoOrderTv.setText((position + 1) + "/" + imageUrls.size());//设置页面的编号
                mPager.setTag(position);//为当前view设置tag
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private int returnClickedPosition() {
        if (imageUrls == null || curImageUrl == null) {
            return -1;
        }
        for (int i = 0; i < imageUrls.size(); i++) {
            if (curImageUrl.equals(imageUrls.get(i))) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.crossIv:
                finish();
                break;
            case R.id.saveTv:
                savePhotoToLocal();
                break;
            default:
                break;
        }
    }

    private void showLoadingAnimation() {
        centerIv.setVisibility(View.VISIBLE);
        centerIv.setImageResource(R.mipmap.loading);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(centerIv, "rotation", 0f, 360f);
        objectAnimator.setDuration(2000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.start();
    }

    private void hideLoadingAnimation() {
        releaseResource();
        centerIv.setVisibility(View.GONE);
    }

    private void showErrorLoading() {
        centerIv.setVisibility(View.VISIBLE);
        releaseResource();
        centerIv.setImageResource(R.mipmap.load_error);
    }

    private void releaseResource() {
        if (centerIv.getAnimation() != null) {
            centerIv.getAnimation().cancel();
        }
    }

    private void occupyOnePosition(int position) {
        initialedPositions[position] = position;
    }

    private void releaseOnePosition(int position) {
        initialedPositions[position] = -1;
    }

    private void initInitialedPositions() {
        for (int i = 0; i < initialedPositions.length; i++) {
            initialedPositions[i] = -1;
        }
    }

    private void savePhotoToLocal() {
        ViewGroup containerTemp = (ViewGroup) mPager.findViewWithTag(mPager.getCurrentItem());
        if (containerTemp == null) {
            return;
        }
        PhotoView photoViewTemp = (PhotoView) containerTemp.getChildAt(0);
        if (photoViewTemp != null) {
            GlideBitmapDrawable glideBitmapDrawable = (GlideBitmapDrawable) photoViewTemp.getDrawable();
            if (glideBitmapDrawable == null) {
                return;
            }
            Bitmap bitmap = glideBitmapDrawable.getBitmap();
            if (bitmap == null) {
                return;
            }
            FileUtils.savePhoto(this, bitmap, new FileUtils.SaveResultCallback() {
                @Override
                public void onSavedSuccess(String path) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PhotoBrowserActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onSavedFailed() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PhotoBrowserActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        releaseResource();
        L.fixInputMethodManagerLeak(this);
        super.onDestroy();
    }
}
