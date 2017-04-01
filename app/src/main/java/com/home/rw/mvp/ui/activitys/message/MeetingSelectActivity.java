package com.home.rw.mvp.ui.activitys.message;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.common.Const;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.CallListEntity;
import com.home.rw.mvp.entity.FacusListEntity;
import com.home.rw.mvp.entity.MeetingSelectEntity;
import com.home.rw.mvp.entity.MeetingSelectTempEntity;
import com.home.rw.mvp.entity.MessegeMainEntity;
import com.home.rw.mvp.entity.MyTeamEntity;
import com.home.rw.mvp.entity.message.BusineseCallEntity;
import com.home.rw.mvp.entity.message.MessageCommonEntity;
import com.home.rw.mvp.presenter.impl.BusinessCallPrensenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.activitys.social.OthersDetailActivity;
import com.home.rw.mvp.ui.activitys.work.SendRollActivity;
import com.home.rw.mvp.ui.adapters.MeetingSelectedAdapter;
import com.home.rw.mvp.ui.adapters.MessegeMainAdapter;
import com.home.rw.mvp.view.BusinessCallView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.home.rw.common.Const.SEARCH_MYTEAM_ADD;
import static com.home.rw.common.Const.SEARCH_MYTEAM_SELECT;
import static com.home.rw.common.Const.SEARCH_RECENT_SELECT;
import static com.home.rw.common.Const.TYPE_ADD;
import static com.home.rw.common.Const.TYPE_NORMAL;

public class MeetingSelectActivity extends BaseActivity implements BusinessCallView{

    @BindView(R.id.back)
    ImageButton mBack;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @BindView(R.id.bt_confirm)
    Button mConfirm;

    @Inject
    BusinessCallPrensenterImpl mBusinessCallPrensenterImpl;
    private ArrayList<MeetingSelectEntity.DataEntity> dataSource = new ArrayList<>();

    private MeetingSelectedAdapter mAdapter;

    private ArrayList<MeetingSelectTempEntity> selectedData;

    private int selectedNum;

    private String entry;

