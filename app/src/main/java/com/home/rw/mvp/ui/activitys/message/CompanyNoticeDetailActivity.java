package com.home.rw.mvp.ui.activitys.message;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.mvp.entity.CompanyNoticeEntity;
import com.home.rw.mvp.entity.GrandEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.entity.message.TopicCommonEntity;
import com.home.rw.mvp.presenter.impl.ReadNoticePresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.view.ReadNoticeView;
import com.home.rw.utils.DateUtils;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class CompanyNoticeDetailActivity extends BaseActivity implements ReadNoticeView{

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.tv_title)
    TextView mTitle;

    @BindView(R.id.tv_date)
    TextView mDate;

    @BindView(R.id.tv_content)
    TextView mContent;

    @Inject
    ReadNoticePresenterImpl mReadNoticePresenterImpl;
    @OnClick({R.id.back,
    })
    public void onClick(View v){
        switch(v.getId()){
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_company_notice_detail;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(R.string.gonggao);
        mback.setImageResource(R.drawable.btn_back);
        mReadNoticePresenterImpl.attachView(this);
        TopicCommonEntity entity = (TopicCommonEntity)getIntent().getSerializableExtra("data");
        mTitle.setText(entity.getTitle());
        mContent.setText(entity.getContent());
        if(!TextUtils.isEmpty(entity.getPubTime()))
            mDate.setText(DateUtils.getTime(new Date(Long.parseLong(entity.getPubTime()))));
        else
            mDate.setText("");
        mReadNoticePresenterImpl.readNotice(entity.getId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    public void setReadNoticeCompleted(BaseEntity data) {

    }

    @Override
    public void showProgress(int reqType) {

    }

    @Override
    public void hideProgress(int reqType) {

    }

    @Override
    public void showErrorMsg(int reqType, String msg) {

    }
}
