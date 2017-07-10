package com.photoselector.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.photoselector.R;
import com.photoselector.model.PhotoModel;

import java.io.IOException;

/**
 * @author Aizaz AZ
 *
 */

public class PhotoItem extends LinearLayout implements OnCheckedChangeListener,
		OnLongClickListener {

	private ImageView ivPhoto;
	private CheckBox cbPhoto;
	private onPhotoItemCheckedListener listener;
	private PhotoModel photo;
	private boolean isCheckAll;
	private onItemClickListener l;
	private int position;
	private int size;
	private PhotoSelectorAdapter adapter;

	private PhotoItem(Context context) {
		super(context);
	}

	public PhotoItem(Context context, onPhotoItemCheckedListener listener, PhotoSelectorAdapter photoSelectorAdapter) {
		this(context);
		LayoutInflater.from(context).inflate(R.layout.layout_photoitem, this,
				true);
		this.listener = listener;
		this.adapter = photoSelectorAdapter;
		setOnLongClickListener(this);

		ivPhoto = (ImageView) findViewById(R.id.iv_photo_lpsi);
		cbPhoto = (CheckBox) findViewById(R.id.cb_photo_lpsi);
		ivPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (cbPhoto.isChecked())
					cbPhoto.setChecked(false);
				else
					cbPhoto.setChecked(true);
				cbPhotoOnClick(cbPhoto,cbPhoto.isChecked());
			}
		});
//		cbPhoto.setOnCheckedChangeListener(this); // CheckBoxѡ��״̬�ı������
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		cbPhotoOnClick(buttonView, isChecked);

	}

	private void cbPhotoOnClick(CompoundButton buttonView, boolean isChecked) {
		if (!isCheckAll) {
			listener.onCheckedChanged(photo, buttonView, isChecked); // ����������ص�����
		}
		// ��ͼƬ�䰵���߱���
		if (adapter.getSize()<=8) {
//			if (isChecked) {
//				setDrawingable();
//				ivPhoto.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
//			} else {
//				ivPhoto.clearColorFilter();
//			}
			photo.setChecked(isChecked, "photoonchange"+adapter.getSize());
		}
	}

	/** ����·���µ�ͼƬ��Ӧ������ͼ */
	public void setImageDrawable(final PhotoModel photo) {
		this.photo = photo;
		loadImage("file://" + photo.getOriginalPath(),photo.getOriginalPath());
//		ImageLoader.getInstance().displayImage(
//				"file://" + photo.getOriginalPath(), ivPhoto);
	}
	private void loadImage( final  String path,final String bimop) {
		ImageLoader.getInstance().loadImage(path, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				loadedImage = readPictureDegree(bimop, loadedImage);
				ivPhoto.setImageBitmap(loadedImage);
			}
			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				ivPhoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_picture_loadfailed));
			}
		});
	}

	/**
	 读取照片exif信息中的旋转角度
	 * @param path 照片路径
	 * @return角度
	 *  这个方法是 对选取的照片(如果照片有旋转)在处理的
	 */
	public static Bitmap readPictureDegree(String path,Bitmap bitmap){
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					return toturn(bitmap,90);
				case ExifInterface.ORIENTATION_ROTATE_180:
					return toturn(bitmap,180);
				case ExifInterface.ORIENTATION_ROTATE_270:
					return toturn(bitmap,270);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return bitmap;
		}
		return bitmap;
	}
	public static Bitmap toturn(Bitmap img,int d){
		Matrix matrix = new Matrix();
		matrix.postRotate(d); /*翻转90度*/
		int width = img.getWidth();
		int height =img.getHeight();
		img = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
		return img;
	}

	private void setDrawingable() {
		ivPhoto.setDrawingCacheEnabled(true);
		ivPhoto.buildDrawingCache();
	}


	@Override
	public void setSelected(boolean selected) {
		if (photo == null) {
			return;
		}
		isCheckAll = true;
		cbPhoto.setChecked(selected);
		isCheckAll = false;
	}

	public void setOnClickListener(onItemClickListener l, int position, PhotoSelectorAdapter s) {
		this.l = l;
		this.adapter=s;
		this.position = position;
	}


	// @Override
	// public void
	// onClick(View v) {
	// if (l != null)
	// l.onItemClick(position);
	// }

	/** ͼƬItemѡ���¼������� */
	public static interface onPhotoItemCheckedListener {
		public void onCheckedChanged(PhotoModel photoModel,
									 CompoundButton buttonView, boolean isChecked);
	}

	/** ͼƬ����¼� */
	public interface onItemClickListener {
		public void onItemClick(int position);
	}

	@Override
	public boolean onLongClick(View v) {
//		if (l != null)
//			l.onItemClick(position);
		return true;
	}

}
