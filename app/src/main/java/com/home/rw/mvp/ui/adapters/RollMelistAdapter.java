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
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.entity.LogEntity;
import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.entity.RollMeEntity;
import com.home.rw.mvp.ui.adapters.base.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2016/12/20.
 */

public class RollMelistAdapter extends BaseRecyclerViewAdapter<RollMeEntity.DataEntity.ResLst> {
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mListener;

    public RollMelistAdapter(ArrayList<RollMeEntity.DataEntity.ResLst> dataSource, Context context){
        super(dataSource);
        this.dataSource = dataSource;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder  holder = null;
        View view = null;

        switch (viewType){
            case TYPE_FOOTER:
                view = inflater.inflate(R.layout.layout_footer_load_more, parent, false);
                holder = new FooterViewHolder(view);
                break;
            case TYPE_HEADER:
                holder = new HeaderViewHolder(mHeaderView);
                break;
            case TYPE_ITEM:
                view = inflater.inflate(R.layout.cell_rollme, parent, false);
                holder = new RollViewHolder(view);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick((int)v.getTag());
                    }
                });
                break;
            default:
                break;
        }


        return holder;
    }
    //设置item监听事件
    public void setOnItemClickListener(OnItemClickListener mListener){

        this.mListener = mListener;
    }
    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof RollViewHolder) {

            if(mIsShowHeader)
                position = position -1;

            final int mPosition = position;
            final RollViewHolder mHolder = (RollViewHolder)holder;

            final RollMeEntity.DataEntity.ResLst entity = dataSource.get(mPosition);
            mHolder.itemView.setTag(position);
            mHolder.mSender.setText(entity.getAuthor());
            mHolder.mTime.setText(entity.getBeginTime());
            mHolder.mDeadLine.setText(entity.getEndTime());
            mHolder.mContent.setText(entity.getContent());
            mHolder.mContent.setVisibility(entity.isExpand()?View.VISIBLE:View.GONE);
            mHolder.mLine.setVisibility(entity.isExpand()?View.VISIBLE:View.GONE);
            mHolder.mRlexpand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isExpand = !entity.isExpand();
                    entity.setExpand(isExpand);
                    mHolder.mContent.setVisibility(isExpand?View.VISIBLE:View.GONE);
                    mHolder.mLine.setVisibility(entity.isExpand()?View.VISIBLE:View.GONE);
                    mHolder.mIVexpand.setImageResource(isExpand?R.drawable.icon_up:R.drawable.icon_down);
                }
            });
        }


    }

    @Override
    public int getItemViewType(int position) {
        if (mIsShowFooter && isFooterPosition(position)) {
            return TYPE_FOOTER;
        } else if (mIsShowHeader && isHeaderPosition(position)) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
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
