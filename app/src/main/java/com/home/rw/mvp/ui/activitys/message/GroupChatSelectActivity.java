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
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.GroupChatEntity;
import com.home.rw.mvp.entity.SelectEntity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.adapters.GroupChatAdapter;
import com.home.rw.mvp.ui.adapters.SelectAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class GroupChatSelectActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageButton mBack;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rightText)
    TextView rightText;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    private SelectAdapter mAdapter;

    private ArrayList<SelectEntity.DataEntity> dataSource = new ArrayList<>();


    @OnClick({R.id.back,
            R.id.rightText,

    })
    public void OnClick(View v){

        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.rightText:
                sendSelectData();
                break;
            default:
                break;
        }

    }

    private void sendSelectData() {
        ArrayList<SelectEntity.DataEntity> selectedData = new ArrayList<>();
        for(int i =0;i<dataSource.size();i++){
            SelectEntity.DataEntity entity = dataSource.get(i);
            if(entity.isSelected())
                selectedData.add(entity);
        }

        Intent intent = new Intent(this, GroupChatNamedActivity.class);
        intent.putExtra("data",selectedData);
        startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_group_chat_select;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        midText.setText(getString(R.string.createGroupChat));
        rightText.setText(getString(R.string.confirm));
        mBack.setImageResource(R.drawable.btn_back);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initRecycleView();
    }

    private void initRecycleView() {
        SelectEntity.DataEntity entity1 = new SelectEntity.DataEntity();
        entity1.setName("张三");

        SelectEntity.DataEntity entity2 = new SelectEntity.DataEntity();
        entity2.setName("李四");

        SelectEntity.DataEntity entity3 = new SelectEntity.DataEntity();
        entity3.setName("王五");

        SelectEntity.DataEntity entity4 = new SelectEntity.DataEntity();
        entity4.setName("赵六");

        SelectEntity.DataEntity entity5 = new SelectEntity.DataEntity();
        entity5.setName("王小二");

        SelectEntity.DataEntity entity6 = new SelectEntity.DataEntity();
        entity6.setName("钱赞企");

        SelectEntity.DataEntity entity7 = new SelectEntity.DataEntity();
        entity7.setName("罗贤");

        SelectEntity.DataEntity entity8 = new SelectEntity.DataEntity();
        entity8.setName("66");

        SelectEntity.DataEntity entity9 = new SelectEntity.DataEntity();
        entity9.setName("接班人");
        entity9.setAvatar("http://imgsrc.baidu.com/forum/pic/item/89c519fa513d269706c1af5d5dfbb2fb4316d84d.jpg");

        SelectEntity.DataEntity entity10 = new SelectEntity.DataEntity();
        entity10.setName("厦门孙一峰");

        SelectEntity.DataEntity entity11 = new SelectEntity.DataEntity();
        entity11.setName("alphaGo");

        SelectEntity.DataEntity entity12 = new SelectEntity.DataEntity();
        entity12.setName("米兰风味");

        dataSource.add(entity1);
        dataSource.add(entity2);
        dataSource.add(entity3);
        dataSource.add(entity4);
        dataSource.add(entity5);
        dataSource.add(entity6);
        dataSource.add(entity7);
        dataSource.add(entity8);
        dataSource.add(entity9);
        dataSource.add(entity10);
        dataSource.add(entity11);
        dataSource.add(entity12);

        mAdapter = new SelectAdapter(dataSource,this);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SelectEntity.DataEntity entity = dataSource.get(position);
                if(entity.isSelected()){
                    dataSource.get(position).setSelected(false);
                }else{
                    dataSource.get(position).setSelected(true);
                }

                mAdapter.notifyItemChanged(position);
            }
        });
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);

    }
}
