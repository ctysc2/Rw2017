package com.home.rw.mvp.ui.adapters;

import android.content.Context;
import android.graphics.Color;
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
import com.home.rw.mvp.entity.ApprovementListEntity;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.utils.FrescoUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2017/1/6.
 */

public class CommunicationAdapter extends RecyclerView.Adapter<CommunicationAdapter.CommViewHolder> {
    private ArrayList<CommunicationEntity.DataEntity> dataSource;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mListener;
    private final int COMPRESS_WIDTH = 200;

    private final int COMPRESS_HEIGH = 120;
    public CommunicationAdapter(ArrayList<CommunicationEntity.DataEntity> dataSource, Context context){
        this.dataSource = dataSource;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public CommViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cell_communication, parent, false);
        CommViewHolder holder = new CommViewHolder(view);

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
    public void onBindViewHolder(final CommViewHolder holder, final int position) {

        final CommunicationEntity.DataEntity entity = dataSource.get(position);
        holder.itemView.setTag(position);
        holder.mHeader.setImageURI(entity.getHeader());
        holder.mName.setText(entity.getName());
        holder.mZanNum.setText(String.valueOf(entity.getZanNum()));
        holder.content.setText(entity.getContent());
        if(!entity.isFacused()){
            holder.mFacus.setEnabled(true);
            holder.mFacus.setText(context.getString(R.string.addFacus));
        }else{
            holder.mFacus.setEnabled(false);
            holder.mFacus.setText(context.getString(R.string.Facused));
        }

        if(!entity.isZaned()){
            holder.mZan.setImageResource(R.drawable.icon_zanlist);
            holder.mZan.setEnabled(true);

        }else{
            holder.mZan.setImageResource(R.drawable.icon_zaned);
            holder.mZan.setEnabled(false);

        }
        if(entity.getName().equals("钉宫理惠")){
            holder.mFacus.setVisibility(View.GONE);
        }else{
            holder.mFacus.setVisibility(View.VISIBLE);
        }

        holder.mFacus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!entity.isFacused()){
                    entity.setFacused(true);
                    holder.mFacus.setText(context.getString(R.string.Facused));
                    holder.mFacus.setEnabled(false);
                }
            }
        });

        holder.mZanContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!entity.isZaned()){
                    entity.setZaned(true);
                    entity.setZanNum(entity.getZanNum()+1);
                    holder.mZan.setImageResource(R.drawable.icon_zaned);
                    holder.mZanNum.setText(""+entity.getZanNum());
                    holder.mZan.setEnabled(false);
                }
            }
        });

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


    class CommViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_header)
        SimpleDraweeView mHeader;

        @BindView(R.id.tv_name)
        TextView mName;

        @BindView(R.id.tv_facus)
        TextView mFacus;

        @BindView(R.id.tv_zan_num)
        TextView mZanNum;

        @BindView(R.id.iv_zan)
        ImageView mZan;

        @BindView(R.id.tv_content)
        TextView content;

        @BindView(R.id.rl_zanContainer)
        RelativeLayout mZanContainer;

        @BindView(R.id.ll_imgContainer)
        LinearLayout mContainer;

        @BindView(R.id.iv_picture1)
        SimpleDraweeView mPic1;

        @BindView(R.id.iv_picture2)
        SimpleDraweeView mPic2;

        @BindView(R.id.iv_picture3)
        SimpleDraweeView mPic3;

        public CommViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
