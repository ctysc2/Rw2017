package com.home.rw.mvp.ui.activitys.work;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.google.gson.Gson;
import com.home.rw.R;
import com.home.rw.common.Const;
import com.home.rw.common.HostType;
import com.home.rw.greendaohelper.UserInfoDaoHelper;
import com.home.rw.listener.AlertDialogListener;
import com.home.rw.mvp.entity.AddApplyEntity;
import com.home.rw.mvp.entity.ApplyDetailEntity;
import com.home.rw.mvp.entity.UploadEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.impl.AddApplyInteractorImpl;
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
import com.home.rw.utils.DateUtils;
import com.home.rw.utils.DialogUtils;
import com.home.rw.utils.KeyBoardUtils;
import com.home.rw.utils.UriUtils;
import com.home.rw.widget.PicTakerPopWindow;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoPreviewActivity;
import com.polites.MathUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.home.rw.common.HostType.ADD_APPLY_LEAVE;
import static com.home.rw.common.HostType.ADD_APPLY_OVERTIME;
import static com.home.rw.common.HostType.APPLY_DO_PASS;
import static com.home.rw.common.HostType.APPLY_DO_REJECT;
import static com.home.rw.common.HostType.DETAIL_APPLY_LEAVE;
import static com.home.rw.common.HostType.DETAIL_APPLY_OVERTIME;
import static com.home.rw.common.HostType.EDIT_APPLY_LEAVE;
import static com.home.rw.common.HostType.EDIT_APPLY_OVERTIME;
import static com.home.rw.common.HostType.UPLOAD;
import static com.home.rw.common.HostType.USER_INFO;

