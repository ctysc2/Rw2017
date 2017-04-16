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
import com.home.rw.greendao.entity.UserInfo;
import com.home.rw.greendaohelper.UserInfoDaoHelper;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.CallListEntity;
import com.home.rw.mvp.entity.MeetingSelectTempEntity;
import com.home.rw.mvp.entity.MyTeamEntity;
import com.home.rw.mvp.entity.OrgEntity;
import com.home.rw.mvp.entity.message.DepartmentEntity;
import com.home.rw.mvp.presenter.impl.DepartmentPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.activitys.work.SendRollActivity;
import com.home.rw.mvp.ui.adapters.OriganzationAdapter;
import com.home.rw.mvp.view.DepartmentView;
import com.home.rw.utils.DateUtils;
import com.home.rw.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

import static com.home.rw.common.Const.SEARCH_MYTEAM_ADD;
import static com.home.rw.common.Const.SEARCH_MYTEAM_SELECT;
import static com.home.rw.common.Const.SEARCH_ORG_ADD;
import static com.home.rw.common.Const.SEARCH_ORG_NORMAL;
import static com.home.rw.common.Const.SEARCH_ORG_SELECT;
import static com.home.rw.common.Const.TYPE_ADD;
import static com.home.rw.common.Const.TYPE_NORMAL;
import static com.home.rw.common.Const.TYPE_SELECT;

public class OriganizationActivity extends BaseActivity implements DepartmentView{

    private ArrayList<OrgEntity.DataEntity> dataSource = new ArrayList();

    private OriganzationAdapter mAdapter;

    private String entryType = "";
    @BindView(R.id.back)
    ImageButton mBack;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @BindView(R.id.bottomBar)
    RelativeLayout mBottomBar;

    @BindView(R.id.bt_confirm)
    Button mConfirm;

    @Inject
    DepartmentPresenterImpl mDepartmentPresenterImpl;
    private String entry;

    private ArrayList<MeetingSelectTempEntity> selectedData;

    @OnClick({R.id.back,
            R.id.bt_confirm,
            R.id.search

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
                int type = SEARCH_ORG_ADD;
                switch (entryType){
                    case TYPE_ADD:
                        type = SEARCH_ORG_ADD;
                        break;
                    case TYPE_NORMAL:
                        type = SEARCH_ORG_NORMAL;
                        break;
                    case TYPE_SELECT:
                        type = SEARCH_ORG_SELECT;
                        intent.putExtra("selectedData",selectedData);
                        break;
                    default:
                        break;

                }
                intent.putExtra("type",type);
                intent.putExtra("data",dataSource);
                intent.putExtra("entry",entry);
                startActivityForResult(intent,type);
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
        return R.layout.activity_origanization;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDepartmentPresenterImpl!=null){
            mDepartmentPresenterImpl.onDestroy();
        }
    }

