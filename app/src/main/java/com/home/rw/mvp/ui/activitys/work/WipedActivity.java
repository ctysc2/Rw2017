package com.home.rw.mvp.ui.activitys.work;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.home.rw.R;
import com.home.rw.common.Const;
import com.home.rw.common.HostType;
import com.home.rw.listener.AlertDialogListener;
import com.home.rw.mvp.entity.AddApplyEntity;
import com.home.rw.mvp.entity.ApplyDetailEntity;
import com.home.rw.mvp.entity.UploadEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.presenter.impl.AddApplyPresenterImpl;
import com.home.rw.mvp.presenter.impl.ApplyDetailPresenterImpl;
import com.home.rw.mvp.presenter.impl.DoApprovePresenterImpl;
import com.home.rw.mvp.presenter.impl.EditApplyPresenterImpl;
import com.home.rw.mvp.presenter.impl.UploadPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.view.AddApplyView;
import com.home.rw.mvp.view.ApplyDetailView;
import com.home.rw.mvp.view.DoApproveView;
import com.home.rw.mvp.view.EditApplyView;
import com.home.rw.mvp.view.UploadView;
import com.home.rw.utils.CompressUtils;
import com.home.rw.utils.DialogUtils;
import com.home.rw.utils.DimenUtil;
import com.home.rw.utils.FrescoUtils;
import com.home.rw.utils.KeyBoardUtils;
import com.home.rw.utils.UriUtils;
import com.home.rw.widget.PicTakerPopWindow;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoPreviewActivity;
import com.photoselector.ui.PhotoSelectorActivity;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.home.rw.common.HostType.ADD_APPLY_EXPENSE;
import static com.home.rw.common.HostType.APPLY_DO_PASS;
import static com.home.rw.common.HostType.APPLY_DO_REJECT;
import static com.home.rw.common.HostType.DETAIL_APPLY_EXPENSE;
import static com.home.rw.common.HostType.DETAIL_APPLY_OVERTIME;
import static com.home.rw.common.HostType.EDIT_APPLY_EXPENSE;
import static com.home.rw.common.HostType.UPLOAD;


public class WipedActivity extends BaseActivity implements AlertDialogListener,AddApplyView,EditApplyView,UploadView,DoApproveView,ApplyDetailView {
    private ArrayList<View> viewContainer = new ArrayList<>();

    private static  final String root = Environment.getExternalStorageDirectory().
            getAbsolutePath()+"/"+"RwCache";
    private  String headerPathTemp;

    private String entryType;

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

    private double mSumMoney = 0.0;

    @Inject
    AddApplyPresenterImpl mAddApplyPresenterImpl;

    @Inject
    EditApplyPresenterImpl mEditApplyPresenterImpl;

    @Inject
    UploadPresenterImpl mUploadPresenterImpl;

    @Inject
    DoApprovePresenterImpl mDoApprovePresenterImpl;

    @Inject
    ApplyDetailPresenterImpl mApplyDetailPresenterImpl;

    private String mainFormID = "";
    private boolean mIsFirstCreateForm = true;


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

    @BindView(R.id.iv_approver_header)
    SimpleDraweeView mApproverHeader;

    @BindView(R.id.tv_approver_name)
    TextView mApproverName;


