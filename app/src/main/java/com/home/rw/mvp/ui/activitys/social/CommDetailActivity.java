package com.home.rw.mvp.ui.activitys.social;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.common.Const;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.activitys.mineme.FeedBackActivity;
import com.home.rw.utils.FrescoUtils;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoPreviewActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class CommDetailActivity extends BaseActivity {

    private CommunicationEntity.DataEntity data;

    ArrayList<PhotoModel> photos = new ArrayList<>();

    private final int COMPRESS_WIDTH = 200;

    private final int COMPRESS_HEIGH = 120;

    private int selectedPosition = 0;

    private String entryType;
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
                rightTextFacus.setText(getString(R.string.Facused));
                rightTextFacus.setTextColor(Color.WHITE);
                rightTextFacus.setBackgroundResource(R.drawable.shape_detail_facus_unable);
                rightTextFacus.setEnabled(false);
                data.setFacused(true);
                break;
            case R.id.iv_zan:
                mZan.setImageResource(R.drawable.icon_zaned);
                mZan.setEnabled(false);
                mNum.setText(""+(data.getZanNum()+1));
                data.setZaned(true);
                data.setZanNum(data.getZanNum()+1);
                break;
            case R.id.iv_feed_back:
                Intent intent1 = new Intent(this, FeedBackActivity.class);
                intent1.putExtra("ectyrType","social");
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

    }

    @Override
    public void initViews() {
        data = (CommunicationEntity.DataEntity)getIntent().getSerializableExtra("commData");
        entryType = getIntent().getStringExtra("entryType");
        if(data.getName().equals("钉宫理惠")){
            rightTextFacus.setVisibility(View.GONE);
            mFeedBack.setVisibility(View.GONE);
        }else{
            rightTextFacus.setVisibility(View.VISIBLE);
        }

        midText.setText(data.getName());
        mback.setImageResource(R.drawable.btn_back);
        rightText.setVisibility(View.GONE);
        if(data.isFacused()){
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
        mTime.setText("2小时前");
        mContent.setText(data.getContent());
        mNum.setText(""+data.getZanNum());
        if(data.isZaned()){
            mZan.setImageResource(R.drawable.icon_zaned);
            mZan.setEnabled(false);
        }
        switch (data.getImgs().size()){
            case 0:
                mContainer.setVisibility(View.GONE);
                break;
            case 1:
                mPic1.setVisibility(View.VISIBLE);
                FrescoUtils.load(Uri.parse(data.getImgs().get(0)),mPic1,COMPRESS_WIDTH,COMPRESS_HEIGH);

                mPic2.setVisibility(View.INVISIBLE);

                mPic3.setVisibility(View.GONE);
                photos.add(new PhotoModel(data.getImgs().get(0)));
                break;
            case 2:
                mPic1.setVisibility(View.VISIBLE);
                mPic2.setVisibility(View.VISIBLE);

                FrescoUtils.load(Uri.parse(data.getImgs().get(0)),mPic1,COMPRESS_WIDTH,COMPRESS_HEIGH);
                FrescoUtils.load(Uri.parse(data.getImgs().get(1)),mPic2,COMPRESS_WIDTH,COMPRESS_HEIGH);

                mPic3.setVisibility(View.GONE);

                photos.add(new PhotoModel(data.getImgs().get(0)));
                photos.add(new PhotoModel(data.getImgs().get(1)));
                break;
            case 3:
                mPic1.setVisibility(View.VISIBLE);
                mPic2.setVisibility(View.VISIBLE);
                mPic3.setVisibility(View.VISIBLE);

                FrescoUtils.load(Uri.parse(data.getImgs().get(0)),mPic1,COMPRESS_WIDTH,COMPRESS_HEIGH);
                FrescoUtils.load(Uri.parse(data.getImgs().get(1)),mPic2,COMPRESS_WIDTH,COMPRESS_HEIGH);
                FrescoUtils.load(Uri.parse(data.getImgs().get(2)),mPic3,COMPRESS_WIDTH,COMPRESS_HEIGH);

                photos.add(new PhotoModel(data.getImgs().get(0)));
                photos.add(new PhotoModel(data.getImgs().get(1)));
                photos.add(new PhotoModel(data.getImgs().get(2)));
                break;
            default:
                break;
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
        FrescoUtils.clearCache();
        FrescoUtils.clearImgMemory(mPic1);
        FrescoUtils.clearImgMemory(mPic2);
        FrescoUtils.clearImgMemory(mPic3);
        super.onDestroy();
    }
}
