package com.home.rw.mvp.ui.activitys.social;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.home.rw.R;
import com.home.rw.common.Const;
import com.home.rw.common.HostType;
import com.home.rw.listener.AlertDialogListener;
import com.home.rw.mvp.entity.UploadEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.presenter.impl.PublishPresenterImpl;
import com.home.rw.mvp.presenter.impl.UploadPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.view.PublishView;
import com.home.rw.mvp.view.UploadView;
import com.home.rw.utils.CompressUtils;
import com.home.rw.utils.DialogUtils;
import com.home.rw.utils.FrescoUtils;
import com.home.rw.utils.KeyBoardUtils;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoPreviewActivity;
import com.photoselector.ui.PhotoSelectorActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

import static com.home.rw.common.Const.PHOTO_SELECT;

public class CommPublishActivity extends BaseActivity implements AlertDialogListener,PublishView,UploadView{

    ArrayList<PhotoModel> photos = new ArrayList<>();

    private static  final String root = Environment.getExternalStorageDirectory().
            getAbsolutePath()+"/"+"RwCache";

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

    @BindView(R.id.rl_type)
    RelativeLayout mRLType;

    @BindView(R.id.tv_typeSelect)
    TextView mTypeSelect;

    @BindView(R.id.et_title)
    EditText mTitle;

    @BindView(R.id.et_detail)
    EditText mDetail;

    @Inject
    PublishPresenterImpl mPublishPresenterImpl;

    @Inject
    UploadPresenterImpl mUploadPresenterImpl;

    private int mCatId = -1;

    //发布类型选择器
    private OptionsPickerView pvOptions;

    private ArrayList<String> Items = new ArrayList<String>();
    @OnClick({
            R.id.back,
            R.id.iv_taked_picture1,
            R.id.iv_taked_picture2,
            R.id.iv_taked_picture3,
            R.id.iv_update_photo,
            R.id.rl_type,
            R.id.bt_commit

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
            case R.id.rl_type:
                pvOptions.show();
                break;
            case R.id.bt_commit:
                if(checkCommitData()){
                    commitImgs();
//                    mPublishPresenterImpl.beforeRequest();
//                    HashMap<String,Object> map = new HashMap<>();
//                    mPublishPresenterImpl.publish(map);
                    return;
                }
                Toast.makeText(this,getString(R.string.checkinput),Toast.LENGTH_SHORT).show();

                break;
            default:
                break;

        }

    }
    private String createNewFilePath(String oldPath){
        String newPath = "";

        String[] data = oldPath.split("/");
        int length = data.length;
        String fileName = data[length-1];

        newPath = root+"/"+fileName;

        return newPath;
    }
    //上传图片
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

                    if(photos.size()!=0){
                        for(int i = 0;i<photos.size();i++){
                            String newPath = createNewFilePath(photos.get(i).getOriginalPath());
                            Log.i("Retrofit","newPath photos:"+newPath);
                            CompressUtils.getInstance().compressAndGenImage(photos.get(i).getOriginalPath(),newPath,500,false);
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

    private boolean checkDate(String s){
        if(s!=null && !s.equals("") && !s.equals(getString(R.string.selectHint)))
            return true;
        else
            return false;
    }
    private boolean checkCommitData(){
        boolean result = true;
        if(!checkDate(mTitle.getText().toString())){
            result = false;
        }
        if(!checkDate(mDetail.getText().toString())){
            result = false;
        }
        if(mCatId == -1){
            result = false;
        }
        return result;

    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_comm_publish;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(R.string.publish);
        mback.setImageResource(R.drawable.btn_back);
        Items.add(getString(R.string.projectCommunication));
        Items.add(getString(R.string.ActivityCommunication));
        Items.add(getString(R.string.CultureCommunication));
        Items.add(getString(R.string.CoopCommunication));
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
                mCatId = options1;
            }
        });
        mPublishPresenterImpl.attachView(this);
        mPublishPresenterImpl.setAddApplyType(HostType.TOPIC_PUBLISH);
        mUploadPresenterImpl.attachView(this);
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
        if((pvOptions!=null)  &&
                pvOptions.isShowing() ){
                pvOptions.dismiss();

            return;
        }
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

    @Override
    public void publishCompleted(BaseEntity data) {
        if(data.getCode().equals("ok")){
            if(mLoadDialog!=null){
                mLoadDialog.dismiss();
                mLoadDialog = null;
            }
            Toast.makeText(this,getString(R.string.commitSuccessed),Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this,getString(R.string.commitFailed),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showProgress(int reqType) {
        if(mLoadDialog == null){
            mLoadDialog = DialogUtils.create(this, DialogUtils.TYPE_UPDATE);
            mLoadDialog.show();
        }
    }

    @Override
    public void hideProgress(int reqType) {

    }

    @Override
    public void showErrorMsg(int reqType, String msg) {
        if(mLoadDialog!=null){
            mLoadDialog.dismiss();
            mLoadDialog = null;
        }
        Toast.makeText(this,getString(R.string.commitFailed),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void uploadComplete(UploadEntity data) {
        if(data.getCode().equals("ok")){
            //发布
            commitPublish(data.getData());

        }else{
            if(mLoadDialog!=null){
                mLoadDialog.dismiss();
                mLoadDialog = null;
            }
            Toast.makeText(this,getString(R.string.commitFailed),Toast.LENGTH_SHORT).show();
        }
    }

    //修改外出单
    private void commitPublish(List<String> data){
        HashMap<String,Object> map = new HashMap<>();
        map.put("title",mTitle.getText().toString());
        map.put("content",mDetail.getText().toString());
        map.put("catId",mCatId);
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
        Log.i("Retrofit","commitPublish commit log body:"+log);
        mPublishPresenterImpl.publish(map);

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = getCurrentFocus();
        new KeyBoardUtils(event,im,v).hideKeyBoardIfNecessary();
        return super.dispatchTouchEvent(event);
    }
}
