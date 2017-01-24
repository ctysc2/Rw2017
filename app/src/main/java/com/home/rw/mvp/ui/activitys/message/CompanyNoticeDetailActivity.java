package com.home.rw.mvp.ui.activitys.message;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.mvp.entity.CompanyNoticeEntity;
import com.home.rw.mvp.entity.GrandEntity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class CompanyNoticeDetailActivity extends BaseActivity {

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

    }

    @Override
    public void initViews() {
        midText.setText(R.string.gonggao);
        mback.setImageResource(R.drawable.btn_back);
        CompanyNoticeEntity.DataEntity entity = (CompanyNoticeEntity.DataEntity)getIntent().getSerializableExtra("data");
        mTitle.setText(entity.getTitle());
        mContent.setText(entity.getContent());
        mDate.setText(entity.getDate());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }
}