public class AskForLeaveActivity extends BaseActivity implements AlertDialogListener,AddApplyView,EditApplyView,UploadView,DoApproveView,ApplyDetailView {

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.tv_startTimeSelect)
    TextView mStartTimeSelect;

    @BindView(R.id.tv_endTimeSelect)
    TextView mEndTimeSelect;

    @BindView(R.id.tv_typeSelect)
    TextView mTypeSelect;

    @BindView(R.id.et_duration)
    EditText mDuration;

    @BindView(R.id.tv_reason_detail)
    EditText mReasonDetail;

    @BindView(R.id.iv_update_photo)
    ImageView iv_photo;

    @BindView(R.id.iv_taked_picture1)
    SimpleDraweeView mPhoto;

    @BindView(R.id.rl_bottom_commit)
    RelativeLayout mBottomCommit;

    @BindView(R.id.rl_bottom_approve)
    RelativeLayout mBottomApprove;

    @BindView(R.id.rl_startTime)
    RelativeLayout mRlStartTime;

    @BindView(R.id.rl_endTime)
    RelativeLayout mRlEndTime;

    @BindView(R.id.rl_type)
    RelativeLayout mRlType;

    @BindView(R.id.iv_1)
    ImageView mIv1;

    @BindView(R.id.iv_2)
    ImageView mIv2;

    @BindView(R.id.iv_3)
    ImageView mIv3;

    @BindView(R.id.iv_approver_header)
    SimpleDraweeView mApproverHeader;

    @BindView(R.id.tv_approver_name)
    TextView mApproverName;


    private ArrayList<String> Items = new ArrayList<String>();

    private static final int DEFAULT = -1;

    private static final int START_TIME = 0;

    private static final int END_TIME = 1;

    private int TimePickerNum = START_TIME;

    private static  final String root = Environment.getExternalStorageDirectory().
            getAbsolutePath()+"/"+"RwCache";
    private  String headerPathTemp;

    private String entryType;
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
    //时间选择器
    private TimePickerView pvTime;
    //加班理由选择器
    private OptionsPickerView pvOptions;

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

    @OnClick({R.id.back,
            R.id.rl_startTime,
            R.id.rl_endTime,
            R.id.rl_type,
            R.id.iv_update_photo,
            R.id.iv_taked_picture1,
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
            case R.id.rl_startTime:
                TimePickerNum = START_TIME;
                pvTime.show();
                break;
            case R.id.rl_endTime:
                TimePickerNum = END_TIME;
                pvTime.show();
                break;
            case R.id.rl_type:
                TimePickerNum = DEFAULT;
                pvOptions.show();
                break;
            case R.id.iv_update_photo:
                showHeaderSelectPopWin();
                break;
            case R.id.iv_taked_picture1:
                startPreview();
                break;
            case R.id.bt_commit:
                if(!checkCommitData()){
                    Toast.makeText(this,getString(R.string.checkinput),Toast.LENGTH_SHORT).show();
                    break;
                }
                if(mainFormID == null || mainFormID.equals("")){
                    //创建失败的话重新创建
                    addLeaveForm();
                }else{
                    //创建成功则直接跳到step2
                    //step2:如果有图片先上传图片,没图片直接跳到step3
                    if(!TextUtils.isEmpty(headerPathTemp)){
                        commitImgs();
                    }else{
                        editLeaveForm(null);
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


    @Override
    public int getLayoutId() {
        return R.layout.activity_ask_for_leave;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(R.string.askforleave);
        mback.setImageResource(R.drawable.btn_back);

        //时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.ALL);

        pvTime.setTime(new Date());
        pvTime.setCyclic(true);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                if(TimePickerNum == START_TIME){
                    mStartTimeSelect.setText(DateUtils.getTime(date));
                }else{
                    mEndTimeSelect.setText(DateUtils.getTime(date));
                }
            }
        });
        pvTime.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {
                calculatorWorkDuration();
            }
        });

        Items.add("年假");
        Items.add("病假");
        Items.add("事假");
        Items.add("婚假");
        Items.add("其他");
        //三级联动效果
        pvOptions = new OptionsPickerView(this);
        pvOptions.setPicker(Items);
        pvOptions.setCyclic(false);
        pvOptions.setCancelable(true);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(1, 1, 1);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                mTypeSelect.setText(Items.get(options1));
            }
        });

        isPhotoDeleteable = true;

    }
    private void initViewsShow(){
        midText.setText(R.string.askforleave);
        mback.setImageResource(R.drawable.btn_back);

        //开始时间
        mRlStartTime.setEnabled(false);
        mStartTimeSelect.setText("");

        //结束时间
        mRlEndTime.setEnabled(false);
        mEndTimeSelect.setText("");

        //请假类型
        mRlType.setEnabled(false);
        mTypeSelect.setText("");

        //请假时间
        mDuration.setEnabled(false);
        mDuration.setText("");

        //请假理由
        mReasonDetail.setEnabled(false);
        mReasonDetail.setText("");
        //上传头像
        iv_photo.setVisibility(View.GONE);

        mIv1.setVisibility(View.INVISIBLE);
        mIv2.setVisibility(View.INVISIBLE);
        mIv3.setVisibility(View.INVISIBLE);

    }
    //计算
    private void calculatorWorkDuration() {
        String mStart = mStartTimeSelect.getText().toString();
        String mEnd = mEndTimeSelect.getText().toString();
        if(checkDate(mStart) && checkDate(mEnd)){
            long start = DateUtils.getDate(mStart).getTime();
            long end = DateUtils.getDate(mEnd).getTime();
            if(start>end){
                mDuration.setText("");
            }else{
                double hours = (end - start)/1000/60/60.0;
                BigDecimal bigResult = new BigDecimal(String.valueOf(hours)).setScale(0, BigDecimal.ROUND_UP);
                int result = bigResult.toBigInteger().intValue();
                if(result<8){
                    mDuration.setText(result+getString(R.string.hours));

                }else{
                    mDuration.setText((result/24+1)+getString(R.string.day));


                }
                mDuration.setSelection(mDuration.length());
            }
        }
    }

    private boolean checkCommitData(){
        boolean result = true;
        if(!checkDate(mStartTimeSelect.getText().toString())){
            result = false;
        }
        if(!checkDate(mEndTimeSelect.getText().toString())){
            result = false;
        }
        if(!checkDate(mReasonDetail.getText().toString())){
            result = false;
        }
        if(!checkDate(mTypeSelect.getText().toString())){
            result = false;
        }
        if(!checkDate(mDuration.getText().toString())){
            result = false;
        }
        return result;

    }

    private boolean checkDate(String s){
        if(s!=null && !s.equals("") && !s.equals(getString(R.string.selectHint)))
            return true;
        else
            return false;
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
                addLeaveForm();

            }else if(entryType.equals("show")){
                //查看模式
                initViewsShow();
                mApplyDetailPresenterImpl.attachView(this);
                mApplyDetailPresenterImpl.setAddApplyType(HostType.DETAIL_APPLY_LEAVE);
                mApplyDetailPresenterImpl.beforeRequest();
                mApplyDetailPresenterImpl.getApplyDetail(mainFormID);
                mBottomCommit.setVisibility(View.INVISIBLE);
                mBottomApprove.setVisibility(View.INVISIBLE);
                mIsFirstCreateForm = false;
            }else{
                //审批模式
                initViewsShow();
                mDoApprovePresenterImpl.attachView(this);
                mApplyDetailPresenterImpl.attachView(this);
                mApplyDetailPresenterImpl.setAddApplyType(HostType.DETAIL_APPLY_LEAVE);
                mApplyDetailPresenterImpl.beforeRequest();
                mApplyDetailPresenterImpl.getApplyDetail(mainFormID);
                mBottomCommit.setVisibility(View.INVISIBLE);
                mBottomApprove.setVisibility(View.VISIBLE);
                mIsFirstCreateForm = false;

            }

        }
        initCache();



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
    @Override
    public void onBackPressed() {

        if((pvTime!=null) &&
                (pvOptions!=null)  &&
                (pvTime.isShowing() || pvOptions.isShowing())){

            if(TimePickerNum == DEFAULT)
                pvOptions.dismiss();
            else
                pvTime.dismiss();

            return;
        }

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
                        mPhoto.setImageURI(Uri.fromFile(new File(headerPathTemp)));
                    }else{
                        headerPathTemp= UriUtils.getPath(this, originalUri);
                        mPhoto.setImageURI(Uri.fromFile(new File(headerPathTemp)));
                    }
                }
                break;
            //从照相机选择
            case RESULT_PICK_FROM_CAMERA_NORMAL:

                if (resultCode == RESULT_OK ) {
                    mPhoto.setImageURI(Uri.fromFile(new File(headerPathTemp)));

                }
                break;
            case Const.PHOTO_PREVIEW:

                if (data != null && data.getExtras() != null) {

                    ArrayList<PhotoModel> p = (ArrayList<PhotoModel>) data.getExtras().getSerializable("photos");

                    this.photos = (ArrayList<PhotoModel>) data.getExtras().getSerializable("photos");


                    //因为只有一张图所以如果删除则返回空集合
                    if(this.photos.size() == 0){

                        ImagePipeline imagePipeline = Fresco.getImagePipeline();
                        imagePipeline.evictFromCache(Uri.fromFile(new File(headerPathTemp)));
                        mPhoto.setImageURI("");
                        headerPathTemp = "";


                    }


                }

                break;

        }


    }
    //更新数据源并启动预览画面
    private void startPreview() {
        if((headerPathTemp == null) || (headerPathTemp.equals(""))){

            return;

        }
        photos.clear();
        photos.add(new PhotoModel(headerPathTemp,false));
        Intent intent = new Intent(this, PhotoPreviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("photos", photos);
        bundle.putInt("position", 0);
        bundle.putBoolean("show", true);
        intent.putExtra("shows",true);
        intent.putExtra("delete",isPhotoDeleteable);
        intent.putExtras(bundle);
        this.startActivityForResult(intent, Const.PHOTO_PREVIEW);
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

    //step1:新增请假单
    private void addLeaveForm(){
        if(!mIsFirstCreateForm){
            mAddApplyPresenterImpl.beforeRequest();
        }
        mAddApplyPresenterImpl.setAddApplyType(ADD_APPLY_LEAVE);
        mAddApplyPresenterImpl.addApply();


    }
    //修改加班单
    private void editLeaveForm(List<String> data){
        mEditApplyPresenterImpl.setAddApplyType(EDIT_APPLY_LEAVE);
        HashMap<String,Object> map = new HashMap<>();
        map.put("beginTime",mStartTimeSelect.getText().toString());
        map.put("endTime",mEndTimeSelect.getText().toString());
        map.put("workType",mTypeSelect.getText().toString());
        map.put("content",mReasonDetail.getText().toString());
        map.put("length",mDuration.getText().toString());
        String imgs = "";
        if(data != null && data.size()>0){
            for(int i = 0;i<data.size();i++){
                imgs+=data.get(i);
                if(i!=data.size()-1)
                    imgs+=",";
            }
        }
        map.put("imgs",imgs);
        String log = new Gson().toJson(map);
        Log.i("Retrofit","editExtraWorkForm commit log body:"+log);
        mEditApplyPresenterImpl.beforeRequest();
        mEditApplyPresenterImpl.editApply(mainFormID,map);

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
                    if(!TextUtils.isEmpty(headerPathTemp)) {
                        String newPath = createNewFilePath(headerPathTemp);
                        Log.i("Retrofit", "newPath head:" + newPath);
                        CompressUtils.getInstance().compressAndGenImage(headerPathTemp, newPath, 500, false);
                        list.add(newPath);
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
            if(!TextUtils.isEmpty(headerPathTemp)){
                commitImgs();
            }else{
                editLeaveForm(null);
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
            editLeaveForm(data.getData());

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
    public void showProgress(int reqType) {
        if(mLoadDialog==null) {
            switch (reqType) {
                case ADD_APPLY_LEAVE:
                case UPLOAD:
                case EDIT_APPLY_LEAVE:
                case APPLY_DO_PASS:
                case APPLY_DO_REJECT:
                    mLoadDialog = DialogUtils.create(this, DialogUtils.TYPE_UPDATE);
                    mLoadDialog.show();
                    break;
                case DETAIL_APPLY_LEAVE:
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
//        if(mLoadDialog!=null){
//            switch (reqType){
//                case ADD_APPLY_LEAVE:
//                case UPLOAD:
//                case EDIT_APPLY_LEAVE:
//                    mLoadDialog.dismiss();
//                    mLoadDialog = null;
//                    break;
//                default:
//                    break;
//            }
//        }


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
        super.onDestroy();
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

    }
    @Override
    public void getApplyDetailCompleted(ApplyDetailEntity data) {
        if(data.getCode().equals("ok") && data.getData()!=null){
            mApproverHeader.setImageURI(data.getData().getAvatar());
            mApproverName.setText(data.getData().getAssessor());
            mStartTimeSelect.setText(data.getData().getBeginTime());
            mEndTimeSelect.setText(data.getData().getEndTime());
            mReasonDetail.setText(data.getData().getContent());
            mDuration.setText(data.getData().getLength());
            mTypeSelect.setText(data.getData().getWorkType());
            headerPathTemp = data.getData().getImgs();
            mPhoto.setImageURI(headerPathTemp);

        }
        if(mLoadDialog!=null){
            mLoadDialog.dismiss();
            mLoadDialog = null;
        }
    }

}
