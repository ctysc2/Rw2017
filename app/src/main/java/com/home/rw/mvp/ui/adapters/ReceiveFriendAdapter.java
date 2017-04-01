package com.home.rw.mvp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.LogEntity;
import com.home.rw.mvp.entity.ReceiveFriendEntity;
import com.home.rw.mvp.entity.message.MessageCommonEntity;
import com.home.rw.mvp.entity.message.NewFriendEntity;
import com.home.rw.mvp.ui.adapters.base.BaseRecyclerViewAdapter;
import com.home.rw.utils.DrawableUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2017/2/2.
 */

public class ReceiveFriendAdapter extends BaseRecyclerViewAdapter<NewFriendEntity.DataEntity.ResLst> {

    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mListener;
    private OnItemClickListener mListener2;


    public ReceiveFriendAdapter(List<NewFriendEntity.DataEntity.ResLst> list, Context context) {
        super(list);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder  holder = null;
        View view = null;
        switch (viewType){
            case TYPE_FOOTER:
                view = inflater.inflate(R.layout.layout_footer_loadmore2, parent, false);
                holder = new FooterViewHolder(view);
                break;
            case TYPE_HEADER:
                holder = new HeaderViewHolder(mHeaderView);
                break;
            case TYPE_ITEM:
                view = inflater.inflate(R.layout.cell_friend_receive, parent, false);
                holder = new ReceiveViewHolder(view);
                break;
            default:
                break;
        }

        return holder;
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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ReceiveViewHolder){

            if(mIsShowHeader)
                position = position -1;

            final int mPosition = position;
            ReceiveViewHolder mHolder = (ReceiveViewHolder)holder;
            NewFriendEntity.DataEntity.ResLst entity = dataSource.get(mPosition);
            String name = TextUtils.isEmpty(entity.getNickname())?entity.getRealname():entity.getNickname();
            if (entity.getAvatar() == null || entity.getAvatar().equals("")) {

                mHolder.mIvHeader.setVisibility(View.INVISIBLE);
                mHolder.mTvHeader.setVisibility(View.VISIBLE);
                if(!TextUtils.isEmpty(name))
                    mHolder.mTvHeader.setText(name.substring(0,1));
                mHolder.mTvHeader.setBackgroundResource(DrawableUtils.getRandomBackgroundResource(name));
            } else {
                mHolder.mIvHeader.setVisibility(View.VISIBLE);
                mHolder.mTvHeader.setVisibility(View.INVISIBLE);
                mHolder.mIvHeader.setImageURI(entity.getAvatar());
            }

            mHolder.mTitle.setText(name);

            if(entity.getRemark() == null || entity.getRemark().equals("")){
                mHolder.msubTitle.setText(context.getString(R.string.addFriendHint));
            }else{
                mHolder.msubTitle.setText(entity.getRemark());
            }

            if(!entity.getIsFriend().equals("0")){
                mHolder.mApprove.setVisibility(View.GONE);
                mHolder.mApproved.setVisibility(View.VISIBLE);
                mHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(mPosition);
                    }
                });
            }else{
                mHolder.mApprove.setVisibility(View.VISIBLE);
                mHolder.mApproved.setVisibility(View.GONE);
                mHolder.mApprove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener2.onItemClick(mPosition);
                    }
                });
            }

            if (mPosition == dataSource.size() - 1) {
                mHolder.mSperate.setVisibility(View.GONE);
            } else {
                mHolder.mSperate.setVisibility(View.VISIBLE);
            }

        }


    }
    //设置item监听事件
    public void setOnItemClickListener(OnItemClickListener mListener){

        this.mListener = mListener;
    }
    //设置item监听事件
    public void setOnAcceptClickListener(OnItemClickListener mListener){

        this.mListener2 = mListener;
    }
    class ReceiveViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_header)
        SimpleDraweeView mIvHeader;

        @BindView(R.id.tv_header)
        TextView mTvHeader;

        @BindView(R.id.tv_title)
        TextView mTitle;

        @BindView(R.id.tv_subtitle)
        TextView msubTitle;

        @BindView(R.id.bt_right_approve)
        Button mApprove;

        @BindView(R.id.tv_right_approved)
        TextView mApproved;

        @BindView(R.id.sperate)
        View mSperate;

        public ReceiveViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
