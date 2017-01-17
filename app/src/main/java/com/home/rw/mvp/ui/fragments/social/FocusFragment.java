package com.home.rw.mvp.ui.fragments.social;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.home.rw.R;
import com.home.rw.annotation.BindValues;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.entity.FacusListEntity;
import com.home.rw.mvp.ui.activitys.social.CommDetailActivity;
import com.home.rw.mvp.ui.activitys.social.CommListActivity;
import com.home.rw.mvp.ui.activitys.social.FocusListActivity;
import com.home.rw.mvp.ui.activitys.social.OthersDetailActivity;
import com.home.rw.mvp.ui.adapters.CommunicationAdapter;
import com.home.rw.mvp.ui.adapters.FacusListAdapter;
import com.home.rw.mvp.ui.adapters.RecycleViewDivider;
import com.home.rw.mvp.ui.adapters.RecycleViewSperate;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

import static android.app.Activity.RESULT_OK;

/**
 * Created by cty on 2017/1/5.
 */

public class FocusFragment extends BaseFragment {

    private ArrayList<FacusListEntity.DataEntity> datasource1 = new ArrayList<>();
    private ArrayList<CommunicationEntity.DataEntity> datasource2 = new ArrayList<>();
    private CommunicationAdapter mAdapterComm;
    private FacusListAdapter mAdapterFacus;

    private final int DETAIL = 1;

    private int requestPos = 0;
    @Inject
    Activity mActivity;

    @BindView(R.id.rv_list)
    RecyclerView mRvList;

    @BindView(R.id.rv_content)
    RecyclerView mRvContent;

    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout mRefresh;

    @Inject
    public FocusFragment(){

    }
    @OnClick({
            R.id.tv_all
    })
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.tv_all:
                startActivity(new Intent(mActivity, FocusListActivity.class));
            default:
                break;


        }

    }
    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {

        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                Observable.timer(2, TimeUnit.SECONDS).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(new Observer<Long>() {
                            @Override
                            public void onCompleted() {
                                mRefresh.setRefreshing(false);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Long aLong) {

                            }
                        });
            }
        });

        FacusListEntity.DataEntity entity1 = new FacusListEntity.DataEntity();
        entity1.setHeader("https://imgsa.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=a31bf4b58fd6277ffd1f3a6a49517455/b90e7bec54e736d10b34ec0693504fc2d562699c.jpg");
        entity1.setName("藤原龙也");
        entity1.setNum(365);

        FacusListEntity.DataEntity entity2 = new FacusListEntity.DataEntity();
        entity2.setHeader("http://h.hiphotos.baidu.com/baike/w%3D268%3Bg%3D0/sign=7a27eb6de21190ef01fb95d9f620fa2b/4bed2e738bd4b31c7813af3b82d6277f9e2ff807.jpg");
        entity2.setName("松重丰");
        entity1.setNum(467);

        FacusListEntity.DataEntity entity3 = new FacusListEntity.DataEntity();
        entity3.setHeader("https://imgsa.baidu.com/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=a3536492d8a20cf4529df68d17602053/1ad5ad6eddc451da7b1aeffebefd5266d0163233.jpg");
        entity3.setName("小栗旬");
        entity3.setNum(908);

        FacusListEntity.DataEntity entity4 = new FacusListEntity.DataEntity();
        entity4.setHeader("https://imgsa.baidu.com/baike/c0%3Dbaike116%2C5%2C5%2C116%2C38/sign=a87330b7880a19d8df0e8c575293e9ee/cc11728b4710b912ac309ff8cbfdfc03934522c6.jpg");
        entity4.setName("木村拓哉");
        entity4.setNum(420);

        datasource1.add(entity1);
        datasource1.add(entity2);
        datasource1.add(entity3);
        datasource1.add(entity4);

        //1
        CommunicationEntity.DataEntity child1 = new CommunicationEntity.DataEntity();
        child1.setHeader("http://imgsrc.baidu.com/baike/pic/item/ac4bd11373f08202100b07c649fbfbedaa641ba9.jpg");
        child1.setName("特维斯");
        child1.setZanNum(5);
        child1.setContent("卡洛斯·特维斯（Carlos Tévez），1984年2月5日出生于阿根廷首都布宜诺斯艾利斯，阿根廷籍足球运动员，场上司职前锋，也可以胜任攻击型中场。特维斯现效力于上海绿地申花足球俱乐部[1]  。\n" +
                "作为博卡青年青训营的产品，年少成名的特维斯曾经效力过南美、欧洲等多家俱乐部。同时，作为阿根廷国家队的11号，特维斯参加了2006、2010两届世界杯和2004、2007、2011三届美洲杯，此外他还代表阿根廷国奥队参加2004年雅典奥运会，帮助球队夺冠并且以8个进球荣获那届比赛最佳射手。2011/12赛季，在一系列转会风波后他最终选择留守曼城，和国家队队友塞尔吉奥·阿圭罗一起征战英超。2016年12月，正式加盟上海绿地申花，征战中超比赛");
        child1.setZaned(false);
        child1.setFacused(true);
        child1.setTitle("特维斯加盟上海申花");
        ArrayList<String> img1 = new ArrayList<>();
        img1.add("http://fwimage.cnfanews.com/founder_xiangyu/websiteimg/2016/20161223/20034130/18562782_980x1200_292.jpg.jpg");
        img1.add("http://fwimage.cnfanews.com/founder_xiangyu/websiteimg/2016/20161223/84198/img8853848_n.jpg.jpg");
        img1.add("http://img1.dongqiudi.com/fastdfs/M00/10/92/ooYBAFhmYyGAKxhWAAFQHTKUf3M947.jpg");
        child1.setImgs(img1);

        //3
        CommunicationEntity.DataEntity child3 = new CommunicationEntity.DataEntity();
        child3.setName("新垣结衣");
        child3.setZanNum(55);
        child3.setTitle("新垣结衣gakki舞这个标题一定要很长很长的");
        child3.setZaned(true);
        child3.setFacused(true);
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

        datasource2.add(child1);
        datasource2.add(child3);
        datasource2.add(child4);
        datasource2.add(child5);

        //内容列表
        mAdapterComm = new CommunicationAdapter(datasource2,mActivity);

        mAdapterComm.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mActivity,CommDetailActivity.class);
                intent.putExtra("commData",datasource2.get(position));
                startActivityForResult(intent,DETAIL);
                requestPos = position;
            }
        });
        mRvContent.setHasFixedSize(true);
        mRvContent.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false));
        mRvContent.addItemDecoration(new RecycleViewSperate());
        mRvContent.setItemAnimator(new DefaultItemAnimator());
        mRvContent.setAdapter(mAdapterComm);
        mRvContent.setNestedScrollingEnabled(false);


        //关注的人列表
        mAdapterFacus = new FacusListAdapter(datasource1,mActivity);

        mAdapterFacus.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mActivity,OthersDetailActivity.class);
                intent.putExtra("data",datasource1.get(position));
                startActivity(intent);
            }
        });
        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false));
        mRvList.addItemDecoration(new RecycleViewDivider(mActivity,RecycleViewDivider.VERTICAL_LIST,RecycleViewDivider.COMMON));
        mRvList.setItemAnimator(new DefaultItemAnimator());
        mRvList.setAdapter(mAdapterFacus);
        mRvList.setNestedScrollingEnabled(false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_focus;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case DETAIL:
                if(resultCode == RESULT_OK){
                    CommunicationEntity.DataEntity entity = (CommunicationEntity.DataEntity)data.getSerializableExtra("newData");
                    datasource2.set(requestPos,entity);
                    mAdapterComm.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }
}
