package com.home.rw.mvp.ui.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.greendao.entity.UserInfo;
import com.home.rw.greendaohelper.UserInfoDaoHelper;
import com.home.rw.mvp.entity.LinkedEntity;
import com.home.rw.mvp.entity.UploadEntity;
import com.home.rw.mvp.entity.UserInfoEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.presenter.impl.LinkedPresenterImpl;
import com.home.rw.mvp.presenter.impl.ModifiUserInfoPresenterImpl;
import com.home.rw.mvp.presenter.impl.UploadPresenterImpl;
import com.home.rw.mvp.presenter.impl.UserInfoPresenterImpl;
import com.home.rw.mvp.ui.activitys.WebViewActivity;
import com.home.rw.mvp.ui.activitys.message.LandMarkDetail;
import com.home.rw.mvp.ui.activitys.mineme.ChangePassWord;
import com.home.rw.mvp.ui.activitys.mineme.OrderActivity;
import com.home.rw.mvp.ui.activitys.mineme.SettingActivity;
import com.home.rw.mvp.ui.activitys.mineme.WalletActivity;
import com.home.rw.mvp.ui.activitys.social.CommDetailActivity;
import com.home.rw.mvp.ui.activitys.social.CommListActivity;
import com.home.rw.mvp.ui.activitys.social.FocusListActivity;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;
import com.home.rw.mvp.view.LinkedView;
import com.home.rw.mvp.view.ModifiUserInfoView;
import com.home.rw.mvp.view.UploadView;
import com.home.rw.mvp.view.UserInfoView;
import com.home.rw.utils.DialogUtils;
import com.home.rw.utils.PreferenceUtils;
import com.home.rw.utils.UriUtils;
import com.home.rw.widget.GenderTakerPopWindow;
import com.home.rw.widget.PicTakerPopWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.home.rw.common.HostType.LOGIN;
import static com.home.rw.common.HostType.MODIFI_USER_INFO;
import static com.home.rw.common.HostType.USER_INFO;

/**
 * Created by cty on 2016/12/13.
 */

