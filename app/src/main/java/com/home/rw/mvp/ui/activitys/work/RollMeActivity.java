package com.home.rw.mvp.ui.activitys.work;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.ApprovementListEntity;
import com.home.rw.mvp.entity.RollMeEntity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.adapters.ApprovementListAdapter;
import com.home.rw.mvp.ui.adapters.RecycleViewSperate;
import com.home.rw.mvp.ui.adapters.RollMelistAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class RollMeActivity extends BaseActivity {

    private RollMelistAdapter mAdapter;

    private ArrayList<RollMeEntity.DataEntity> dataSource  = new ArrayList<>();
    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.iv_top)
    ImageView mTop;


    @OnClick({
            R.id.back,
            R.id.iv_top
    })
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.iv_top:
                mRecycleView.smoothScrollToPosition(0);
                break;
            default:
                break;


        }

    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_roll_me;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(R.string.rollme);
        mback.setImageResource(R.drawable.btn_back);
        initRecycleView();
    }

    private void initRecycleView() {

        RollMeEntity.DataEntity entity1 = new RollMeEntity.DataEntity();
        entity1.setSender("小黄");
        entity1.setSendTime("2017-01-01 16:34");
        entity1.setDeadLineTime("2017-01-02 15:34");
        entity1.setContent("除了正常工作外长期于各直播平台直播赛制，其中最著名节目智商杯深受广大群众好评，素有“夜间没有旭东黄，唯对镜子撸断肠。之说。\n" +
                "其自做视频“戊戌六君子”“谐星语录”更是深入人心。");

        RollMeEntity.DataEntity entity2 = new RollMeEntity.DataEntity();
        entity2.setSender("小孙");
        entity2.setSendTime("2017-01-02 16:34");
        entity2.setDeadLineTime("2017-11-02 15:34");
        entity2.setContent("孙一峰，星际争霸虫族选手，ID 为 F91，1983 年出生于浙江杭州，多次获得全国冠军，并代表中国参加 WCG 世界总决赛。被誉为“中国虫王”。");



        RollMeEntity.DataEntity entity3 = new RollMeEntity.DataEntity();
        entity3.setSender("小钱");
        entity3.setSendTime("2017-01-02 16:34");
        entity3.setDeadLineTime("2017-11-02 15:34");
        entity3.setContent("钱赞企是星际争霸2的一个职业的虫族选手，人称NO总，以凶猛的爆蟑螂莽死对手而闻名\n" +
                " \n" +
                "他的ZVT战术“庐山升龙霸”，使用1攻1防提速的蟑螂海针对人族10分半多的时候的真空期进行猛攻，成功莽死了不少职业选手，猛的要死\n" +
                " \n" +
                "于是，广大水友间就有了这么一种说法：\n" +
                "大喊“我叫钱赞企！”的话蟑螂就可以加两攻……");

        RollMeEntity.DataEntity entity4 = new RollMeEntity.DataEntity();
        entity4.setSender("小马");
        entity4.setSendTime("2017-01-02 16:34");
        entity4.setDeadLineTime("2017-09-02 15:34");
        entity4.setContent("2014年11月14日，中国选手马雪(Mayuki)在第六届世界电子竞技锦标赛星际争霸2女子组比赛中，以3:2战胜芬兰选手勇夺冠军。这是近年来中国女选手在世界电子竞技大赛中获得的最佳成绩。\n" +
                "　　赛后，Mayuki夺冠的消息得到国家体育总局的公开告示，题目是《中国女选手勇夺第六届巴库世界电子竞技锦标赛金牌》。电竞圈认为，这不仅是中国电子竞技今年的一大业绩，也是中国《星际争霸2》回到大众焦点的强心剂。");

        dataSource.add(entity1);
        dataSource.add(entity2);
        dataSource.add(entity3);
        dataSource.add(entity4);


        mAdapter = new RollMelistAdapter(dataSource,this);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {


            }
        });
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecycleView.addItemDecoration(new RecycleViewSperate());
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }
}
