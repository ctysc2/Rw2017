package com.photoselector.ui;
/**
 * 
 * @author Aizaz AZ
 *
 */

import android.app.Activity;
import android.os.Bundle;
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
import com.photoselector.R;
import com.photoselector.model.PhotoModel;
import com.photoselector.util.AnimationUtil;

import java.util.List;

public class BasePhotoPreviewActivity extends Activity implements OnPageChangeListener, OnClickListener {

	private ViewPager mViewPager;
	protected RelativeLayout layoutTop,layoutBottom;
	private ImageView btnBack;
	private TextView tvPercent;
	protected List<PhotoModel> photos;
	protected int current;
	protected Button btnRight;
	protected Button btnBottomRight;
	private ImageView ivMore;
	private ViewPager mContainer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_photopreview);
		layoutTop = (RelativeLayout) findViewById(R.id.layout_top_app);
		layoutBottom = (RelativeLayout) findViewById(R.id.layout_bottom_app);

		btnBack = (ImageView) findViewById(R.id.btn_back_app);
//		btnBack.setTextColor(getResources().getColor(android.R.color.transparent));
//		btnBack.setCompoundDrawablesWithIntrinsicBounds(R.drawable.iconfont_fanhui, 0, 0, 0);
		ivMore=(ImageView)findViewById(R.id.ivMore);
		btnRight = (Button)findViewById(R.id.btnRight);
		btnRight.setOnClickListener(this);
		btnBottomRight = (Button)findViewById(R.id.btnBottomRight);
		btnBottomRight.setOnClickListener(this);
		
		tvPercent = (TextView) findViewById(R.id.tv_percent_app);
		mViewPager = (ViewPager) findViewById(R.id.vp_base_app);

		btnBack.setOnClickListener(this);
		mViewPager.setOnPageChangeListener(this);

		overridePendingTransition(R.anim.activity_alpha_action_in, R.anim.activity_alpha_action_out); // 渐入效果

		if (getIntent().hasExtra("show")) {
			isShow = true;
			if(getIntent().getBooleanExtra("delete",false) == true)
				btnRight.setVisibility(View.VISIBLE);
			ivMore.setVisibility(View.GONE);
		} else {
			btnRight.setVisibility(View.GONE);
			ivMore.setVisibility(View.VISIBLE);
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
	@Override
	protected void onDestroy() {
		Log.e("phtos+","onDestroy");
		mContainer.removeAllViews();
		Glide.get(getApplicationContext()).clearMemory();
		super.onDestroy();

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
			mContainer = (ViewPager)container;
			PhotoPreview photoPreview = new PhotoPreview(getApplicationContext());
			photoPreview.setBackgroundResource(android.R.color.black);
			photoPreview.setCome(PhotoPreview.ISPREVIEW);
			((ViewPager) container).addView(photoPreview);
			Log.e("phtos+","path="+photos.get(position));
			photoPreview.loadImage(photos.get(position));
			photoPreview.setOnClickListener(photoItemClickListener);
			return photoPreview;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			Glide.clear((PhotoPreview)object);
			((ViewPager)container).removeView((PhotoPreview) object);
			Log.e("phtos+","destroyItem position="+position);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	};
	protected boolean isUp;

	@Override
	public void onBackPressed() {
		Log.e("phtos+","onBackPressed");
		clearCache();
		finish();
		overridePendingTransition(R.anim.activity_alpha_action_in, R.anim.activity_alpha_action_out); // 渐入效果

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_back_app){
			Log.e("phtos+","Back");
			clearCache();
			finish();
			overridePendingTransition(R.anim.activity_alpha_action_in, R.anim.activity_alpha_action_out); // 渐入效果

		}

		else if (v.getId() == R.id.btnRight){
			//Log.e("onClick","current:"+current);
			onRightBtn(current);
			if (current==1){
				current--;
			}
		}
		else if (v.getId() == R.id.btnBottomRight){
			clearCache();
			onBottomRightBtn();
		}

			
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
	private void clearCache(){
		mContainer.removeAllViews();
		Glide.get(getApplicationContext()).clearMemory();
	}
}