public class MineMeFragment extends BaseFragment implements UploadView,UserInfoView,ModifiUserInfoView,LinkedView{
    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.back)
    ImageButton mSetting;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rightText)
    TextView rightText;

    @BindView(R.id.iv_header)
    SimpleDraweeView mHeaderView;

    @BindView(R.id.rl_gender)
    RelativeLayout mGender;

    @BindView(R.id.tv_gender)
    TextView mtvGender;

    @BindView(R.id.tv_username)
    TextView mUserName;

    @BindView(R.id.tv_publish)
    TextView mPublish;

    @BindView(R.id.tv_focus)
    TextView mFocus;

    @BindView(R.id.compName)
    TextView mComp;

    @BindView(R.id.tv_phone)
    TextView mPhoneNum;

    @BindView(R.id.iv_ad)
    ImageView mAD;


    @Inject
    Activity mActivity;

    @Inject
    UploadPresenterImpl mUploadPresenterImpl;

    @Inject
    UserInfoPresenterImpl mUserInfoPresenterImpl;

    @Inject
    ModifiUserInfoPresenterImpl mModifiUserInfoPresenterImpl;

    @Inject
    LinkedPresenterImpl mLinkedPresenterImpl;

    //选择头像
    PicTakerPopWindow menuWindow;

    //选择性别
    GenderTakerPopWindow genderWindow;
    //图库
    private static final int RESULT_PICK_FROM_PHOTO_NORMAL = 0;

    //相机
    private static final int RESULT_PICK_FROM_CAMERA_NORMAL = 1;

    //剪切完成
    private static final int CROP_A_PICTURE = 2;

    private static  final String root = Environment.getExternalStorageDirectory().
            getAbsolutePath()+"/"+"RwCache";
    private  String headerPathTemp;

    private int mSettingType;

    private String headNetUrl = "";
    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener(){

        public void onClick(View v) {
            if(menuWindow!=null){
                menuWindow.dismiss();
                menuWindow = null;
            }
            if(genderWindow!=null){
                genderWindow.dismiss();
                genderWindow = null;
            }
            Intent intent;
            switch (v.getId()) {
                case R.id.btn_take_photo:
                    mSettingType = 0;
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(createTempFile()));
                    startActivityForResult(intent, RESULT_PICK_FROM_CAMERA_NORMAL);

                    break;
                case R.id.btn_pick_photo:
                    // 去图库中选择图片
                    mSettingType = 0;
                    if (Build.VERSION.SDK_INT < 19) {
                        intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");

                    } else {
                        intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    }
                    startActivityForResult(intent, RESULT_PICK_FROM_PHOTO_NORMAL);

                    break;
                case R.id.btn_male:
                    mSettingType = 1;
                    mModifiUserInfoPresenterImpl.beforeRequest();
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("gender","0");
                    mModifiUserInfoPresenterImpl.modifiUserInfo(map);
                    break;
                case R.id.btn_female:
                    mModifiUserInfoPresenterImpl.beforeRequest();
                    HashMap<String,Object> map1 = new HashMap<>();
                    map1.put("gender","1");
                    mSettingType = 2;
                    mModifiUserInfoPresenterImpl.modifiUserInfo(map1);
                    break;
                case R.id.btn_cancel:
                    break;
            }
        }
    };
    @OnClick({R.id.iv_header,
              R.id.back,
              R.id.rl_changePsw,
              R.id.rightText,
              R.id.rl_gender,
              R.id.ll_focus,
              R.id.ll_fabu,
              R.id.ib_wallet})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.iv_header:
                showHeaderSelectPopWin();
                break;
            case R.id.rl_gender:
                showGenderSelectPopWin();
                break;
            case R.id.back:
                startActivity(new Intent(mActivity, SettingActivity.class));
                break;
            case R.id.rl_changePsw:
                startActivity(new Intent(mActivity, ChangePassWord.class));
                break;
            case R.id.rightText:
                startActivity(new Intent(mActivity, OrderActivity.class));
                break;
            case R.id.ib_wallet:
                startActivity(new Intent(mActivity, WalletActivity.class));
                break;
            case R.id.ll_focus:
                startActivity(new Intent(mActivity, FocusListActivity.class));
                break;
            case R.id.ll_fabu:
                Intent intent = new Intent(mActivity, CommListActivity.class);
                intent.putExtra("startType",4);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {
        mToolBar.setBackgroundResource(R.color.transparent);
        rightText.setText(R.string.order);
        mSetting.setImageResource(R.drawable.btn_setting);
        initCache();
        mUploadPresenterImpl.attachView(this);
        mUserInfoPresenterImpl.attachView(this);
        mModifiUserInfoPresenterImpl.attachView(this);
        mLinkedPresenterImpl.attachView(this);
        mLinkedPresenterImpl.setReqType(HostType.OUT_LINK4);
        mLinkedPresenterImpl.getLinked();

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mineme;
    }

    //选择图片过程中创建临时文件
    private File createTempFile(){

        Date date = new Date();

        headerPathTemp = root+"/"+date.getTime()+".png";

        return new File(headerPathTemp);

    }

    //选择图片
    private void showHeaderSelectPopWin(){

        menuWindow = new PicTakerPopWindow(mActivity,itemsOnClick);
        menuWindow.showAtLocation(mHeaderView,
                Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);

    }
    //选择图片
    private void showGenderSelectPopWin(){

        genderWindow = new GenderTakerPopWindow(mActivity,itemsOnClick);
        genderWindow.showAtLocation(mHeaderView,
                Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);

    }
    private void initCache(){

        File mFilePath = new File(root);
        if (!mFilePath.exists()) {
            mFilePath.mkdirs();
        }

    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            // 从相册选择图片后返回图片的uri 并且执行
            case RESULT_PICK_FROM_PHOTO_NORMAL:
                Log.i("info", "RESULT_PICK_FROM_PHOTO_NORMAL");
                if (resultCode == getActivity().RESULT_OK && data != null) {
                    Uri originalUri = data.getData();

                    String[] proj = {MediaStore.Images.Media.DATA};

                    Cursor cursor = mActivity.getContentResolver().query(originalUri, proj, null, null, null);


                    if (cursor != null) {
                        cursor.moveToFirst();
                        String path = cursor.getString(0);
                        cursor.close();
                        cropImageUri(path);
                    }else{
                        String path= UriUtils.getPath(mActivity, originalUri);
                        cropImageUri(path);
                    }
                }
                break;
            //从照相机选择
            case RESULT_PICK_FROM_CAMERA_NORMAL:

                if (resultCode == mActivity.RESULT_OK ) {

                    cropImageUri(headerPathTemp);

                }
                break;
            case CROP_A_PICTURE:
                //剪裁后的图片更新为图像
                Log.i("info","CROP_A_PICTURE");
                if (resultCode == mActivity.RESULT_OK && data != null) {
                    Log.i("info","CROP_A_PICTURE2");
                    //上传头像
                    List<String> files = new ArrayList<>();
                    files.add(headerPathTemp);
                    mUploadPresenterImpl.beforeRequest();
                    mUploadPresenterImpl.processUpload(files);
                }
                break;
                }


        }

        private void cropImageUri(String path) {
            Log.i("info", "cropImageUri" );
            Log.i("info", "path1"+path );
            if(path == null)
                return;

            File file = new File(path);
            Intent intent = new Intent("com.android.camera.action.CROP");
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            // outputX outputY 是裁剪图片宽高
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY",100);
            // 设置为true直接返回bitmap
            intent.putExtra("return-data", false);
            intent.putExtra("noFaceDetection", true);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(createTempFile()));
            intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
            startActivityForResult(intent, CROP_A_PICTURE);
        }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(hidden){
            Log.i("cty","Mineme onHiddenChanged 隐藏");
        }else{
            Log.i("cty","Mineme onHiddenChanged 显示");
            //首先从数据库获取数据
            UserInfo user = UserInfoDaoHelper.getInstance().getUserInfoById(PreferenceUtils.getPrefLong(mActivity,"ID",0));
            if(user!=null){
                Log.i("GreenDao","获取user成功 id:"+user.getId());
                updateViewsByCache(user);
            }
            mUserInfoPresenterImpl.getUserInfo();

        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void uploadComplete(UploadEntity data) {
        if(data.getCode().equals("ok")){
            if (data.getData().size()>0){
                HashMap<String,Object> map = new HashMap<>();
                map.put("avatar",data.getData().get(0));
                mModifiUserInfoPresenterImpl.beforeRequest();
                mModifiUserInfoPresenterImpl.modifiUserInfo(map);
                headNetUrl = data.getData().get(0);
            }

        }
    }
    @Override
    public void showProgress(int reqType) {
        switch (reqType){
            case USER_INFO:
                break;
            case MODIFI_USER_INFO:
                if(mAlertDialog == null){
                    mAlertDialog = DialogUtils.create(mActivity,DialogUtils.TYPE_UPDATE);
                    mAlertDialog.show();
                }
                break;
            default:
                break;
        }
    }
    @Override
    public void hideProgress(int reqType) {
        if(mAlertDialog!=null){
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }

    }

    @Override
    public void showErrorMsg(int reqType,String msg) {
        Log.i("Retrofit","type error:"+reqType+" "+msg);
        switch (reqType){
            case MODIFI_USER_INFO:
                Toast.makeText(mActivity,getString(R.string.modifiFailed),Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }
    @Override
    public void getUserInfoCompleted(UserInfoEntity data) {
        Log.i("Retrofit","getUserInfoCompleted data:"+data.toString());
        updateViews(data.getData());
        headNetUrl = data.getData().getAvatar();
        UserInfo user = UserInfoDaoHelper.getInstance().parseEntity2UserInfo(data.getData());
        UserInfoDaoHelper.getInstance().insertUserInfo(user);

    }
    private void updateViewsByCache(UserInfo user){
        mUserName.setText(user.getRealName());
        mtvGender.setText(user.getGender().equals("0")?getString(R.string.male):getString(R.string.female));
        //if(!headNetUrl.equals(user.getAvatar()))
        mHeaderView.setImageURI(user.getAvatar());
        mFocus.setText(user.getFocusNum());
        mPublish.setText(user.getPubNum());
        mComp.setText(user.getCompany());
        String phone = user.getPhone();
        if(phone!=null){
            mPhoneNum.setText(phone.substring(0,2)+"***"+phone.substring(5,phone.length()));
        }else
            mPhoneNum.setText("");
    }
    private void updateViews(UserInfoEntity.DataEntity data){
        mUserName.setText(data.getRealname());
        mtvGender.setText(data.getGender().equals("0")?getString(R.string.male):getString(R.string.female));
       // if(!headNetUrl.equals(data.getAvatar()))
        mHeaderView.setImageURI(data.getAvatar());
        mFocus.setText(data.getFocusNum());
        mPublish.setText(data.getPubNum());
        mComp.setText(data.getCompany().getName());
        String phone = data.getPhone();
        if(phone!=null){
            mPhoneNum.setText(phone.substring(0,2)+"***"+phone.substring(5,phone.length()));
        }else
            mPhoneNum.setText("");

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mUploadPresenterImpl != null)
            mUploadPresenterImpl.onDestroy();

        if(mUserInfoPresenterImpl!=null)
            mUserInfoPresenterImpl.onDestroy();

        if(mModifiUserInfoPresenterImpl!=null)
            mModifiUserInfoPresenterImpl.onDestroy();

        if(mLinkedPresenterImpl!=null)
            mLinkedPresenterImpl.onDestroy();
    }

    @Override
    public void modifiUserInfoCompleted(BaseEntity data) {
        if(data.getCode().equals("ok")){
            Toast.makeText(mActivity,getString(R.string.modifiSuccessed),Toast.LENGTH_SHORT).show();

            switch (mSettingType){
                case 0:
                    mHeaderView.setImageURI(Uri.fromFile(new File(headerPathTemp)));
                    break;
                case 1:
                    mtvGender.setText(getString(R.string.male));
                    break;
                case 2:
                    mtvGender.setText(getString(R.string.female));
                    break;
                default:
                    break;

            }

            UserInfo user = UserInfoDaoHelper.getInstance().getUserInfoById(PreferenceUtils.getPrefLong(mActivity,"ID",0));

            if(user!=null){
                Log.i("GreenDao","设置user成功 id:"+user.getId());
                user.setAvatar(headerPathTemp);
                user.setGender(mtvGender.getText().toString().equals(getString(R.string.male))?"0":"1");
                UserInfoDaoHelper.getInstance().insertUserInfo(user);
            }
        }

    }

    @Override
    public void getLinkedCompleted(int reqType, LinkedEntity data) {
        if(data.getCode().equals("ok")){

            if(data.getData().size() == 0)
                return;

            final LinkedEntity.DataEntity entity = data.getData().get(0);
            Glide.with(this).load(data.getData().get(0).getImgs()).into(mAD);
            mAD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    if(entity.getType().equals("0")){
                        intent = new Intent(mActivity, WebViewActivity.class);
                        intent.putExtra("title",entity.getTitle());
                        intent.putExtra("url",entity.getToLink());
                        startActivity(intent);
                    }else if(entity.getType().equals("1")){
                        intent = new Intent(mActivity, LandMarkDetail.class);
                        intent.putExtra("id",entity.getId());
                        startActivity(intent);
                    }else {
                        intent = new Intent(mActivity, CommDetailActivity.class);
                        intent.putExtra("id",entity.getId());
                        startActivity(intent);
                    }
                }
            });
        }
    }
}
