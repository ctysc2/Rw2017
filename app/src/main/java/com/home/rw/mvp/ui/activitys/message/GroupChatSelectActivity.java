package com.home.rw.mvp.ui.activitys.message;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.GroupChatEntity;
import com.home.rw.mvp.entity.SelectEntity;
import com.home.rw.mvp.entity.message.MessageCommonEntity;
import com.home.rw.mvp.entity.message.MyFriendEntity;
import com.home.rw.mvp.presenter.impl.MyFriendPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.adapters.GroupChatAdapter;
import com.home.rw.mvp.ui.adapters.SelectAdapter;
import com.home.rw.mvp.view.MyFriendView;
import com.home.rw.utils.DialogUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class GroupChatSelectActivity extends BaseActivity implements MyFriendView{

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

    @Inject
    MyFriendPresenterImpl mMyFriendPresenterImpl;

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

        if(selectedData.size() == 0){
            Toast.makeText(this,getString(R.string.chooseGroupMember),Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, GroupChatNamedActivity.class);
        intent.putExtra("data",selectedData);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mMyFriendPresenterImpl!=null)
            mMyFriendPresenterImpl.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_group_chat_select;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(getString(R.string.createGroupChat));
        rightText.setText(getString(R.string.confirm));
        mBack.setImageResource(R.drawable.btn_back);
        mMyFriendPresenterImpl.attachView(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initRecycleView();
        mMyFriendPresenterImpl.beforeRequest();
        mMyFriendPresenterImpl.getMyFriendList();
    }

    private void initRecycleView() {


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

                mAdapter.notifyDataSetChanged();
            }
        });
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);

    }
    private ArrayList<SelectEntity.DataEntity> dataTransfer(ArrayList<MessageCommonEntity> source){
        if(source == null || source.size() == 0)
            return null;

        ArrayList<SelectEntity.DataEntity> entityList = new ArrayList<>();

        for(int i = 0;i<source.size();i++){
            SelectEntity.DataEntity entity = new SelectEntity.DataEntity();
            MessageCommonEntity sourceEntity = source.get(i);
            entity.setName(!TextUtils.isEmpty(sourceEntity.getNickname())?sourceEntity.getNickname():sourceEntity.getRealname());
            entity.setAvatar(sourceEntity.getAvatar());
            entity.setSelected(false);
            entity.setId(sourceEntity.getUserId());
            entityList.add(entity);
        }

        return entityList;
    }

    @Override
    public void getMyFriendCompleted(MyFriendEntity data) {
        if(data.getCode().equals("ok")){
            ArrayList<SelectEntity.DataEntity> temp = dataTransfer(data.getData().getFriends());
            if(temp!=null){
                dataSource = temp;
                mAdapter.setList(dataSource);
                mAdapter.notifyDataSetChanged();
            }else{
                Toast.makeText(this,getString(R.string.noFriends),Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void showProgress(int reqType) {
        if(mLoadDialog ==null){
            mLoadDialog = DialogUtils.create(this, DialogUtils.TYPE_LOADING);
            mLoadDialog.show();
        }
    }

    @Override
    public void hideProgress(int reqType) {
        if(mLoadDialog !=null){
            mLoadDialog.dismiss();
            mLoadDialog = null;
        }
    }

    @Override
    public void showErrorMsg(int reqType, String msg) {
        Toast.makeText(this,getString(R.string.loadFailed),Toast.LENGTH_SHORT).show();

    }
}
