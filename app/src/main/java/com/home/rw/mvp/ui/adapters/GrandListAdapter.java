package com.home.rw.mvp.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.entity.GrandEntity;
import com.home.rw.mvp.entity.MessegeMainEntity;
import com.home.rw.mvp.entity.message.TopicCommonEntity;
import com.home.rw.mvp.ui.adapters.base.BaseRecyclerViewAdapter;
import com.home.rw.utils.DateUtils;
import com.home.rw.utils.DimenUtil;
import com.home.rw.utils.DrawableUtils;
import com.home.rw.utils.FrescoUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2017/1/18.
 */

public class GrandListAdapter extends BaseRecyclerViewAdapter<TopicCommonEntity> {

    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mListener;

    public GrandListAdapter(ArrayList<TopicCommonEntity> dataSource, Context context) {
        super(dataSource);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    //设置item监听事件
    public void setOnItemClickListener(OnItemClickListener mListener){

        this.mListener = mListener;
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
                view = inflater.inflate(R.layout.cell_grand, parent, false);
                holder = new GrandViewHolder(view);
                break;
            default:
                break;
        }

        return holder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof GrandViewHolder) {

            if(mIsShowHeader)
                position = position -1;

            final int mPosition = position;

            final GrandViewHolder mHolder = (GrandViewHolder)holder;

            final TopicCommonEntity entity = dataSource.get(mPosition);

            mHolder.mTitle.setText(entity.getTitle());

            if(!TextUtils.isEmpty(entity.getPubTime())){
                mHolder.mTopTime.setText(DateUtils.getRw(new Date(Long.parseLong(entity.getPubTime()))));
                mHolder.mDate.setText(DateUtils.getTime(new Date(Long.parseLong(entity.getPubTime()))));
            }else{
                mHolder.mTopTime.setText("");
                mHolder.mDate.setText("");
            }
            if(entity.getImgs()!=null)
                FrescoUtils.load(Uri.parse(entity.getImgs()),mHolder.mImg,500,300);
            mHolder.mContent.setText(entity.getContent());
            mHolder.mBottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(mPosition);
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
    class GrandViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.top_time)
        TextView mTopTime;

        @BindView(R.id.tv_title)
        TextView mTitle;

        @BindView(R.id.tv_date)
        TextView mDate;

        @BindView(R.id.iv_img)
        SimpleDraweeView mImg;

        @BindView(R.id.tv_content)
        TextView mContent;

        @BindView(R.id.rl_bottom)
        RelativeLayout mBottom;

        public GrandViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
