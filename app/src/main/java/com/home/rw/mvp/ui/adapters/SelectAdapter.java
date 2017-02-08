package com.home.rw.mvp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.GroupChatEntity;
import com.home.rw.mvp.entity.SelectEntity;
import com.home.rw.utils.DrawableUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2017/2/3.
 */

public class SelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mListener;
    private ArrayList<SelectEntity.DataEntity> dataSource;

    public SelectAdapter(ArrayList<SelectEntity.DataEntity> dataSource, Context context){
        this.dataSource = dataSource;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    //设置item监听事件
    public void setOnItemClickListener(OnItemClickListener mListener){

        this.mListener = mListener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder  holder = null;
        View view = null;

        view = inflater.inflate(R.layout.cell_chat_select, parent, false);
        holder = new SelectViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick((int)v.getTag());
            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SelectViewHolder mHolder = (SelectViewHolder)holder;
        SelectEntity.DataEntity entity = dataSource.get(position);
        mHolder.itemView.setTag(position);
        mHolder.mTitle.setText(entity.getName());

        if (entity.getAvatar() == null || entity.getAvatar().equals("")) {
            mHolder.mIvHeader.setVisibility(View.INVISIBLE);
            mHolder.mTvHeader.setVisibility(View.VISIBLE);
            mHolder.mTvHeader.setText(entity.getName().substring(0,1));
            mHolder.mTvHeader.setBackgroundResource(DrawableUtils.getRandomBackgroundResource(entity.getName()));
        } else {
            mHolder.mIvHeader.setVisibility(View.VISIBLE);
            mHolder.mTvHeader.setVisibility(View.INVISIBLE);
            mHolder.mIvHeader.setImageURI(entity.getAvatar());
        }

        if(entity.isSelected()){
            mHolder.mSelect.setSelected(true);
        }else{
            mHolder.mSelect.setSelected(false);
        }
        if(position == dataSource.size()-1){
            mHolder.mSperate.setVisibility(View.GONE);
        }else{
            mHolder.mSperate.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    class SelectViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.iv_select)
        ImageView mSelect;

        @BindView(R.id.iv_header)
        SimpleDraweeView mIvHeader;

        @BindView(R.id.tv_header)
        TextView mTvHeader;

        @BindView(R.id.tv_title)
        TextView mTitle;

        @BindView(R.id.sperate)
        View mSperate;

        public SelectViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
