package com.photoselector.ui;
/**
 * 
 * @author Aizaz AZ
 *
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.photoselector.R;
import com.photoselector.model.PhotoModel;
import com.photoselector.util.CommonUtils;
import com.polites.GestureImageView;

import java.io.File;

public class PhotoPreview extends LinearLayout implements OnClickListener {

	public static final int ISBROWSE=0;
	public static final int ISPREVIEW=1;
	private final Context context;
	private ProgressBar pbLoading;
	private GestureImageView ivContent;
	private OnClickListener l;
	private int come;

	public PhotoPreview(Context context) {
		super(context);
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.view_photopreview, this, true);

		pbLoading = (ProgressBar) findViewById(R.id.pb_loading_vpp);
		ivContent = (GestureImageView) findViewById(R.id.iv_content_vpp);
		ivContent.setOnClickListener(this);
	}

	public PhotoPreview(Context context, AttributeSet attrs, int defStyle) {
		this(context);
	}

	public PhotoPreview(Context context, AttributeSet attrs) {
		this(context);
	}

	public void loadImage(PhotoModel photoModel) {
		if (photoModel.getOriginalPath().contains("http://")||
				photoModel.getOriginalPath().contains("https://")
				){
			//loadImage(photoModel.getOriginalPath());
			Glide.with(context).load(photoModel.getOriginalPath()).dontAnimate().override(360,600).into(ivContent);
		}else {
			Glide.with(context).load(new File(photoModel.getOriginalPath())).dontAnimate().override(360,600).into(ivContent);
			//loadImage("file://" + photoModel.getOriginalPath(),photoModel.getOriginalPath());
		}
	}

	private void loadImage(final String path,final String path2) {
		Log.e("loadImage+","loadImage="+path);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		imageLoader.loadImage(path,new SimpleImageLoadingListener() {
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				loadedImage = CommonUtils.readPictureDegree(path2, loadedImage);
				ivContent.setImageBitmap(loadedImage);
				pbLoading.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				ivContent.setImageDrawable(getResources().getDrawable(R.drawable.ic_picture_loadfailed));
				pbLoading.setVisibility(View.GONE);
			}
		});
	}
	private void loadImage(final String path) {
		Log.e("loadImage+","loadImage="+path);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		imageLoader.getInstance().loadImage(path, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				Log.e("loadImage++","onLoadingComplete path="+path);
				ivContent.setImageBitmap(loadedImage);
				pbLoading.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				Log.e("loadImage++","onLoadingFailed failReason="+failReason.getCause().getMessage());
				ivContent.setImageDrawable(getResources().getDrawable(R.drawable.ic_picture_loadfailed));
				pbLoading.setVisibility(View.GONE);
			}
		});
	}



	@Override
	public void setOnClickListener(OnClickListener l) {
		this.l = l;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.iv_content_vpp && l != null)
			l.onClick(ivContent);
	};

	public void setCome(int come) {
		this.come = come;
		switch (come){
			case 0 :
				findViewById(R.id.layout).setBackgroundColor(getResources().getColor(R.color.bg_black));
				break;
			case 1 :
				findViewById(R.id.layout).setBackgroundColor(getResources().getColor(R.color.bg_white));
				break;
		}
	}

}