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
import com.home.rw.mvp.entity.SignEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2016/12/20.
 */

public class SignListAdapter extends RecyclerView.Adapter<SignListAdapter.SignViewHolder> {
    private ArrayList<SignEntity.DataEntity> dataSource;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mListener;

    public SignListAdapter(ArrayList<SignEntity.DataEntity> dataSource, Context context){
        this.dataSource = dataSource;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public SignViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cell_sign, parent, false);
        SignViewHolder holder = new SignViewHolder(view);

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
    public void onBindViewHolder(final SignViewHolder holder, int position) {
        final SignEntity.DataEntity entity = dataSource.get(position);
        holder.itemView.setTag(position);
        holder.mHeader.setImageURI(entity.getHeader());
        holder.mName.setText(entity.getName());
        holder.mDate.setText(entity.getTime());
        holder.mAddress.setText(entity.getAddress());

        if(dataSource.size() == position+1){
            holder.mSperate.setVisibility(View.GONE);
        }


    }


    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    class SignViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_header)
        SimpleDraweeView mHeader;

        @BindView(R.id.tv_name)
        TextView mName;

        @BindView(R.id.tv_date)
        TextView mDate;

        @BindView(R.id.tv_address)
        TextView mAddress;

        @BindView(R.id.sperate)
        View mSperate;


        public SignViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
