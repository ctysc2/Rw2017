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

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.home.rw.R;
import com.home.rw.common.Const;
import com.home.rw.greendaohelper.UserInfoDaoHelper;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.utils.DateUtils;
import com.home.rw.utils.KeyBoardUtils;
import com.home.rw.utils.UriUtils;
import com.home.rw.widget.PicTakerPopWindow;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoPreviewActivity;
import com.polites.MathUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class AskForLeaveActivity extends BaseActivity {

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

    private ArrayList<String> Items = new ArrayList<String>();

    private static final int DEFAULT = -1;

    private static final int START_TIME = 0;

    private static final int END_TIME = 1;

    private int TimePickerNum = START_TIME;

    private static  final String root = Environment.getExternalStorageDirectory().
            getAbsolutePath()+"/"+"RwCache";
    private  String headerPathTemp;

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
    @OnClick({R.id.back,
            R.id.rl_startTime,
            R.id.rl_endTime,
            R.id.rl_type,
            R.id.iv_update_photo,
            R.id.iv_taked_picture1
    })
    public void onClick(View v){
        switch(v.getId()){
            case R.id.back:
                finish();
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
        mStartTimeSelect.setText("2016年12月11日 9:30");

        //结束时间
        mRlEndTime.setEnabled(false);
        mEndTimeSelect.setText("2016年12月12日 9:30");

        //请假类型
        mRlType.setEnabled(false);
        mTypeSelect.setText("事假");

        //请假时间
        mDuration.setEnabled(false);
        mDuration.setText("1小时");

        //请假理由
        mReasonDetail.setEnabled(false);
        mReasonDetail.setText("身体不适请假半年");
        //上传头像
        iv_photo.setVisibility(View.GONE);
        headerPathTemp = "http://img1.gamersky.com/image2016/11/20161114zd_337_39/gamersky_01small_02_20161114165972E.jpg";
        mPhoto.setImageURI(headerPathTemp);

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
    private boolean checkDate(String s){
        if(s!=null && !s.equals("") && !s.equals(getString(R.string.selectHint)))
            return true;
        else
            return false;
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
                mBottomCommit.setVisibility(View.INVISIBLE);
                mBottomApprove.setVisibility(View.INVISIBLE);
            }else{
                //审批模式
                initViewsShow();
                mBottomCommit.setVisibility(View.INVISIBLE);
                mBottomApprove.setVisibility(View.VISIBLE);

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

}
