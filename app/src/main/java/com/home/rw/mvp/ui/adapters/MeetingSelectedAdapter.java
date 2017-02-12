package com.home.rw.mvp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.MeetingSelectEntity;
import com.home.rw.mvp.entity.MessegeMainEntity;
import com.home.rw.mvp.entity.SelectEntity;
import com.home.rw.utils.DrawableUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2017/2/4.
 */

public class MeetingSelectedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int TYPE_MAIN  = 0;
    private final static int TYPE_SUB   = 1;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mListener;
    private ArrayList<MeetingSelectEntity.DataEntity> dataSource;

    //设置item监听事件
    public void setOnItemClickListener(OnItemClickListener mListener){

        this.mListener = mListener;
    }
    public void setDataSource(ArrayList<MeetingSelectEntity.DataEntity> dataSource){
        this.dataSource = dataSource;
        notifyDataSetChanged();

    }
    public MeetingSelectedAdapter(ArrayList<MeetingSelectEntity.DataEntity> dataSource,Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.dataSource = dataSource;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;

        switch (viewType){
            case TYPE_MAIN:
                view = inflater.inflate(R.layout.cell_meeting_top, parent, false);
                holder = new MainViewHolder(view);
                break;
            case TYPE_SUB:
                view = inflater.inflate(R.layout.cell_meeting_sub, parent, false);
                holder = new SubViewHolder(view);
                break;
        }


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
        final MeetingSelectEntity.DataEntity entity = dataSource.get(position);

        if (holder instanceof MainViewHolder) {
            MainViewHolder mHolder = (MainViewHolder)holder;
            mHolder.itemView.setTag(position);
            mHolder.mRightImg.setVisibility(View.INVISIBLE);
            switch (entity.getId()){
                case -1:
                    mHolder.mHeader.setImageResource(R.drawable.icon_contact);
                    mHolder.mtitle.setText(context.getString(R.string.phoneContact));
                    break;
                case -2:
                    mHolder.mHeader.setImageResource(R.drawable.icon_org);
                    mHolder.mtitle.setText(context.getString(R.string.organization));
                    break;
                case -3:
                    mHolder.mHeader.setImageResource(R.drawable.icon_meeting_call);
                    mHolder.mtitle.setText(context.getString(R.string.myTeam));
                    break;
                case -4:
                    mHolder.mHeader.setImageResource(R.drawable.icon_meeting_changyong);
                    mHolder.mtitle.setText(context.getString(R.string.changYong));
                    mHolder.mRightImg.setVisibility(View.VISIBLE);
                    if(entity.isExpanded())
                        mHolder.mRightImg.setImageResource(R.drawable.icon_down);
                    else
                        mHolder.mRightImg.setImageResource(R.drawable.icon_right_more);

                    break;
                default:
                    break;
            }

        }else if(holder instanceof SubViewHolder){
            SubViewHolder mHolder = (SubViewHolder)holder;
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

    @Override
    public int getItemViewType(int position) {

        int id = dataSource.get(position).getId();
        if(id<=0){
            return TYPE_MAIN;
        }else{
            return TYPE_SUB;
        }
    }
    class MainViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_header)
        ImageView mHeader;

        @BindView(R.id.tv_text)
        TextView mtitle;

        @BindView(R.id.sperate)
        View mSperate;

        @BindView(R.id.iv_right)
        ImageView mRightImg;

        public MainViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class SubViewHolder extends RecyclerView.ViewHolder{

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


        public SubViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
