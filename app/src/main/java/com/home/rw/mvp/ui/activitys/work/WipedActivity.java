package com.home.rw.mvp.ui.activitys.work;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.home.rw.R;
import com.home.rw.common.Const;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.utils.DimenUtil;
import com.home.rw.utils.FrescoUtils;
import com.home.rw.utils.KeyBoardUtils;
import com.home.rw.utils.UriUtils;
import com.home.rw.widget.PicTakerPopWindow;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoPreviewActivity;
import com.photoselector.ui.PhotoSelectorActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;



public class WipedActivity extends BaseActivity {
    private ArrayList<View> viewContainer = new ArrayList<>();

    private static  final String root = Environment.getExternalStorageDirectory().
            getAbsolutePath()+"/"+"RwCache";
    private  String headerPathTemp;

    private final int COMPRESS_WIDTH = 200;

    private final int COMPRESS_HEIGH = 120;
    //图库插件指定类型
    ArrayList<PhotoModel> photos = new ArrayList<>();
    //图库
    private static final int RESULT_PICK_FROM_PHOTO_NORMAL = 0;

    //相机
    private static final int RESULT_PICK_FROM_CAMERA_NORMAL = 1;

    //是否可以删除
    private  boolean isPhotoDeleteable = false;

    //自定义的弹出框类
    private PicTakerPopWindow menuWindow;

    private int selectedPosition = 0;
    //是否选择附件图片
    private boolean isAccess;

