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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2016/12/20.
 */

public class ApprovementListAdapter extends RecyclerView.Adapter<ApprovementListAdapter.ApprovementViewHolder> {
    private ArrayList<ApprovementListEntity.DataEntity> dataSource;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mListener;

    public ApprovementListAdapter(ArrayList<ApprovementListEntity.DataEntity> dataSource, Context context){
        this.dataSource = dataSource;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public ApprovementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cell_approvement, parent, false);
        ApprovementViewHolder holder = new ApprovementViewHolder(view);

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
    public void onBindViewHolder(ApprovementViewHolder holder, int position) {

        holder.itemView.setTag(position);

        resolveAppStates(holder.mResult,position);
        resolveAppType(holder.mType,position);
        holder.mName.setText(dataSource.get(position).getName());

        if(position == dataSource.size()-1)
            holder.mSperate.setVisibility(View.INVISIBLE);
        else
            holder.mSperate.setVisibility(View.VISIBLE);

    }

    private void resolveAppStates(TextView tvStatus,int position) {
        if(dataSource.get(position).getAppStatus() == -1) {
            tvStatus.setVisibility(View.INVISIBLE);
        } else if(dataSource.get(position).getAppStatus() == 0){
            tvStatus.setText(R.string.agree);
            tvStatus.setTextColor(Color.parseColor("#49CA5D"));
            tvStatus.setBackgroundResource(R.drawable.shape_agree_bac);
        }else{
            tvStatus.setText(R.string.disagree);
            tvStatus.setTextColor(Color.parseColor("#F46C6A"));
            tvStatus.setBackgroundResource(R.drawable.shape_disagree_bac);
        }
    }

    private void resolveAppType(TextView type,int position) {
        switch (dataSource.get(position).getAppType()){
            case 0:
                type.setText(R.string.getout);
                type.setBackgroundResource(R.drawable.shape_getout_bac);
                break;
            case 1:
                type.setText(R.string.askforleave);
                type.setBackgroundResource(R.drawable.shape_leave_bac);
                break;
            case 2:
                type.setText(R.string.extrawork);
                type.setBackgroundResource(R.drawable.shape_extra_bac);
                break;
            case 3:
                type.setText(R.string.wiped);
                type.setBackgroundResource(R.drawable.shape_wiped_bac);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    class ApprovementViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_header)
        SimpleDraweeView mHeader;

        @BindView(R.id.tv_name)
        TextView mName;

        @BindView(R.id.tv_date)
        TextView mDate;

        @BindView(R.id.tv_type)
        TextView mType;

        @BindView(R.id.tv_result)
        TextView mResult;

        @BindView(R.id.iv_sperate)
        View mSperate;

        public ApprovementViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
