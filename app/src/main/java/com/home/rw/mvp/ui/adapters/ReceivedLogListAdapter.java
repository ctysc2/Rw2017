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
import com.home.rw.mvp.entity.LogEntity;
import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.ui.adapters.base.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2016/12/20.
 */

public class ReceivedLogListAdapter extends BaseRecyclerViewAdapter<LogEntity.DataEntity.ResLst> {
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mListener;

    public ReceivedLogListAdapter(ArrayList<LogEntity.DataEntity.ResLst> dataSource, Context context){
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
                view = inflater.inflate(R.layout.layout_footer_load_more, parent, false);
                holder = new FooterViewHolder(view);
                break;
            case TYPE_HEADER:
                holder = new HeaderViewHolder(mHeaderView);
                break;
            case TYPE_ITEM:
                view = inflater.inflate(R.layout.cell_log, parent, false);
                holder = new LogViewHolder(view);

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
    public int getItemViewType(int position) {
        if (mIsShowFooter && isFooterPosition(position)) {
            return TYPE_FOOTER;
        } else if (mIsShowHeader && isHeaderPosition(position)) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }
    //设置item监听事件
    public void setOnItemClickListener(OnItemClickListener mListener){

        this.mListener = mListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof LogViewHolder){

            if(mIsShowHeader)
                position = position -1;

            final int mPosition = position;
            LogViewHolder mHolder = (LogViewHolder)holder;
            LogEntity.DataEntity.ResLst entity = dataSource.get(mPosition);
            mHolder.itemView.setTag(mPosition);
            mHolder.mHeader.setImageURI(entity.getAvatar());
            mHolder.mName.setText(entity.getAuthor());
            mHolder.mDate.setText(entity.getCreatedDate());
            mHolder.mContent.setText(entity.getContent());

        }


    }


    class LogViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_header)
        SimpleDraweeView mHeader;

        @BindView(R.id.tv_name)
        TextView mName;

        @BindView(R.id.tv_date)
        TextView mDate;

        @BindView(R.id.tv_content)
        TextView mContent;


        public LogViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
