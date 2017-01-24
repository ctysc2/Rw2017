package com.home.rw.mvp.ui.activitys.message;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.GrandEntity;
import com.home.rw.mvp.entity.SignEntity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.activitys.work.SendRollActivity;
import com.home.rw.mvp.ui.adapters.GrandListAdapter;
import com.home.rw.mvp.ui.adapters.RecycleViewSperate;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class LandMarkNotice extends BaseActivity {

    private boolean mIsLoadingMore;

    private GrandListAdapter mAdapter;

    private  ArrayList<GrandEntity.DataEntity> dataSource = new ArrayList<>();

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout mRefresh;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;


    @OnClick({R.id.back,
    })
    public void onClick(View v){
        switch(v.getId()){
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_land_mark_notice;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        midText.setText(R.string.zhiDi);
        mback.setImageResource(R.drawable.btn_back);
        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                Observable.timer(1, TimeUnit.SECONDS).
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initRecycleView();
    }

    private void initRecycleView() {
        GrandEntity.DataEntity entity1 = new GrandEntity.DataEntity();
        entity1.setImg("https://imgsa.baidu.com/baike/crop%3D18%2C0%2C682%2C449%3Bc0%3Dbaike92%2C5%2C5%2C92%2C30/sign=9b4d625c992397ddc236c24464b38a9b/0df431adcbef7609b543c22426dda3cc7dd99ec5.jpg");
        entity1.setDate("10月14日 17:00");
        entity1.setTitle("山下智博，在华日本人，1985年出生于北海道，目前居住于上海");
        entity1.setDetailTitle("山下智博简介");
        entity1.setContent("山下智博，在华日本人，1985年出生于北海道，目前居住于上海。2013年因多次带着人偶参与漫展而被国内网民熟知，2014年7月推出微电影作品《日本屌丝》，引起网络热议。2014年12月24日起在Bilibili推出个人娱乐栏目《绅士大概一分钟》，每日更新一期。在2016年10月2日宣布《绅士大概一分钟》停止日更，但星期二晚上9：00依然会直播。在2016年10月14日宣布将会在每周星期一和星期四更新《绅士大概一分钟》。");

        GrandEntity.DataEntity entity2 = new GrandEntity.DataEntity();
        entity2.setImg("https://imgsa.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=60e8da8524a446236ac7ad30f94b196b/574e9258d109b3de91484497c8bf6c81810a4c5d.jpg");
        entity2.setDate("10月12日 07:00");
        entity2.setDetailTitle("宫崎葵简介");
        entity2.setTitle("宫崎葵，1985年11月30日出生于日本东京，女演员、模特，就职于平田事务所（Hirata Office）。");
        entity2.setContent("2001年，主演电影《害虫》并获得法国南特三大陆电影节影后。2002年，主演电影《人造天堂》，获得第16回高崎电影节最佳新人奖。2005年，因出演漫画改编电影《NANA》中小松奈奈一角而大受欢迎[1]  。2006年，主演NHK晨间剧《纯情闪耀》并获第50回日剧学院赏最佳女主角。2008年，主演NHK大河剧《笃姬》，成为日本史上最年轻的大河剧主役[2-3]  。2011年，主演电影《丈夫得了抑郁症》，获第24届日刊体育电影赏影后[4]  。2013年，主演电影《编舟记》获第37回日本电影学院赏最佳影片[5]  。2016年，搭档佐藤健联合出演由日本电影制作人川村元气创作的同名小说《假如猫从世界上消失了》改编的电影[6]  ；出演由吉田康弘执导的的新作电影《生日卡片》");

        GrandEntity.DataEntity entity3 = new GrandEntity.DataEntity();
        entity3.setImg("https://imgsa.baidu.com/baike/c0%3Dbaike116%2C5%2C5%2C116%2C38/sign=0eb6ebd6a586c9171c0e5a6ba8541baa/0ff41bd5ad6eddc4caa6bbd53bdbb6fd5266335e.jpg");
        entity3.setDate("10月10日 18:00");
        entity3.setDetailTitle("泽尻绘理香简介");
        entity3.setTitle("泽尻绘理香，1986年4月8日在日本东京都出生，是一名演员，歌手及模特");
        entity3.setContent("2004年泽尻绘里香出演电影《没有问题的我们》并在片中担任反派角色新谷麻绮，饰演一名由别校转学的不良少女。2005年主演电视连续剧《一公升的眼泪》饰演池内亚2006年5月泽尻绘里香出演《间宫兄弟》饰演碟片店店员直美。同年9月出演《风味绝佳》饰演渡边乃里子。2007年9月29日在宣传电影《尘封笔记本》的公开活动中态度不佳，暂时退出演艺圈，去英国留学。\n" +
                "2010年3月15日，泽尻宣布复出并召开发布会，向媒体宣布复出。2011年5月1日，日本大型演艺公司爱贝克斯在官方网站宣布，爱贝克斯与泽尻英龙华的个人事务所签订契约，他们将帮助泽尻英龙华正式复出。\n" +
                "2012年4月30日主演日本TBS台SP剧《关于恶女》，收视率为14.7%，同年5月15日因身体原因，宣布暂时休止活动，同年7月14日出席主演电影《狼狈/ ヘルタースケルター /Helter Skelter》日本首映会，再次开始演艺工作[1]  。2013年3月08日泽尻出席第36届日本电影学院奖（别名-日本奥斯卡金像奖），凭借《狼狈》里莉莉子一角入围优秀女主演奖。2014年4月8日泽尻绘里香在4月8日以形象代言人的身份出席了于东京举办的“24h cosme”最新广告发布会。同年4月19日主演日本富士电视台的连续剧《First Class》饰演吉成千奈美。2016年，参演8月27日播放的24时TV每年惯例的SP特别剧《盲眼的淑则先生》");

        GrandEntity.DataEntity entity4 = new GrandEntity.DataEntity();
        entity4.setImg("https://imgsa.baidu.com/baike/crop%3D0%2C294%2C1667%2C1101%3Bc0%3Dbaike220%2C5%2C5%2C220%2C73/sign=3dd7c6a7346d55fbd1892c6650126378/3ac79f3df8dcd100a1e152587b8b4710b9122f4d.jpg");
        entity4.setDate("10月2日 18:00");
        entity4.setDetailTitle("花泽香菜简介");
        entity4.setTitle("花泽香菜，1989年2月25日出生于日本东京都，日本声优、演员、歌手，所属事务所为大泽事务所");
        entity4.setContent("原本是儿童演员出身，2006年正式开始声优工作[1-2]  ，在随后的几年之内经由对《化物语》的千石抚子、《Angel Beats!》的立华奏等角色的演绎而积累了人气，并在2012年以歌手身份出道[3]  。2015年5月3日，在日本武道馆举办个人演唱会，成为第八位在武道馆举办个人演唱会的声优[4]  。代表作品有《小鸠》花户小鸠、《化物语》千石抚子、《Angel Beats!》立华奏、《命运石之门》 椎名真由理、《无头骑士异闻录》园原杏里、《我的妹妹不可能那么可爱》五更琉璃（黑猫）、《黑岩射手》黑衣麻陶/黑岩射手、《言叶之庭》雪野百香里、《心理测量者》常守朱、《出包王女》结城美柑（第一至第四季）、《伪恋》小野寺小咲、《目隐都市的演绎者》MARY / 小樱茉莉等。");


        dataSource.add(entity1);
        dataSource.add(entity2);
        dataSource.add(entity3);
        dataSource.add(entity4);
        mAdapter = new GrandListAdapter(dataSource,this);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(LandMarkNotice.this,LandMarkDetail.class);
                intent.putExtra("data",dataSource.get(position));
                startActivity(intent);
            }
        });
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecycleView.addItemDecoration(new RecycleViewSperate());
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findLastCompletelyVisibleItemPosition();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if (!mIsLoadingMore && visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItemCount - 1) {
                    mAdapter.showFooter();
                    mIsLoadingMore = true;
                    mRecycleView.scrollToPosition(mAdapter.getItemCount() - 1);
                    Observable.timer(1, TimeUnit.SECONDS).
                            observeOn(AndroidSchedulers.mainThread()).
                            subscribe(new Observer<Long>() {
                                @Override
                                public void onCompleted() {
                                    mAdapter.hideFooter();
                                    mIsLoadingMore = false;

                                    GrandEntity.DataEntity entity1 = new GrandEntity.DataEntity();
                                    entity1.setImg("https://imgsa.baidu.com/baike/crop%3D18%2C0%2C682%2C449%3Bc0%3Dbaike92%2C5%2C5%2C92%2C30/sign=9b4d625c992397ddc236c24464b38a9b/0df431adcbef7609b543c22426dda3cc7dd99ec5.jpg");
                                    entity1.setDate("10月14日 17:00");
                                    entity1.setTitle("山下智博，在华日本人，1985年出生于北海道，目前居住于上海");
                                    entity1.setContent("山下智博，在华日本人，1985年出生于北海道，目前居住于上海。2013年因多次带着人偶参与漫展而被国内网民熟知，2014年7月推出微电影作品《日本屌丝》，引起网络热议。2014年12月24日起在Bilibili推出个人娱乐栏目《绅士大概一分钟》，每日更新一期。在2016年10月2日宣布《绅士大概一分钟》停止日更，但星期二晚上9：00依然会直播。在2016年10月14日宣布将会在每周星期一和星期四更新《绅士大概一分钟》。");

                                    ArrayList<GrandEntity.DataEntity> temp  = new ArrayList<>();
                                    temp.add(entity1);
                                    temp.add(entity1);
                                    temp.add(entity1);
                                    temp.add(entity1);
                                    temp.add(entity1);
                                    mAdapter.addMore(temp);
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(Long aLong) {

                                }
                            });
                }
            }

        });
    }
}
