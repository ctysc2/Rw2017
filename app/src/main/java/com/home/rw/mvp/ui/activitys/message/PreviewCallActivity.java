package com.home.rw.mvp.ui.activitys.message;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.CallListEntity;
import com.home.rw.mvp.entity.MeetingSelectTempEntity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.adapters.CallListAdatper;
import com.home.rw.mvp.ui.adapters.SpaceItemDecoration;
import com.home.rw.utils.DimenUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class PreviewCallActivity extends BaseActivity {

    private CallListEntity.DataEntity receiveData;
    private ArrayList<CallListEntity.DataEntity> dataSource = new ArrayList<>();

    private CallListAdatper mAdapter;

    private String minviteableNum;


    private boolean isEditing;
    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.leftText)
    TextView leftText;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @OnClick({R.id.back,
            R.id.leftText,

    })
    public void onClick(View v){
        switch(v.getId()){
            case R.id.back:
            case R.id.leftText:
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_preview_call;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        mback.setImageResource(R.drawable.btn_back);
        leftText.setText(R.string.dialogCancle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initRecycleView();
    }

    private void initRecycleView() {

        receiveData = (CallListEntity.DataEntity)(getIntent().getSerializableExtra("data"));

        CallListEntity.DataEntity entity1 = new CallListEntity.DataEntity();
        entity1.setAvatar("https://imgsa.baidu.com/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=e5082defe4cd7b89fd6132d16e4d29c2/6a600c338744ebf82b43491adbf9d72a6059a7e0.jpg");
        entity1.setName("泽拉图");
        entity1.setId(999);
        entity1.setEditing(false);

        CallListEntity.DataEntity entityAdd = new CallListEntity.DataEntity();
        entityAdd.setAvatar("res://" +
                getPackageName() +
                "/" + R.drawable.icon_add_call);
        entityAdd.setName(minviteableNum);
        entityAdd.setEditing(false);

        CallListEntity.DataEntity entityRemove = new CallListEntity.DataEntity();
        entityRemove.setAvatar("res://" +
                getPackageName() +
                "/" + R.drawable.icon_remove_call);
        entityRemove.setName("移除");
        entityRemove.setEditing(false);
        dataSource.add(entity1);
        if(receiveData != null)
            dataSource.add(receiveData);
        dataSource.add(entityAdd);
        dataSource.add(entityRemove);
        minviteableNum = String.format(getString(R.string.inviteNum),10-dataSource.size());
        entityAdd.setName(minviteableNum);
        mAdapter = new CallListAdatper(dataSource,this);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(isEditing == true){
                    if(position<dataSource.size()-2){
                        //编辑中点击自己以外头像删除
                        if(position!=0)
                            mAdapter.delete(position);
                        //如果删得只剩下自己了则退出编辑状态
                        if(dataSource.size() == 3){
                            for(int i = 0;i<dataSource.size();i++){
                                dataSource.get(i).setEditing(false);
                            }
                            isEditing = false;
                            //不需要更新界面
                            //mAdapter.notifyDataSetChanged();
                        }

                    }else{
                        //编辑中点击+-退出编辑状态
                        for(int i = 0;i<dataSource.size();i++){
                            dataSource.get(i).setEditing(false);
                        }
                        isEditing = false;
                        mAdapter.notifyDataSetChanged();

                        if(position == dataSource.size()-2){
                            if(dataSource.size() == 10){
                                Toast.makeText(PreviewCallActivity.this,getString(R.string.canNotAddMoreCallMember),Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //编辑状态点击+
                            Intent intent = new Intent(PreviewCallActivity.this,MeetingSelectActivity.class);
                            intent.putExtra("selectedData",previewData());
                            startActivity(intent);

                        }
                    }
                    minviteableNum = String.format(getString(R.string.inviteNum),10-dataSource.size());
                    dataSource.get(dataSource.size()-2).setName(minviteableNum);
                    return;
                }
                else if(position == dataSource.size()-1){
                    //点击-进入编辑状态
                    for(int i = 0;i<dataSource.size();i++){
                        dataSource.get(i).setEditing(true);
                    }
                    isEditing = true;
                    mAdapter.notifyDataSetChanged();
                    return;
                }
                else{

                    if(position < dataSource.size()-2){
                        //正常状态点击头像
                    }else{
                        if(dataSource.size() == 10){
                            Toast.makeText(PreviewCallActivity.this,getString(R.string.canNotAddMoreCallMember),Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //正常状态点击+
                        Intent intent = new Intent(PreviewCallActivity.this,MeetingSelectActivity.class);
                        intent.putExtra("selectedData",previewData());
                        startActivity(intent);
                   }
                    return;
                }

            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);

        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.addItemDecoration(new SpaceItemDecoration((int)DimenUtil.dp2px(5)));
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setAdapter(mAdapter);

    }
    private ArrayList<MeetingSelectTempEntity> previewData(){
        if(dataSource.size()<=3)
                return null;

        ArrayList<MeetingSelectTempEntity> newData = new ArrayList<>();

        for(int i = 1;i<dataSource.size()-2;i++){
            MeetingSelectTempEntity data = new MeetingSelectTempEntity();
            data.setId(dataSource.get(i).getId());
            data.setAvatar(dataSource.get(i).getAvatar());
            data.setName(dataSource.get(i).getName());

            newData.add(data);
        }
        return newData;

    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ArrayList<MeetingSelectTempEntity> data = (ArrayList<MeetingSelectTempEntity>)(intent.getSerializableExtra("newdata"));

        if((data == null)||
            data.size() == 0)
            return;

        ArrayList<CallListEntity.DataEntity> newData = new ArrayList<CallListEntity.DataEntity>();

        while(dataSource.size()>3){
            dataSource.remove(1);
        }
        for(int i = 0;i<data.size();i++){
            CallListEntity.DataEntity entity = new CallListEntity.DataEntity();
            entity.setName(data.get(i).getName());
            entity.setId(data.get(i).getId());
            entity.setAvatar(data.get(i).getAvatar());
            newData.add(entity);
        }
        if(newData!=null &&
                newData.size() != 0)
            dataSource.addAll(dataSource.size()-2,newData);

        mAdapter.notifyDataSetChanged();
        minviteableNum = String.format(getString(R.string.inviteNum),10-dataSource.size());
        dataSource.get(dataSource.size()-2).setName(minviteableNum);
    }
}
