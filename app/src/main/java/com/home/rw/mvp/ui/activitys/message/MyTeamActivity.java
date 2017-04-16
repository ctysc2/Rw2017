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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.common.Const;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.CallListEntity;
import com.home.rw.mvp.entity.MeetingSelectEntity;
import com.home.rw.mvp.entity.MeetingSelectTempEntity;
import com.home.rw.mvp.entity.MyTeamEntity;
import com.home.rw.mvp.entity.OrgEntity;
import com.home.rw.mvp.entity.message.MessageCommonEntity;
import com.home.rw.mvp.presenter.impl.MyTeamPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.activitys.work.SendRollActivity;
import com.home.rw.mvp.ui.adapters.MyTeamAdapter;
import com.home.rw.mvp.ui.adapters.OriganzationAdapter;
import com.home.rw.mvp.view.MyTeamView;
import com.home.rw.utils.PreferenceUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.home.rw.common.Const.SEARCH_MYTEAM_ADD;
import static com.home.rw.common.Const.SEARCH_MYTEAM_SELECT;
import static com.home.rw.common.Const.TYPE_ADD;

public class MyTeamActivity extends BaseActivity implements MyTeamView{

    private ArrayList<MyTeamEntity.DataEntity> dataSource = new ArrayList();

    private MyTeamAdapter mAdapter;

    private ArrayList<MeetingSelectTempEntity> selectedData;

    private String entryType = "";

    private String entry;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @BindView(R.id.back)
    ImageButton mBack;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.bottomBar)
    RelativeLayout mBottomBar;

    @BindView(R.id.bt_confirm)
    Button mConfirm;

    @Inject
    MyTeamPresenterImpl mMyTeamPresenterImpl;
    @OnClick({R.id.back,
            R.id.bt_confirm,
            R.id.search,

    })
    public void OnClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.back:
                intent = new Intent();
                intent.putExtra("newData",selectedData);
                setResult(RESULT_OK,intent);
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
                intent.putExtra("type",entryType.equals(TYPE_ADD)?SEARCH_MYTEAM_ADD:SEARCH_MYTEAM_SELECT);
                intent.putExtra("data",dataSource);
                intent.putExtra("entry",entry);
                intent.putExtra("selectedData",selectedData);
                startActivityForResult(intent,entryType.equals(TYPE_ADD)?SEARCH_MYTEAM_ADD:SEARCH_MYTEAM_SELECT);
                overridePendingTransition(R.anim.search_fade_in,R.anim.search_fade_out);
                break;
            default:
                break;
        }

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("newData",selectedData);
        setResult(RESULT_OK,intent);
        finish();
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_my_team;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(getString(R.string.myTeam));
        mBack.setImageResource(R.drawable.btn_back);
        mMyTeamPresenterImpl.attachView(this);
        entryType = getIntent().getStringExtra("type");
        entry = getIntent().getStringExtra("entry");

        switch (entryType){
            case TYPE_ADD:
                initRecycleViewAdd();
                break;
            case Const.TYPE_NORMAL:
                initRecycleViewNormal();
                break;
            case Const.TYPE_SELECT:
                selectedData = (ArrayList<MeetingSelectTempEntity>)(getIntent().getSerializableExtra("selectedData"));

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

                initRecycleViewSelect();
                mBottomBar.setVisibility(View.VISIBLE);
                break;
        }
        mMyTeamPresenterImpl.getMyTeam();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mMyTeamPresenterImpl!=null)
            mMyTeamPresenterImpl.onDestroy();
    }

    private void checkInitSelect(){
        if(selectedData != null ){

            for(int i = 0;i<dataSource.size();i++){
                dataSource.get(i).setSelected(false);
                for (int j = 0;j<selectedData.size();j++){
                    if(dataSource.get(i).getId() == selectedData.get(j).getId()){
                        dataSource.get(i).setSelected(true);
                        break;
                    }

                }
            }
        }
    }
    private void addSelect(MyTeamEntity.DataEntity entity) {

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
        data.setPhone(entity.getSubTitle());
        selectedData.add(data);

    }

    private void removeSelect(MyTeamEntity.DataEntity entity) {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        
    }

    private void initRecycleViewSelect() {


        mAdapter = new MyTeamAdapter(dataSource,this,Const.TYPE_SELECT);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                MyTeamEntity.DataEntity entity = dataSource.get(position);
                if(entity.isSelected()){
                    entity.setSelected(false);
                    removeSelect(entity);
                }else{
                    if(selectedData != null && selectedData.size() == 7 && entry.equals("fromMeeting")){
                        Toast.makeText(MyTeamActivity.this,getString(R.string.numCantOverHint),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    entity.setSelected(true);
                    addSelect(entity);
                }
                if(entry.equals("fromMeeting")){
                    mConfirm.setText(String.format(getString(R.string.containNum),selectedData.size()));
                }else{
                    mConfirm.setText(String.format(getString(R.string.containRollNum),selectedData.size()));
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

    private void initRecycleViewAdd() {

        mAdapter = new MyTeamAdapter(dataSource,this, TYPE_ADD);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = new Intent(MyTeamActivity.this,SendFriendVerifiAvtivity.class);
                intent.putExtra("userId",String.valueOf(dataSource.get(position).getId()));
                startActivity(intent);

            }
        });
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);
    }

    private void initRecycleViewNormal() {


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case SEARCH_MYTEAM_SELECT:
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
                break;
            default:
                break;
        }
    }

    @Override
    public void getMyTeamCompleted(com.home.rw.mvp.entity.message.MyTeamEntity data) {
        if(data.getCode().equals("ok")){
            ArrayList<MessageCommonEntity> persons = data.getData().getPersons();
            if(entryType.equals(Const.TYPE_SELECT)||
                    entryType.equals(Const.TYPE_ADD)){
                String myUserId = String.valueOf(PreferenceUtils.getPrefLong(this,"ID",0));
                for(int i=0;i<persons.size();i++){
                    if(persons.get(i).getUserId().equals(myUserId)){
                        persons.remove(i);
                        break;
                    }
                }
            }
            dataSource = dataTransFer(persons);

            if(entryType.equals(Const.TYPE_SELECT)){
                checkInitSelect();
            }
            mAdapter.setDataSource(dataSource);
        }
    }
    private ArrayList<MyTeamEntity.DataEntity> dataTransFer(ArrayList<MessageCommonEntity> sourceList){
        ArrayList<MyTeamEntity.DataEntity> listData = new ArrayList<>();
        if(sourceList == null || sourceList.size() == 0){
            return listData;
        }
        for(int i = 0;i<sourceList.size();i++){
            MyTeamEntity.DataEntity entity = new MyTeamEntity.DataEntity();
            MessageCommonEntity source = sourceList.get(i);
            if(source.getUserId()!=null)
                entity.setId(Integer.parseInt(source.getUserId()));
            entity.setAvatar(source.getAvatar());
            entity.setTitle(!TextUtils.isEmpty(source.getNickname())?source.getNickname():source.getRealname());
            entity.setSubTitle(source.getPhone());
            entity.setAdded(source.getIsFriend().equals("0")?false:true);
            listData.add(entity);
        }


        return listData;
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
