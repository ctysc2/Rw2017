package com.home.rw.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.home.rw.R;
import com.home.rw.application.App;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;

import java.io.InputStream;

/**
 * Created by cty on 2017/2/22.
 */

public class ImageHelper {


    private static ImageHelper mInstance;
    private BitmapUtils bitmapUtils;

    public ImageHelper() {
        bitmapUtils = new BitmapUtils(App.getAppContext());
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
    }

    public static class BitmapHelpHolder {
        public static ImageHelper instance = new ImageHelper();
    }

    public static ImageHelper getInstance() {
        if (mInstance == null) {
            mInstance = new ImageHelper();
        }
        return mInstance;
    }

    public void displayWithLoadCallBack(ImageView container, String url, int resID) {
        if (container == null || TextUtils.isEmpty(url)) {
            return;
        }
        bitmapUtils.configDefaultLoadingImage(resID);
        bitmapUtils.configDefaultLoadFailedImage(resID);
        bitmapUtils.display(container, url, new CustomBitmapLoadCallBack());
    }

    public void display(ImageView container, String url) {
        display(container, url, R.drawable.rc_default_portrait);
    }

    public void display(ImageView container, String url, int resID) {
        if (container == null || TextUtils.isEmpty(url)) {
            return;
        }
        bitmapUtils.configDefaultLoadingImage(resID);
        bitmapUtils.configDefaultLoadFailedImage(resID);
//        bitmapUtils.clearCache(url);  //不缓存
        bitmapUtils.display(container, url);
    }

    /**
     * 不清除缓存
     *
     * @param container
     * @param url
     */
    public void displayWithCache(ImageView container, String url) {
        displayWithCache(container, url, R.drawable.rc_default_portrait);
    }

    public void displayWithCache(ImageView container, String url, int resID) {
        if (container == null || TextUtils.isEmpty(url)) {
            return;
        }
        bitmapUtils.configDefaultLoadingImage(resID);
        bitmapUtils.configDefaultLoadFailedImage(resID);
        bitmapUtils.display(container, url);
    }

    public void displayWithoutCache(ImageView container, String url) {
        displayWithoutCache(container, url, R.drawable.rc_default_portrait);
    }

    public void displayWithoutCache(ImageView container, String url, final int resID) {
        if (container == null || TextUtils.isEmpty(url)) {
            return;
        }
        bitmapUtils.configDefaultLoadingImage(resID);
        bitmapUtils.configDefaultLoadFailedImage(resID);
        bitmapUtils.clearCache(url);
        bitmapUtils.display(container, url);
    }

    private static final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(Color.TRANSPARENT);

    /**
     * @param imageView
     * @param bitmap
     */
    private static void fadeInDisplay(ImageView imageView, Bitmap bitmap) {
        final TransitionDrawable transitionDrawable = new TransitionDrawable(
                new Drawable[]{TRANSPARENT_DRAWABLE,
                        new BitmapDrawable(imageView.getResources(), bitmap)});
        imageView.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(500);
    }

    public static class CustomBitmapLoadCallBack extends
            DefaultBitmapLoadCallBack<ImageView> {

        @Override
        public void onLoading(ImageView container, String uri,
                              BitmapDisplayConfig config, long total, long current) {
        }

        @Override
        public void onLoadCompleted(ImageView container, String uri,
                                    Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
            // super.onLoadCompleted(container, uri, bitmap, config, from);
            fadeInDisplay(container, bitmap);
        }

        @Override
        public void onLoadFailed(ImageView container, String uri,
                                 Drawable drawable) {
        }
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param ctx
     * @param resId
     * @return
     */
    public static BitmapDrawable getBitmapDrawable(Context ctx, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = ctx.getResources().openRawResource(resId);
        Bitmap bm = BitmapFactory.decodeStream(is, null, opt);
        BitmapDrawable bd = new BitmapDrawable(ctx.getResources(), bm);
        return bd;
    }

    /**
     * 清除缓存
     */
    public void clearCache() {
        bitmapUtils.clearCache();
    }

    /**
     * 清除指定缓存缓存
     */
    public void clearCache(String url) {
        bitmapUtils.clearCache(url);
    }
}