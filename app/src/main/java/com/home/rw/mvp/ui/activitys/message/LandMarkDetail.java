package com.home.rw.mvp.ui.activitys.message;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.common.Const;
import com.home.rw.mvp.entity.GrandEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.entity.message.RwNoticeDetailEntity;
import com.home.rw.mvp.entity.message.TopicCommonEntity;
import com.home.rw.mvp.presenter.impl.RwNoticeDetailPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.view.RwNoticeDetailView;
import com.home.rw.utils.DateUtils;
import com.home.rw.utils.FrescoUtils;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoPreviewActivity;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LandMarkDetail extends BaseActivity implements RwNoticeDetailView{

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.tv_title)
    TextView mTitle;

    @BindView(R.id.tv_date)
    TextView mDate;

    @BindView(R.id.iv_img)
    SimpleDraweeView mImg;

    @BindView(R.id.tv_content)
    TextView mContent;

    @Inject
    RwNoticeDetailPresenterImpl mRwNoticeDetailPresenterImpl;

    ArrayList<PhotoModel> photos = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_land_mark_detail;
    }

    @OnClick({R.id.back,
              R.id.iv_img
})
    public void onClick(View v){
        switch(v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.iv_img:
                if(photos.size()>0)
                    startPreview();
                break;
            default:
                break;
        }
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        mback.setImageResource(R.drawable.btn_back);
        mRwNoticeDetailPresenterImpl.attachView(this);
        TopicCommonEntity entity = (TopicCommonEntity)getIntent().getSerializableExtra("data");
        String id;
        if(entity!=null){
            midText.setText(entity.getPromulgator());
            mTitle.setText(entity.getTitle());
            mContent.setText(entity.getContent());
            if(!TextUtils.isEmpty(entity.getPubTime())){
                mDate.setText(DateUtils.getTime(new Date(Long.parseLong(entity.getPubTime()))));
            }
            if(!TextUtils.isEmpty(entity.getImgs())){
                mImg.setImageURI(entity.getImgs());
                photos.add(new PhotoModel(entity.getImgs()));
            }
        }else if((id = getIntent().getStringExtra("id")) != null){
            //根据id获取详情
            mRwNoticeDetailPresenterImpl.getRwNoticeDetail(id);
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
        bundle.putInt("position", 0);
        bundle.putBoolean("show", true);
        intent.putExtra("shows",true);
        intent.putExtra("delete",false);
        intent.putExtras(bundle);
        this.startActivityForResult(intent, Const.PHOTO_PREVIEW);
    }

    @Override
    public void getRwNoticeDetailCompleted(RwNoticeDetailEntity data) {
        if(data.getCode().equals("ok")){
            TopicCommonEntity entity = data.getData();
            midText.setText(entity.getPromulgator());
            mTitle.setText(entity.getTitle());
            mContent.setText(entity.getContent());
            if(!TextUtils.isEmpty(entity.getPubTime())){
                mDate.setText(DateUtils.getTime(new Date(Long.parseLong(entity.getPubTime()))));
            }
            if(!TextUtils.isEmpty(entity.getImgs())){
                mImg.setImageURI(entity.getImgs());
                photos.add(new PhotoModel(entity.getImgs()));
            }
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
        Toast.makeText(this,getString(R.string.loadFailed),Toast.LENGTH_SHORT).show();
    }
}
