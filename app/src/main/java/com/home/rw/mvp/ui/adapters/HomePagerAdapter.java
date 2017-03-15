package com.home.rw.mvp.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.ui.adapters.base.BaseRecyclerViewAdapter;
import com.home.rw.utils.FrescoUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2017/1/10.
 */

public class HomePagerAdapter extends BaseRecyclerViewAdapter<CommunicationEntity.DataEntity.ResLst> {
    //private ArrayList<CommunicationEntity.DataEntity> dataSource;
    private Context context;
    private LayoutInflater inflater;
    private String entryType = "common";
    private OnItemClickListener mListener;
    private final int COMPRESS_WIDTH = 200;
    private final int COMPRESS_HEIGH = 120;
    public HomePagerAdapter(ArrayList<CommunicationEntity.DataEntity.ResLst> dataSource, Context context){
        super(dataSource);
        this.context = context;
        inflater = LayoutInflater.from(context);

    }
    public HomePagerAdapter(ArrayList<CommunicationEntity.DataEntity.ResLst> dataSource, String type, Context context){
        super(dataSource);
        this.context = context;
        this.entryType = type;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder  holder = null;
        View view = null;
        switch (viewType){
            case TYPE_FOOTER:
                view = inflater.inflate(R.layout.layout_footer_load_more, parent, false);
                holder = new FooterViewHolder(view);
                break;
            case TYPE_HEADER:
                holder = new HeaderViewHolder(mHeaderView);
                break;
            case TYPE_ITEM:
                view = inflater.inflate(R.layout.cell_homepage, parent, false);
                holder = new HomePagerAdapter.HomePageViewHolder(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick((int)v.getTag());
                    }
                });
                break;
            default:
                break;
        }

        return holder;
    }
    //设置item监听事件
    public void setOnItemClickListener(OnItemClickListener mListener){

        this.mListener = mListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (mIsShowFooter && isFooterPosition(position)) {
            return TYPE_FOOTER;
        } else if (mIsShowHeader && isHeaderPosition(position)) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,  int position) {

        if(holder instanceof HomePageViewHolder){
            if(mIsShowHeader)
                 position = position -1;

            final int mPosition = position;

            final HomePageViewHolder mHolder = (HomePageViewHolder)holder;
            final CommunicationEntity.DataEntity.ResLst entity = dataSource.get(mPosition);

            holder.itemView.setTag(mPosition);

            if(mPosition == 0){
                mHolder.mCompType.setText(context.getString(R.string.compIntroduction));
                mHolder.mCompTypeEn.setText(context.getString(R.string.compIntroductionEn));
                mHolder.mTitle.setText(entity.getTitle());
                mHolder.mContent1.setVisibility(View.VISIBLE);
                mHolder.mContent1.setText(entity.getContent());
                mHolder.mContent2.setVisibility(View.GONE);
            }else{
                mHolder.mCompType.setText(context.getString(R.string.compDynamic));
                mHolder.mCompTypeEn.setText(context.getString(R.string.compDynamicEn));
                mHolder.mTitle.setText(entity.getTitle());
                mHolder.mContent1.setVisibility(View.GONE);
                mHolder.mContent2.setVisibility(View.VISIBLE);
                mHolder.mContent2.setText(entity.getContent());
            }
            if(!TextUtils.isEmpty(entity.getImgs())) {
                String[] imgs = entity.getImgs().split(",");
                switch (imgs.length) {
                    case 0:
                        mHolder.mContainer.setVisibility(View.GONE);
                        break;
                    case 1:
                        mHolder.mContainer.setVisibility(View.VISIBLE);
                        mHolder.mPic1.setVisibility(View.VISIBLE);
                        mHolder.mPic2.setVisibility(View.INVISIBLE);
                        mHolder.mPic3.setVisibility(View.GONE);
                        FrescoUtils.load(Uri.parse(imgs[0]), mHolder.mPic1, COMPRESS_WIDTH, COMPRESS_HEIGH);
                        break;
                    case 2:
                        mHolder.mContainer.setVisibility(View.VISIBLE);
                        mHolder.mPic1.setVisibility(View.VISIBLE);
                        mHolder.mPic2.setVisibility(View.VISIBLE);
                        mHolder.mPic3.setVisibility(View.GONE);
                        FrescoUtils.load(Uri.parse(imgs[0]), mHolder.mPic1, COMPRESS_WIDTH, COMPRESS_HEIGH);
                        FrescoUtils.load(Uri.parse(imgs[1]), mHolder.mPic2, COMPRESS_WIDTH, COMPRESS_HEIGH);
                        break;
                    case 3:
                        mHolder.mContainer.setVisibility(View.VISIBLE);
                        mHolder.mPic1.setVisibility(View.VISIBLE);
                        mHolder.mPic2.setVisibility(View.VISIBLE);
                        mHolder.mPic3.setVisibility(View.VISIBLE);
                        FrescoUtils.load(Uri.parse(imgs[0]), mHolder.mPic1, COMPRESS_WIDTH, COMPRESS_HEIGH);
                        FrescoUtils.load(Uri.parse(imgs[1]), mHolder.mPic2, COMPRESS_WIDTH, COMPRESS_HEIGH);
                        FrescoUtils.load(Uri.parse(imgs[2]), mHolder.mPic3, COMPRESS_WIDTH, COMPRESS_HEIGH);

                        break;
                    default:
                        break;

                }
            }else{
                mHolder.mContainer.setVisibility(View.GONE);
            }
        }




    }


    class HomePageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_comptype)
        TextView mCompType;

        @BindView(R.id.tv_comptypeEn)
        TextView mCompTypeEn;

        @BindView(R.id.tv_title)
        TextView mTitle;

        @BindView(R.id.tv_content_type1)
        TextView mContent1;

        @BindView(R.id.tv_content_type2)
        TextView mContent2;

        @BindView(R.id.ll_imgContainer)
        LinearLayout mContainer;

        @BindView(R.id.iv_picture1)
        SimpleDraweeView mPic1;

        @BindView(R.id.iv_picture2)
        SimpleDraweeView mPic2;

        @BindView(R.id.iv_picture3)
        SimpleDraweeView mPic3;

        public HomePageViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
