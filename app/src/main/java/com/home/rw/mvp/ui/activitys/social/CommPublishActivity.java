package com.home.rw.mvp.ui.activitys.social;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.common.Const;
import com.home.rw.listener.AlertDialogListener;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.utils.DialogUtils;
import com.home.rw.utils.FrescoUtils;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoPreviewActivity;
import com.photoselector.ui.PhotoSelectorActivity;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.home.rw.common.Const.PHOTO_SELECT;

public class CommPublishActivity extends BaseActivity implements AlertDialogListener {

    ArrayList<PhotoModel> photos = new ArrayList<>();

    private int selectedPosition = 0;

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.iv_taked_picture1)
    SimpleDraweeView mPic1;

    @BindView(R.id.iv_taked_picture2)
    SimpleDraweeView mPic2;

    @BindView(R.id.iv_taked_picture3)
    SimpleDraweeView mPic3;

    @BindView(R.id.iv_taked_picture4)
    SimpleDraweeView mPic4;

    @OnClick({
            R.id.back,
            R.id.iv_taked_picture1,
            R.id.iv_taked_picture2,
            R.id.iv_taked_picture3,
            R.id.iv_update_photo,

    })
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                mAlertDialog = DialogUtils.create(this,DialogUtils.TYPE_ALERT);
                mAlertDialog.show(this,getString(R.string.editExitHint1),getString(R.string.editExitHint2));
                break;
            case R.id.iv_taked_picture1:
                selectedPosition = 0;
                startPreview();
                break;
            case R.id.iv_taked_picture2:
                selectedPosition = 1;
                startPreview();
                break;
            case R.id.iv_taked_picture3:
                selectedPosition = 2;
                startPreview();
                break;
            case R.id.iv_update_photo:
                startSelect();
                break;
            default:
                break;

        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_comm_publish;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        midText.setText(R.string.publish);
        mback.setImageResource(R.drawable.btn_back);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    //从图库中选择附件
    private void startSelect() {
        //跳转至最终的选择图片页面
        Intent intent = new Intent(this, PhotoSelectorActivity.class);
        intent.putExtra(PhotoSelectorActivity.KEY_MAX, 3);
        intent.putExtra("photos", photos);
        this.startActivityForResult(intent, PHOTO_SELECT);
    }

    //更新数据源并启动预览画面
    private void startPreview() {
        if((photos == null)||(photos.size() == 0)){
            return;
        }
        Intent intent = new Intent(this, PhotoPreviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("photos", photos);
        bundle.putInt("position", selectedPosition);
        bundle.putBoolean("show", true);
        intent.putExtra("shows",true);
        intent.putExtra("delete",true);
        intent.putExtras(bundle);
        this.startActivityForResult(intent, Const.PHOTO_PREVIEW);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Const.PHOTO_PREVIEW:
            case PHOTO_SELECT:
                if (data != null && data.getExtras() != null) {

                    this.photos = (ArrayList<PhotoModel>) data.getExtras().getSerializable("photos");

                    mPic1.setVisibility(View.INVISIBLE);
                    mPic2.setVisibility(View.INVISIBLE);
                    mPic3.setVisibility(View.INVISIBLE);

                    for(int i = 0;i<photos.size();i++){
                        PhotoModel model = photos.get(i);
                        switch (i){
                            case 0:
                                mPic1.setVisibility(View.VISIBLE);
                                FrescoUtils.load(Uri.fromFile(new File(model.getOriginalPath())),mPic1,200,120);
                                break;
                            case 1:
                                mPic2.setVisibility(View.VISIBLE);
                                FrescoUtils.load(Uri.fromFile(new File(model.getOriginalPath())),mPic2,200,120);
                                break;
                            case 2:
                                mPic3.setVisibility(View.VISIBLE);
                                FrescoUtils.load(Uri.fromFile(new File(model.getOriginalPath())),mPic3,200,120);
                                break;
                            default:
                                break;
                        }

                    }

                }
//                else{
//                    if(requestCode == PHOTO_SELECT){
//                        this.photos.clear();
//                        mPic1.setVisibility(View.INVISIBLE);
//                        mPic2.setVisibility(View.INVISIBLE);
//                        mPic3.setVisibility(View.INVISIBLE);
//                        FrescoUtils.clearCache();
//                    }
//                }
                break;
            default:
                break;
        }

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

        if((mAlertDialog != null) && (mAlertDialog.isShowing())){
            mAlertDialog.dismiss();
        }else{
            mAlertDialog = DialogUtils.create(this,DialogUtils.TYPE_ALERT);
            mAlertDialog.show(this,getString(R.string.editExitHint1),getString(R.string.editExitHint2));
        }
    }
}