    @Override
    public void initViews() {
        midText.setText(getString(R.string.organization));
        mBack.setImageResource(R.drawable.btn_back);
        entryType = getIntent().getStringExtra("type");
        entry = getIntent().getStringExtra("entry");
        mDepartmentPresenterImpl.attachView(this);
        switch (entryType){
            case Const.TYPE_ADD:
                initRecycleViewAdd();
                break;
            case TYPE_NORMAL:
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
        mDepartmentPresenterImpl.getDepartmentList();
        
    }
    //更新选择数目
    private void checkInitSelect(){
        if(selectedData != null ){

            for(int i = 0;i<dataSource.size();i++){
                if(dataSource.get(i).getSubData() != null){
                    ArrayList<OrgEntity.DataEntity> subData = dataSource.get(i).getSubData();
                    for(int j = 0;j<subData.size();j++){
                        subData.get(j).setSelected(false);
                        for (int k = 0;k<selectedData.size();k++){
                            if(subData.get(j).getId() == selectedData.get(k).getId()){
                                subData.get(j).setSelected(true);
                                break;
                            }

                        }
                    }
                }



            }
        }
    }
    private void initRecycleViewSelect(){

        mAdapter = new OriganzationAdapter(dataSource,this,Const.TYPE_SELECT);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                OrgEntity.DataEntity entity = dataSource.get(position);
                if(entity.getSubData() == null){
                    if(entity.isSelected()){
                        entity.setSelected(false);
                        removeSelect(entity);
                    }else{
                        if(selectedData != null && selectedData.size() == 7 && entry.equals("fromMeeting")){
                            Toast.makeText(OriganizationActivity.this,getString(R.string.numCantOverHint),Toast.LENGTH_SHORT).show();
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
    private void initRecycleViewAdd() {


        mAdapter = new OriganzationAdapter(dataSource,this,Const.TYPE_ADD);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                OrgEntity.DataEntity entity = dataSource.get(position);
                if(entity.getSubData() == null){
                    Intent intent = new Intent(OriganizationActivity.this,SendFriendVerifiAvtivity.class);
                    intent.putExtra("userId",String.valueOf(entity.getId()));
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initRecycleViewNormal() {

        mAdapter = new OriganzationAdapter(dataSource,this, TYPE_NORMAL);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                OrgEntity.DataEntity entity = dataSource.get(position);
                if(entity.getSubData() == null){
                    if(entity.isAdded()){
                        UserInfo user = new UserInfo();
                        user.setAvatar(dataSource.get(position).getAvatar());
                        long id = dataSource.get(position).getId();
                        user.setId(id);
                        user.setPhone(dataSource.get(position).getSubTitle());
                        user.setRealName(dataSource.get(position).getTitle());
                        user.setNickName(dataSource.get(position).getNickname());
                        UserInfoDaoHelper.getInstance().insertUserInfo(user);
                        RongIM.getInstance().startConversation(OriganizationActivity.this, Conversation.ConversationType.PRIVATE,String.valueOf(dataSource.get(position).getId()),dataSource.get(position).getNickname()==null?dataSource.get(position).getTitle():dataSource.get(position).getNickname());
                    }else{
                        CallListEntity.DataEntity data = new CallListEntity.DataEntity();
                        data.setName(entity.getTitle());
                        data.setAvatar(entity.getAvatar());
                        data.setId(entity.getId());
                        data.setPhone(entity.getSubTitle());
                        Intent intent = new Intent(OriganizationActivity.this,PreviewCallActivity.class);
                        intent.putExtra("data",data);
                        intent.putExtra("entry","fromMeeting");
                        startActivity(intent);
                    }

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
    private void addSelect(OrgEntity.DataEntity entity) {

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

    private void removeSelect(OrgEntity.DataEntity entity) {
        if(selectedData == null)
            selectedData = new ArrayList<>();

        for(int i = 0;i<selectedData.size();i++){
            if(entity.getId() == selectedData.get(i).getId()){
                selectedData.remove(i);
                return;
            }



        }

    }
    private void checkAddStatus(ArrayList<OrgEntity.DataEntity> tempDataSource,OrgEntity.DataEntity source){

        for(int i = 0;i<tempDataSource.size();i++){

            OrgEntity.DataEntity tempEntity = tempDataSource.get(i);

            if(tempEntity.getSubData() != null){
                for(int j = 0;j<tempEntity.getSubData().size();j++){
                    OrgEntity.DataEntity subTempEntity = tempEntity.getSubData().get(j);
                    if(source.getId() == subTempEntity.getId()){
                        source.setAdded(subTempEntity.isAdded());
                    }
                }
            }


        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case SEARCH_ORG_SELECT:
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
    private ArrayList<OrgEntity.DataEntity> dataTransfer(ArrayList<DepartmentEntity.DataEntity> sourceList){
        ArrayList<OrgEntity.DataEntity> list = new ArrayList<>();

        if(sourceList == null || sourceList.size() == 0)
            return list;

        for(int i = 0;i<sourceList.size();i++){
            DepartmentEntity.DataEntity source = sourceList.get(i);
            OrgEntity.DataEntity entity = new OrgEntity.DataEntity();
            ArrayList<OrgEntity.DataEntity> subEntityList = new ArrayList<>();
            for(int j = 0;j<source.getEmployees().size();j++){

                DepartmentEntity.DataEntity.Employees employees = source.getEmployees().get(j);
                OrgEntity.DataEntity subEntity = new OrgEntity.DataEntity();
                subEntity.setTitle(!TextUtils.isEmpty(employees.getNickname())?employees.getNickname():employees.getRealname());
                subEntity.setSubTitle(employees.getPhone());
                subEntity.setAvatar(employees.getAvatar());
                subEntity.setAdded(employees.getIsFriend().equals("0")?false:true);
                subEntity.setId(Integer.parseInt(employees.getId()));
                if(!TextUtils.isEmpty(employees.getLastSpeakingTime()))
                    subEntity.setDate(DateUtils.getMessageMain(new Date(Long.parseLong(employees.getLastSpeakingTime()))));
                subEntityList.add(subEntity);

            }
            entity.setId(-1);
            entity.setTitle(source.getName());
            entity.setAvatar(source.getLogo());
            entity.setSubData(subEntityList);
            list.add(entity);

        }

        return list;
    }

    @Override
    public void getDepartmentListCompleted(DepartmentEntity data) {
        if(data.getCode().equals("ok")){
            ArrayList<DepartmentEntity.DataEntity> orgList =  data.getData();
            if(entryType.equals(Const.TYPE_SELECT)||
                    entryType.equals(Const.TYPE_ADD)){

                String myUserId = String.valueOf(PreferenceUtils.getPrefLong(this,"ID",0));

                for(int i=0;i<orgList.size();i++){
                    ArrayList<DepartmentEntity.DataEntity.Employees> employees =  orgList.get(i).getEmployees();
                    for(int j = 0;j<employees.size();j++){
                        if(employees.get(j).getId().equals(myUserId)){
                            orgList.get(i).getEmployees().remove(j);
                            break;
                        }
                    }

                }
            }

            dataSource = dataTransfer(orgList);
            mAdapter.setDataSource(dataSource);
            if(entryType.equals(Const.TYPE_SELECT))
                checkInitSelect();
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