    private int mSumMoney = 0;
    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.sc_container)
    ScrollView mscrollView;

    @BindView(R.id.ll_top)
    LinearLayout mTop;

    @BindView(R.id.iv_update_photo)
    ImageView iv_photo;

    @BindView(R.id.iv_taked_picture1)
    SimpleDraweeView mPhoto;

    @BindView(R.id.rl_bottom_commit)
    RelativeLayout mBottomCommit;

    @BindView(R.id.rl_bottom_approve)
    RelativeLayout mBottomApprove;

    @BindView(R.id.iv_wiped_picture1)
    SimpleDraweeView mWiped1;

    @BindView(R.id.iv_wiped_picture2)
    SimpleDraweeView mWiped2;

    @BindView(R.id.iv_wiped_picture3)
    SimpleDraweeView mWiped3;

    @BindView(R.id.rl_access)
    RelativeLayout mAccess;

    @BindView(R.id.ll_wiped_container)
    LinearLayout mWipedcontainer;

    @BindView(R.id.iv_right_arrow)
    ImageView mRightArrow;

    @BindView(R.id.iv_update_wiped)
    ImageView mUpdatewiped;

    @BindView(R.id.ll_add)
    LinearLayout mAdd;

    @BindView(R.id.tv_sum)
    TextView mSum;



    @OnClick({R.id.back,
            R.id.ll_add,
            R.id.iv_update_photo,
            R.id.iv_taked_picture1,
            R.id.iv_wiped_picture1,
            R.id.iv_wiped_picture2,
            R.id.iv_wiped_picture3,
            R.id.rl_access,
            R.id.iv_update_wiped
    })
    public void onClick(View v){
        switch(v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.ll_add:
                addView();
                break;
            case R.id.iv_update_photo:
                showHeaderSelectPopWin();
                break;
            case R.id.iv_taked_picture1:
                isAccess = false;
                selectedPosition = 0;
                startPreview();
                break;
            case R.id.iv_wiped_picture1:
                isAccess = true;
                selectedPosition = 0;
                startPreviewWiped();
                break;
            case R.id.iv_wiped_picture2:
                isAccess = true;
                selectedPosition = 1;
                startPreviewWiped();
                break;
            case R.id.iv_wiped_picture3:
                isAccess = true;
                selectedPosition = 2;
                startPreviewWiped();
                break;
            case R.id.rl_access:
                if(photos.size() == 0)
                    startSelect();
                break;
            case R.id.iv_update_wiped:
                startSelect();
                break;
            default:
                break;
        }
    }
    //从图库中选择附件
    private void startSelect() {
        //跳转至最终的选择图片页面
        Intent intent = new Intent(this, PhotoSelectorActivity.class);
        intent.putExtra(PhotoSelectorActivity.KEY_MAX, 3);
        intent.putExtra("photos", photos);
        this.startActivityForResult(intent, Const.PHOTO_SELECT);
    }

    private void addView(){
        final View child = getLayoutInflater().inflate(R.layout.wiped_layout,null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)DimenUtil.dp2px(220));
        child.findViewById(R.id.rl_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTop.removeView(child);
                viewContainer.remove(child);
                reCalculatorSumMoney();
                updateWipedList();
            }
        });
        ((EditText)child.findViewById(R.id.et_wipedmoney)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                reCalculatorSumMoney();

            }
        });
        child.setLayoutParams(lp);
        mTop.addView(child);
        viewContainer.add(child);
        updateWipedList();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wiped;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        midText.setText(R.string.wiped);
        mback.setImageResource(R.drawable.btn_back);
        mSum.setText(String.format(getString(R.string.wipedSum),mSumMoney));
        //添加第一个报销明细
        View child = getLayoutInflater().inflate(R.layout.wiped_layout,null);
        child.findViewById(R.id.rl_del).setVisibility(View.INVISIBLE);
        child.findViewById(R.id.top_sperate).setVisibility(View.GONE);
        ((EditText)child.findViewById(R.id.et_wipedmoney)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                reCalculatorSumMoney();

            }
        });
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)DimenUtil.dp2px(220));
        child.setLayoutParams(lp);
        mTop.addView(child);
        viewContainer.add(child);
        updateWipedList();

        isPhotoDeleteable = true;
    }

    private void reCalculatorSumMoney() {
        mSumMoney = 0;
        for(int i = 0;i<viewContainer.size();i++){
            try {
                int money = Integer.parseInt(((EditText)viewContainer.get(i).findViewById(R.id.et_wipedmoney)).getText().toString());
                mSumMoney+=money;
            }catch (NumberFormatException e){
                continue;
            }
       }
        mSum.setText(String.format(getString(R.string.wipedSum),mSumMoney));
    }

    private void initViewsShow(){
        midText.setText(R.string.wiped);
        mback.setImageResource(R.drawable.btn_back);
        mSum.setText(String.format(getString(R.string.wipedSum),mSumMoney));
        //添加数据
        for(int i = 0;i<4;i++){
            updateWipedData(i);
        }

        updateWipedList();

        headerPathTemp = "http://img1.gamersky.com/image2016/11/20161114zd_337_39/gamersky_01small_02_20161114165972E.jpg";
        mPhoto.setImageURI(headerPathTemp);
        mRightArrow.setVisibility(View.GONE);
        iv_photo.setVisibility(View.GONE);
        mUpdatewiped.setVisibility(View.GONE);
        mWipedcontainer.setVisibility(View.VISIBLE);
        mAdd.setVisibility(View.GONE);
        mUpdatewiped.setVisibility(View.GONE);

        photos.add(new PhotoModel("http://img1.gamersky.com/image2016/11/20161114zd_337_39/gamersky_01small_02_20161114165972E.jpg"));
        photos.add(new PhotoModel("https://imgsa.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=9963e0334e90f60310bd9415587bd87e/ac345982b2b7d0a21f15689fcfef76094a369ad7.jpg"));
        photos.add(new PhotoModel("http://img1.cache.netease.com/game/starcraft2/news/201601/62.jpg"));

        if (photos.size() == 3){
            mWiped1.setVisibility(View.VISIBLE);
            mWiped2.setVisibility(View.VISIBLE);
            mWiped3.setVisibility(View.VISIBLE);

            FrescoUtils.load(Uri.parse(photos.get(0).getOriginalPath()),mWiped1,200,120);
            FrescoUtils.load(Uri.parse(photos.get(1).getOriginalPath()),mWiped2,200,120);
            FrescoUtils.load(Uri.parse(photos.get(2).getOriginalPath()),mWiped3,200,120);

        }else if(photos.size() == 2){
            mWiped1.setVisibility(View.VISIBLE);
            mWiped2.setVisibility(View.VISIBLE);
            mWiped3.setVisibility(View.GONE);

            FrescoUtils.load(Uri.parse(photos.get(0).getOriginalPath()),mWiped1,200,120);
            FrescoUtils.load(Uri.parse(photos.get(1).getOriginalPath()),mWiped2,200,120);

        }else if(photos.size() == 1){
            mWiped1.setVisibility(View.VISIBLE);
            mWiped2.setVisibility(View.INVISIBLE);
            mWiped3.setVisibility(View.GONE);

            FrescoUtils.load(Uri.parse(photos.get(0).getOriginalPath()),mWiped1,200,120);

        }else{
            mWiped1.setVisibility(View.GONE);
            mWiped2.setVisibility(View.GONE);
            mWiped3.setVisibility(View.GONE);
        }
        mSum.setText(String.format(getString(R.string.wipedSum),mSumMoney));
    }
    private void updateWipedData(int index){

        View child = getLayoutInflater().inflate(R.layout.wiped_layout,null);
        child.findViewById(R.id.rl_del).setVisibility(View.INVISIBLE);
        if(index == 0)
            child.findViewById(R.id.top_sperate).setVisibility(View.GONE);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)DimenUtil.dp2px(220));
        child.setLayoutParams(lp);
        mTop.addView(child);
        viewContainer.add(child);

        EditText etMoney = (EditText)child.findViewById(R.id.et_wipedmoney);
        etMoney.setEnabled(false);
        etMoney.setText("15000");

        EditText etType = (EditText)child.findViewById(R.id.et_wipedtype);
        etType.setEnabled(false);
        etType.setText("出差");

        EditText etDetail = (EditText)child.findViewById(R.id.et_wipeddetail);
        etDetail.setEnabled(false);
        etDetail.setText("出差有钱不拿白不拿");

        mSumMoney+=Integer.parseInt(etMoney.getText().toString());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String entryType;
        if((entryType = getIntent().getStringExtra("entryType")) != null){
            if(entryType.equals("edit")){
                //编辑模式
                initViews();


            }else if(entryType.equals("show")){
                //查看模式
                initViewsShow();
                mBottomCommit.setVisibility(View.GONE);
                mBottomApprove.setVisibility(View.GONE);
            }else{
                //审批模式
                initViewsShow();
                mBottomCommit.setVisibility(View.GONE);
                mBottomApprove.setVisibility(View.VISIBLE);

            }

        }


        initCache();
    }

    //更新报销清单列表
    private void updateWipedList(){
        String index = getResources().getString(R.string.wipedTitle);

        for(int i = 0;i<viewContainer.size();i++){
            String sFinalAge = String.format(index, i+1);
            ((TextView)(viewContainer.get(i).findViewById(R.id.tv_title))).setText(sFinalAge);
        }



    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = getCurrentFocus();
        new KeyBoardUtils(event,im,v).hideKeyBoardIfNecessary();
        return super.dispatchTouchEvent(event);
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener(){

        public void onClick(View v) {
            menuWindow.dismiss();
            Intent intent;
            switch (v.getId()) {
                case R.id.btn_take_photo:
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(createTempFile()));
                    startActivityForResult(intent, RESULT_PICK_FROM_CAMERA_NORMAL);

                    break;
                case R.id.btn_pick_photo:
                    // 去图库中选择图片
                    if (Build.VERSION.SDK_INT < 19) {
                        intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");

                    } else {
                        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    }
                    startActivityForResult(intent, RESULT_PICK_FROM_PHOTO_NORMAL);

                    break;
                case R.id.btn_cancel:
                    break;
            }
        }
    };
    //选择图片过程中创建临时文件
    private File createTempFile(){

        Date date = new Date();

        headerPathTemp = root+"/"+date.getTime()+".jpg";

        return new File(headerPathTemp);

    }

    private void initCache(){

        File mFilePath = new File(root);
        if (!mFilePath.exists()) {
            mFilePath.mkdirs();
        }

    }

    //选择图片
    private void showHeaderSelectPopWin(){

        menuWindow = new PicTakerPopWindow(this,itemsOnClick);
        menuWindow.showAtLocation(mPhoto,
                Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);

    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            // 从相册选择图片后返回图片的uri 并且执行
            case RESULT_PICK_FROM_PHOTO_NORMAL:
                Log.i("info", "RESULT_PICK_FROM_PHOTO_NORMAL");
                if (resultCode == RESULT_OK && data != null) {
                    Uri originalUri = data.getData();

                    String[] proj = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(originalUri, proj, null, null, null);


                    if (cursor != null) {
                        cursor.moveToFirst();
                        headerPathTemp = cursor.getString(0);
                        cursor.close();
                        FrescoUtils.load(Uri.fromFile(new File(headerPathTemp)),mPhoto,200,120);
                    }else{
                        headerPathTemp= UriUtils.getPath(this, originalUri);
                        FrescoUtils.load(Uri.fromFile(new File(headerPathTemp)),mPhoto,200,120);
                    }
                }
                break;
            //从照相机选择
            case RESULT_PICK_FROM_CAMERA_NORMAL:

                if (resultCode == RESULT_OK ) {
                    FrescoUtils.load(Uri.fromFile(new File(headerPathTemp)),mPhoto,200,120);

                }
                break;
            case Const.PHOTO_PREVIEW:

                if (data != null && data.getExtras() != null) {


                    ArrayList<PhotoModel> photos = (ArrayList<PhotoModel>) data.getExtras().getSerializable("photos");

                    if(isAccess == false){
                        //因为只有一张图所以如果删除则返回空集合
                        if(photos.size() == 0){

                            ImagePipeline imagePipeline = Fresco.getImagePipeline();
                            imagePipeline.evictFromCache(Uri.fromFile(new File(headerPathTemp)));
                            mPhoto.setImageURI("");
                            headerPathTemp = "";


                        }

                    }else{
                        this.photos = photos;
                        //根据返回的数据调整布局显示
                        mWipedcontainer.setVisibility(View.VISIBLE);
                        mRightArrow.setVisibility(View.GONE);
                        if(isPhotoDeleteable)
                            mUpdatewiped.setVisibility(View.VISIBLE);
                        if (photos.size() == 3){
                            mWiped1.setVisibility(View.VISIBLE);
                            mWiped2.setVisibility(View.VISIBLE);
                            mWiped3.setVisibility(View.VISIBLE);
                            if(isPhotoDeleteable){
                                FrescoUtils.load(Uri.fromFile(new File(photos.get(0).getOriginalPath())),mWiped1,200,120);
                                FrescoUtils.load(Uri.fromFile(new File(photos.get(1).getOriginalPath())),mWiped2,200,120);
                                FrescoUtils.load(Uri.fromFile(new File(photos.get(2).getOriginalPath())),mWiped3,200,120);

                            }else{
                                FrescoUtils.load(Uri.parse(photos.get(0).getOriginalPath()),mWiped1,200,120);
                                FrescoUtils.load(Uri.parse(photos.get(1).getOriginalPath()),mWiped2,200,120);
                                FrescoUtils.load(Uri.parse(photos.get(2).getOriginalPath()),mWiped3,200,120);
                            }

                        }else if(photos.size() == 2){
                            mWiped1.setVisibility(View.VISIBLE);
                            mWiped2.setVisibility(View.VISIBLE);
                            mWiped3.setVisibility(View.GONE);

                            if(isPhotoDeleteable){
                                FrescoUtils.load(Uri.fromFile(new File(photos.get(0).getOriginalPath())),mWiped1,200,120);
                                FrescoUtils.load(Uri.fromFile(new File(photos.get(1).getOriginalPath())),mWiped2,200,120);
                            }else{
                                FrescoUtils.load(Uri.parse(photos.get(0).getOriginalPath()),mWiped1,200,120);
                                FrescoUtils.load(Uri.parse(photos.get(1).getOriginalPath()),mWiped2,200,120);
                            }
                        }else if(photos.size() == 1){
                            mWiped1.setVisibility(View.VISIBLE);
                            mWiped2.setVisibility(View.INVISIBLE);
                            mWiped3.setVisibility(View.GONE);

                            if(isPhotoDeleteable){
                                FrescoUtils.load(Uri.fromFile(new File(photos.get(0).getOriginalPath())),mWiped1,200,120);
                           }else{
                                FrescoUtils.load(Uri.parse(photos.get(0).getOriginalPath()),mWiped1,200,120);
                           }
                        }else{
                            mRightArrow.setVisibility(View.VISIBLE);
                            mWipedcontainer.setVisibility(View.GONE);
                            mUpdatewiped.setVisibility(View.GONE);
                        }
                    }



                }

                break;

            case Const.PHOTO_SELECT:
                if (data != null && data.getExtras() != null) {

                    this.photos = (ArrayList<PhotoModel>) data.getExtras().getSerializable("photos");


                    //根据返回的数据调整布局显示
                    mWipedcontainer.setVisibility(View.VISIBLE);
                    mRightArrow.setVisibility(View.GONE);
                    mUpdatewiped.setVisibility(View.VISIBLE);
                    if (photos.size() == 3){
                        mWiped1.setVisibility(View.VISIBLE);
                        mWiped2.setVisibility(View.VISIBLE);
                        mWiped3.setVisibility(View.VISIBLE);
                        FrescoUtils.load(Uri.fromFile(new File(photos.get(0).getOriginalPath())),mWiped1,200,120);
                        FrescoUtils.load(Uri.fromFile(new File(photos.get(1).getOriginalPath())),mWiped2,200,120);
                        FrescoUtils.load(Uri.fromFile(new File(photos.get(2).getOriginalPath())),mWiped3,200,120);
//

                    }else if(photos.size() == 2){
                        mWiped1.setVisibility(View.VISIBLE);
                        mWiped2.setVisibility(View.VISIBLE);
                        mWiped3.setVisibility(View.GONE);

                        FrescoUtils.load(Uri.fromFile(new File(photos.get(0).getOriginalPath())),mWiped1,200,120);
                        FrescoUtils.load(Uri.fromFile(new File(photos.get(1).getOriginalPath())),mWiped2,200,120);

                    }else if(photos.size() == 1){
                        mWiped1.setVisibility(View.VISIBLE);
                        mWiped2.setVisibility(View.INVISIBLE);
                        mWiped3.setVisibility(View.GONE);

                        FrescoUtils.load(Uri.fromFile(new File(photos.get(0).getOriginalPath())),mWiped1,200,120);

                    }else{
                        mRightArrow.setVisibility(View.VISIBLE);
                        mWipedcontainer.setVisibility(View.GONE);
                        mUpdatewiped.setVisibility(View.GONE);
                    }



                }else{
                    this.photos.clear();
                    mRightArrow.setVisibility(View.VISIBLE);
                    mWipedcontainer.setVisibility(View.GONE);
                    mUpdatewiped.setVisibility(View.GONE);
                }
                break;

        }


    }
    //更新数据源并启动预览画面
    private void startPreview() {
        if((headerPathTemp == null) || (headerPathTemp.equals(""))){
            return;
        }
        ArrayList<PhotoModel> photos = new ArrayList<>();
        photos.add(new PhotoModel(headerPathTemp,false));
        Intent intent = new Intent(this, PhotoPreviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("photos", photos);
        bundle.putInt("position", selectedPosition);
        bundle.putBoolean("show", true);
        intent.putExtra("shows",true);
        intent.putExtra("delete",isPhotoDeleteable);
        intent.putExtras(bundle);
        this.startActivityForResult(intent, Const.PHOTO_PREVIEW);
    }

    //更新数据源并启动预览画面
    private void startPreviewWiped() {
        if((photos == null)||(photos.size() == 0)){
            return;
        }
        Intent intent = new Intent(this, PhotoPreviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("photos", photos);
        bundle.putInt("position", selectedPosition);
        bundle.putBoolean("show", true);
        intent.putExtra("shows",true);
        intent.putExtra("delete",isPhotoDeleteable);
        intent.putExtras(bundle);
        this.startActivityForResult(intent, Const.PHOTO_PREVIEW);
    }


    @Override
    protected void onDestroy() {
        Log.i("Wiped","onDestroy");
        clearCache();
        super.onDestroy();
    }

    private void clearCache(){
        FrescoUtils.clearCache();
        FrescoUtils.clearImgMemory(mWiped1);
        FrescoUtils.clearImgMemory(mWiped2);
        FrescoUtils.clearImgMemory(mWiped3);
        FrescoUtils.clearImgMemory(mPhoto);

    }
}
