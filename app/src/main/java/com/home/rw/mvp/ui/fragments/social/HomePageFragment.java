package com.home.rw.mvp.ui.fragments.social;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.ui.activitys.social.CommDetailActivity;
import com.home.rw.mvp.ui.adapters.HomePagerAdapter;
import com.home.rw.mvp.ui.adapters.RecycleViewSperate;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by cty on 2017/1/5.
 */

public class HomePageFragment extends BaseFragment {

    private HomePagerAdapter mAdapter;

    private boolean mIsLoadingMore;

    @Inject
    Activity mActivity;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout mRefresh;

    private ArrayList<CommunicationEntity.DataEntity> dataSource  = new ArrayList<>();

    View mHeader;
    @Inject
    public HomePageFragment(){

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
        initRecycleView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_homepage;
    }
    private void initRecycleView(){
        //dummy data


        //1
        CommunicationEntity.DataEntity child1 = new CommunicationEntity.DataEntity();
        child1.setType(0);
        child1.setName("若为置业");
        child1.setContent("若为国际集团是一家香港独资公司。公司主要市场分布于国内及中东、南亚、东南亚、澳洲、美洲及俄罗斯等，主要经营金融和非金融领域。金融部分涵盖基金管理、资产管理及投资银行业务，而非金融部分则囊括了地产、国际贸易、能源矿业资源、机械制造和农业工程等。\n" +
                "\n" +
                "经过多年的发展，公司现已拥有超过20家分支机构，如若为工程、若为投资、若为资源、若为置业、若为资产管理公司、若为贸易、若为农业技术等。\n" +
                "\n" +
                "在国家经济转型的大背景下，若为国际也逐步将投资重点从传统领域转移到新科技、新能源和IT项目上。同时，公司在逐步拓展自己业务版图的过程中，与中国几大主要金融和非金融机构都建立了良好的合作关系。\n" +
                "\n" +
                "作为一家致力于成长为卓越的跨国资产管理公司的企业，“上善若水，无为而治”是我们的理念。我们的团队将以坚定而明确的国际化目标持续拓展自身的事业版图，并继续为社会做出贡献。");
        child1.setTitle("我们的公司");
        ArrayList<String> img1 = new ArrayList<>();
        child1.setImgs(img1);

        //2
        CommunicationEntity.DataEntity child2 = new CommunicationEntity.DataEntity();
        child2.setType(1);
        child2.setTitle("小米啊小米");
        child2.setName("若为置业");
        child2.setContent("北京小米科技有限责任公司成立2010年4月，是一家专注于智能硬件和电子产品研发的移动互联网公司。“为发烧而生”是小米的产品概念。小米公司首创了用互联网模式开发手机操作系统、发烧友参与开发改进的模式。[1] \n" +
                "2014年12月14日，美的集团发出公告称，与小米科技签署战略合作协议，小米12.7亿元入股美的集团。2015年9月22日，小米在北京发布了新品小米4c，这款新品由小米4i升级而来，配备5英寸显示屏，搭载骁龙808处理器，号称安卓小王子。\n" +
                "2016年7月27日的发布会上小米笔记本终于正式亮相，这款产品叫做小米笔记本Air。2016年9月27日的小米2016秋季新品发布会上，小米5s和小米5s Plus以及小米电视3s 55英寸和65英寸发布。[2]  2016年10月25日，小米正式推出了小米Note2系列和小米mix。[3] \n" +
                "小米负责宣传联络的一名工作人员上周末表示，该公司2015年营收达到780亿元人民币(约合125亿美元)，较2014年的743亿元人民币增长了5%。[4]  1月5日，小米在美国CES展会上发布系列新品，其中包括小米Mix手机白色版（皓月白），以及小米电视4新品。");
        ArrayList<String> img2 = new ArrayList<>();
        img2.add("https://imgsa.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=f7840d699513b07ea9b0585a6dbefa46/d788d43f8794a4c271686d6308f41bd5ad6e392a.jpg");
        img2.add("http://image.ssports.com/images/resources/2016/1217/20161217101233_133.jpg");
        img2.add("http://cdn8.staztic.com/app/a/2875/2875155/chelsea-fc-hd-wallpapers-3-3-s-307x512.jpg");
        child2.setImgs(img2);

        //3
        CommunicationEntity.DataEntity child3 = new CommunicationEntity.DataEntity();
        child3.setType(1);
        child3.setName("若为置业");
        child3.setTitle("华为啊华为");
        child3.setContent("华为技术有限公司是一家生产销售通信设备的民营通信科技公司，总部位于中国广东省深圳市龙岗区坂田华为基地。华为的产品主要涉及通信网络中的交换网络、传输网络、无线及有线固定接入网络和数据通信网络及无线终端产品，为世界各地通信运营商及专业网络拥有者提供硬件设备、软件、服务和解决方案。华为于1987年在中国深圳正式注册成立。\n" +
                "华为的产品和解决方案已经应用于全球170多个国家，服务全球运营商50强中的45家及全球1/3的人口。\n" +
                "据了解，华为在2010年以218.21亿美元营业收入首次杀入《财富》世界500强榜单，排名为第397位。2011年以273.557亿美元年营业收入位居第352位。2012年，华为连续3年入选财富500强，以315.4亿美元名列351位。2013年，华为首超全球第一大电信设备商爱立信，排名第315位，爱立信排名第333位。2014年，华为排名由去年的第315位上升至285位。2015年华为排名相较2014年又有大幅提升，上升57位至228位。2016年，华为又提升了将近百名，位居第129位[1]  。\n" +
                "2015年，评为新浪科技2014年度风云榜年度杰出企业。[2] \n" +
                "2016年，研究机构Millward Brown编制的BrandZ全球100个最具价值品牌排行榜中，华为从2015年的排名第70位上升到第50位。[3]  8月，全国工商联发布“2016中国民营企业500强”榜单，华为以3950.09亿元的年营业收入成为500强榜首。[4]  8月，华为在\"2016中国企业500强\"中排名第27位");
         ArrayList<String> img3 = new ArrayList<>();
        img3.add("https://imgsa.baidu.com/baike/c0%3Dbaike150%2C5%2C5%2C150%2C50/sign=25383fdac0fcc3cea0cdc161f32cbded/d01373f082025aafc627cd33f3edab64034f1a34.jpg");
        img3.add("https://imgsa.baidu.com/baike/c0%3Dbaike116%2C5%2C5%2C116%2C38/sign=52d9c13eb23533fae1bb9b7cc9ba967a/b812c8fcc3cec3fdb702ef45d588d43f879427c5.jpg");

        child3.setImgs(img3);


        //4
        CommunicationEntity.DataEntity child4 = new CommunicationEntity.DataEntity();
        child4.setType(1);
        child4.setName("若为置业");
        child4.setTitle("百度啊百度");
        child4.setContent("百度（纳斯达克：BIDU），全球最大的中文搜索引擎、最大的中文网站。1999年底,身在美国硅谷的李彦宏看到了中国互联网及中文搜索引擎服务的巨大发展潜力，抱着技术改变世界的梦想，他毅然辞掉硅谷的高薪工作，携搜索引擎专利技术，于 2000年1月1日在中关村创建了百度公司。\n" +
                "“百度”二字,来自于八百年前南宋词人辛弃疾的一句词：众里寻他千百度。这句话描述了词人对理想的执着追求。\n" +
                "百度拥有数万名研发工程师，这是中国乃至全球最为优秀的技术团队。这支队伍掌握着世界上最为先进的搜索引擎技术，使百度成为中国掌握世界尖端科学核心技术的中国高科技企业，也使中国成为美国、俄罗斯、和韩国之外，全球仅有的4个拥有搜索引擎核心技术的国家之一。");
        ArrayList<String> img4 = new ArrayList<>();
        img4.add("https://imgsa.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=0fc9b8c7261f95cab2f89ae4a87e145b/1c950a7b02087bf49212ea50f1d3572c10dfcf89.jpg");
        img4.add("https://imgsa.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=d1badcd90dd79123f4ed9c26cc5d32e7/7c1ed21b0ef41bd5b654642550da81cb38db3ddf.jpg");

        child4.setImgs(img4);

        dataSource.add(child1);
        dataSource.add(child2);
        dataSource.add(child3);
        dataSource.add(child4);

        mHeader = LayoutInflater.from(mActivity).inflate(R.layout.layout_homepage_header, null, false);

        mAdapter = new HomePagerAdapter(dataSource,mActivity);
        mAdapter.setIsShowHeader(true);
        mAdapter.setHeaderView(mHeader);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(dataSource.get(position).getType()!=0){
                    Intent intent = new Intent(mActivity,CommDetailActivity.class);
                    intent.putExtra("commData",dataSource.get(position));
                    intent.putExtra("entryType","HomePage");
                    startActivity(intent);
                }

            }
        });

        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(mActivity,
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
                        .findLastVisibleItemPosition();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if (!mIsLoadingMore && visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItemCount - 1) {
                    Log.i("mRecycleView","end");
                    mAdapter.showFooter();
                    mIsLoadingMore = true;
                    mRecycleView.scrollToPosition(mAdapter.getItemCount() - 1);
                    Observable.timer(2, TimeUnit.SECONDS).
                            observeOn(AndroidSchedulers.mainThread()).
                            subscribe(new Observer<Long>() {
                                @Override
                                public void onCompleted() {
                                    mAdapter.hideFooter();
                                    mIsLoadingMore = false;
                                    CommunicationEntity.DataEntity child3 = new CommunicationEntity.DataEntity();
                                    child3.setType(1);
                                    child3.setName("若为置业");
                                    child3.setTitle("华为啊华为");
                                    child3.setContent("华为技术有限公司是一家生产销售通信设备的民营通信科技公司，总部位于中国广东省深圳市龙岗区坂田华为基地。华为的产品主要涉及通信网络中的交换网络、传输网络、无线及有线固定接入网络和数据通信网络及无线终端产品，为世界各地通信运营商及专业网络拥有者提供硬件设备、软件、服务和解决方案。华为于1987年在中国深圳正式注册成立。\n" +
                                            "华为的产品和解决方案已经应用于全球170多个国家，服务全球运营商50强中的45家及全球1/3的人口。\n" +
                                            "据了解，华为在2010年以218.21亿美元营业收入首次杀入《财富》世界500强榜单，排名为第397位。2011年以273.557亿美元年营业收入位居第352位。2012年，华为连续3年入选财富500强，以315.4亿美元名列351位。2013年，华为首超全球第一大电信设备商爱立信，排名第315位，爱立信排名第333位。2014年，华为排名由去年的第315位上升至285位。2015年华为排名相较2014年又有大幅提升，上升57位至228位。2016年，华为又提升了将近百名，位居第129位[1]  。\n" +
                                            "2015年，评为新浪科技2014年度风云榜年度杰出企业。[2] \n" +
                                            "2016年，研究机构Millward Brown编制的BrandZ全球100个最具价值品牌排行榜中，华为从2015年的排名第70位上升到第50位。[3]  8月，全国工商联发布“2016中国民营企业500强”榜单，华为以3950.09亿元的年营业收入成为500强榜首。[4]  8月，华为在\"2016中国企业500强\"中排名第27位");
                                    ArrayList<String> img3 = new ArrayList<>();
                                    img3.add("https://imgsa.baidu.com/baike/c0%3Dbaike150%2C5%2C5%2C150%2C50/sign=25383fdac0fcc3cea0cdc161f32cbded/d01373f082025aafc627cd33f3edab64034f1a34.jpg");
                                    img3.add("https://imgsa.baidu.com/baike/c0%3Dbaike116%2C5%2C5%2C116%2C38/sign=52d9c13eb23533fae1bb9b7cc9ba967a/b812c8fcc3cec3fdb702ef45d588d43f879427c5.jpg");
                                    child3.setImgs(img3);
                                    ArrayList<CommunicationEntity.DataEntity> temp  = new ArrayList<>();
                                    temp.add(child3);
                                    temp.add(child3);
                                    temp.add(child3);
                                    temp.add(child3);
                                    temp.add(child3);
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
