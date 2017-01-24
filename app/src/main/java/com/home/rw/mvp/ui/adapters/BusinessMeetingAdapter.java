package com.home.rw.mvp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.BusinessMeetingPhoneEntity;
import com.home.rw.mvp.ui.adapters.base.BaseRecyclerViewAdapter;
import com.home.rw.utils.DrawableUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2017/1/23.
 */

public class BusinessMeetingAdapter extends BaseRecyclerViewAdapter<BusinessMeetingPhoneEntity.DataEntity> {

    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mListener;

    public BusinessMeetingAdapter(List<BusinessMeetingPhoneEntity.DataEntity> dataSource,Context context) {
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
                view = inflater.inflate(R.layout.layout_footer_loadmore2, parent, false);
                holder = new FooterViewHolder(view);
                break;
            case TYPE_HEADER:
                holder = new HeaderViewHolder(mHeaderView);
                break;
            case TYPE_ITEM:
                view = inflater.inflate(R.layout.cell_business_meeting, parent, false);
                holder = new BusinessViewHolder(view);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof BusinessViewHolder){

            if(mIsShowHeader)
                position = position -1;

             final int mPosition = position;

            holder.itemView.setTag(mPosition);

            BusinessMeetingPhoneEntity.DataEntity entity = dataSource.get(mPosition);

            ((BusinessViewHolder) holder).mTitle.setText(entity.getTitle());

            ((BusinessViewHolder) holder).mSubTitle.setText(entity.getSubTitle());

            ((BusinessViewHolder) holder).mDate.setText(entity.getDate());

            if(entity.getAvatar()!=null){
                ((BusinessViewHolder) holder).mHeaderText.setVisibility(View.INVISIBLE);
                ((BusinessViewHolder) holder).mHeader.setVisibility(View.VISIBLE);
                ((BusinessViewHolder) holder).mHeader.setImageURI(entity.getAvatar());
            }else{
                ((BusinessViewHolder) holder).mHeaderText.setVisibility(View.VISIBLE);
                ((BusinessViewHolder) holder).mHeader.setVisibility(View.INVISIBLE);
                ((BusinessViewHolder) holder).mHeaderText.setText(entity.getTitle().substring(0,1));
                ((BusinessViewHolder) holder).mHeaderText.setBackgroundResource(DrawableUtils.getRandomBackgroundResource());
            }

            if(mPosition == dataSource.size()-1)
                ((BusinessViewHolder) holder).mSperate.setVisibility(View.INVISIBLE);
            else
                ((BusinessViewHolder) holder).mSperate.setVisibility(View.VISIBLE);

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

    class BusinessViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_header)
        SimpleDraweeView mHeader;

        @BindView(R.id.tv_header)
        TextView mHeaderText;

        @BindView(R.id.tv_line1)
        TextView mTitle;

        @BindView(R.id.tv_line2)
        TextView mSubTitle;

        @BindView(R.id.tv_right)
        TextView mDate;

        @BindView(R.id.sperate)
        View mSperate;

        public BusinessViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
