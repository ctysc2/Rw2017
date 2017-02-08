package com.home.rw.mvp.ui.activitys.message;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.activitys.social.OthersDetailActivity;
import com.home.rw.mvp.ui.adapters.MeetingSelectedAdapter;
import com.home.rw.mvp.ui.adapters.MessegeMainAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.home.rw.common.Const.TYPE_ADD;
import static com.home.rw.common.Const.TYPE_NORMAL;

public class MeetingSelectActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageButton mBack;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @BindView(R.id.bt_confirm)
    Button mConfirm;

    private ArrayList<MeetingSelectEntity.DataEntity> dataSource = new ArrayList<>();

    private MeetingSelectedAdapter mAdapter;

    private ArrayList<MeetingSelectTempEntity> selectedData;

    private int selectedNum;

    @OnClick({R.id.back,
            R.id.bt_confirm,

    })
    public void OnClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.bt_confirm:
                intent = new Intent(this,PreviewCallActivity.class);
                intent.putExtra("newdata",selectedData);
                startActivity(intent);
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

        ArrayList<MeetingSelectEntity.DataEntity> subData = new ArrayList<>();

        MeetingSelectEntity.DataEntity sub1 = new MeetingSelectEntity.DataEntity();
        sub1.setId(11);
        sub1.setTitle("山下智博");
        sub1.setAvatar("https://imgsa.baidu.com/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=e955d37d62600c33e474d69a7b253a6a/bd3eb13533fa828b24288d8af41f4134970a5a4b.jpg");

        MeetingSelectEntity.DataEntity sub2 = new MeetingSelectEntity.DataEntity();
        sub2.setId(12);
        sub2.setTitle("软软冰");
        sub2.setAvatar("https://imgsa.baidu.com/baike/w%3D268/sign=a0b1ad538e82b9013dadc4354b8ca97e/7af40ad162d9f2d33861a29badec8a136327cc56.jpg");

        MeetingSelectEntity.DataEntity sub3 = new MeetingSelectEntity.DataEntity();
        sub3.setId(13);
        sub3.setTitle("宫崎");

        MeetingSelectEntity.DataEntity sub4 = new MeetingSelectEntity.DataEntity();
        sub4.setId(14);
        sub4.setTitle("三河");

        MeetingSelectEntity.DataEntity sub5 = new MeetingSelectEntity.DataEntity();
        sub5.setId(15);
        sub5.setTitle("yoyo");

        MeetingSelectEntity.DataEntity sub6 = new MeetingSelectEntity.DataEntity();
        sub6.setId(16);
        sub6.setTitle("糖分");
        sub6.setAvatar("http://tva1.sinaimg.cn/crop.0.0.750.750.180/9d323854jw8fa4n4l6ekxj20ku0ku3zk.jpg");

        subData.add(sub1);
        subData.add(sub2);
        subData.add(sub3);
        subData.add(sub4);
        subData.add(sub5);
        subData.add(sub6);
        entity4.setSubData(subData);


        dataSource.add(entity1);
        dataSource.add(entity2);
        dataSource.add(entity3);
        dataSource.add(entity4);

        checkInitSelect();

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
                            startActivityForResult(intent,0);
                            break;
                        case -2:
                            intent = new Intent(MeetingSelectActivity.this,OriganizationActivity.class);
                            intent.putExtra("type", Const.TYPE_SELECT);
                            intent.putExtra("selectedData",selectedData);
                            startActivityForResult(intent,0);
                            break;
                        case -3:
                            intent = new Intent(MeetingSelectActivity.this,MyTeamActivity.class);
                            intent.putExtra("type", Const.TYPE_SELECT);
                            intent.putExtra("selectedData",selectedData);
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
                        if(selectedData != null && selectedData.size() == 7){
                            Toast.makeText(MeetingSelectActivity.this,getString(R.string.numCantOverHint),Toast.LENGTH_SHORT).show();
                            return;
                        }
                        entity.setSelected(true);
                        addSelect(entity);
                    }
                    mConfirm.setText(String.format(getString(R.string.containNum),selectedData.size()));
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

    }

    @Override
    public void initViews() {

        mBack.setImageResource(R.drawable.btn_back);
        midText.setText(R.string.meetintSelectHint1);
        selectedData = (ArrayList<MeetingSelectTempEntity>)(getIntent().getSerializableExtra("selectedData"));



        if((selectedData == null)||
            selectedData.size() == 0){
            mConfirm.setText(String.format(getString(R.string.containNum),0));

        }else{
            mConfirm.setText(String.format(getString(R.string.containNum),selectedData.size()));
        }



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
                        mConfirm.setText(String.format(getString(R.string.containNum),0));

                    }else{
                        mConfirm.setText(String.format(getString(R.string.containNum),selectedData.size()));
                    }
                }
                break;
            default:
                break;
        }
    }
}
