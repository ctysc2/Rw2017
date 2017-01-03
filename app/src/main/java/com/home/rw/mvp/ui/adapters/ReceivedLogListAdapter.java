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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2016/12/20.
 */

public class ReceivedLogListAdapter extends RecyclerView.Adapter<ReceivedLogListAdapter.LogViewHolder> {
    private ArrayList<LogEntity.DataEntity> dataSource;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mListener;

    public ReceivedLogListAdapter(ArrayList<LogEntity.DataEntity> dataSource, Context context){
        this.dataSource = dataSource;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public LogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cell_log, parent, false);
        LogViewHolder holder = new LogViewHolder(view);

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
    public void onBindViewHolder(LogViewHolder holder, int position) {
        LogEntity.DataEntity entity = dataSource.get(position);
        holder.itemView.setTag(position);
        holder.mHeader.setImageURI(entity.getHeadUrl());
        holder.mName.setText(entity.getName());
        holder.mDate.setText(entity.getDate());
        holder.mContent.setText(entity.getContent());

    }


    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    class LogViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_header)
        SimpleDraweeView mHeader;

        @BindView(R.id.tv_name)
        TextView mName;

        @BindView(R.id.tv_date)
        TextView mDate;

        @BindView(R.id.tv_content)
        TextView mContent;


        public LogViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
