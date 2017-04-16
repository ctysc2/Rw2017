package com.home.rw.mvp.ui.activitys.message;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.greendao.entity.UserInfo;
import com.home.rw.greendaohelper.UserInfoDaoHelper;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.CallListEntity;
import com.home.rw.mvp.entity.MeetingSelectTempEntity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.adapters.CallListAdatper;
import com.home.rw.mvp.ui.adapters.SpaceItemDecoration;
import com.home.rw.utils.DimenUtil;
import com.home.rw.utils.PreferenceUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.callkit.RongCallKit;
import io.rong.calllib.RongCallClient;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

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
            R.id.ll_netCall,
            R.id.ll_meeting,
            R.id.ll_normalCall,

    })
    public void onClick(View v){
        switch(v.getId()){
            case R.id.back:
            case R.id.leftText:
                finish();
                break;
            case R.id.ll_netCall:
            case R.id.ll_meeting:
            case R.id.ll_normalCall:
                processCall(v.getId());
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
    private void processCall(int id){
        int callListLength = dataSource.size();
        if(isEditing == true){
            for(int i = 0;i<dataSource.size();i++){
                dataSource.get(i).setEditing(false);
            }
            isEditing = false;
            mAdapter.notifyDataSetChanged();
        }
        if(RongCallClient.getInstance().getCallSession() != null){
            Toast.makeText(this,R.string.calling,Toast.LENGTH_SHORT).show();
            return;
        }
        switch (id){
            case R.id.ll_netCall:
                if(callListLength<4){
                    Toast.makeText(this,getString(R.string.toastAtLeastOneOp),Toast.LENGTH_SHORT).show();
                }else if(callListLength >4){
                    Toast.makeText(this,getString(R.string.onlyOneOp),Toast.LENGTH_SHORT).show();
                }else{
                    RongCallKit.startSingleCall( this, String.valueOf(dataSource.get(1).getId()), RongCallKit.CallMediaType.CALL_MEDIA_TYPE_AUDIO );
               }
                break;
            case R.id.ll_meeting:
                if(callListLength<5){
                    Toast.makeText(this,getString(R.string.toastatleast3formeeting),Toast.LENGTH_SHORT).show();
                }else{

                    ArrayList<String> userIds = new ArrayList<>();


                    for(CallListEntity.DataEntity ids:dataSource){
                        if(ids.getId()>0)
                            userIds.add(String.valueOf(ids.getId()));
                    }
                    final ArrayList<String> userlist = userIds;
                    RongIM.getInstance().createDiscussion("temp", userIds, new RongIMClient.CreateDiscussionCallback() {
                        @Override
                        public void onSuccess(String s) {
                            Log.i("RongYun","createDiscussion success current id:"+s);
                            RongCallKit.startMultiCall(PreviewCallActivity.this, Conversation.ConversationType.DISCUSSION, s, RongCallKit.CallMediaType.CALL_MEDIA_TYPE_AUDIO,  userlist);

                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            Log.i("RongYun","createDiscussion error errorCode:"+errorCode.getMessage());
                        }
                    });
              }
                break;
            case R.id.ll_normalCall:
                if(callListLength<4){
                    Toast.makeText(this,getString(R.string.toastAtLeastOneOp),Toast.LENGTH_SHORT).show();
                }else if(callListLength >4){
                    Toast.makeText(this,getString(R.string.onlyOneOp),Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + receiveData.getPhone()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }

    }
    private void initRecycleView() {

        receiveData = (CallListEntity.DataEntity)(getIntent().getSerializableExtra("data"));

        CallListEntity.DataEntity entity1 = new CallListEntity.DataEntity();
        UserInfo info = UserInfoDaoHelper.getInstance().getUserInfoById(PreferenceUtils.getPrefLong(this,"ID",0));
        if(info !=null){
            entity1.setAvatar(info.getAvatar());
            entity1.setName(info.getRealName());
            long id = info.getId();
            entity1.setId((int)id);

        }else{
            entity1.setName(PreferenceUtils.getPrefString(this,"realname",""));
            entity1.setAvatar(PreferenceUtils.getPrefString(this,"avatar",""));
            entity1.setId((int)PreferenceUtils.getPrefLong(this,"ID",0));
        }
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
                            intent.putExtra("entry","fromMeeting");
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
                        intent.putExtra("entry","fromMeeting");
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
            data.setPhone(dataSource.get(i).getPhone());

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
            entity.setPhone(data.get(i).getPhone());
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
