package com.home.rw.mvp.ui.activitys.message;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.BusinessMeetingPhoneEntity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.activitys.social.CommDetailActivity;
import com.home.rw.mvp.ui.activitys.social.CommListActivity;
import com.home.rw.mvp.ui.adapters.BusinessMeetingAdapter;
import com.home.rw.mvp.ui.adapters.CommunicationAdapter;
import com.home.rw.mvp.ui.adapters.RecycleViewSperate;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class BusinessPhoneActivity extends BaseActivity {

    private BusinessMeetingAdapter mAdapter;

    private ArrayList<BusinessMeetingPhoneEntity.DataEntity> dataSource  = new ArrayList<>();

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

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
        return R.layout.activity_business_phone;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        midText.setText(getString(R.string.shangWu));
        mback.setImageResource(R.drawable.btn_back);
        initRecycleView();
    }

    private void initRecycleView() {
        BusinessMeetingPhoneEntity.DataEntity entity1 = new BusinessMeetingPhoneEntity.DataEntity();
        entity1.setAvatar("http://imgsrc.baidu.com/forum/w%3D580/sign=34aec2faf8edab6474724dc8c737af81/5fbf2f2eb9389b50df635c638635e5dde6116ed5.jpg");
        entity1.setTitle("波波先生");
        entity1.setDate("11-11");
        entity1.setSubTitle("语音07-23");

        BusinessMeetingPhoneEntity.DataEntity entity2 = new BusinessMeetingPhoneEntity.DataEntity();
        entity2.setTitle("李洛克");
        entity2.setDate("11-07");
        entity2.setSubTitle("语音07-23");

        BusinessMeetingPhoneEntity.DataEntity entity3 = new BusinessMeetingPhoneEntity.DataEntity();
        entity3.setAvatar("http://tv03.tgbusdata.cn/v2/thumb/jpg/MkQ0RSw1MDAsMTAwLDQsMywxLC0xLDAscms1MA==/u/ol.tgbus.com/news/UploadFiles_2374/201412/2014121110060659.jpg");
        entity3.setTitle("茂茂");
        entity3.setDate("11-01");
        entity3.setSubTitle("语音07-23");
        dataSource.add(entity1);
        dataSource.add(entity2);
        dataSource.add(entity3);
        dataSource.add(entity2);
        dataSource.add(entity2);
        dataSource.add(entity3);
        dataSource.add(entity1);
        dataSource.add(entity1);
        dataSource.add(entity1);
        dataSource.add(entity3);

        mAdapter = new BusinessMeetingAdapter(dataSource,this);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }
}