    @OnClick({R.id.back,
            R.id.ll_add,
            R.id.iv_update_photo,
            R.id.iv_taked_picture1,
            R.id.iv_wiped_picture1,
            R.id.iv_wiped_picture2,
            R.id.iv_wiped_picture3,
            R.id.rl_access,
            R.id.iv_update_wiped,
            R.id.bt_commit,
            R.id.bt_passed,
            R.id.bt_refused,
    })
    public void onClick(View v){
        switch(v.getId()){
            case R.id.back:
                if(entryType.equals("edit")){
                    mAlertDialog = DialogUtils.create(this,DialogUtils.TYPE_ALERT);
                    mAlertDialog.show(this,getString(R.string.editExitHint1),getString(R.string.editExitHint2));

                }else{
                    finish();
                }
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
            case R.id.bt_commit:
                if(!checkCommitData()){
                    Toast.makeText(this,getString(R.string.checkinput),Toast.LENGTH_SHORT).show();
                    break;
                }
                if(mainFormID == null || mainFormID.equals("")){
                    //创建失败的话重新创建
                    addWipedForm();
                }else{
                    //创建成功则直接跳到step2
                    //step2:如果有图片先上传图片,没图片直接跳到step3
                    if(!TextUtils.isEmpty(headerPathTemp) || photos.size()!=0){
                        commitImgs();
                    }else{
                        editWipedForm(null);
                    }
                }
                break;
            case R.id.bt_passed:
                mDoApprovePresenterImpl.setAddApplyType(HostType.APPLY_DO_PASS);
                mDoApprovePresenterImpl.beforeRequest();
                mDoApprovePresenterImpl.doApprove(mainFormID);
                break;
            case R.id.bt_refused:
                mDoApprovePresenterImpl.setAddApplyType(HostType.APPLY_DO_REJECT);
                mDoApprovePresenterImpl.beforeRequest();
                mDoApprovePresenterImpl.doApprove(mainFormID);
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

                mAlertDialog = DialogUtils.create(WipedActivity.this,DialogUtils.TYPE_ALERT);
                mAlertDialog.show(new AlertDialogListener() {
                    @Override
                    public void onConFirm() {
                        mAlertDialog.dismiss();
                        mTop.removeView(child);
                        viewContainer.remove(child);
                        reCalculatorSumMoney();
                        updateWipedList();
                    }

                    @Override
                    public void onCancel() {
                        mAlertDialog.dismiss();
                    }
                }, String.format(getString(R.string.deleteHint1), ((TextView)(child.findViewById(R.id.tv_title))).getText()));


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
        mActivityComponent.inject(this);
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
        mSumMoney = 0.0;
        for(int i = 0;i<viewContainer.size();i++){
            try {
                double money = Double.parseDouble(((EditText)viewContainer.get(i).findViewById(R.id.et_wipedmoney)).getText().toString());
                mSumMoney+=money;
            }catch (NumberFormatException e){
                continue;
            }
       }
        mSumMoney = new BigDecimal(mSumMoney).setScale(2, RoundingMode.UP).doubleValue();
        mSum.setText(String.format(getString(R.string.wipedSum),mSumMoney));
    }
    private boolean checkDate(String s){
        if(s!=null && !s.equals("") )
            return true;
        else
            return false;
    }
    private boolean checkCommitData(){
        boolean result = true;
        for(int i = 0;i<viewContainer.size();i++){
            View child = viewContainer.get(i);
            EditText etMoney = (EditText)child.findViewById(R.id.et_wipedmoney);
            EditText etType = (EditText)child.findViewById(R.id.et_wipedtype);

            if(!checkDate(etMoney.getText().toString())){
                return false;
            }
            if(!checkDate(etType.getText().toString())){
                return false;
            }

        }

        return result;

    }
    private void initViewsShow(){
        midText.setText(R.string.wiped);
        mback.setImageResource(R.drawable.btn_back);
        mSum.setText(String.format(getString(R.string.wipedSum),mSumMoney));
        //添加数据
        updateWipedData(null);

        updateWipedList();

        mRightArrow.setVisibility(View.GONE);
        iv_photo.setVisibility(View.GONE);
        mUpdatewiped.setVisibility(View.GONE);
        mWipedcontainer.setVisibility(View.VISIBLE);
        mAdd.setVisibility(View.GONE);
        mUpdatewiped.setVisibility(View.GONE);


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
    private void updateWipedData(ArrayList<ApplyDetailEntity.DataEntity.Detail> detailList){

        if(detailList == null){
            View child = getLayoutInflater().inflate(R.layout.wiped_layout,null);
            child.findViewById(R.id.rl_del).setVisibility(View.INVISIBLE);
            //第一行不显示
            child.findViewById(R.id.top_sperate).setVisibility(View.GONE);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)DimenUtil.dp2px(220));
            child.setLayoutParams(lp);
            mTop.addView(child);
            viewContainer.add(child);

            EditText etMoney = (EditText)child.findViewById(R.id.et_wipedmoney);
            etMoney.setEnabled(false);
            etMoney.setText("");

            EditText etType = (EditText)child.findViewById(R.id.et_wipedtype);
            etType.setEnabled(false);
            etType.setText("");

            EditText etDetail = (EditText)child.findViewById(R.id.et_wipeddetail);
            etDetail.setEnabled(false);
            etDetail.setText("");
            return;
        }
        mTop.removeAllViews();
        viewContainer.clear();
        viewContainer = new ArrayList<>();

        for(int i = 0;i<detailList.size();i++){
            ApplyDetailEntity.DataEntity.Detail detail = detailList.get(i);
            View child = getLayoutInflater().inflate(R.layout.wiped_layout,null);
            child.findViewById(R.id.rl_del).setVisibility(View.INVISIBLE);
            //第一行不显示
            if (i == 0 )
                child.findViewById(R.id.top_sperate).setVisibility(View.GONE);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)DimenUtil.dp2px(220));
            child.setLayoutParams(lp);
            mTop.addView(child);
            viewContainer.add(child);

