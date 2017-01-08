package com.home.rw.mvp.ui.activitys.social;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.event.BeforeReadEvent;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.ApprovementListEntity;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.activitys.work.AskForLeaveActivity;
import com.home.rw.mvp.ui.activitys.work.ExtraWorkActivity;
import com.home.rw.mvp.ui.activitys.work.GetOutActivity;
import com.home.rw.mvp.ui.activitys.work.WipedActivity;
import com.home.rw.mvp.ui.adapters.ApprovementListAdapter;
import com.home.rw.mvp.ui.adapters.CommunicationAdapter;
import com.home.rw.mvp.ui.adapters.RecycleViewSperate;
import com.home.rw.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CommListActivity extends BaseActivity {

    private String mStartType = "";

    private CommunicationAdapter mAdapter;

    private ArrayList<CommunicationEntity.DataEntity> dataSource  = new ArrayList<>();

    private final int DETAIL = 1;

    private int requestPos = 0;
    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @Override
    public int getLayoutId() {
        return R.layout.activity_comm_list;
    }

    @Override
    public void initInjector() {

    }
    @OnClick({
            R.id.back
    })
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                finish();
            default:
                break;


        }

    }
    @Override
    public void initViews() {
        mStartType = getIntent().getStringExtra("startType");

        switch (mStartType){
            case "Proj":
                midText.setText(R.string.projectCommunicationTitle);
                break;
            case "Activity":
                midText.setText(R.string.ActivityCommunicationTitle);
                break;
            case "Culture":
                midText.setText(R.string.CultureCommunicationTitle);
                break;
            case "Coop":
                midText.setText(R.string.CoopCommunicationTitle);
                break;
            case "Mine":
                midText.setText(R.string.minePublish);
            default:
                break;

        }

        mback.setImageResource(R.drawable.btn_back);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initRecycleView();
    }

    private void initRecycleView(){
        //dummy data


        //1
        CommunicationEntity.DataEntity child1 = new CommunicationEntity.DataEntity();
        child1.setHeader("http://imgsrc.baidu.com/baike/pic/item/ac4bd11373f08202100b07c649fbfbedaa641ba9.jpg");
        child1.setName("特维斯");
        child1.setZanNum(5);
        child1.setContent("卡洛斯·特维斯（Carlos Tévez），1984年2月5日出生于阿根廷首都布宜诺斯艾利斯，阿根廷籍足球运动员，场上司职前锋，也可以胜任攻击型中场。特维斯现效力于上海绿地申花足球俱乐部[1]  。\n" +
                "作为博卡青年青训营的产品，年少成名的特维斯曾经效力过南美、欧洲等多家俱乐部。同时，作为阿根廷国家队的11号，特维斯参加了2006、2010两届世界杯和2004、2007、2011三届美洲杯，此外他还代表阿根廷国奥队参加2004年雅典奥运会，帮助球队夺冠并且以8个进球荣获那届比赛最佳射手。2011/12赛季，在一系列转会风波后他最终选择留守曼城，和国家队队友塞尔吉奥·阿圭罗一起征战英超。2016年12月，正式加盟上海绿地申花，征战中超比赛");
        child1.setZaned(false);
        child1.setFacused(false);
        child1.setTitle("特维斯加盟上海申花");
        ArrayList<String> img1 = new ArrayList<>();
        img1.add("http://fwimage.cnfanews.com/founder_xiangyu/websiteimg/2016/20161223/20034130/18562782_980x1200_292.jpg.jpg");
        img1.add("http://fwimage.cnfanews.com/founder_xiangyu/websiteimg/2016/20161223/84198/img8853848_n.jpg.jpg");
        img1.add("http://img1.dongqiudi.com/fastdfs/M00/10/92/ooYBAFhmYyGAKxhWAAFQHTKUf3M947.jpg");
        child1.setImgs(img1);

        //2
        CommunicationEntity.DataEntity child2 = new CommunicationEntity.DataEntity();
        child2.setName("钉宫理惠");
        child2.setZanNum(55);
        child2.setTitle("新垣结衣gakki舞这个标题一定要很长很长的");
        child2.setZaned(true);
        child2.setFacused(false);
        child2.setHeader("http://img1.gamersky.com/image2016/07/20160726_308_whx_9/gamersky_03small_06_20167261458325.jpg");
        child2.setContent("钉宫理惠，1979年5月30日出生于大阪府，成长于熊本县熊本市，日本女性声优、歌手，所属事务所为I'm Enterprise。\n" +
                "代表作品有《钢之炼金术师》阿尔冯斯·艾尔利克、《灼眼的夏娜》夏娜、《龙与虎》逢坂大河、《旋风管家！》三千院凪、《零之使魔》露易丝、《银魂》神乐、《妖精的尾巴》哈比等。");
        ArrayList<String> img2 = new ArrayList<>();
        img2.add("https://imgsa.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=3a527841eddde711f3df4ba4c686a57e/a50f4bfbfbedab64ec7f8584ff36afc379311e61.jpg");
        img2.add("https://imgsa.baidu.com/baike/crop%3D0%2C18%2C790%2C521%3Bc0%3Dbaike92%2C5%2C5%2C92%2C30/sign=514f2b9dba1c8701c2f9e8a61a4fb21e/63d9f2d3572c11dfd6d02a5a6a2762d0f703c242.jpg");
        img2.add("https://imgsa.baidu.com/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=a3e95f21a8d3fd1f2204aa6851274e7a/9f2f070828381f30c84641a6a9014c086e06f066.jpg");
        child2.setImgs(img2);

        //3
        CommunicationEntity.DataEntity child3 = new CommunicationEntity.DataEntity();
        child3.setName("新垣结衣");
        child3.setZanNum(55);
        child3.setTitle("新垣结衣gakki舞这个标题一定要很长很长的");
        child3.setZaned(true);
        child3.setFacused(false);
        child3.setHeader("https://imgsa.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=fcf279a7dc0735fa85fd46ebff3864d6/8644ebf81a4c510f8e1339a66859252dd52aa5b3.jpg");
        child3.setContent("新垣结衣（Aragaki Yui），1988年6月11日出生于冲绳县那霸市。日本演员、歌手、模特。毕业于日出高中[1]  。\n" +
                "2001年，参加《nicola》模特比赛并获得最优秀奖。2005年，首次出演了电视连续剧《龙樱》[2]  。2006年，出演了电视连续剧《我的老板，我的英雄》，并出版第一本写真集《水漾青春》。2007年从日出高等学校毕业后专注于演艺圈发展，同年出演电视剧《父女七日变》，发表个人首张音乐专辑《そら》（《天空》）。2007年，因主演电影《恋空》而知名度增加，并接连获得多个电影新人奖项。2010年，与生田斗真主演电影《花水木》[3]  。2012年，与堺雅人主演电视剧《Legal High》[4]  。2013年，与堺雅人主演的《Legal High SP》在日本播出[5]  。2014年，与向井理、绫野刚主演电视剧《S最后的警官》[6]  ；同年主演电影《黎明的沙耶》[7]  。2015年2月28日，主演的电影《唇上之歌》上映[8-9]  。8月29日，参演的电影《剧场版 S-最后的警官-》上映[10]  。10月10日，主演的电视剧《掟上今日子的备忘录》在日本首播[11]  。2016年，主演10月开播的新剧《逃避虽可耻但很有用》");
        ArrayList<String> img3 = new ArrayList<>();
        img3.add("https://imgsa.baidu.com/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=ec1ccc4ebe12c8fca0fefe9f9d6af920/f603918fa0ec08facfaaadc95eee3d6d55fbda32.jpg");
        img3.add("https://imgsa.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=e874ea57a1ec08fa320d1bf538875608/e1fe9925bc315c602bdf25078cb1cb134854778b.jpg");
        child3.setImgs(img3);
        //4
        CommunicationEntity.DataEntity child4 = new CommunicationEntity.DataEntity();
        child4.setName("马丁斯");
        child4.setTitle("黑旋风马丁斯");
        child4.setZanNum(2);
        child4.setZaned(true);
        child4.setFacused(true);
        child4.setHeader("https://imgsa.baidu.com/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=8d0bdc8f7f094b36cf9f13bfc2a517bc/80cb39dbb6fd52666cb01e44a218972bd50736c2.jpg");
        child4.setContent("奥巴费米·马丁斯（Obafemi Martins），1984年10月28日出生于尼日利亚拉各斯（Lagos,Nigeria），具有尼日利亚和意大利双重国籍，职业足球运动员，司职中锋或边锋。现效力于中超的上海绿地申花足球俱乐部。\n" +
                "奥巴费米·马丁斯曾先后效力于国际米兰足球俱乐部、沃尔夫斯堡足球俱乐部、纽卡斯尔联足球俱乐部、喀山鲁宾足球俱乐部、莱万特足球俱乐部、西雅图海湾人足球俱乐部、上海绿地申花足球俱乐部。");
        //5
        CommunicationEntity.DataEntity child5 = new CommunicationEntity.DataEntity();
        child5.setName("金基熙");
        child5.setTitle("kim");
        child5.setZanNum(20);
        child5.setZaned(false);
        child5.setFacused(true);
        child5.setHeader("https://imgsa.baidu.com/baike/w%3D268/sign=db990a638e94a4c20a23e02d36f41bac/a50f4bfbfbedab640e5ea6acfe36afc379311ea9.jpg");
        child5.setContent("金基熙出生于1989年7月13日，身高188cm，在场上可以司职中后卫、后腰、边后卫等多个位置，是现役韩国国家队成员。职业生涯先后效力于韩国大邱FC、卡塔尔阿尔赛利亚、韩国全北现代汽车足球俱乐部。2012年，金基熙随韩国国奥队征战伦敦奥运会获得男足铜牌。2012年11月，金基熙首次代表韩国国家队出场。2013年，金基熙从卡塔尔转会加盟全北现代，帮助球队连续两年问鼎韩国K联赛冠军，并入选了2015赛季K联赛最佳阵容。2016年2月19日晚，上海绿地申花足球俱乐部与韩国全北现代汽车足球俱乐部达成转会协议，韩国球员金基熙（Kim Kee-Hee）正式加盟绿地申花。");
        ArrayList<String> img5 = new ArrayList<>();
        img5.add("https://imgsa.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=e8c4eef236a85edfee81f671283d6246/f703738da9773912e7ee02c3fa198618377ae24c.jpg");
        child5.setImgs(img5);

        if(mStartType.equals("Mine")){
            child1.setHeader("http://img1.gamersky.com/image2016/07/20160726_308_whx_9/gamersky_03small_06_20167261458325.jpg");
            child3.setHeader("http://img1.gamersky.com/image2016/07/20160726_308_whx_9/gamersky_03small_06_20167261458325.jpg");
            child4.setHeader("http://img1.gamersky.com/image2016/07/20160726_308_whx_9/gamersky_03small_06_20167261458325.jpg");
            child5.setHeader("http://img1.gamersky.com/image2016/07/20160726_308_whx_9/gamersky_03small_06_20167261458325.jpg");

            child1.setName("钉宫理惠");
            child2.setName("钉宫理惠");
            child3.setName("钉宫理惠");
            child4.setName("钉宫理惠");
            child5.setName("钉宫理惠");
        }
        dataSource.add(child1);
        dataSource.add(child2);
        dataSource.add(child3);
        dataSource.add(child4);
        dataSource.add(child5);


        mAdapter = new CommunicationAdapter(dataSource,this);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(CommListActivity.this,CommDetailActivity.class);
                intent.putExtra("commData",dataSource.get(position));
                startActivityForResult(intent,DETAIL);
                requestPos = position;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case DETAIL:
                if(resultCode == RESULT_OK){
                    CommunicationEntity.DataEntity entity = (CommunicationEntity.DataEntity)data.getSerializableExtra("newData");
                    dataSource.set(requestPos,entity);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }
}