    @OnClick({R.id.back,
            R.id.bt_confirm,
            R.id.search,

    })
    public void OnClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.bt_confirm:
                if(entry.equals("fromMeeting"))
                    intent = new Intent(this,PreviewCallActivity.class);
                else
                    intent = new Intent(this,SendRollActivity.class);
                intent.putExtra("newdata",selectedData);
                startActivity(intent);
                break;
            case R.id.search:
                intent = new Intent(this,SearchActivity.class);
                intent.putExtra("selectedData",selectedData);
                intent.putExtra("type",SEARCH_RECENT_SELECT);
                intent.putExtra("data",dataSource);
                intent.putExtra("entry",entry);
                startActivityForResult(intent,0);
                overridePendingTransition(R.anim.search_fade_in,R.anim.search_fade_out);
                break;
            default:
                break;
        }

    }
    private void initRecycleView() {
        MeetingSelectEntity.DataEntity entity1 = new MeetingSelectEntity.DataEntity();
        entity1.setId(-1);

        MeetingSelectEntity.DataEntity entity2 = new MeetingSelectEntity.DataEntity();
        entity2.setId(-2);

        MeetingSelectEntity.DataEntity entity3 = new MeetingSelectEntity.DataEntity();
        entity3.setId(-3);

        MeetingSelectEntity.DataEntity entity4 = new MeetingSelectEntity.DataEntity();
        entity4.setId(-4);


        dataSource.add(entity1);
        dataSource.add(entity2);
        dataSource.add(entity3);
        dataSource.add(entity4);


        mAdapter = new MeetingSelectedAdapter(dataSource,this);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                MeetingSelectEntity.DataEntity entity = dataSource.get(position);
                if(entity.getId()<=0){
                    Intent intent;
                    switch (entity.getId()){
                        case -1:
                            intent = new Intent(MeetingSelectActivity.this,ContactsActivity.class);
                            intent.putExtra("type",Const.TYPE_SELECT);
                            intent.putExtra("selectedData",selectedData);
                            intent.putExtra("entry",entry);
                            startActivityForResult(intent,0);
                            break;
                        case -2:
                            intent = new Intent(MeetingSelectActivity.this,OriganizationActivity.class);
                            intent.putExtra("type", Const.TYPE_SELECT);
                            intent.putExtra("selectedData",selectedData);
                            intent.putExtra("entry",entry);
                            startActivityForResult(intent,0);
                            break;
                        case -3:
                            intent = new Intent(MeetingSelectActivity.this,MyTeamActivity.class);
                            intent.putExtra("type", Const.TYPE_SELECT);
                            intent.putExtra("selectedData",selectedData);
                            intent.putExtra("entry",entry);
                            startActivityForResult(intent,0);
                            break;
                        case -4:
                            if(entity.isExpanded()){
                                entity.setExpanded(false);
                                if(entity.getSubData()!=null)
                                    dataSource.removeAll(entity.getSubData());
                            }else{
                                entity.setExpanded(true);
                                if(entity.getSubData()!=null)
                                    dataSource.addAll(entity.getSubData());
                            }
                            mAdapter.notifyDataSetChanged();
                            break;
                        default:
                            break;
                    }
                }else{
                    if(entity.isSelected()){
                        entity.setSelected(false);
                        removeSelect(entity);
                    }else{
                        if(selectedData != null && selectedData.size() == 7 && entry.equals("fromMeeting")){
                            Toast.makeText(MeetingSelectActivity.this,getString(R.string.numCantOverHint),Toast.LENGTH_SHORT).show();
                            return;
                        }
                        entity.setSelected(true);
                        addSelect(entity);
                    }
                    if(entry.equals("fromMeeting")){
                        mConfirm.setText(String.format(getString(R.string.containNum),selectedData.size()));
                    }else{
                        mConfirm.setText(String.format(getString(R.string.containRollNum),selectedData.size()));
                    }                   mAdapter.notifyDataSetChanged();
                }
            }
        });
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);
    }
    private void checkInitSelect(){
        MeetingSelectEntity.DataEntity entity = dataSource.get(3);
        ArrayList<MeetingSelectEntity.DataEntity> subData = entity.getSubData();
        if(selectedData != null &&
                subData !=null ){

            for(int i = 0;i<subData.size();i++){
                subData.get(i).setSelected(false);
                for (int j = 0;j<selectedData.size();j++){
                    if(subData.get(i).getId() == selectedData.get(j).getId()){
                        subData.get(i).setSelected(true);
                        break;
                    }

                }
            }
        }
    }
    private void addSelect(MeetingSelectEntity.DataEntity entity) {

        if(selectedData == null)
            selectedData = new ArrayList<>();


        for(int i = 0;i<selectedData.size();i++){
            if(entity.getId() == selectedData.get(i).getId()){
                return;
            }

        }
        MeetingSelectTempEntity data = new MeetingSelectTempEntity();
        data.setId(entity.getId());
        data.setAvatar(entity.getAvatar());
        data.setName(entity.getTitle());
        data.setPhone(entity.getPhone());
        selectedData.add(data);

    }

    private void removeSelect(MeetingSelectEntity.DataEntity entity) {
        if(selectedData == null)
            selectedData = new ArrayList<>();

        for(int i = 0;i<selectedData.size();i++){
            if(entity.getId() == selectedData.get(i).getId()){
                selectedData.remove(i);
                return;
            }



        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_meeting_select;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBusinessCallPrensenterImpl!=null)
            mBusinessCallPrensenterImpl.onDestroy();
    }

    @Override
    public void initViews() {

        mBack.setImageResource(R.drawable.btn_back);
        selectedData = (ArrayList<MeetingSelectTempEntity>)(getIntent().getSerializableExtra("selectedData"));
        entry = getIntent().getStringExtra("entry");
        mBusinessCallPrensenterImpl.attachView(this);
        if(entry.equals("fromMeeting")){
            midText.setText(R.string.meetintSelectHint1);
        }else{
            midText.setText(R.string.meetintSelectHint2);
        }

        if((selectedData == null)||
            selectedData.size() == 0){
            if(entry.equals("fromMeeting")){
                mConfirm.setText(String.format(getString(R.string.containNum),0));
            }else{
                mConfirm.setText(String.format(getString(R.string.containRollNum),0));
            }

        }else{
            if(entry.equals("fromMeeting")){
                mConfirm.setText(String.format(getString(R.string.containNum),selectedData.size()));
            }else{
                mConfirm.setText(String.format(getString(R.string.containRollNum),selectedData.size()));
            }
        }
        mBusinessCallPrensenterImpl.getBusinessCall();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initRecycleView();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 0:
                if(resultCode == RESULT_OK){
                    selectedData = (ArrayList<MeetingSelectTempEntity>)(data.getSerializableExtra("newData"));
                    checkInitSelect();
                    mAdapter.notifyDataSetChanged();
                    if((selectedData == null)||
                            selectedData.size() == 0){
                        if(entry.equals("fromMeeting")){
                            mConfirm.setText(String.format(getString(R.string.containNum),0));
                        }else{
                            mConfirm.setText(String.format(getString(R.string.containRollNum),0));
                        }

                    }else{
                        if(entry.equals("fromMeeting")){
                            mConfirm.setText(String.format(getString(R.string.containNum),selectedData.size()));
                        }else{
                            mConfirm.setText(String.format(getString(R.string.containRollNum),selectedData.size()));
                        }
                    }
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void getBusinessCallCompleted(BusineseCallEntity data) {
        if(data.getCode().equals("ok")){
            ArrayList<MeetingSelectEntity.DataEntity> subData = dataTransfer(data.getData());
            dataSource.get(3).setSubData(subData);
            checkInitSelect();
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
    private ArrayList<MeetingSelectEntity.DataEntity> dataTransfer(ArrayList<MessageCommonEntity> source){
        ArrayList<MeetingSelectEntity.DataEntity> list = new ArrayList<>();
        if(source == null || source.size() == 0)
            return list;

        for(int i = 0;i<source.size();i++){
            MeetingSelectEntity.DataEntity entity = new MeetingSelectEntity.DataEntity();
            MessageCommonEntity sourceEntity = source.get(i);
            entity.setId(Integer.parseInt(sourceEntity.getUserId()));
            entity.setAvatar(sourceEntity.getAvatar());
            entity.setTitle(!TextUtils.isEmpty(sourceEntity.getNickname())?sourceEntity.getNickname():sourceEntity.getRealname());
            entity.setPhone(sourceEntity.getPhone());
            list.add(entity);
        }
        return list;
    }
}
