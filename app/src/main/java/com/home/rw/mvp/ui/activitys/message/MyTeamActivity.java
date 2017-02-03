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
import com.home.rw.common.Const;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.CallListEntity;
import com.home.rw.mvp.entity.MyTeamEntity;
import com.home.rw.mvp.entity.OrgEntity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.adapters.MyTeamAdapter;
import com.home.rw.mvp.ui.adapters.OriganzationAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MyTeamActivity extends BaseActivity {

    private ArrayList<MyTeamEntity.DataEntity> dataSource = new ArrayList();

    private MyTeamAdapter mAdapter;

    private String entryType = "";
    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @BindView(R.id.back)
    ImageButton mBack;

    @BindView(R.id.midText)
    TextView midText;

    @OnClick({R.id.back,

    })
    public void OnClick(View v){
        Intent intent;
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
        return R.layout.activity_my_team;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        midText.setText(getString(R.string.myTeam));
        mBack.setImageResource(R.drawable.btn_back);
        entryType = getIntent().getStringExtra("type");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();

        switch (entryType){
            case Const.TYPE_ADD:
                initRecycleViewAdd();
                break;
            case Const.TYPE_NORMAL:
                initRecycleViewNormal();
                break;
            case Const.TYPE_SELECT:
                break;
        }
        
    }

    private void initRecycleViewAdd() {
        MyTeamEntity.DataEntity sub1 = new MyTeamEntity.DataEntity();
        sub1.setTitle("陈5人");
        sub1.setSubTitle("最近通话 张19");
        sub1.setDate("10-28");
        sub1.setAdded(true);
        sub1.setId(1);
        sub1.setAvatar("http://y0.ifengimg.com/e6ce10787c9a3bdb/2014/0423/re_53571adb03caf.jpg");

        MyTeamEntity.DataEntity sub2 = new MyTeamEntity.DataEntity();
        sub2.setTitle("张19");
        sub2.setSubTitle("最近通话 求总");
        sub2.setDate("10-24");
        sub2.setAdded(true);
        sub2.setId(2);
        sub2.setAvatar("https://imgsa.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=9963e0334e90f60310bd9415587bd87e/ac345982b2b7d0a21f15689fcfef76094a369ad7.jpg");

        MyTeamEntity.DataEntity sub3 = new MyTeamEntity.DataEntity();
        sub3.setTitle("2B导师");
        sub3.setSubTitle("最近通话 喵喵");
        sub3.setDate("10-23");
        sub3.setId(3);
        sub3.setAdded(true);

        MyTeamEntity.DataEntity sub4 = new MyTeamEntity.DataEntity();
        sub4.setTitle("李思聪");
        sub4.setSubTitle("最近通话 王尼玛");
        sub4.setDate("10-22");
        sub4.setAdded(true);
        sub4.setId(4);

        MyTeamEntity.DataEntity sub5 = new MyTeamEntity.DataEntity();
        sub5.setTitle("唐军");
        sub5.setSubTitle("最近通话 聪哥");
        sub5.setDate("10-14");
        sub5.setAdded(false);
        sub5.setId(5);


        MyTeamEntity.DataEntity sub6 = new MyTeamEntity.DataEntity();
        sub6.setTitle("松松");
        sub6.setSubTitle("最近通话 sy");
        sub6.setDate("10-06");
        sub6.setAdded(true);
        sub6.setId(6);

        MyTeamEntity.DataEntity sub7 = new MyTeamEntity.DataEntity();
        sub7.setTitle("炮神");
        sub7.setSubTitle("最近通话 sy");
        sub7.setDate("10-06");
        sub7.setAvatar("http://t0.qlogo.cn/mbloghead/117e399658e349b2fd24/0");
        sub7.setId(7);
        sub7.setAdded(true);

        dataSource.add(sub1);
        dataSource.add(sub2);
        dataSource.add(sub3);
        dataSource.add(sub4);
        dataSource.add(sub5);
        dataSource.add(sub6);
        dataSource.add(sub7);

        mAdapter = new MyTeamAdapter(dataSource,this,Const.TYPE_ADD);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                MyTeamEntity.DataEntity entity = dataSource.get(position);
                entity.setAdded(true);
                mAdapter.notifyDataSetChanged();


            }
        });
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);
    }

    private void initRecycleViewNormal() {

        MyTeamEntity.DataEntity sub1 = new MyTeamEntity.DataEntity();
        sub1.setTitle("陈5人");
        sub1.setSubTitle("最近通话 张19");
        sub1.setDate("10-28");
        sub1.setId(1);
        sub1.setAvatar("http://y0.ifengimg.com/e6ce10787c9a3bdb/2014/0423/re_53571adb03caf.jpg");

        MyTeamEntity.DataEntity sub2 = new MyTeamEntity.DataEntity();
        sub2.setTitle("张19");
        sub2.setSubTitle("最近通话 求总");
        sub2.setDate("10-24");
        sub2.setId(2);
        sub2.setAvatar("https://imgsa.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=9963e0334e90f60310bd9415587bd87e/ac345982b2b7d0a21f15689fcfef76094a369ad7.jpg");

        MyTeamEntity.DataEntity sub3 = new MyTeamEntity.DataEntity();
        sub3.setTitle("2B导师");
        sub3.setSubTitle("最近通话 喵喵");
        sub3.setDate("10-23");
        sub3.setId(3);

        MyTeamEntity.DataEntity sub4 = new MyTeamEntity.DataEntity();
        sub4.setTitle("李思聪");
        sub4.setSubTitle("最近通话 王尼玛");
        sub4.setDate("10-22");
        sub4.setId(4);

        MyTeamEntity.DataEntity sub5 = new MyTeamEntity.DataEntity();
        sub5.setTitle("唐军");
        sub5.setSubTitle("最近通话 聪哥");
        sub5.setDate("10-14");
        sub5.setId(5);


        MyTeamEntity.DataEntity sub6 = new MyTeamEntity.DataEntity();
        sub6.setTitle("松松");
        sub6.setSubTitle("最近通话 sy");
        sub6.setDate("10-06");
        sub6.setId(6);

        MyTeamEntity.DataEntity sub7 = new MyTeamEntity.DataEntity();
        sub7.setTitle("炮神");
        sub7.setSubTitle("最近通话 sy");
        sub7.setDate("10-06");
        sub7.setAvatar("http://t0.qlogo.cn/mbloghead/117e399658e349b2fd24/0");
        sub7.setId(7);



        dataSource.add(sub1);
        dataSource.add(sub2);
        dataSource.add(sub3);
        dataSource.add(sub4);
        dataSource.add(sub5);
        dataSource.add(sub6);
        dataSource.add(sub7);

        mAdapter = new MyTeamAdapter(dataSource,this,Const.TYPE_NORMAL);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                MyTeamEntity.DataEntity entity = dataSource.get(position);

                    CallListEntity.DataEntity data = new CallListEntity.DataEntity();
                    data.setName(entity.getTitle());
                    data.setAvatar(entity.getAvatar());
                    Intent intent = new Intent(MyTeamActivity.this,PreviewCallActivity.class);
                    intent.putExtra("data",data);
                    startActivity(intent);


            }
        });
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);
    }
}
