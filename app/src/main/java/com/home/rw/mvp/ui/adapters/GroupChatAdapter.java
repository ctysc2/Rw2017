package com.home.rw.mvp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.ApprovementListEntity;
import com.home.rw.mvp.entity.GroupChatEntity;
import com.home.rw.mvp.ui.adapters.base.BaseRecyclerViewAdapter;
import com.home.rw.utils.DrawableUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2017/2/3.
 */

public class GroupChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mListener;
    private ArrayList<GroupChatEntity.DataEntity> dataSource;

    public GroupChatAdapter(ArrayList<GroupChatEntity.DataEntity> dataSource, Context context){
        this.dataSource = dataSource;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder  holder = null;
        View view = null;

        view = inflater.inflate(R.layout.cell_group_chat, parent, false);
        holder = new GroupChatViewHolder(view);

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        GroupChatViewHolder mHolder = (GroupChatViewHolder)holder;
        GroupChatEntity.DataEntity entity = dataSource.get(position);
        mHolder.itemView.setTag(position);
        mHolder.mHeader.setText(entity.getName().substring(0,1));
        mHolder.mHeader.setBackgroundResource(DrawableUtils.getRandomBackgroundResource(entity.getName()));
        mHolder.mTitle.setText(entity.getName()+String.format(context.getString(R.string.orgNum),entity.getNum()));

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

    class GroupChatViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_header)
        TextView mHeader;

        @BindView(R.id.tv_title)
        TextView mTitle;

        @BindView(R.id.sperate)
        View mSperate;

        public GroupChatViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
