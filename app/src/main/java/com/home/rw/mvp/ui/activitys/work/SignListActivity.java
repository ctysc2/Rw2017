package com.home.rw.mvp.ui.activitys.work;

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
import com.home.rw.mvp.entity.RollMeEntity;
import com.home.rw.mvp.entity.SignEntity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.adapters.RecycleViewSperate;
import com.home.rw.mvp.ui.adapters.RollMelistAdapter;
import com.home.rw.mvp.ui.adapters.SignListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class SignListActivity extends BaseActivity {

    private SignListAdapter mAdapter;

    private ArrayList<SignEntity.DataEntity> dataSource  = new ArrayList<>();

    //http://p1.qhimg.com/dmsmty/350_200_/t01cf5a5108bd50b4fc.jpg

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @OnClick({
            R.id.back,
    })
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                finish();
                break;

            default:
                break;


        }

    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_list;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        midText.setText(R.string.signList);
        mback.setImageResource(R.drawable.btn_back);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initRecycleView();
    }

    private void initRecycleView() {

        SignEntity.DataEntity entity1 = new SignEntity.DataEntity();
        entity1.setHeader("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1482949311&di=2cf75ddae16bd743f5e8453ed12e8dc9&src=http://a2.att.hudong.com/31/34/01300001128119142116348739631_s.jpg");
        entity1.setName("Miss");
        entity1.setTime("2016年11月12日 09:38");
        entity1.setAddress("上海市清涧路");

        SignEntity.DataEntity entity2 = new SignEntity.DataEntity();
        entity2.setHeader("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1482949311&di=2cf75ddae16bd743f5e8453ed12e8dc9&src=http://a2.att.hudong.com/31/34/01300001128119142116348739631_s.jpg");
        entity2.setName("Miss");
        entity2.setTime("2016年11月13日 08:37");
        entity2.setAddress("人民广场");
        dataSource.add(entity1);
        dataSource.add(entity2);
        mAdapter = new SignListAdapter(dataSource,this);

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
}
