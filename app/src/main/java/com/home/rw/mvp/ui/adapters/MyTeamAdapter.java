package com.home.rw.mvp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.common.Const;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.MyTeamEntity;
import com.home.rw.mvp.entity.OrgEntity;
import com.home.rw.mvp.entity.ReceiveFriendEntity;
import com.home.rw.mvp.entity.SelectEntity;
import com.home.rw.utils.DrawableUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2017/1/30.
 */

public class MyTeamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final static int TYPE_MAIN  = 0;
    private final static int TYPE_SUB   = 1;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mListener;
    private ArrayList<MyTeamEntity.DataEntity> dataSource;
    private String entryType = Const.TYPE_NORMAL;

    public MyTeamAdapter(ArrayList<MyTeamEntity.DataEntity> dataSource, Context context,String entryType){
        this.dataSource = dataSource;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.entryType = entryType;
    }
    public void setDataSource(ArrayList<MyTeamEntity.DataEntity> dataSource){
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }
    //设置item监听事件
    public void setOnItemClickListener(OnItemClickListener mListener){

        this.mListener = mListener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        if(entryType.equals(Const.TYPE_NORMAL)){
            view = inflater.inflate(R.layout.cell_org_items, parent, false);
            holder = new MyTeamAdapter.MyTeamViewHolder(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick((int)v.getTag());
                }
            });
        }else if(entryType.equals(Const.TYPE_ADD)){
            view = inflater.inflate(R.layout.cell_friend_add, parent, false);
            holder = new MyTeamAdapter.MyTeamAddViewHolder(view);
        }else{
            view = inflater.inflate(R.layout.cell_chat_select, parent, false);
            holder = new MyTeamAdapter.MyTeamSelectViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick((int)v.getTag());
                }
            });
        }

        return holder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        MyTeamEntity.DataEntity entity = dataSource.get(position);
        if(entryType.equals(Const.TYPE_NORMAL)){
            MyTeamViewHolder mHolder = (MyTeamViewHolder)holder;
            mHolder.mtitle.setText(entity.getTitle());
            mHolder.mSubTitle.setText(entity.getSubTitle());
            mHolder.mTvRight.setText(entity.getDate());
            if(entity.getAvatar()!=null){
                mHolder.mHeaderText.setVisibility(View.INVISIBLE);
                mHolder.mHeader.setVisibility(View.VISIBLE);
                mHolder.mHeader.setImageURI(entity.getAvatar());
            }else{
                mHolder.mHeaderText.setVisibility(View.VISIBLE);
                mHolder.mHeader.setVisibility(View.INVISIBLE);
                mHolder.mHeaderText.setText(entity.getTitle().substring(0,1));
                mHolder.mHeaderText.setBackgroundResource(DrawableUtils.getRandomBackgroundResource(entity.getTitle()));
            }
            if((position == dataSource.size()-1)||
                    dataSource.get(position+1).getId()<=0)
            {
                mHolder.mSperate.setVisibility(View.GONE);
            }else{
                mHolder.mSperate.setVisibility(View.VISIBLE);
            }
        }else if(entryType.equals(Const.TYPE_ADD)){
            MyTeamAddViewHolder mHolder = (MyTeamAddViewHolder)holder;
            if (entity.getAvatar() == null || entity.getAvatar().equals("")) {
                mHolder.mIvHeader.setVisibility(View.INVISIBLE);
                mHolder.mTvHeader.setVisibility(View.VISIBLE);
                mHolder.mTvHeader.setText(entity.getTitle().substring(0,1));
                mHolder.mTvHeader.setBackgroundResource(DrawableUtils.getRandomBackgroundResource(entity.getTitle()));
            } else {
                mHolder.mIvHeader.setVisibility(View.VISIBLE);
                mHolder.mTvHeader.setVisibility(View.INVISIBLE);
                mHolder.mIvHeader.setImageURI(entity.getAvatar());
            }
            mHolder.mTitle.setText(entity.getTitle());
            if(entity.isAdded()){
                mHolder.mAdd.setVisibility(View.GONE);
                mHolder.mAdded.setVisibility(View.VISIBLE);
            }else{
                mHolder.mAdd.setVisibility(View.VISIBLE);
                mHolder.mAdded.setVisibility(View.GONE);
                mHolder.mAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(position);
                    }
                });
            }

            if (position == dataSource.size() - 1) {
                mHolder.mSperate.setVisibility(View.GONE);
            } else {
                mHolder.mSperate.setVisibility(View.VISIBLE);
            }

        }else{
            MyTeamSelectViewHolder mHolder = (MyTeamSelectViewHolder)holder;
            mHolder.itemView.setTag(position);
            mHolder.mTitle.setText(entity.getTitle());

            if (entity.getAvatar() == null || entity.getAvatar().equals("")) {
                mHolder.mIvHeader.setVisibility(View.INVISIBLE);
                mHolder.mTvHeader.setVisibility(View.VISIBLE);
                mHolder.mTvHeader.setText(entity.getTitle().substring(0,1));
                mHolder.mTvHeader.setBackgroundResource(DrawableUtils.getRandomBackgroundResource(entity.getTitle()));
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

    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    class MyTeamViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_header)
        TextView mHeaderText;

        @BindView(R.id.iv_header)
        SimpleDraweeView mHeader;

        @BindView(R.id.tv_line1)
        TextView mtitle;

        @BindView(R.id.tv_line2)
        TextView mSubTitle;

        @BindView(R.id.tv_right)
        TextView mTvRight;

        @BindView(R.id.sperate)
        View mSperate;

        public MyTeamViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    class MyTeamAddViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_header)
        SimpleDraweeView mIvHeader;

        @BindView(R.id.tv_header)
        TextView mTvHeader;

        @BindView(R.id.tv_line1)
        TextView mTitle;

        @BindView(R.id.bt_right_add)
        Button mAdd;

        @BindView(R.id.tv_right_added)
        TextView mAdded;

        @BindView(R.id.sperate)
        View mSperate;

        public MyTeamAddViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    class MyTeamSelectViewHolder extends RecyclerView.ViewHolder {


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

        public MyTeamSelectViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}