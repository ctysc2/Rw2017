package com.home.rw.mvp.ui.activitys.message;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.common.Const;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.BusinessMeetingPhoneEntity;
import com.home.rw.mvp.entity.CallListEntity;
import com.home.rw.mvp.entity.ContractAfterEntity;
import com.home.rw.mvp.entity.MeetingSelectEntity;
import com.home.rw.mvp.entity.MeetingSelectTempEntity;
import com.home.rw.mvp.entity.MyTeamEntity;
import com.home.rw.mvp.entity.OrgEntity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.activitys.work.SendRollActivity;
import com.home.rw.mvp.ui.adapters.BusinessMeetingAdapter;
import com.home.rw.mvp.ui.adapters.ContactSearchAdapter;
import com.home.rw.mvp.ui.adapters.MeetingSelectedAdapter;
import com.home.rw.mvp.ui.adapters.MyTeamAdapter;
import com.home.rw.mvp.ui.adapters.OriganzationAdapter;
import com.home.rw.utils.KeyBoardUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.home.rw.common.Const.SEARCH_CONTACT_ADD;
import static com.home.rw.common.Const.SEARCH_CONTACT_SELECT;
import static com.home.rw.common.Const.SEARCH_MYTEAM_ADD;
import static com.home.rw.common.Const.SEARCH_MYTEAM_SELECT;
import static com.home.rw.common.Const.SEARCH_ORG_ADD;
import static com.home.rw.common.Const.SEARCH_ORG_NORMAL;
import static com.home.rw.common.Const.SEARCH_ORG_SELECT;
import static com.home.rw.common.Const.SEARCH_RECENT;
import static com.home.rw.common.Const.SEARCH_RECENT_SELECT;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.bt_search)
    Button mBtSearch;

    @BindView(R.id.et_search)
    EditText mEtSearch;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @BindView(R.id.tv_notFind)
    TextView mNotFind;

    @BindView(R.id.bottomBar)
    RelativeLayout mBottomBar;

    @BindView(R.id.bt_confirm)
    Button mConfirm;
    private int mType;
    private String entry;

    private MyTeamAdapter mTeamAdapter;
    private ArrayList<MyTeamEntity.DataEntity> mTeamAllData = new ArrayList<>();
    private ArrayList<MyTeamEntity.DataEntity> mTeamFilterData = new ArrayList<>();

    private OriganzationAdapter mOrgAdapter;
    private ArrayList<OrgEntity.DataEntity> mOrgAllData = new ArrayList<>();
    private ArrayList<OrgEntity.DataEntity> mOrgFilterData = new ArrayList<>();


    private ContactSearchAdapter mContactAdapter;
    private ArrayList<ContractAfterEntity> mContactAllData = new ArrayList<>();
    private ArrayList<ContractAfterEntity> mContactFilterData = new ArrayList<>();

    private BusinessMeetingAdapter mRecentAdapter;
    private ArrayList<BusinessMeetingPhoneEntity.DataEntity> mRecentAllData = new ArrayList<>();
    private ArrayList<BusinessMeetingPhoneEntity.DataEntity> mRecentFilterData = new ArrayList<>();

    private MeetingSelectedAdapter mMeetingAdapter;
    private ArrayList<MeetingSelectEntity.DataEntity> mMeetingAllData = new ArrayList<>();
    private ArrayList<MeetingSelectEntity.DataEntity> mMeetingFilterData = new ArrayList<>();

    private ArrayList<MeetingSelectTempEntity> selectedData;
    @OnClick({R.id.back,
            R.id.bt_search,
            R.id.iv_del,
            R.id.bt_confirm,
    })
    public void onClick(View v){
        Intent intent;
        switch(v.getId()){
            case R.id.back:
                processFinish();
                break;
            case R.id.bt_confirm:
                if(entry.equals("fromMeeting"))
                    intent = new Intent(this,PreviewCallActivity.class);
                else
                    intent = new Intent(this,SendRollActivity.class);
                intent.putExtra("newdata",selectedData);
                startActivity(intent);
                break;
            case R.id.bt_search:
                if(mEtSearch.getText().toString().equals("")){
                    Toast.makeText(SearchActivity.this,getString(R.string.searchCannotEmpty),Toast.LENGTH_SHORT).show();
                }else{
                    doSearch();
                }
                break;
            case R.id.iv_del:
                mEtSearch.setText("");
                break;
            default:
                break;
        }
    }

    private void processFinish() {
        Intent intent;
        switch (mType){
            case SEARCH_CONTACT_ADD:
                intent = new Intent();
                intent.putExtra("searchDataAfter",mContactAllData);
                setResult(RESULT_OK,intent);
                break;
            case SEARCH_CONTACT_SELECT:
                intent = new Intent();
                intent.putExtra("newData",selectedData);
                setResult(RESULT_OK,intent);
                break;
            case SEARCH_ORG_ADD:
                intent = new Intent();
                intent.putExtra("searchDataAfter",mOrgAllData);
                setResult(RESULT_OK,intent);
                break;
            case SEARCH_ORG_SELECT:
                intent = new Intent();
                intent.putExtra("newData",selectedData);
                setResult(RESULT_OK,intent);
                break;
            case SEARCH_ORG_NORMAL:
                break;
            case SEARCH_MYTEAM_ADD:
                intent = new Intent();
                intent.putExtra("searchDataAfter",mTeamAllData);
                setResult(RESULT_OK,intent);
                break;
            case SEARCH_MYTEAM_SELECT:
                intent = new Intent();
                intent.putExtra("newData",selectedData);
                setResult(RESULT_OK,intent);
                break;
            case SEARCH_RECENT:
                break;
            case SEARCH_RECENT_SELECT:
                intent = new Intent();
                intent.putExtra("newData",selectedData);
                setResult(RESULT_OK,intent);
                break;
            default:
                break;
        }
        finish();
        overridePendingTransition(R.anim.search_fade_in, R.anim.search_fade_out);
    }

    private void doSearch(){
        switch (mType){
            case SEARCH_CONTACT_ADD:
            case SEARCH_CONTACT_SELECT:
                filterContactData(mEtSearch.getText().toString());
                break;
            case SEARCH_ORG_ADD:
            case SEARCH_ORG_NORMAL:
            case SEARCH_ORG_SELECT:
                filterOrgData(mEtSearch.getText().toString());
                break;
            case SEARCH_MYTEAM_ADD:
            case SEARCH_MYTEAM_SELECT:
                filterTeamData(mEtSearch.getText().toString());
                break;
            case SEARCH_RECENT:
                filterRecentData(mEtSearch.getText().toString());
                break;
            case SEARCH_RECENT_SELECT:
                filterRecentSelectData(mEtSearch.getText().toString());
            default:
                break;
        }
    }

    private void processNotFind() {

        String str = String.format(getString(R.string.notFind),mEtSearch.getText().toString());
        mRecycleView.setVisibility(View.GONE);
        mNotFind.setVisibility(View.VISIBLE);

        SpannableStringBuilder mSpannableStringBuilder=new SpannableStringBuilder(str);

        mSpannableStringBuilder.setSpan
                (new ForegroundColorSpan(Color.parseColor("#999999")),0 , 5, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        mSpannableStringBuilder.setSpan
                (new ForegroundColorSpan(Color.RED), 5, str.length()-1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        mSpannableStringBuilder.setSpan
                (new ForegroundColorSpan(Color.parseColor("#999999")), str.length()-1, str.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        mNotFind.setText(mSpannableStringBuilder);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void onBackPressed() {
        processFinish();
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        entry = getIntent().getStringExtra("entry");
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    //完成自己的事件
                    if(mEtSearch.getText().toString().equals("")){
                        Toast.makeText(SearchActivity.this,getString(R.string.searchCannotEmpty),Toast.LENGTH_SHORT).show();
                    }else{
                        doSearch();
                    }

                }
                return false;
            }
        });
        processSearchType();
    }

    private void processSearchType() {
        RecyclerView.Adapter mAdapter = null;
        mType = getIntent().getIntExtra("type",0);
        switch (mType){
            case SEARCH_CONTACT_ADD:
                mContactAllData = (ArrayList<ContractAfterEntity>)(getIntent().getSerializableExtra("data"));
                mContactAdapter = new ContactSearchAdapter(mContactFilterData,this, Const.TYPE_ADD);
                mContactAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(SearchActivity.this,SendFriendVerifiAvtivity.class);
                        intent.putExtra("position",position);
                        startActivityForResult(intent,SEARCH_CONTACT_ADD);
                    }
                });
                mAdapter = mContactAdapter;
                break;
            case SEARCH_CONTACT_SELECT:
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

                mBottomBar.setVisibility(View.VISIBLE);
                mContactAllData = (ArrayList<ContractAfterEntity>)(getIntent().getSerializableExtra("data"));
                mContactAdapter = new ContactSearchAdapter(mContactFilterData,this, Const.TYPE_SELECT);
                mContactAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        ContractAfterEntity entity = mContactFilterData.get(position);
                        if(entity.isSelected()){
                            entity.setSelected(false);
                            removeContactSelect(entity);
                        }else{
                            if(selectedData != null && selectedData.size() == 7 && entity.equals("fromMeeting")){
                                Toast.makeText(SearchActivity.this,getString(R.string.numCantOverHint),Toast.LENGTH_SHORT).show();
                                return;
                            }
                            entity.setSelected(true);
                            addContactSelect(entity);
                        }
                        if(entry.equals("fromMeeting")){
                            mConfirm.setText(String.format(getString(R.string.containNum),selectedData.size()));
                        }else{
                            mConfirm.setText(String.format(getString(R.string.containRollNum),selectedData.size()));
                        }                      mContactAdapter.notifyDataSetChanged();
                    }
                });

                mAdapter = mContactAdapter;
                break;
            case SEARCH_ORG_ADD:
                mOrgAllData = (ArrayList<OrgEntity.DataEntity>)(getIntent().getSerializableExtra("data"));
                mOrgAdapter = new OriganzationAdapter(mOrgFilterData,this, Const.TYPE_ADD);
                mOrgAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(SearchActivity.this,SendFriendVerifiAvtivity.class);
                        intent.putExtra("position",position);
                        startActivityForResult(intent,SEARCH_ORG_ADD);
                    }
                });

                mAdapter = mOrgAdapter;
                break;
            case SEARCH_ORG_SELECT:
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

                mBottomBar.setVisibility(View.VISIBLE);
                mOrgAllData = (ArrayList<OrgEntity.DataEntity>)(getIntent().getSerializableExtra("data"));
                mOrgAdapter = new OriganzationAdapter(mOrgFilterData,this, Const.TYPE_SELECT);
                mOrgAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        OrgEntity.DataEntity entity = mOrgFilterData.get(position);
                        if(entity.isSelected()){
                            entity.setSelected(false);
                            removeOrgSelect(entity);
                        }else{
                            if(selectedData != null && selectedData.size() == 7 && entity.equals("fromMeeting")){
                                Toast.makeText(SearchActivity.this,getString(R.string.numCantOverHint),Toast.LENGTH_SHORT).show();
                                return;
                            }
                            entity.setSelected(true);
                            addOrgSelect(entity);
                        }
                        if(entry.equals("fromMeeting")){
                            mConfirm.setText(String.format(getString(R.string.containNum),selectedData.size()));
                        }else{
                            mConfirm.setText(String.format(getString(R.string.containRollNum),selectedData.size()));
                        }                       mOrgAdapter.notifyDataSetChanged();
                    }
                });

                mAdapter = mOrgAdapter;
                break;
            case SEARCH_ORG_NORMAL:
                mOrgAllData = (ArrayList<OrgEntity.DataEntity>)(getIntent().getSerializableExtra("data"));
                mOrgAdapter = new OriganzationAdapter(mOrgFilterData,this, Const.TYPE_NORMAL);
                mOrgAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        OrgEntity.DataEntity entity = mOrgFilterData.get(position);
                        CallListEntity.DataEntity data = new CallListEntity.DataEntity();
                        data.setName(entity.getTitle());
                        data.setAvatar(entity.getAvatar());
                        Intent intent = new Intent(SearchActivity.this,PreviewCallActivity.class);
                        intent.putExtra("data",data);
                        intent.putExtra("entry","fromMeeting");
                        startActivity(intent);
                    }
                });
                mAdapter = mOrgAdapter;
                break;
            case SEARCH_MYTEAM_ADD:
                mTeamAllData = (ArrayList<MyTeamEntity.DataEntity>)(getIntent().getSerializableExtra("data"));
                mTeamAdapter = new  MyTeamAdapter(mTeamFilterData,this, Const.TYPE_ADD);
                mTeamAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(SearchActivity.this,SendFriendVerifiAvtivity.class);
                        intent.putExtra("position",position);
                        startActivityForResult(intent,SEARCH_MYTEAM_ADD);
                    }
                });

                mAdapter = mTeamAdapter;
                break;
            case SEARCH_MYTEAM_SELECT:
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

                mBottomBar.setVisibility(View.VISIBLE);
                mTeamAllData = (ArrayList<MyTeamEntity.DataEntity>)(getIntent().getSerializableExtra("data"));
                mTeamAdapter = new  MyTeamAdapter(mTeamFilterData,this, Const.TYPE_SELECT);
                mTeamAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        MyTeamEntity.DataEntity entity = mTeamFilterData.get(position);
                        if(entity.isSelected()){
                            entity.setSelected(false);
                            removeTeamSelect(entity);
                        }else{
                            if(selectedData != null && selectedData.size() == 7 && entity.equals("fromMeeting")){
                                Toast.makeText(SearchActivity.this,getString(R.string.numCantOverHint),Toast.LENGTH_SHORT).show();
                                return;
                            }
                            entity.setSelected(true);
                            addTeamSelect(entity);
                        }
                        if(entry.equals("fromMeeting")){
                            mConfirm.setText(String.format(getString(R.string.containNum),selectedData.size()));
                        }else{
                            mConfirm.setText(String.format(getString(R.string.containRollNum),selectedData.size()));
                        }                      mTeamAdapter.notifyDataSetChanged();
                    }
                });

                mAdapter = mTeamAdapter;
                break;
            case SEARCH_RECENT:
                mRecentAllData = (ArrayList<BusinessMeetingPhoneEntity.DataEntity>)(getIntent().getSerializableExtra("data"));
                mRecentAdapter = new BusinessMeetingAdapter(mRecentFilterData,this);
                mRecentAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        BusinessMeetingPhoneEntity.DataEntity entity = mRecentFilterData.get(position);
                        CallListEntity.DataEntity data = new CallListEntity.DataEntity();
                        data.setName(entity.getTitle());
                        data.setAvatar(entity.getAvatar());
                        Intent intent = new Intent(SearchActivity.this,PreviewCallActivity.class);
                        intent.putExtra("data",data);
                        intent.putExtra("entry","fromMeeting");
                        startActivity(intent);
                    }
                });
                mAdapter = mRecentAdapter;
                break;
            case SEARCH_RECENT_SELECT:
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

                mBottomBar.setVisibility(View.VISIBLE);
                mMeetingAllData = (ArrayList<MeetingSelectEntity.DataEntity>)(getIntent().getSerializableExtra("data"));
                mMeetingAdapter = new  MeetingSelectedAdapter(mMeetingFilterData,this);
                mMeetingAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        MeetingSelectEntity.DataEntity entity = mMeetingFilterData.get(position);
                        if(entity.isSelected()){
                            entity.setSelected(false);
                            removeMeetingSelect(entity);
                        }else{
                            if(selectedData != null && selectedData.size() == 7 && entity.equals("fromMeeting")){
                                Toast.makeText(SearchActivity.this,getString(R.string.numCantOverHint),Toast.LENGTH_SHORT).show();
                                return;
                            }
                            entity.setSelected(true);
                            addMeetingSelect(entity);
                        }
                        if(entry.equals("fromMeeting")){
                            mConfirm.setText(String.format(getString(R.string.containNum),selectedData.size()));
                        }else{
                            mConfirm.setText(String.format(getString(R.string.containRollNum),selectedData.size()));
                        }                       mMeetingAdapter.notifyDataSetChanged();
                    }
                });

                mAdapter = mMeetingAdapter;
            default:
                break;
        }

        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);
    }

    private void filterContactData(String name){
        mContactFilterData = new ArrayList<>();
        for(int i = 0;i<mContactAllData.size();i++){
            ContractAfterEntity entity = mContactAllData.get(i);
            if(entity.getName().contains(name)){
                mContactFilterData.add(entity);
            }
        }
        if(mContactFilterData.size() == 0){
            processNotFind();
        }else{
            mRecycleView.setVisibility(View.VISIBLE);
            mNotFind.setVisibility(View.GONE);
            mContactAdapter.setDataSource(mContactFilterData);
        }

    }
    private void filterOrgData(String name) {
        mOrgFilterData = new ArrayList<>();
        for(int i = 0;i<mOrgAllData.size();i++){
            OrgEntity.DataEntity entity = mOrgAllData.get(i);
            if(entity.getSubData()!=null){
                for(int j = 0;j<entity.getSubData().size();j++){
                    OrgEntity.DataEntity subEntity = entity.getSubData().get(j);
                    if(subEntity.getTitle().contains(name) &&
                            !mOrgFilterData.contains(subEntity)){
                        mOrgFilterData.add(subEntity);
                    }
                }
            }
        }
        if(mOrgFilterData.size() == 0){
            processNotFind();
        }else{
            mRecycleView.setVisibility(View.VISIBLE);
            mNotFind.setVisibility(View.GONE);
            mOrgAdapter.setDataSource(mOrgFilterData);
        }
    }
    private void filterTeamData(String name){
        mTeamFilterData = new ArrayList<>();
        for(int i = 0;i<mTeamAllData.size();i++){
            MyTeamEntity.DataEntity entity = mTeamAllData.get(i);
            if(entity.getTitle().contains(name)){
                mTeamFilterData.add(entity);
            }
        }
        if(mTeamFilterData.size() == 0){
            processNotFind();
        }else{
            mRecycleView.setVisibility(View.VISIBLE);
            mNotFind.setVisibility(View.GONE);
            mTeamAdapter.setDataSource(mTeamFilterData);
        }

    }
    private void filterRecentSelectData(String name){
        mMeetingFilterData = new ArrayList<>();
        for(int i = 0;i<mMeetingAllData.size();i++){
            MeetingSelectEntity.DataEntity entity = mMeetingAllData.get(i);
            if(entity.getSubData()!=null){
                for(int j = 0;j<entity.getSubData().size();j++){
                    MeetingSelectEntity.DataEntity subEntity = entity.getSubData().get(j);
                    if(subEntity.getTitle().contains(name) &&
                            !mMeetingFilterData.contains(subEntity)){
                        mMeetingFilterData.add(subEntity);
                    }
                }
            }
        }
        if(mMeetingFilterData.size() == 0){
            processNotFind();
        }else{
            mRecycleView.setVisibility(View.VISIBLE);
            mNotFind.setVisibility(View.GONE);
            mMeetingAdapter.setDataSource(mMeetingFilterData);
        }
    }
    private void filterRecentData(String name){
        mRecentFilterData = new ArrayList<>();
        for(int i = 0;i<mRecentAllData.size();i++){
            BusinessMeetingPhoneEntity.DataEntity entity = mRecentAllData.get(i);
            if(entity.getTitle().contains(name)){
                mRecentFilterData.add(entity);
            }
        }
        if(mRecentFilterData.size() == 0){
            processNotFind();
        }else{
            mRecycleView.setVisibility(View.VISIBLE);
            mNotFind.setVisibility(View.GONE);
            mRecentAdapter.setDataSource(mRecentFilterData);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = getCurrentFocus();
        new KeyBoardUtils(event,im,v).hideKeyBoardIfNecessary();
        return super.dispatchTouchEvent(event);
    }
    private void addOrgSelect(OrgEntity.DataEntity entity) {

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
        selectedData.add(data);

    }

    private void removeOrgSelect(OrgEntity.DataEntity entity) {
        if(selectedData == null)
            selectedData = new ArrayList<>();

        for(int i = 0;i<selectedData.size();i++){
            if(entity.getId() == selectedData.get(i).getId()){
                selectedData.remove(i);
                return;
            }



        }

    }
    private void addTeamSelect(MyTeamEntity.DataEntity entity) {

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
        selectedData.add(data);

    }

    private void removeTeamSelect(MyTeamEntity.DataEntity entity) {
        if(selectedData == null)
            selectedData = new ArrayList<>();

        for(int i = 0;i<selectedData.size();i++){
            if(entity.getId() == selectedData.get(i).getId()){
                selectedData.remove(i);
                return;
            }



        }

    }

    private void addContactSelect(ContractAfterEntity entity) {

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
        data.setName(entity.getName());
        selectedData.add(data);

    }

    private void removeContactSelect(ContractAfterEntity entity) {
        if(selectedData == null)
            selectedData = new ArrayList<>();

        for(int i = 0;i<selectedData.size();i++){
            if(entity.getId() == selectedData.get(i).getId()){
                selectedData.remove(i);
                return;
            }



        }

    }

    private void addMeetingSelect(MeetingSelectEntity.DataEntity entity) {

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
        selectedData.add(data);

    }

    private void removeMeetingSelect(MeetingSelectEntity.DataEntity entity) {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case SEARCH_MYTEAM_ADD:
                if(resultCode == RESULT_OK){
                    int position = data.getIntExtra("position",0);
                    mTeamFilterData.get(position).setAdded(true);
                    for(int i = 0;i<mTeamAllData.size();i++){
                        if(mTeamAllData.get(i).getId() == mTeamFilterData.get(position).getId()){
                            mTeamAllData.get(i).setAdded(true);
                        }
                    }
                    mTeamAdapter.notifyDataSetChanged();
                }
                break;
            case SEARCH_ORG_ADD:
                if(resultCode == RESULT_OK){
                    int position = data.getIntExtra("position",0);
                    mOrgFilterData.get(position).setAdded(true);
                    for(int i = 0;i<mOrgAllData.size();i++){
                        OrgEntity.DataEntity entity = mOrgAllData.get(i);
                        if(entity.getSubData() != null){
                            for(int j = 0;j<entity.getSubData().size();j++){
                                OrgEntity.DataEntity subEntity = entity.getSubData().get(j);
                                if(subEntity.getId() == mOrgFilterData.get(position).getId()){
                                    subEntity.setAdded(true);
                                }
                            }
                        }
                    }
                    mOrgAdapter.notifyDataSetChanged();
                }
                break;
            case SEARCH_CONTACT_ADD:
                if(resultCode == RESULT_OK){
                    int position = data.getIntExtra("position",0);
                    mContactFilterData.get(position).setAdded(true);
                    for(int i = 0;i<mContactAllData.size();i++){
                        if(mContactAllData.get(i).getId() == mContactFilterData.get(position).getId()){
                            mContactAllData.get(i).setAdded(true);
                        }
                    }
                    mContactAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }
}
