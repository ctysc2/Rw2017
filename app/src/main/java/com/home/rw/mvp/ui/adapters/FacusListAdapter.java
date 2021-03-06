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
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.entity.FacusListEntity;
import com.home.rw.mvp.ui.adapters.base.BaseRecyclerViewAdapter;
import com.home.rw.utils.FrescoUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2017/1/8.
 */

public class FacusListAdapter extends BaseRecyclerViewAdapter<FacusListEntity.DataEntity.ResLst> {

    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mListener;
    private final int COMPRESS_WIDTH = 200;
    private final int COMPRESS_HEIGH = 200;

    public FacusListAdapter(ArrayList<FacusListEntity.DataEntity.ResLst> dataSource, Context context){
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
                view = inflater.inflate(R.layout.cell_facus_list, parent, false);
                holder = new FacusHolder(view);

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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,  int position) {

        if (holder instanceof FacusHolder) {
            if(mIsShowHeader)
                position = position -1;

            final int mPosition = position;
            final FacusHolder mHolder = (FacusHolder)holder;

            FacusListEntity.DataEntity.ResLst entity = dataSource.get(mPosition);
            holder.itemView.setTag(position);
            mHolder.mHeader.setImageURI(entity.getAvatar());
            mHolder.mName.setText(entity.getRealname());
            mHolder.mNum.setText(""+entity.getPubNum());
        }

    }



    class FacusHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_header)
        SimpleDraweeView mHeader;

        @BindView(R.id.tv_name)
        TextView mName;

        @BindView(R.id.tv_num)
        TextView mNum;

        public FacusHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
