package com.photoselector.ui;
/**
 * 
 * @author Aizaz AZ
 *
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.photoselector.R;
import com.photoselector.model.PhotoModel;
import com.photoselector.util.AnimationUtil;
import com.photoselector.util.MyPopupWindow;
import com.photoselector.util.ToastView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class BasePhotoBrowseActivity extends Activity implements OnPageChangeListener, OnClickListener {

	private ViewPager mViewPager;
	protected RelativeLayout layoutTop,layoutBottom;
	private ImageView btnBack;
	private TextView tvPercent;
	protected List<PhotoModel> photos;
	protected int current;
	protected Button btnBottomRight;
	private ImageView ivMore;
	private MyPopupWindow menuWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_photobrowse);
		layoutTop = (RelativeLayout) findViewById(R.id.layout_top_app);
		layoutBottom = (RelativeLayout) findViewById(R.id.layout_bottom_app);
		ivMore = (ImageView) findViewById(R.id.ivMore);
		ivMore.setOnClickListener(this);

		btnBack = (ImageView) findViewById(R.id.btn_back_app);
//		btnBack.setTextColor(getResources().getColor(android.R.color.transparent));
//		btnBack.setCompoundDrawablesWithIntrinsicBounds(R.drawable.iconfont_fanhui, 0, 0, 0);
		
		btnBottomRight = (Button)findViewById(R.id.btnBottomRight);
		btnBottomRight.setOnClickListener(this);
		
		tvPercent = (TextView) findViewById(R.id.tv_percent_app);
		mViewPager = (ViewPager) findViewById(R.id.vp_base_app);

		btnBack.setOnClickListener(this);
		mViewPager.setOnPageChangeListener(this);

		overridePendingTransition(R.anim.activity_alpha_action_in, 0); // 渐入效果

		if (getIntent().hasExtra("show")) {
			isShow = true;
		} else {
			layoutBottom.setVisibility(View.GONE);
		}
	}

	boolean isShow;
	/** 绑定数据，更新界面 */
	protected void bindData() {
		//Log.e("bindData","current:"+current+",photosize:"+photos.size());

		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setCurrentItem(current);
	}

	private PagerAdapter mPagerAdapter = new PagerAdapter() {

		@Override
		public int getCount() {
			if (photos == null) {
				return 0;
			} else {
				return photos.size();
			}
		}

		@Override
		public View instantiateItem(final ViewGroup container, final int position) {
			PhotoPreview photoPreview = new PhotoPreview(getApplicationContext());
			photoPreview.setCome(PhotoPreview.ISBROWSE);
			((ViewPager) container).addView(photoPreview);
			Log.e("phtos2+","path="+photos.get(position));
			photoPreview.loadImage(photos.get(position));
			photoPreview.setOnClickListener(photoItemClickListener);
			return photoPreview;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager)container).removeView((View) object);
			Log.e("phtos2+","destroyItem position="+position);

		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}
	};
	protected boolean isUp;

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_back_app)
			finish();
		else if (v.getId() == R.id.ivMore){
			menuWindow = new MyPopupWindow(this,itemsOnClick);
		}
	}

	private View.OnClickListener itemsOnClick = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			menuWindow.dismiss();
			int i = v.getId();
			if (i == R.id.btn_save) {
				loadBlur(photos.get(mViewPager.getCurrentItem()).getOriginalPath());
			}
		}
	};

	private void loadBlur(String path) {
		Glide.with(this).load(path).asBitmap().into(new SimpleTarget<Bitmap>() {
			@Override
			public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
				saveImage(BasePhotoBrowseActivity.this, resource);
			}
		});
	}



	/**
	 * Drawable转化为Bitmap
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;

	}

	public  void saveImage(Context context, Bitmap bmp) {
		// 首先保存图片
		File appDir = new File(Environment.getExternalStorageDirectory(), "MiGuThrumb");
		if (!appDir.exists()) {
			appDir.mkdir();
		}
		String fileName = System.currentTimeMillis() + ".jpg";
		File file = new File(appDir, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
			ToastView.show(this, R.drawable.toast_img, "保存成功", 1000);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 其次把文件插入到系统图库
		try {
			MediaStore.Images.Media.insertImage(context.getContentResolver(),
					file.getAbsolutePath(), fileName, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// 最后通知图库更新
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File("/sdcard/Gk_Image/"+fileName))));
	}

	protected void onRightBtn(int current) {
		
	}
	protected void onBottomRightBtn() {
		
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		//Log.e("onPageSelected","pos:"+arg0);
		current = arg0;
		updatePercent();
	}

	protected void updatePercent() {
		//Log.e("onPageSelected","current + 1:"+(current + 1)+",photos.size():"+photos.size());
		tvPercent.setText((current+ 1) + "/" + photos.size());
	}

	/** 图片点击事件回调 */
	private OnClickListener photoItemClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (!isUp) {
				AnimationUtil anim = new AnimationUtil(getApplicationContext(), R.anim.translate_up)
						.setInterpolator(new LinearInterpolator()).setFillAfter(true);
				anim.startAnimation(layoutTop);
				if (isShow)
					layoutBottom.setVisibility(View.GONE);
				isUp = true;
			} else {
				AnimationUtil anim = new AnimationUtil(getApplicationContext(), R.anim.translate_down_current)
						.setInterpolator(new LinearInterpolator()).setFillAfter(true);
				anim.startAnimation(layoutTop);
				if (isShow)
					layoutBottom.setVisibility(View.VISIBLE);
				isUp = false;
			}
		}
	};
}
