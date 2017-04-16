package com.home.rw.mvp.ui.activitys.social;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.common.Const;
import com.home.rw.common.HostType;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.entity.TopicDetailEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.presenter.impl.FocusPresenterImpl;
import com.home.rw.mvp.presenter.impl.TopicDetailPresenterImpl;
import com.home.rw.mvp.presenter.impl.ZanPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.activitys.mineme.FeedBackActivity;
import com.home.rw.mvp.view.FocusView;
import com.home.rw.mvp.view.TopicDetailView;
import com.home.rw.mvp.view.ZanView;
import com.home.rw.utils.DateUtils;
import com.home.rw.utils.FrescoUtils;
import com.home.rw.utils.PreferenceUtils;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoPreviewActivity;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class CommDetailActivity extends BaseActivity implements TopicDetailView,FocusView,ZanView{


    private CommunicationEntity.DataEntity.ResLst data;

    ArrayList<PhotoModel> photos = new ArrayList<>();

    private final int COMPRESS_WIDTH = 200;

    private final int COMPRESS_HEIGH = 120;

    private int selectedPosition = 0;

    private String entryType = "Common";

    private String mMyID = "";

    private String mTopicID = "";

    @Inject
    TopicDetailPresenterImpl mTopicDetailPresenterImpl;

    @Inject
    FocusPresenterImpl mFocusPresenterImpl;

    @Inject
    ZanPresenterImpl mZanPresenterImpl;
    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rightText)
    TextView rightText;

    @BindView(R.id.tv_title)
    TextView mTitle;

    @BindView(R.id.tv_time)
    TextView mTime;

    @BindView(R.id.tv_content)
    TextView mContent;

    @BindView(R.id.rightTextFacus)
    TextView rightTextFacus;

    @BindView(R.id.tv_num)
    TextView mNum;

    @BindView(R.id.ll_imgContainer)
    LinearLayout mContainer;

    @BindView(R.id.iv_picture1)
    SimpleDraweeView  mPic1;

    @BindView(R.id.iv_picture2)
    SimpleDraweeView  mPic2;

    @BindView(R.id.iv_picture3)
    SimpleDraweeView  mPic3;

    @BindView(R.id.iv_zan)
    ImageView mZan;

    @BindView(R.id.iv_feed_back)
    ImageView mFeedBack;

    @OnClick({R.id.back,
            R.id.iv_picture1,
            R.id.iv_picture2,
            R.id.iv_picture3,
            R.id.iv_zan,
            R.id.iv_feed_back,
            R.id.rightTextFacus


    })
    public void onClick(View v){
        switch(v.getId()){
            case R.id.back:
                Intent intent = new Intent();
                intent.putExtra("newData",data);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.iv_picture1:
                selectedPosition = 0;
                startPreview();
                break;
            case R.id.iv_picture2:
                selectedPosition = 1;
                startPreview();
                break;
            case R.id.iv_picture3:
                selectedPosition = 2;
                startPreview();
                break;
            case R.id.rightTextFacus:
                if(data == null)
                    return;
                mFocusPresenterImpl.setReqType(HostType.FOCUS);
                mFocusPresenterImpl.doFocus(data.getCreatedBy());
                break;
            case R.id.iv_zan:
                if(data == null)
                    return;
                mZanPresenterImpl.zan(data.getId());
                break;
            case R.id.iv_feed_back:
                if(data == null)
                    return;
                Intent intent1 = new Intent(this, FeedBackActivity.class);
                intent1.putExtra("mTopicId",data.getId());
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("newData",data);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_comm_detail;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        mTopicDetailPresenterImpl.attachView(this);
        mFocusPresenterImpl.attachView(this);
        mZanPresenterImpl.attachView(this);
        mMyID = String.valueOf(PreferenceUtils.getPrefLong(this,"ID",0));
        mback.setImageResource(R.drawable.btn_back);
        data = (CommunicationEntity.DataEntity.ResLst)getIntent().getSerializableExtra("commData");
        mTopicID = getIntent().getStringExtra("id");
        entryType = getIntent().getStringExtra("entryType");
        if(data == null){
            //根据id获取帖子详情
            if(mTopicID!=null){
                mTopicDetailPresenterImpl.getTpoicDetial(mTopicID);
            }
            return;
        }else{
            resolveData(data);
        }


    }

    private void resolveData(CommunicationEntity.DataEntity.ResLst data){
        if(String.valueOf(PreferenceUtils.getPrefLong(this,"ID",0)).equals(data.getCreatedBy())){
            rightTextFacus.setVisibility(View.GONE);
            mFeedBack.setVisibility(View.GONE);
        }else{
            rightTextFacus.setVisibility(View.VISIBLE);
        }
        if((entryType!=null) && (entryType.equals("Other"))){
            rightTextFacus.setVisibility(View.GONE);
        }

        if((entryType!=null) && (entryType.equals("HomePage"))){
            rightTextFacus.setVisibility(View.GONE);
            mZan.setVisibility(View.GONE);
            mFeedBack.setVisibility(View.GONE);
            mNum.setVisibility(View.GONE);
        }
        midText.setText(data.getAuthor());

        rightText.setVisibility(View.GONE);
        if(data.getFocus().equals("1")){
            rightTextFacus.setText(getString(R.string.Facused));
            rightTextFacus.setTextColor(Color.WHITE);
            rightTextFacus.setBackgroundResource(R.drawable.shape_detail_facus_unable);
            rightTextFacus.setEnabled(false);
        }else{
            rightTextFacus.setText(getString(R.string.addFacus));
            rightTextFacus.setTextColor(Color.parseColor("#0034F7"));
            rightTextFacus.setBackgroundResource(R.drawable.shape_detail_facus_enable);
            rightTextFacus.setEnabled(true);
        }
        mTitle.setText(data.getTitle());
        if(!TextUtils.isEmpty(data.getCreatedDate()))
            mTime.setText(data.getCreatedDate());
        else
            mTime.setText("");
        mContent.setText(data.getContent());
        mNum.setText(""+data.getSupportNum());
        if(data.getSupport().equals("1")){
            mZan.setImageResource(R.drawable.icon_zaned);
            mZan.setEnabled(false);

        }

        if(!TextUtils.isEmpty(data.getImgs())){
            String[] imgs = data.getImgs().split(",");
            switch (imgs.length) {
                case 0:
                    mContainer.setVisibility(View.GONE);
                    break;
                case 1:
                    mPic1.setVisibility(View.VISIBLE);
                    FrescoUtils.load(Uri.parse(imgs[0]), mPic1, COMPRESS_WIDTH, COMPRESS_HEIGH);

                    mPic2.setVisibility(View.INVISIBLE);

                    mPic3.setVisibility(View.GONE);
                    photos.add(new PhotoModel(imgs[0]));
                    break;
                case 2:
                    mPic1.setVisibility(View.VISIBLE);
                    mPic2.setVisibility(View.VISIBLE);

                    FrescoUtils.load(Uri.parse(imgs[0]), mPic1, COMPRESS_WIDTH, COMPRESS_HEIGH);
                    FrescoUtils.load(Uri.parse(imgs[1]), mPic2, COMPRESS_WIDTH, COMPRESS_HEIGH);

                    mPic3.setVisibility(View.GONE);

                    photos.add(new PhotoModel(imgs[0]));
                    photos.add(new PhotoModel(imgs[1]));
                    break;
                case 3:
                    mPic1.setVisibility(View.VISIBLE);
                    mPic2.setVisibility(View.VISIBLE);
                    mPic3.setVisibility(View.VISIBLE);

                    FrescoUtils.load(Uri.parse(imgs[0]), mPic1, COMPRESS_WIDTH, COMPRESS_HEIGH);
                    FrescoUtils.load(Uri.parse(imgs[1]), mPic2, COMPRESS_WIDTH, COMPRESS_HEIGH);
                    FrescoUtils.load(Uri.parse(imgs[2]), mPic3, COMPRESS_WIDTH, COMPRESS_HEIGH);

                    photos.add(new PhotoModel(imgs[0]));
                    photos.add(new PhotoModel(imgs[1]));
                    photos.add(new PhotoModel(imgs[2]));
                    break;
                default:
                    break;
            }
        }else{
            mContainer.setVisibility(View.GONE);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }
    private void startPreview() {
        Intent intent = new Intent(this, PhotoPreviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("photos", photos);
        bundle.putInt("position", selectedPosition);
        bundle.putBoolean("show", true);
        intent.putExtra("shows",true);
        intent.putExtra("delete",false);
        intent.putExtras(bundle);
        this.startActivityForResult(intent, Const.PHOTO_PREVIEW);
    }
    protected void onDestroy() {
        Log.i("CommDetail","onDestroy");
//        FrescoUtils.clearCache();
//        FrescoUtils.clearImgMemory(mPic1);
//        FrescoUtils.clearImgMemory(mPic2);
//        FrescoUtils.clearImgMemory(mPic3);
        if(mTopicDetailPresenterImpl!=null){
            mTopicDetailPresenterImpl = null;
        }
        if(mFocusPresenterImpl!=null){
            mFocusPresenterImpl = null;
        }
        if(mZanPresenterImpl!=null){
            mZanPresenterImpl = null;
        }

        super.onDestroy();
    }

    @Override
    public void getTopicDetailCompleted(TopicDetailEntity data) {
        if(data.getCode().equals("ok")){
            this.data = data.getData();
            resolveData(this.data);
        }
    }

    @Override
    public void showProgress(int reqType) {

    }

    @Override
    public void hideProgress(int reqType) {

    }

    @Override
    public void showErrorMsg(int reqType, String msg) {
        if(reqType == HostType.FOCUS){
            Toast.makeText(this,getString(R.string.focusFailed),Toast.LENGTH_SHORT).show();
        }else if(reqType == HostType.ZAN){
            Toast.makeText(this,getString(R.string.zanFailed),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void doFocusCompleted(BaseEntity data) {
        if(data.getCode().equals("ok")){
            rightTextFacus.setText(getString(R.string.Facused));
            rightTextFacus.setTextColor(Color.WHITE);
            rightTextFacus.setBackgroundResource(R.drawable.shape_detail_facus_unable);
            rightTextFacus.setEnabled(false);
            this.data.setFocus("1");
            Toast.makeText(this,getString(R.string.focusSucceed),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void zanCompleted(BaseEntity data) {
        if(data.getCode().equals("ok")){
            int supNum = Integer.parseInt(this.data.getSupportNum());
            String newSupNum = String.valueOf(supNum+1);
            mZan.setImageResource(R.drawable.icon_zaned);
            mZan.setEnabled(false);
            mNum.setText(newSupNum);
            this.data.setSupport("1");
            this.data.setSupportNum(newSupNum);
            Toast.makeText(this,getString(R.string.zanSucceed),Toast.LENGTH_SHORT).show();
        }
    }
}
