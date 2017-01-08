package com.home.rw.mvp.ui.activitys.social;

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
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.entity.FacusListEntity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.adapters.CommunicationAdapter;
import com.home.rw.mvp.ui.adapters.FacusListAdapter;
import com.home.rw.mvp.ui.adapters.RecycleViewDivider;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class FocusListActivity extends BaseActivity {

    private ArrayList<FacusListEntity.DataEntity> datasource = new ArrayList<>();

    private FacusListAdapter mAdapterFacus;



    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @OnClick({
            R.id.back
    })
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                finish();
            default:
                break;


        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_focus_list;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        midText.setText(R.string.MyFacus);
        mback.setImageResource(R.drawable.btn_back);
        initRecycleView();
    }

    private void initRecycleView() {
        FacusListEntity.DataEntity entity1 = new FacusListEntity.DataEntity();
        entity1.setHeader("https://imgsa.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=a31bf4b58fd6277ffd1f3a6a49517455/b90e7bec54e736d10b34ec0693504fc2d562699c.jpg");
        entity1.setName("藤原龙也");
        entity1.setNum(365);

        FacusListEntity.DataEntity entity2 = new FacusListEntity.DataEntity();
        entity2.setHeader("http://h.hiphotos.baidu.com/baike/w%3D268%3Bg%3D0/sign=7a27eb6de21190ef01fb95d9f620fa2b/4bed2e738bd4b31c7813af3b82d6277f9e2ff807.jpg");
        entity2.setName("松重丰");
        entity1.setNum(467);

        FacusListEntity.DataEntity entity3 = new FacusListEntity.DataEntity();
        entity3.setHeader("https://imgsa.baidu.com/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=a3536492d8a20cf4529df68d17602053/1ad5ad6eddc451da7b1aeffebefd5266d0163233.jpg");
        entity3.setName("小栗旬");
        entity3.setNum(908);

        FacusListEntity.DataEntity entity4 = new FacusListEntity.DataEntity();
        entity4.setHeader("https://imgsa.baidu.com/baike/c0%3Dbaike116%2C5%2C5%2C116%2C38/sign=a87330b7880a19d8df0e8c575293e9ee/cc11728b4710b912ac309ff8cbfdfc03934522c6.jpg");
        entity4.setName("木村拓哉");
        entity4.setNum(420);

        FacusListEntity.DataEntity entity5 = new FacusListEntity.DataEntity();
        entity5.setHeader("http://b.hiphotos.baidu.com/baike/w%3D268%3Bg%3D0/sign=22dd700b0123dd542173a06ee932d4e3/562c11dfa9ec8a138ae95b8fff03918fa1ecc0ea.jpg");
        entity5.setName("小田切让");
        entity5.setNum(420);

        FacusListEntity.DataEntity entity6 = new FacusListEntity.DataEntity();
        entity6.setHeader("https://imgsa.baidu.com/baike/crop%3D0%2C70%2C1600%2C1058%3Bc0%3Dbaike180%2C5%2C5%2C180%2C60/sign=38a87614b099a9012f7a017620a5264c/c2fdfc039245d688f3a718a4acc27d1ed21b240c.jpg");
        entity6.setName("户田惠梨香");
        entity6.setNum(420);

        datasource.add(entity1);
        datasource.add(entity2);
        datasource.add(entity3);
        datasource.add(entity4);
        datasource.add(entity5);
        datasource.add(entity6);
        //关注的人列表
        mAdapterFacus = new FacusListAdapter(datasource,this);

        mAdapterFacus.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecycleView.addItemDecoration(new RecycleViewDivider(this,RecycleViewDivider.VERTICAL_LIST,RecycleViewDivider.COMMON));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapterFacus);
        mRecycleView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

}
