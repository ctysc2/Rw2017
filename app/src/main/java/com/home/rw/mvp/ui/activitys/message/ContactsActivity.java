package com.home.rw.mvp.ui.activitys.message;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.common.Const;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.CallListEntity;
import com.home.rw.mvp.entity.ContractAfterEntity;
import com.home.rw.mvp.entity.ContractInitialEntity;
import com.home.rw.mvp.entity.MeetingSelectTempEntity;
import com.home.rw.mvp.entity.MyTeamEntity;
import com.home.rw.mvp.entity.message.ContactListEntity;
import com.home.rw.mvp.entity.message.MessageCommonEntity;
import com.home.rw.mvp.interactor.impl.UserByPhoneInteractorImpl;
import com.home.rw.mvp.presenter.impl.UserByPhonePresenterImpl;
import com.home.rw.mvp.presenter.impl.UserInfoPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.activitys.work.SendRollActivity;
import com.home.rw.mvp.ui.adapters.ContactAdapter;
import com.home.rw.mvp.ui.adapters.ContactAdapterAdd;
import com.home.rw.mvp.ui.adapters.ContactAdapterSelect;
import com.home.rw.mvp.ui.adapters.base.BaseListViewAdapter;
import com.home.rw.mvp.view.UserByPhoneView;
import com.home.rw.utils.CharacterParser;
import com.home.rw.utils.PinyinComparator;
import com.home.rw.widget.SideBar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.home.rw.common.Const.SEARCH_CONTACT_ADD;
import static com.home.rw.common.Const.SEARCH_CONTACT_SELECT;
import static com.home.rw.common.Const.SEARCH_MYTEAM_ADD;
import static com.home.rw.common.Const.SEARCH_MYTEAM_SELECT;
import static com.home.rw.common.Const.TYPE_ADD;

