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
import com.home.rw.mvp.entity.GroupChatEntity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.adapters.GroupChatAdapter;
import com.home.rw.mvp.ui.adapters.ReceiveFriendAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class GroupChatActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageButton mBack;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rightText)
    TextView rightText;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    private GroupChatAdapter mAdapter;

    private ArrayList<GroupChatEntity.DataEntity> dataSource = new ArrayList<>();
    @OnClick({R.id.back,
            R.id.rightText,

    })
    public void OnClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.rightText:
                intent = new Intent(this, GroupChatSelectActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_group_chat;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        midText.setText(getString(R.string.groupChat));
        rightText.setText(getString(R.string.createGroupChat));
        mBack.setImageResource(R.drawable.btn_back);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initRecycleView();
    }

    private void initRecycleView() {
        GroupChatEntity.DataEntity entity1 = new GroupChatEntity.DataEntity();
        entity1.setName("同城交友");
        entity1.setNum(678);

        GroupChatEntity.DataEntity entity2 = new GroupChatEntity.DataEntity();
        entity2.setName("老司机资源共享群");
        entity2.setNum(1002);

        GroupChatEntity.DataEntity entity3 = new GroupChatEntity.DataEntity();
        entity3.setName("天上人间会所");
        entity3.setNum(134);

        GroupChatEntity.DataEntity entity4 = new GroupChatEntity.DataEntity();
        entity4.setName("react native有问必答群");
        entity4.setNum(456);

        GroupChatEntity.DataEntity entity5 = new GroupChatEntity.DataEntity();
        entity5.setName("上海申花蓝魔球迷会");
        entity5.setNum(839);

        dataSource.add(entity1);
        dataSource.add(entity2);
        dataSource.add(entity3);
        dataSource.add(entity4);
        dataSource.add(entity5);

        mAdapter = new GroupChatAdapter(dataSource,this);
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
