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
import com.home.rw.mvp.entity.FacusListEntity;
import com.home.rw.mvp.entity.MessegeMainEntity;
import com.home.rw.utils.DimenUtil;
import com.home.rw.utils.DrawableUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cty on 2017/1/15.
 */

public class MessegeMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final static int TYPE_MAIN  = 0;
    private final static int TYPE_SUB   = 1;
    private OnItemClickListener mListener;
    private OnItemClickListener mRemark;
    private OnItemClickListener mDetail;
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MessegeMainEntity.DataEntity> dataSource;
    private Map<String,Integer> headMap = new HashMap<>();
    private int chatNum = 0;

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
        this.dataSource = dataSource;
        this.context = context;
        inflater = LayoutInflater.from(context);

        headMap.put(context.getString(R.string.newMessage),R.drawable.icon_haoyou);
        headMap.put(context.getString(R.string.shangWu),R.drawable.icon_shangwu);
        headMap.put(context.getString(R.string.zhiDi),R.drawable.icon_zhidi);
        headMap.put(context.getString(R.string.gongSi),R.drawable.icon_gongsi);
        headMap.put(context.getString(R.string.qiYe),R.drawable.icon_qiye);
        headMap.put(context.getString(R.string.haoYou),R.drawable.icon_haoyou);
        headMap.put(context.getString(R.string.changYong),R.drawable.icon_changyong);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder holder = null;

        switch (viewType){
            case TYPE_MAIN:
                view = inflater.inflate(R.layout.cell_messege_common_list, parent, false);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick((int)v.getTag());
                    }
                });
                holder = new MainViewHolder(view);
                break;
            case TYPE_SUB:
                view = inflater.inflate(R.layout.cell_swipe_delete_item_2, parent, false);
                holder = new SubViewHolder(view);
                break;
        }



        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        final MessegeMainEntity.DataEntity entity = dataSource.get(position);

        if (holder instanceof MainViewHolder) {
            holder.itemView.setTag(position);
            ((MainViewHolder)holder).mHeader.setImageResource(headMap.get(entity.getTitle()));
            ((MainViewHolder)holder).mtitle.setText(entity.getTitle());
            if(entity.getSubTitle()!=null){
                ((MainViewHolder)holder).mSubTitle.setVisibility(View.VISIBLE);
                ((MainViewHolder)holder).mSubTitle.setText(entity.getSubTitle());
            } else{
                ((MainViewHolder)holder).mSubTitle.setVisibility(View.GONE);
            }
            if(entity.getDate()!=null){
                ((MainViewHolder)holder).mRightText.setVisibility(View.VISIBLE);
                ((MainViewHolder)holder).mRightText.setText(entity.getDate());
            } else{
                ((MainViewHolder)holder).mRightText.setVisibility(View.GONE);
            }
            if(entity.getTitle().equals(context.getString(R.string.changYong))){

                ((MainViewHolder)holder).mRightImg.setVisibility(View.VISIBLE);
                if(entity.isExpanded())
                    ((MainViewHolder)holder).mRightImg.setImageResource(R.drawable.icon_down);
                else
                    ((MainViewHolder)holder).mRightImg.setImageResource(R.drawable.icon_right_more);
            }else{
                ((MainViewHolder)holder).mRightImg.setVisibility(View.GONE);
            }

            if(entity.getId() == -100 && chatNum!=0){
                ((MainViewHolder)holder).mChatNum.setVisibility(View.VISIBLE);
                ((MainViewHolder)holder).mChatNum.setText(String.valueOf(chatNum));
            }else{
                ((MainViewHolder)holder).mChatNum.setVisibility(View.GONE);
            }
            if(position == dataSource.size()-1){
                ((MainViewHolder)holder).mSperate.setVisibility(View.GONE);
            }
            else{
                ((MainViewHolder)holder).mSperate.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)((MainViewHolder)holder).mSperate.getLayoutParams();
                if(dataSource.get(position+1).getType() == 1){
                    params.setMargins(0,0,0,0);
                }else{
                    params.setMargins((int)DimenUtil.dp2px(65),0,0,0);
                }
                ((MainViewHolder)holder).mSperate.setLayoutParams(params);

            }

        } else if (holder instanceof SubViewHolder){
            ((SubViewHolder)holder).mHeader.setBackgroundResource(DrawableUtils.getRandomBackgroundResource(entity.getTitle()));
            ((SubViewHolder)holder).mHeader.setText(entity.getTitle().substring(0,1));
            ((SubViewHolder)holder).mtitle.setText(entity.getTitle());
            ((SubViewHolder)holder).mSubTitle.setText(entity.getSubTitle());
            ((SubViewHolder)holder).mContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(position);
                }
            });

            if(position == dataSource.size()-1)
                ((SubViewHolder)holder).mSperate.setVisibility(View.GONE);
            else
                ((SubViewHolder)holder).mSperate.setVisibility(View.VISIBLE);

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
        }

    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    @Override
    public int getItemViewType(int position) {

        int type = dataSource.get(position).getType();
       switch (type){
           case 0:
               return TYPE_MAIN;
           case 1:
               return TYPE_SUB;
           default:
               return TYPE_MAIN;
       }
    }

    class MainViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_header)
        ImageView mHeader;

        @BindView(R.id.tv_line1)
        TextView mtitle;

        @BindView(R.id.tv_line2)
        TextView mSubTitle;

        @BindView(R.id.tv_right)
        TextView mRightText;

        @BindView(R.id.iv_right)
        ImageView mRightImg;

        @BindView(R.id.tv_chat_num)
        TextView mChatNum;

        @BindView(R.id.sperate)
        View mSperate;

        public MainViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
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


        public SubViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