            EditText etMoney = (EditText)child.findViewById(R.id.et_wipedmoney);
            etMoney.setEnabled(false);
            int amount = detail.getAmount();
            String point = "00";
            if(amount%100 != 0){
                point = ""+amount%100;
            }
            String newAmount = amount/100+"."+point;
            etMoney.setText(newAmount);

            EditText etType = (EditText)child.findViewById(R.id.et_wipedtype);
            etType.setEnabled(false);
            etType.setText(detail.getExpenseType());

            EditText etDetail = (EditText)child.findViewById(R.id.et_wipeddetail);
            etDetail.setEnabled(false);
            etDetail.setText(detail.getRemark());
            mSumMoney+=Double.parseDouble(etMoney.getText().toString());
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainFormID = getIntent().getStringExtra("formID");
        if((entryType = getIntent().getStringExtra("entryType")) != null){
            if(entryType.equals("edit")){
                //编辑模式
                initViews();
                mAddApplyPresenterImpl.attachView(this);
                mEditApplyPresenterImpl.attachView(this);
                mUploadPresenterImpl.attachView(this);
                addWipedForm();


            }else if(entryType.equals("show")){
                //查看模式
                initViewsShow();
                mApplyDetailPresenterImpl.attachView(this);
                mApplyDetailPresenterImpl.setAddApplyType(DETAIL_APPLY_EXPENSE);
                mApplyDetailPresenterImpl.beforeRequest();
                mApplyDetailPresenterImpl.getApplyDetail(mainFormID);
                mBottomCommit.setVisibility(View.GONE);
                mBottomApprove.setVisibility(View.GONE);
                mIsFirstCreateForm = false;
            }else{
                //审批模式
                initViewsShow();
                mDoApprovePresenterImpl.attachView(this);
                mApplyDetailPresenterImpl.attachView(this);
                mApplyDetailPresenterImpl.setAddApplyType(DETAIL_APPLY_EXPENSE);
                mApplyDetailPresenterImpl.beforeRequest();
                mApplyDetailPresenterImpl.getApplyDetail(mainFormID);
                mBottomCommit.setVisibility(View.GONE);
                mBottomApprove.setVisibility(View.VISIBLE);
                mIsFirstCreateForm = false;

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




    private void clearCache(){
        FrescoUtils.clearCache();
        FrescoUtils.clearImgMemory(mWiped1);
        FrescoUtils.clearImgMemory(mWiped2);
        FrescoUtils.clearImgMemory(mWiped3);
        FrescoUtils.clearImgMemory(mPhoto);

    }
    @Override
    public void onConFirm() {
        mAlertDialog.dismiss();
        finish();
    }

    @Override
    public void onCancel() {
        mAlertDialog.dismiss();

    }
    @Override
    public void onBackPressed() {

        showOrDismissDialog();
    }

    private void showOrDismissDialog(){
        if(entryType.equals("edit")){
            if((mAlertDialog != null) && (mAlertDialog.isShowing())){
                mAlertDialog.dismiss();
            }else{
                mAlertDialog = DialogUtils.create(this,DialogUtils.TYPE_ALERT);
                mAlertDialog.show(this,getString(R.string.editExitHint1),getString(R.string.editExitHint2));
            }
            return;
        }
        finish();
    }
    //step1:新增外出单
    private void addWipedForm(){
        if(!mIsFirstCreateForm){
            mAddApplyPresenterImpl.beforeRequest();
        }
        mAddApplyPresenterImpl.setAddApplyType(ADD_APPLY_EXPENSE);
        mAddApplyPresenterImpl.addApply();


    }
    //修改外出单
    private void editWipedForm(List<String> data){
        mEditApplyPresenterImpl.setAddApplyType(HostType.EDIT_APPLY_EXPENSE);
        mEditApplyPresenterImpl.beforeRequest();

        HashMap<String,Object> container = new HashMap<>();
        List<HashMap<String,Object>> content = new ArrayList<>();
        for(int i = 0;i<viewContainer.size();i++){
            View v = viewContainer.get(i);
            EditText num = (EditText)v.findViewById(R.id.et_wipedmoney);
            EditText type = (EditText)v.findViewById(R.id.et_wipedtype);
            EditText detail = (EditText)v.findViewById(R.id.et_wipeddetail);
            HashMap<String,Object> map = new HashMap<>();
            map.put("amount",(int)(Double.parseDouble(num.getText().toString())*100));
            map.put("expenseType",type.getText().toString());
            map.put("remark",detail.getText().toString());
            content.add(map);
        }

        String stringContent = new Gson().toJson(content);
        String extra = "";
        String imgs = "";
        if(data != null && data.size()>0){
            if(TextUtils.isEmpty(headerPathTemp)){
                for(int j = 0;j<data.size();j++){
                    extra+=data.get(j);
                    if(j!=data.size()-1)
                        extra+=",";
                }
            }else{
                imgs = data.get(0);
                for(int j = 1;j<data.size();j++){
                    extra+=data.get(j);
                    if(j!=data.size()-1)
                        extra+=",";
                }
            }

        }
        container.put("extra",extra);
        container.put("imgs",imgs);
        container.put("details",stringContent);
        String log = new Gson().toJson(container);
        Log.i("Retrofit","editWipedForm commit log body:"+log);

        mEditApplyPresenterImpl.editApply(mainFormID,container);




    }
    private String createNewFilePath(String oldPath){
        String newPath = "";

        String[] data = oldPath.split("/");
        int length = data.length;
        String fileName = data[length-1];

        newPath = root+"/"+fileName;

        return newPath;
    }
    private void commitImgs(){
        mUploadPresenterImpl.beforeRequest();
        rx.Observable.create(new rx.Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onNext(null);

            }
        }).map(new Func1<Object, ArrayList<String>>() {
            @Override
            public ArrayList<String> call(Object o) {
                ArrayList<String> list = new ArrayList<String>();
                try {
                    if(!TextUtils.isEmpty(headerPathTemp)){
                        String newPath = createNewFilePath(headerPathTemp);
                        Log.i("Retrofit","newPath head:"+newPath);
                        CompressUtils.getInstance().compressAndGenImage(headerPathTemp,newPath,800,false);
                        list.add(newPath);
                    }
                    if(photos.size()!=0){
                        for(int i = 0;i<photos.size();i++){
                            String newPath = createNewFilePath(photos.get(i).getOriginalPath());
                            Log.i("Retrofit","newPath photos:"+newPath);
                            CompressUtils.getInstance().compressAndGenImage(photos.get(i).getOriginalPath(),newPath,800,false);
                            list.add(newPath);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();

                }
                return list;
            }
        })
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Action1<ArrayList<String>>() {
                    @Override
                    public void call(ArrayList<String> list) {
                        mUploadPresenterImpl.processUpload(list);
                    }
                });
    }
    @Override
    public void addApplyCompleted(AddApplyEntity data) {
        Log.i("Retrofit","addApplyCompleted");
        if(data.getCode().equals("ok")){
            mainFormID = data.getData().getId();
            mApproverHeader.setImageURI(data.getData().getAvatar());
            mApproverName.setText(data.getData().getAssessor());
            Log.i("Retrofit","addApplyCompleted ok mainFormID:"+mainFormID);
            if(mIsFirstCreateForm){
                mIsFirstCreateForm = false;
                return;
            }
            //step2:如果有图片先上传图片,没图片直接跳到step3
            if(!TextUtils.isEmpty(headerPathTemp) || photos.size()!=0){
                commitImgs();
            }else{
                editWipedForm(null);
            }
        }else{
            if(!mIsFirstCreateForm){
                if(mLoadDialog!=null){
                    mLoadDialog.dismiss();
                    mLoadDialog = null;
                }
                Toast.makeText(this,getString(R.string.commitFailed),Toast.LENGTH_SHORT).show();
            }
        }
        mIsFirstCreateForm = false;
    }
    @Override
    public void uploadComplete(UploadEntity data) {
        if(data.getCode().equals("ok")){
            //step3:编辑
            editWipedForm(data.getData());

        }else{
            if(mLoadDialog!=null){
                mLoadDialog.dismiss();
                mLoadDialog = null;
            }
            Toast.makeText(this,getString(R.string.commitFailed),Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void editApplyCompleted(BaseEntity data) {
        if(data.getCode().equals("ok")){
            Toast.makeText(this,getString(R.string.commitSuccessed),Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this,getString(R.string.commitFailed),Toast.LENGTH_SHORT).show();
        }
        if(mLoadDialog!=null){
            mLoadDialog.dismiss();
            mLoadDialog = null;
        }
    }
    @Override
    public void showProgress(int reqType) {
        if(mLoadDialog==null) {
            switch (reqType) {
                case ADD_APPLY_EXPENSE:
                case UPLOAD:
                case EDIT_APPLY_EXPENSE:
                case APPLY_DO_PASS:
                case APPLY_DO_REJECT:
                    mLoadDialog = DialogUtils.create(this, DialogUtils.TYPE_UPDATE);
                    mLoadDialog.show();
                    break;
                case DETAIL_APPLY_EXPENSE:
                    mLoadDialog = DialogUtils.create(this, DialogUtils.TYPE_LOADING);
                    mLoadDialog.show();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void hideProgress(int reqType) {



    }

    @Override
    public void showErrorMsg(int reqType, String msg) {
        if(!mIsFirstCreateForm){
            if(entryType.equals("edit")){
                Toast.makeText(this,getString(R.string.commitFailed),Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,getString(R.string.getFailed),Toast.LENGTH_SHORT).show();
            }
        }
        mIsFirstCreateForm = false;

        if(mLoadDialog!=null){
            mLoadDialog.dismiss();
            mLoadDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        Log.i("Wiped","onDestroy");
        clearCache();
        if(mAddApplyPresenterImpl!=null){
            mAddApplyPresenterImpl.onDestroy();
        }

        if(mEditApplyPresenterImpl!=null){
            mEditApplyPresenterImpl.onDestroy();
        }

        if(mUploadPresenterImpl!=null){
            mUploadPresenterImpl.onDestroy();
        }

        if(mDoApprovePresenterImpl!=null){
            mDoApprovePresenterImpl.onDestroy();
        }

        if(mApplyDetailPresenterImpl!=null){
            mApplyDetailPresenterImpl.onDestroy();
        }
        super.onDestroy();
    }
    @Override
    public void doApproveCompleted(BaseEntity data) {
        if(data.getCode().equals("ok")){
            Toast.makeText(this,getString(R.string.commitSuccessed),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("position",getIntent().getIntExtra("position",0));
            setResult(RESULT_OK,intent);
            finish();
        }else{
            Toast.makeText(this,getString(R.string.commitFailed),Toast.LENGTH_SHORT).show();
        }
        if(mLoadDialog!=null){
            mLoadDialog.dismiss();
            mLoadDialog = null;
        }

    }

    @Override
    public void getApplyDetailCompleted(ApplyDetailEntity data) {
        if(data.getCode().equals("ok") && data.getData()!=null){
            mApproverHeader.setImageURI(data.getData().getAvatar());
            mApproverName.setText(data.getData().getAssessor());
            ArrayList<ApplyDetailEntity.DataEntity.Detail> detailList = new Gson().fromJson(data.getData().getDetails(), new TypeToken<List<ApplyDetailEntity.DataEntity.Detail>>() {}.getType());
            updateWipedData(detailList);
            updateWipedList();
            reCalculatorSumMoney();
            headerPathTemp = data.getData().getImgs();
            mPhoto.setImageURI(headerPathTemp);
            if(!TextUtils.isEmpty(data.getData().getExtra())){
                String[] extras = data.getData().getExtra().split(",");
                for(int i = 0;i<extras.length;i++){
                    photos.add(new PhotoModel(extras[i]));
                }
            }
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
        }
        if(mLoadDialog!=null){
            mLoadDialog.dismiss();
            mLoadDialog = null;
        }
    }

}
