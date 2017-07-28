package com.home.rw.mvp.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.home.rw.mvp.entity.FacusListEntity;
import com.home.rw.mvp.entity.MessegeMainEntity;
import com.home.rw.mvp.entity.message.MessageCommonEntity;
import com.home.rw.mvp.ui.adapters.base.BaseRecyclerViewAdapter;
import com.home.rw.utils.DimenUtil;
import com.home.rw.utils.DrawableUtils;
import com.home.rw.widget.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cty on 2017/1/15.
 */

public class MessegeMainAdapter extends BaseRecyclerViewAdapter<MessegeMainEntity.DataEntity> {

    private OnItemClickListener mListener;
    private OnItemClickListener mRemark;
    private OnItemClickListener mDetail;
    private Context context;
    private LayoutInflater inflater;

    private int chatNum = 0;

    //更新聊天未读消息数
    public void updateChatNum(int chatNum){
        this.chatNum = chatNum;
        notifyDataSetChanged();
    }
    //设置详情监听事件
    public void setOnDetailClickListener(OnItemClickListener mListener){

        this.mDetail = mListener;
    }
    //设置修改备注监听事件
    public void setOnRemarkClickListener(OnItemClickListener mListener){

        this.mRemark = mListener;
    }
    //设置item监听事件
    public void setOnItemClickListener(OnItemClickListener mListener){

        this.mListener = mListener;
    }

    public MessegeMainAdapter(ArrayList<MessegeMainEntity.DataEntity> dataSource, Context context){
        super(dataSource);
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder  holder = null;
        View view = null;
        switch (viewType){
            case TYPE_FOOTER:
                break;
            case TYPE_HEADER:
                holder = new BaseRecyclerViewAdapter.HeaderViewHolder(mHeaderView);
                break;
            case TYPE_ITEM:
                view = inflater.inflate(R.layout.cell_swipe_delete_item_2, parent, false);
                holder = new SubViewHolder(view);
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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,  int pos) {

        if(holder instanceof BaseRecyclerViewAdapter.HeaderViewHolder){
            return;
        }

        final int position = pos-1;

        final MessegeMainEntity.DataEntity entity = dataSource.get(position);

        if(!TextUtils.isEmpty(entity.getAvatar())){
            ((SubViewHolder)holder).mHeader.setVisibility(View.INVISIBLE);
            ((SubViewHolder)holder).mHeader2.setVisibility(View.VISIBLE);
            ((SubViewHolder)holder).mHeader2.setImageURI(entity.getAvatar());
        }else{
            ((SubViewHolder)holder).mHeader.setVisibility(View.VISIBLE);
            ((SubViewHolder)holder).mHeader2.setVisibility(View.INVISIBLE);
            ((SubViewHolder)holder).mHeader.setBackgroundResource(DrawableUtils.getRandomBackgroundResource(entity.getTitle()));

            if(!TextUtils.isEmpty(entity.getTitle()))
                ((SubViewHolder)holder).mHeader.setText(entity.getTitle().substring(0,1));
        }

        if(!TextUtils.isEmpty(entity.getNickName())){
            ((SubViewHolder)holder).mtitle.setText(entity.getNickName());
        }else{
            ((SubViewHolder)holder).mtitle.setText(entity.getTitle());
        }

        ((SubViewHolder)holder).mSubTitle.setText(entity.getSubTitle());
        ((SubViewHolder)holder).mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(position);
            }
        });



        ((SubViewHolder)holder).mButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看详细
                mDetail.onItemClick(position);
            }
        });

        ((SubViewHolder)holder).mButtonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改备注
                mRemark.onItemClick(position);
            }
        });

        if(position == dataSource.size()-1)
            ((SubViewHolder)holder).swipe.setBackgroundResource(R.drawable.shape_message_main_half_bac2);
        else
            ((SubViewHolder)holder).swipe.setBackgroundColor(Color.parseColor("#FFFFFF"));

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


    class SubViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_header)
        TextView mHeader;

        @BindView(R.id.iv_header)
        SimpleDraweeView mHeader2;

        @BindView(R.id.tv_line1)
        TextView mtitle;

        @BindView(R.id.tv_line2)
        TextView mSubTitle;

        @BindView(R.id.sperate)
        View mSperate;

        @BindView(R.id.btnLeft)
        Button mButtonLeft;

        @BindView(R.id.btnRight)
        Button mButtonRight;

        @BindView(R.id.rl_container)
        RelativeLayout mContainer;

        @BindView(R.id.swipe)
        SwipeMenuLayout swipe;


        public SubViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
