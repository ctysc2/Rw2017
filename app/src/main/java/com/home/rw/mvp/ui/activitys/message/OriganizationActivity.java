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
import com.home.rw.mvp.entity.OrgEntity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.adapters.OriganzationAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class OriganizationActivity extends BaseActivity {

    private ArrayList<OrgEntity.DataEntity> dataSource = new ArrayList();

    private OriganzationAdapter mAdapter;

    private String entryType = "";
    @BindView(R.id.back)
    ImageButton mBack;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

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
        return R.layout.activity_origanization;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        midText.setText(getString(R.string.organization));
        mBack.setImageResource(R.drawable.btn_back);
        entryType = getIntent().getStringExtra("type");
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

        OrgEntity.DataEntity entity1 = new OrgEntity.DataEntity();
        entity1.setTitle("市场部");
        entity1.setId(-1);
        entity1.setAvatar("http://www.wangbangbang.cn/uploads/allimg/201304110542365950.jpg");

        OrgEntity.DataEntity entity2 = new OrgEntity.DataEntity();
        entity2.setTitle("销售部");
        entity2.setId(-2);
        entity2.setAvatar("http://img10.360buyimg.com/n0/g15/M04/10/00/rBEhWVJfotYIAAAAAABbO4mnGvEAAEQvQBfS7sAAFtT961.jpg");

        OrgEntity.DataEntity entity3 = new OrgEntity.DataEntity();
        entity3.setTitle("开发部");
        entity3.setId(-3);
        entity3.setAvatar("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1485627120254&di=17ff139b283258caa78dcef6c60a3b6b&imgtype=0&src=http%3A%2F%2Fi9.qhimg.com%2Ft017d891ca365ef60b5.jpg");


        OrgEntity.DataEntity sub1 = new OrgEntity.DataEntity();
        sub1.setTitle("陈5人");
        sub1.setSubTitle("最近通话 张19");
        sub1.setDate("10-28");
        sub1.setId(1);
        sub1.setAdded(true);

        sub1.setAvatar("http://y0.ifengimg.com/e6ce10787c9a3bdb/2014/0423/re_53571adb03caf.jpg");

        OrgEntity.DataEntity sub2 = new OrgEntity.DataEntity();
        sub2.setTitle("张19");
        sub2.setSubTitle("最近通话 求总");
        sub2.setDate("10-24");
        sub2.setId(2);
        sub2.setAdded(true);
        sub2.setAvatar("https://imgsa.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=9963e0334e90f60310bd9415587bd87e/ac345982b2b7d0a21f15689fcfef76094a369ad7.jpg");

        OrgEntity.DataEntity sub3 = new OrgEntity.DataEntity();
        sub3.setTitle("2B导师");
        sub3.setSubTitle("最近通话 喵喵");
        sub3.setDate("10-23");
        sub3.setAdded(true);
        sub3.setId(3);

        OrgEntity.DataEntity sub4 = new OrgEntity.DataEntity();
        sub4.setTitle("李思聪");
        sub4.setSubTitle("最近通话 王尼玛");
        sub4.setDate("10-22");
        sub4.setAdded(true);
        sub4.setId(4);

        OrgEntity.DataEntity sub5 = new OrgEntity.DataEntity();
        sub5.setTitle("唐军");
        sub5.setSubTitle("最近通话 聪哥");
        sub5.setDate("10-14");
        sub5.setAdded(false);
        sub5.setId(5);


        OrgEntity.DataEntity sub6 = new OrgEntity.DataEntity();
        sub6.setTitle("松松");
        sub6.setSubTitle("最近通话 sy");
        sub6.setDate("10-06");
        sub6.setAdded(true);
        sub6.setId(6);

        OrgEntity.DataEntity sub7 = new OrgEntity.DataEntity();
        sub7.setTitle("炮神");
        sub7.setSubTitle("最近通话 sy");
        sub7.setDate("10-06");
        sub7.setAdded(true);
        sub7.setAvatar("http://t0.qlogo.cn/mbloghead/117e399658e349b2fd24/0");
        sub7.setId(7);
        ArrayList<OrgEntity.DataEntity> subDatasource1 = new ArrayList<>();
        subDatasource1.add(sub1);
        subDatasource1.add(sub2);
        subDatasource1.add(sub3);
        entity1.setSubData(subDatasource1);
        ArrayList<OrgEntity.DataEntity> subDatasource2 = new ArrayList<>();
        subDatasource2.add(sub4);
        subDatasource2.add(sub5);
        subDatasource2.add(sub6);
        entity2.setSubData(subDatasource2);

        ArrayList<OrgEntity.DataEntity> subDatasource3 = new ArrayList<>();
        subDatasource3.add(sub7);
        entity3.setSubData(subDatasource3);



        dataSource.add(entity1);
        dataSource.add(entity2);
        dataSource.add(entity3);
        mAdapter = new OriganzationAdapter(dataSource,this,Const.TYPE_ADD);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                OrgEntity.DataEntity entity = dataSource.get(position);
                if(entity.getSubData() == null){
                    entity.setAdded(true);
                    mAdapter.notifyDataSetChanged();
                }else{
                    if(entity.isExpanded()){
                        entity.setExpanded(false);
                        mAdapter.delete(position+1,entity.getSubData());
                    }else{
                        entity.setExpanded(true);
                        mAdapter.addMore(position+1,entity.getSubData());
                    }
                    mAdapter.notifyDataSetChanged();
                }

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

    private void initRecycleViewNormal() {

        OrgEntity.DataEntity entity1 = new OrgEntity.DataEntity();
        entity1.setTitle("市场部");
        entity1.setId(-1);
        entity1.setAvatar("http://www.wangbangbang.cn/uploads/allimg/201304110542365950.jpg");

        OrgEntity.DataEntity entity2 = new OrgEntity.DataEntity();
        entity2.setTitle("销售部");
        entity2.setId(-2);
        entity2.setAvatar("http://img10.360buyimg.com/n0/g15/M04/10/00/rBEhWVJfotYIAAAAAABbO4mnGvEAAEQvQBfS7sAAFtT961.jpg");

        OrgEntity.DataEntity entity3 = new OrgEntity.DataEntity();
        entity3.setTitle("开发部");
        entity3.setId(-3);
        entity3.setAvatar("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1485627120254&di=17ff139b283258caa78dcef6c60a3b6b&imgtype=0&src=http%3A%2F%2Fi9.qhimg.com%2Ft017d891ca365ef60b5.jpg");


        OrgEntity.DataEntity sub1 = new OrgEntity.DataEntity();
        sub1.setTitle("陈5人");
        sub1.setSubTitle("最近通话 张19");
        sub1.setDate("10-28");
        sub1.setId(1);
        sub1.setAvatar("http://y0.ifengimg.com/e6ce10787c9a3bdb/2014/0423/re_53571adb03caf.jpg");

        OrgEntity.DataEntity sub2 = new OrgEntity.DataEntity();
        sub2.setTitle("张19");
        sub2.setSubTitle("最近通话 求总");
        sub2.setDate("10-24");
        sub2.setId(2);
        sub2.setAvatar("https://imgsa.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=9963e0334e90f60310bd9415587bd87e/ac345982b2b7d0a21f15689fcfef76094a369ad7.jpg");

        OrgEntity.DataEntity sub3 = new OrgEntity.DataEntity();
        sub3.setTitle("2B导师");
        sub3.setSubTitle("最近通话 喵喵");
        sub3.setDate("10-23");
        sub3.setId(3);

        OrgEntity.DataEntity sub4 = new OrgEntity.DataEntity();
        sub4.setTitle("李思聪");
        sub4.setSubTitle("最近通话 王尼玛");
        sub4.setDate("10-22");
        sub4.setId(4);

        OrgEntity.DataEntity sub5 = new OrgEntity.DataEntity();
        sub5.setTitle("唐军");
        sub5.setSubTitle("最近通话 聪哥");
        sub5.setDate("10-14");
        sub5.setId(5);


        OrgEntity.DataEntity sub6 = new OrgEntity.DataEntity();
        sub6.setTitle("松松");
        sub6.setSubTitle("最近通话 sy");
        sub6.setDate("10-06");
        sub6.setId(6);

        OrgEntity.DataEntity sub7 = new OrgEntity.DataEntity();
        sub7.setTitle("炮神");
        sub7.setSubTitle("最近通话 sy");
        sub7.setDate("10-06");
        sub7.setAvatar("http://t0.qlogo.cn/mbloghead/117e399658e349b2fd24/0");
        sub7.setId(7);
        ArrayList<OrgEntity.DataEntity> subDatasource1 = new ArrayList<>();
        subDatasource1.add(sub1);
        subDatasource1.add(sub2);
        subDatasource1.add(sub3);
        entity1.setSubData(subDatasource1);
        ArrayList<OrgEntity.DataEntity> subDatasource2 = new ArrayList<>();
        subDatasource2.add(sub4);
        subDatasource2.add(sub5);
        subDatasource2.add(sub6);
        entity2.setSubData(subDatasource2);

        ArrayList<OrgEntity.DataEntity> subDatasource3 = new ArrayList<>();
        subDatasource3.add(sub7);
        entity3.setSubData(subDatasource3);



        dataSource.add(entity1);
        dataSource.add(entity2);
        dataSource.add(entity3);
        mAdapter = new OriganzationAdapter(dataSource,this,Const.TYPE_NORMAL);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                OrgEntity.DataEntity entity = dataSource.get(position);
                if(entity.getSubData() == null){
                    CallListEntity.DataEntity data = new CallListEntity.DataEntity();
                    data.setName(entity.getTitle());
                    data.setAvatar(entity.getAvatar());
                    Intent intent = new Intent(OriganizationActivity.this,PreviewCallActivity.class);
                    intent.putExtra("data",data);
                    startActivity(intent);
                }else{
                    if(entity.isExpanded()){
                        entity.setExpanded(false);
                        mAdapter.delete(position+1,entity.getSubData());
                    }else{
                        entity.setExpanded(true);
                        mAdapter.addMore(position+1,entity.getSubData());
                    }
                    mAdapter.notifyDataSetChanged();
                }

            }
        });
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);

    }
}
