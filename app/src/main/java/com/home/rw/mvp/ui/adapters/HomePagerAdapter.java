package com.home.rw.mvp.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
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
import com.home.rw.utils.FrescoUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2017/1/10.
 */

public class HomePagerAdapter extends RecyclerView.Adapter<HomePagerAdapter.HomePageViewHolder>{
    private ArrayList<CommunicationEntity.DataEntity> dataSource;
    private Context context;
    private LayoutInflater inflater;
    private String entryType = "common";
    private OnItemClickListener mListener;
    private final int COMPRESS_WIDTH = 200;
    private final int COMPRESS_HEIGH = 120;
    public HomePagerAdapter(ArrayList<CommunicationEntity.DataEntity> dataSource, Context context){
        this.dataSource = dataSource;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    public HomePagerAdapter(ArrayList<CommunicationEntity.DataEntity> dataSource, String type, Context context){
        this.dataSource = dataSource;
        this.context = context;
        this.entryType = type;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public HomePagerAdapter.HomePageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cell_homepage, parent, false);
        HomePagerAdapter.HomePageViewHolder holder = new HomePagerAdapter.HomePageViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick((int)v.getTag());
            }
        });
        return holder;
    }
    //设置item监听事件
    public void setOnItemClickListener(OnItemClickListener mListener){

        this.mListener = mListener;
    }

    @Override
    public void onBindViewHolder(final HomePagerAdapter.HomePageViewHolder holder, final int position) {

        final CommunicationEntity.DataEntity entity = dataSource.get(position);

        holder.itemView.setTag(position);

        if(entity.getType() == 0){
            holder.mCompType.setText(context.getString(R.string.compIntroduction));
            holder.mCompTypeEn.setText(context.getString(R.string.compIntroductionEn));
            holder.mTitle.setText(entity.getTitle());
            holder.mContent1.setVisibility(View.VISIBLE);
            holder.mContent1.setText(entity.getContent());
            holder.mContent2.setVisibility(View.GONE);
        }else{
            holder.mCompType.setText(context.getString(R.string.compDynamic));
            holder.mCompTypeEn.setText(context.getString(R.string.compDynamicEn));
            holder.mTitle.setText(entity.getTitle());
            holder.mContent1.setVisibility(View.GONE);
            holder.mContent2.setVisibility(View.VISIBLE);
            holder.mContent2.setText(entity.getContent());
        }

        switch (entity.getImgs().size()){
            case 0:
                holder.mContainer.setVisibility(View.GONE);
                break;
            case 1:
                holder.mContainer.setVisibility(View.VISIBLE);
                holder.mPic1.setVisibility(View.VISIBLE);
                holder.mPic2.setVisibility(View.INVISIBLE);
                holder.mPic3.setVisibility(View.GONE);
                FrescoUtils.load(Uri.parse(entity.getImgs().get(0)),holder.mPic1,COMPRESS_WIDTH,COMPRESS_HEIGH);
                break;
            case 2:
                holder.mContainer.setVisibility(View.VISIBLE);
                holder.mPic1.setVisibility(View.VISIBLE);
                holder.mPic2.setVisibility(View.VISIBLE);
                holder.mPic3.setVisibility(View.GONE);
                FrescoUtils.load(Uri.parse(entity.getImgs().get(0)),holder.mPic1,COMPRESS_WIDTH,COMPRESS_HEIGH);
                FrescoUtils.load(Uri.parse(entity.getImgs().get(1)),holder.mPic2,COMPRESS_WIDTH,COMPRESS_HEIGH);
                break;
            case 3:
                holder.mContainer.setVisibility(View.VISIBLE);
                holder.mPic1.setVisibility(View.VISIBLE);
                holder.mPic2.setVisibility(View.VISIBLE);
                holder.mPic3.setVisibility(View.VISIBLE);
                FrescoUtils.load(Uri.parse(entity.getImgs().get(0)),holder.mPic1,COMPRESS_WIDTH,COMPRESS_HEIGH);
                FrescoUtils.load(Uri.parse(entity.getImgs().get(1)),holder.mPic2,COMPRESS_WIDTH,COMPRESS_HEIGH);
                FrescoUtils.load(Uri.parse(entity.getImgs().get(2)),holder.mPic3,COMPRESS_WIDTH,COMPRESS_HEIGH);

                break;
            default:
                break;

        }


    }

    @Override
    public int getItemCount() {
        return dataSource.size();
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
