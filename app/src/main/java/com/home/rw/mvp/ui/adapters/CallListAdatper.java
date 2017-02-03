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
import com.home.rw.mvp.entity.BusinessMeetingPhoneEntity;
import com.home.rw.mvp.entity.CallListEntity;
import com.home.rw.utils.DrawableUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2017/1/24.
 */

public class CallListAdatper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<CallListEntity.DataEntity> dataSource;
    private OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener mListener){

        this.mListener = mListener;
    }

    public CallListAdatper(ArrayList<CallListEntity.DataEntity> dataSource, Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.dataSource = dataSource;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cell_call_list, parent, false);
        RecyclerView.ViewHolder holder = new CallViewHolder(view);
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
        CallListEntity.DataEntity entity = dataSource.get(position);
        CallViewHolder mHolder = (CallViewHolder)holder;
        mHolder.itemView.setTag(position);
        if(entity.getAvatar()!=null){
            mHolder.mHeader.setVisibility(View.VISIBLE);
            mHolder.mTextHeader.setVisibility(View.GONE);
            mHolder.mHeader.setImageURI(entity.getAvatar());
        }else{
            mHolder.mHeader.setVisibility(View.GONE);
            mHolder.mTextHeader.setVisibility(View.VISIBLE);
            mHolder.mTextHeader.setText(entity.getName().substring(0,1));
            mHolder.mTextHeader.setBackgroundResource(DrawableUtils.getRandomBackgroundResource());
        }

        mHolder.mName.setText(entity.getName());

        if(entity.isEditing()
                && position<dataSource.size()-2
                && position != 0){

            mHolder.mFlag.setVisibility(View.VISIBLE);
        }else{
            mHolder.mFlag.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }
    public void addMore(int position,List<CallListEntity.DataEntity> data) {
        dataSource.addAll(position,data);
        notifyItemInserted(position);
        notifyItemRangeChanged(position,getItemCount()-position);
    }
    public void add(int position, CallListEntity.DataEntity item) {

        if(position>=dataSource.size())
            return;

        dataSource.add(position, item);


        notifyItemInserted(position);
        notifyItemRangeChanged(position,getItemCount()-position);
    }

    public void delete(int position) {
        if(position>=dataSource.size())
            return;

        dataSource.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,getItemCount()-position);
    }

    class CallViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_header)
        SimpleDraweeView mHeader;


        @BindView(R.id.tv_header)
        TextView mTextHeader;

        @BindView(R.id.tv_name)
        TextView mName;

        @BindView(R.id.iv_flag)
        ImageView mFlag;

        public CallViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
