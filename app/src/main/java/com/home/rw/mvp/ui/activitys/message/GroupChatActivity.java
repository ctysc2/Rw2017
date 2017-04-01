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
import com.home.rw.mvp.entity.message.MyGroupEntity;
import com.home.rw.mvp.presenter.impl.MyGroupPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.adapters.GroupChatAdapter;
import com.home.rw.mvp.ui.adapters.ReceiveFriendAdapter;
import com.home.rw.mvp.view.MyGroupView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

public class GroupChatActivity extends BaseActivity implements MyGroupView{

    @BindView(R.id.back)
    ImageButton mBack;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rightText)
    TextView rightText;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    private GroupChatAdapter mAdapter;

    private ArrayList<MyGroupEntity.DataEntity> dataSource = new ArrayList<>();

    @Inject
    MyGroupPresenterImpl mMyGroupPresenterImpl;

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
    protected void onDestroy() {
        super.onDestroy();
        if(mMyGroupPresenterImpl!=null)
            mMyGroupPresenterImpl.onDestroy();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_group_chat;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(getString(R.string.groupChat));
        rightText.setText(getString(R.string.createGroupChat));
        mBack.setImageResource(R.drawable.btn_back);
        mMyGroupPresenterImpl.attachView(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initRecycleView();
        mMyGroupPresenterImpl.getMyGroupList();
    }

    private void initRecycleView() {

        mAdapter = new GroupChatAdapter(dataSource,this);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                RongIM.getInstance().startConversation(GroupChatActivity.this, Conversation.ConversationType.GROUP,String.valueOf(dataSource.get(position).getId()),dataSource.get(position).getName());
            }
        });
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public void getMyGroupCompleted(MyGroupEntity data) {
        if(data.getCode().equals("ok")){
            dataSource = data.getData();
            mAdapter.setList(dataSource);
            mAdapter.notifyDataSetChanged();

        }
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
