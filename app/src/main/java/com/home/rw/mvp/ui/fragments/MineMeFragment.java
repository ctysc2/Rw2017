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
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.greendaohelper.UserInfoDaoHelper;
import com.home.rw.mvp.ui.activitys.mineme.ChangePassWord;
import com.home.rw.mvp.ui.activitys.mineme.OrderActivity;
import com.home.rw.mvp.ui.activitys.mineme.SettingActivity;
import com.home.rw.mvp.ui.activitys.mineme.WalletActivity;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;
import com.home.rw.utils.UriUtils;
import com.home.rw.widget.GenderTakerPopWindow;
import com.home.rw.widget.PicTakerPopWindow;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by cty on 2016/12/13.
 */

public class MineMeFragment extends BaseFragment {
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


    @Inject
    Activity mActivity;

    //选择投降
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
                        intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    }
                    startActivityForResult(intent, RESULT_PICK_FROM_PHOTO_NORMAL);

                    break;
                case R.id.btn_male:
                    mtvGender.setText(getString(R.string.male));
                    break;
                case R.id.btn_female:
                    mtvGender.setText(getString(R.string.female));
                    break;
                case R.id.btn_cancel:
                    UserInfoDaoHelper.getInstance().getDataByUser("15502145237",UserInfoDaoHelper.HEADURL);
                    break;
            }
        }
    };
    @OnClick({R.id.iv_header,
              R.id.back,
              R.id.rl_changePsw,
              R.id.rightText,
              R.id.rl_gender,
              R.id.ib_wallet})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.iv_header:
                showHeaderSelectPopWin();
//                Map<String,String> map = new HashMap<>();
//                map.put(UserInfoDaoHelper.NICKNAME,"cty");
//                map.put(UserInfoDaoHelper.HEADURL,"www.baidu.com");
//                map.put("hahah","sb");
//                UserInfoDaoHelper.getInstance().updateUserInfo("15502145237",map);
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
        mHeaderView.setImageURI("http://img5.imgtn.bdimg.com/it/u=116895778,83897167&fm=21&gp=0.jpg");

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mineme;
    }

    //选择图片过程中创建临时文件
    private File createTempFile(){

        Date date = new Date();

        headerPathTemp = root+"/"+date.getTime()+".jpg";

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
                   mHeaderView.setImageURI(Uri.fromFile(new File(headerPathTemp)));

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
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            startActivityForResult(intent, CROP_A_PICTURE);
        }
}
