package com.home.rw.mvp.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
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
import com.home.rw.mvp.entity.CompanyNoticeEntity;
import com.home.rw.mvp.entity.FacusListEntity;
import com.home.rw.mvp.ui.adapters.base.BaseRecyclerViewAdapter;
import com.home.rw.utils.FrescoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2017/1/18.
 */

public class CompanyNoticeListAdapter extends BaseRecyclerViewAdapter<CompanyNoticeEntity.DataEntity> {
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mListener;
    private OnItemClickListener mDelete;
    public CompanyNoticeListAdapter(ArrayList<CompanyNoticeEntity.DataEntity> dataSource, Context context) {
        super(dataSource);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    //设置item监听事件
    public void setOnItemClickListener(OnItemClickListener mListener){

        this.mListener = mListener;
    }

    //设置删除监听事件
    public void setOnItemDeleteListener(OnItemClickListener mListener){

        this.mDelete = mListener;
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
                view = inflater.inflate(R.layout.cell_swipe_delete_item_1, parent, false);
                holder = new CompanyNoticeViewHolder(view);

                break;
            default:
                break;
        }

        return holder;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,  int position) {

        if (holder instanceof CompanyNoticeViewHolder) {
            if(mIsShowHeader)
                position = position -1;

            final int mPosition = position;
            final CompanyNoticeViewHolder mHolder = (CompanyNoticeViewHolder)holder;

            CompanyNoticeEntity.DataEntity entity = dataSource.get(mPosition);
            holder.itemView.setTag(position);
            mHolder.mTitle.setText(entity.getTitle());
            mHolder.mDate.setText(entity.getDate());
            mHolder.rl_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(mPosition);
                }
            });
            mHolder.rl_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDelete.onItemClick(mPosition);
                }
            });
            if(mPosition == dataSource.size()-1)
                mHolder.mSperate.setVisibility(View.GONE);
            else
                mHolder.mSperate.setVisibility(View.VISIBLE);
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

    class CompanyNoticeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_line1)
        TextView mTitle;

        @BindView(R.id.tv_line2)
        TextView mDate;

        @BindView(R.id.rl_del)
        RelativeLayout rl_del;

        @BindView(R.id.rl_continer)
        RelativeLayout rl_container;

        @BindView(R.id.sperate)
        View mSperate;

        public CompanyNoticeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
