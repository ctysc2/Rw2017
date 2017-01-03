package com.home.rw.mvp.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.ApprovementListEntity;
import com.home.rw.mvp.entity.LogEntity;
import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.entity.RollMeEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2016/12/20.
 */

public class RollMelistAdapter extends RecyclerView.Adapter<RollMelistAdapter.RollViewHolder> {
    private ArrayList<RollMeEntity.DataEntity> dataSource;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mListener;

    public RollMelistAdapter(ArrayList<RollMeEntity.DataEntity> dataSource, Context context){
        this.dataSource = dataSource;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public RollViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cell_rollme, parent, false);
        RollViewHolder holder = new RollViewHolder(view);

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
    public void onBindViewHolder(final RollViewHolder holder, int position) {
        final RollMeEntity.DataEntity entity = dataSource.get(position);
        holder.itemView.setTag(position);
        holder.mSender.setText(entity.getSender());
        holder.mTime.setText(entity.getSendTime());
        holder.mDeadLine.setText(entity.getDeadLineTime());
        holder.mContent.setText(entity.getContent());
        holder.mContent.setVisibility(entity.isExpand()?View.VISIBLE:View.GONE);
        holder.mLine.setVisibility(entity.isExpand()?View.VISIBLE:View.GONE);
        holder.mRlexpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isExpand = !entity.isExpand();
                entity.setExpand(isExpand);
                holder.mContent.setVisibility(isExpand?View.VISIBLE:View.GONE);
                holder.mLine.setVisibility(entity.isExpand()?View.VISIBLE:View.GONE);
                holder.mIVexpand.setImageResource(isExpand?R.drawable.icon_up:R.drawable.icon_down);
            }
        });

    }


    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    class RollViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_sender)
        TextView mSender;

        @BindView(R.id.tv_send_time)
        TextView mTime;

        @BindView(R.id.tv_deadline)
        TextView mDeadLine;

        @BindView(R.id.tv_content)
        TextView mContent;

        @BindView(R.id.rl_expand)
        RelativeLayout mRlexpand;

        @BindView(R.id.iv_expand)
        ImageView mIVexpand;

        @BindView(R.id.lastLine)
        View mLine;

        public RollViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