public class ContactsActivity extends BaseActivity implements UserByPhoneView{

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rv_list)
    ListView mListView;

    @BindView(R.id.sidrbar)
    SideBar mSidBar;

    @BindView(R.id.group_dialog)
    TextView mDialogTextView;

    @BindView(R.id.bottomBar)
    RelativeLayout mBottomBar;

    @BindView(R.id.bt_confirm)
    Button mConfirm;

    @Inject
    UserByPhonePresenterImpl mUserByPhonePresenterImpl;

    private String entry;

    private ArrayList<ContractInitialEntity> initContacts;
    private ArrayList<ContractAfterEntity> datasource;
    private String entryType = "";
    private BaseListViewAdapter mAdapter;
    private PinyinComparator mPinyinComparator;
    private CharacterParser mCharacterParser;
    private ArrayList<MeetingSelectTempEntity> selectedData;

    @OnClick({R.id.back,
            R.id.bt_confirm,
            R.id.search
    })
    public void onClick(View v){
        Intent intent;
        switch(v.getId()){
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
                intent.putExtra("type",entryType.equals(TYPE_ADD)?SEARCH_CONTACT_ADD:SEARCH_CONTACT_SELECT);
                intent.putExtra("data",datasource);
                intent.putExtra("selectedData",selectedData);
                intent.putExtra("entry",entry);
                startActivityForResult(intent,entryType.equals(TYPE_ADD)?SEARCH_CONTACT_ADD:SEARCH_CONTACT_SELECT);
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
        return R.layout.activity_contacts;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }
    private void initListViewAdd(){
        mAdapter = new ContactAdapterAdd(this, datasource);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(ContactsActivity.this,SendFriendVerifiAvtivity.class);
                intent.putExtra("userId",String.valueOf(datasource.get(position).getId()));
                startActivity(intent);
            }
        });
        mListView.setAdapter(mAdapter);
    }
    private void initListViewNormal(){
        mAdapter = new ContactAdapter(this, datasource);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ContractAfterEntity entity = datasource.get(position);

                CallListEntity.DataEntity data = new CallListEntity.DataEntity();
                data.setName(entity.getName());
                data.setAvatar(entity.getAvatar());
                Intent intent = new Intent(ContactsActivity.this,PreviewCallActivity.class);
                intent.putExtra("data",data);
                startActivity(intent);
            }
        });
        mListView.setAdapter(mAdapter);

    }

    private void initListViewSelect() {
        mAdapter = new ContactAdapterSelect(this, datasource);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ContractAfterEntity entity = datasource.get(position);
                if(entity.isSelected()){
                    entity.setSelected(false);
                    removeSelect(entity);
                }else{
                    if(selectedData != null && selectedData.size() == 7 && entry.equals("fromMeeting")){
                        Toast.makeText(ContactsActivity.this,getString(R.string.numCantOverHint),Toast.LENGTH_SHORT).show();
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
        mListView.setAdapter(mAdapter);
    }
    @Override
    public void initViews() {
        midText.setText(R.string.phoneContacts);
        mback.setImageResource(R.drawable.btn_back);
        mUserByPhonePresenterImpl.attachView(this);
        entry = getIntent().getStringExtra("entry");
        mCharacterParser = CharacterParser.getInstance();
        mPinyinComparator = PinyinComparator.getInstance();
        entryType = getIntent().getStringExtra("type");
        getPhoneNumberFromMobile();
        mSidBar.setTextView(mDialogTextView);
        //设置右侧触摸监听
        mSidBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mListView.setSelection(position);
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mUserByPhonePresenterImpl!=null)
            mUserByPhonePresenterImpl.onDestroy();
    }

    private void shiftDataSource(ArrayList<MessageCommonEntity> sourceList) {
        datasource = new ArrayList<>();
        for(int i = 0;i<sourceList.size();i++){

            ContractAfterEntity entity = new ContractAfterEntity();
            MessageCommonEntity source = sourceList.get(i);

            if(TextUtils.isEmpty(source.getRealname()))
                continue;

            entity.setId(Integer.parseInt(source.getUserId()));
            entity.setName(source.getRealname());
            entity.setLetter(mCharacterParser.getSpelling(source.getRealname()));
            entity.setAdded(source.getIsFriend().equals("0")?false:true);
            entity.setPhone(source.getPhone());
            datasource.add(entity);
        }
        Collections.sort(datasource,mPinyinComparator);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        switch (entryType){
            case Const.TYPE_ADD:
                initListViewAdd();
                break;
            case Const.TYPE_NORMAL:
                initListViewNormal();
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

                mBottomBar.setVisibility(View.VISIBLE);
                initListViewSelect();

                break;
            default:
                break;
        }

    }



    public void getPhoneNumberFromMobile() {
        // TODO Auto-generated constructor stub
        initContacts = new ArrayList();
        String phones = "";
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        //moveToNext方法返回的是一个boolean类型的数据
        while (cursor.moveToNext()) {
            if(!phones.equals(""))
                phones+=",";
            //读取通讯录的姓名
            String name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            //读取通讯录的号码
            String number = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            ContractInitialEntity phoneInfo = new ContractInitialEntity(name, number);
            initContacts.add(phoneInfo);
            phones+=number;
        }

        mUserByPhonePresenterImpl.getUserByPhone(phones);
    }

    private String replaceFirstCharacterWithUppercase(String spelling) {
        if (!TextUtils.isEmpty(spelling)) {
            char first = spelling.charAt(0);
            char newFirst = first;
            if (first >= 'a' && first <= 'z') {
                newFirst -= 32;
            }
            return spelling.replaceFirst(String.valueOf(first), String.valueOf(newFirst));
        } else {
            return "#";
        }
    }
    private void checkInitSelect(){
        if(selectedData != null ){

            for(int i = 0;i<datasource.size();i++){
                datasource.get(i).setSelected(false);
                for (int j = 0;j<selectedData.size();j++){
                    if(datasource.get(i).getId() == selectedData.get(j).getId()){
                        datasource.get(i).setSelected(true);
                        break;
                    }

                }
            }
        }
    }
    private void addSelect(ContractAfterEntity entity) {

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
        data.setPhone(entity.getPhone());
        selectedData.add(data);

    }

    private void removeSelect(ContractAfterEntity entity) {
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
            case SEARCH_CONTACT_SELECT:
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
    public void getUserByPhoneCompleted(ContactListEntity data) {
        if(data.getCode().equals("ok")){
            shiftDataSource(data.getData().getFriends());
            mAdapter.setDataSource(datasource);
            if(entryType.equals(Const.TYPE_SELECT)){
                checkInitSelect();
            }

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
